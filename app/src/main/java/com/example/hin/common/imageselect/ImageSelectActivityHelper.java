/**
 *
 */
package com.example.hin.common.imageselect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hin.Consts.Config;
import com.example.hin.common.select.CommonAdapter;
import com.example.hin.common.select.ImageCaptureItem;
import com.example.hin.common.select.ImageCrop;
import com.example.hin.common.select.ImageSelectHandler;
import com.example.hin.common.select.ImageSelectViewHelper;
import com.example.hin.common.select.SingleSelectImageItem;
import com.example.hin.common.select.ViewHolder;
import com.example.hin.common.select.interfaces.ImageSelectHook;
import com.example.hin.common.select.interfaces.LeadItem;
import com.example.hin.system.R;
import com.example.hin.utils.CommonUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author yanry
 *         <p>
 *         2016年5月4日
 */
public abstract class ImageSelectActivityHelper implements OnClickListener, OnItemClickListener {

    private Activity activity;
    private ListPopupWindow folderPop;
    private File currentFolder;
    private List<File> folderList;
    private ImageSelectViewHelper igv;
    private MultiSelectItem msi;
    private TextView tvCommit;
    private TextView tvSelectFolder;
    private ImageSelectHandler handler;

    public ImageSelectActivityHelper(Activity activity, ImageSelectHandler handler) {
        this.activity = activity;
        tvSelectFolder = (TextView) activity.findViewById(R.id.category_btn);
        tvSelectFolder.setOnClickListener(this);
        tvCommit = (TextView) activity.findViewById(R.id.commit);
        tvCommit.setOnClickListener(this);
        setImageSelectHandler(handler);
    }

    public Activity getActivity() {
        return activity;
    }

    public void commit() {
        handler.getHook().onSelectImages(handler.getSelectedImages());
        onCommit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        igv.onActivityResult(requestCode, resultCode, data);
    }

    private void dispatchSelectionChangeEvent() {
        tvCommit.setText(String.format("完成(%s/%s)", handler.getSelectedImages().size(), msi.getNumLimit()));
        onSelectionChange();
    }

