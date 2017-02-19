/**
 *
 */
package com.example.hin.common.select;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.example.hin.common.select.interfaces.OnActivityResultListener;

import java.io.File;
import java.util.List;

/**
 * @author yanry
 *         <p>
 *         2016年1月16日
 */
public abstract class ImageCaptureHandler extends AbstractCropHelper implements OnActivityResultListener, ImageCrop.OnCropResultListener {
    private File captureOut;
    private File cropOut;
    private ImageCrop crop;
    private Activity ctx;

    /**
     * @param context don't forget to call
     *                {@link #onActivityResult(int, int, Intent)} on this activity.
     */
    public void start(Activity context) {
        captureOut = createTempFile();
        ctx = context;
        requestCamera(ctx, captureOut, getRequestCode());
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getRequestCode()) {
            if (resultCode == Activity.RESULT_OK) {
                cropOut = createTempFile();
                crop = getImageCrop(ctx, captureOut, cropOut);
                if (crop == null) {
                    onResult(captureOut);
                } else {
                    crop.execute(ctx);
                }
            } else {
                if (captureOut.isFile()) {
                    captureOut.delete();
                }
            }
            return true;
        }
        if (crop != null) {
            return crop.onActivityResult(requestCode, resultCode, data, this);
        }
        return false;
    }

    @Override
    public void onCropResult(Bitmap bmp) {
        if (captureOut.isFile()) {
            captureOut.delete();
        }
        onResult(cropOut);
    }

    protected abstract int getRequestCode();

    protected abstract void onResult(File file);

    public boolean requestCamera(Activity ctx, File out, int requestCode) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(ctx.getPackageManager()) != null) {
            if (!out.getParentFile().exists()) {
                out.getParentFile().mkdirs();
            }
            Uri outUri = FileProvider.getUriForFile(ctx, "com.gzlc.android.commonlib.fileprovider", out);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
            List<ResolveInfo> resolveInfoList = ctx.getPackageManager().queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resolveInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                ctx.grantUriPermission(packageName, outUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            ctx.startActivityForResult(cameraIntent, requestCode);
            return true;
        }
        Log.e(ImageCaptureHandler.class.getSimpleName(), "no system camera found.");
        return false;
    }
}
