/**
 * 
 */
package com.example.hin.common.select;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.example.hin.common.select.interfaces.ImageItem;
import com.example.hin.utils.CommonUtils;

import java.io.File;

/**
 * @author yanry
 *
 *         2016年1月21日
 */
public abstract class SingleSelectImageItem extends AbstractCropHelper implements ImageItem, ImageCrop.OnCropResultListener {
	private ImageCrop crop;
	private File cropFile;
	private Activity activity;

	/**
	 * Close current activity after selection is done, you can optionally
	 * override this method.
	 * 
	 * @param file
	 */
	protected void onResult(File file) {
		getImageSelectHandler().getHook().onSelectImage(file);
		if (activity != null) {
			activity.finish();
		}
	}

	protected abstract ImageSelectHandler getImageSelectHandler();

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (crop != null) {
			return crop.onActivityResult(requestCode, resultCode, data, this);
		}
		return false;
	}

	@Override
	public boolean onClick(View itemView, File imageFile) {
		cropFile = createTempFile();
		crop = getImageCrop(itemView.getContext(), imageFile, cropFile);
		activity = CommonUtils.getActivity(itemView.getContext());
		if (crop == null) {
			onResult(imageFile);
		} else {
			if (activity != null) {
				crop.execute(activity);
			}
		}
		return false;
	}

	@Override
	public void onCropResult(Bitmap bmp) {
		onResult(cropFile);
	}

	@Override
	protected ImageCrop customizeImageCrop(ImageCrop rawCrop) {
		return getImageSelectHandler().getHook().customizeImageCrop(rawCrop);
	}

}