    private void setImageSelectHandler(ImageSelectHandler handler) {
        this.handler = handler;
        msi = new MultiSelectItem(handler.getHook().getImageSelectLimit()) {

            @Override
            protected void onSelectedNumberChange() {
                dispatchSelectionChangeEvent();
            }

            @Override
            protected ImageSelectHandler getImageSelectHandler() {
                return ImageSelectActivityHelper.this.handler;
            }
        };
        GridView gridView = (GridView) activity.findViewById(R.id.grid);
        final ImageSelectHook hook = handler.getHook();
        SingleSelectImageItem ssi = new SingleSelectImageItem() {

            @Override
            protected ImageSelectHandler getImageSelectHandler() {
                return ImageSelectActivityHelper.this.handler;
            }

            @Override
            public void onResult(File output) {
                hook.onSelectImage(output);
                activity.finish();
            }

            @Override
            protected int getCropImageRequestCode() {
                return Config.REQUEST_CODE_IMAGE_CROP;
            }

            @Override
            protected File getTempFolder() {
                return getImageSelectTempFile();
            }

            @Override
            public int getItemViewId() {
                return R.layout.imageselect_item_image_single;
            }

            @Override
            public void display(ViewHolder holder, File imageFile, int viewWidth, boolean isClick) {
                ((SimpleDraweeView) holder.getView(R.id.image)).setController(Fresco.newDraweeControllerBuilder()
                        .setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(imageFile))
                                .setResizeOptions(new ResizeOptions(150, 150)).build()).build());
            }

        };
        igv = new ImageSelectViewHelper(handler, gridView, 90, hook.isMultiSelect() ? msi : ssi, getLeadItems()) {
            @Override
            protected void onEvaluateItemWidth(int width) {
                ImageSelectActivityHelper.this.onEvaluateItemWidth(width);
            }

            @Override
            protected void onPermissionDenied() {
                Toast.makeText(activity, "未能获取读取外部存储权限", Toast.LENGTH_SHORT).show();
            }
        };
        final TextView tvTime = (TextView) activity.findViewById(R.id.timeline_area);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView absListView, int state) {
                if (state == SCROLL_STATE_IDLE) {
                    Fresco.getImagePipeline().resume();
                    // 停止滑动，日期指示器消失
                    tvTime.setVisibility(View.GONE);
                } else if (state == SCROLL_STATE_FLING) {
                    Fresco.getImagePipeline().pause();
                    tvTime.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (tvTime.getVisibility() == View.VISIBLE) {
                    int index = firstVisibleItem + 1 == view.getAdapter().getCount() ? view.getAdapter().getCount() - 1
                            : firstVisibleItem + 1;
                    File image = ImageSelectActivityHelper.this.handler.getAlbumImages(currentFolder).get(index);
                    tvTime.setText(String.format("%tF", image.lastModified()));
                }
            }
        });
    }

    private File getImageSelectTempFile() {
        return new File(CommonUtils.getDiskCacheDir(activity), getImageTempFolderName());
    }

    private ImageCrop getCrop(final ImageSelectHook hook, File src, File dest) {
        return hook.customizeImageCrop(new ImageCrop(activity, src, dest) {

            @Override
            protected int getRequestCode() {
                return getCropImageRequestCode();
            }
        });
    }

    protected void onEvaluateItemWidth(int width) {

    }

    protected void onSelectionChange() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.category_btn) {
            if (folderPop == null) {
                folderPop = new ListPopupWindow(activity);
                int width = v.getResources().getDisplayMetrics().widthPixels;
                folderPop.setContentWidth(width);
                folderPop.setWidth(width);
                folderPop.setHeight(v.getResources().getDisplayMetrics().heightPixels * 5 / 8);
                folderPop.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                folderPop.setAnchorView(v);
                folderPop.setModal(true);
                folderPop.setOnItemClickListener(this);
                Set<File> folders = handler.getAlbumFolders();
                if (folders != null) {
                    folderList = new ArrayList<File>(folders);
                } else {
                    folderList = new ArrayList<File>();
                }
                folderPop.setAdapter(new FolderAdapter());
            }
            if (folderPop.isShowing()) {
                folderPop.dismiss();
            } else {
                folderPop.show();
            }
        } else if (id == R.id.commit) {
            commit();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // click folder item
        TextView tvTitle = (TextView) activity.findViewById(R.id.tv_title);
        if (position == 0) {
            currentFolder = null;
            tvTitle.setText("所有图片");
        } else {
            currentFolder = folderList.get(position - 1);
            tvTitle.setText(currentFolder.getName());
        }
        folderPop.dismiss();
        igv.setFolder(currentFolder);
    }

    protected abstract LeadItem[] getLeadItems();

    protected abstract int getCropImageRequestCode();

    protected abstract int getCaptureImageRequestCode();

    protected abstract String getImageTempFolderName();

    protected abstract void onCommit();

    private class FolderAdapter extends CommonAdapter {
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (position == 0) {
                return this;
            } else {
                return folderList.get(position - 1);
            }
        }

        @Override
        public int getCount() {
            if (folderList != null && handler.getAlbumImages(null) != null) {
                return folderList.size() + (handler.getAlbumImages(null).size() > 0 ? 1 : 0);
            }
            return 0;
        }

        @Override
        public int getItemViewId(int viewType) {
            return R.layout.imageselect_item_folder;
        }

        @Override
        public View getItemView(int viewType) {
            return null;
        }

        @Override
        protected void display(ViewHolder holder, int position) {
            File folder;
            if (position == 0) {
                folder = null;
                holder.getTextView(R.id.name).setText("所有图片");
            } else {
                folder = folderList.get(position - 1);
                holder.getTextView(R.id.name).setText(folder.getName());
            }
            List<File> albumImages = handler.getAlbumImages(folder);
            ((SimpleDraweeView) holder.getView(R.id.cover)).setController(Fresco.newDraweeControllerBuilder()
                    .setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(albumImages.get(0)))
                            .setResizeOptions(new ResizeOptions(150, 150)).build()).build());
            holder.getTextView(R.id.size).setText(albumImages.size() + "张");
            if (folder == null && currentFolder == null || folder != null && folder.equals(currentFolder)) {
                holder.getView(R.id.indicator).setVisibility(View.VISIBLE);
            } else {
                holder.getView(R.id.indicator).setVisibility(View.GONE);
            }
        }
    }

    public abstract class StandardImageCaptureItem extends ImageCaptureItem {
        @Override
        public void onResult(File output) {
            ImageSelectHook hook = handler.getHook();
            if (hook.isMultiSelect()) {
                handler.getSelectedImages().add(output);
                dispatchSelectionChangeEvent();
                hook.onSelectImages(handler.getSelectedImages());
            } else {
                hook.onSelectImage(output);
            }
            commit();
        }

        public void display(ViewHolder holder, boolean isClick) {
            holder.getConvertView().setEnabled(isClickable());
        }

        @Override
        public boolean isClickable() {
            return handler.getSelectedImages().size() < msi.getNumLimit();
        }

        @Override
        protected int getRequestCode() {
            return getCaptureImageRequestCode();
        }

        @Override
        protected File getTempFolder() {
            return getImageSelectTempFile();
        }

        @Override
        protected int getCropImageRequestCode() {
            return Config.REQUEST_CODE_IMAGE_CROP;
        }

        @Override
        protected ImageSelectHandler getImageSelectHandler() {
            return ImageSelectActivityHelper.this.handler;
        }

        @Override
        protected ImageSelectViewHelper getImageSelectViewHelper() {
            return igv;
        }
    }

}