/**
 *
 */
package com.example.hin.common.imageselect;

import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.hin.common.select.MultiSelectImageItem;
import com.example.hin.common.select.ViewHolder;
import com.example.hin.system.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

/**
 * @author yanry
 *         <p>
 *         2016年3月3日
 */
public abstract class MultiSelectItem extends MultiSelectImageItem {

    private Animation anim;

    public MultiSelectItem(int numLimit) {
        super(numLimit);
    }

    @Override
    public int getItemViewId() {
        return R.layout.imageselect_item_image_multi;
    }

    @Override
    public void display(ViewHolder holder, File imageFile, int viewWidth, boolean isClick) {
        int selectIndex = getImageSelectHandler().getSelectedImages().indexOf(imageFile);
        if (-1 != selectIndex) {
            holder.getTextView(R.id.image_check_view).setVisibility(View.VISIBLE);
            holder.getTextView(R.id.image_check_view).setText((selectIndex + 1) + "");
            holder.getView(R.id.image_check_mask).setVisibility(View.VISIBLE);
            holder.getView(R.id.image_check_mask).setBackgroundResource(R.drawable.imageselect_check_mask);
            holder.getView(R.id.image_mask).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.image_check_mask).setVisibility(View.GONE);
            holder.getTextView(R.id.image_check_view).setVisibility(View.GONE);
            boolean full = getNumLimit() == getImageSelectHandler().getSelectedImages().size();
            holder.getView(R.id.image_mask).setVisibility(full ? View.VISIBLE : View.GONE);
        }
        if (isClick) {
            holder.getConvertView().clearAnimation();
            if (anim == null) {
                anim = AnimationUtils.loadAnimation(holder.getConvertView().getContext(), R.anim.imageselect_check);
            }
            holder.getConvertView().startAnimation(anim);
        }
        ((SimpleDraweeView) holder.getView(R.id.image)).setController(Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(imageFile))
                        .setResizeOptions(new ResizeOptions(150, 150)).build()).build());

    }

}