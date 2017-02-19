/**
 * 
 */
package com.example.hin.common.select;

import android.content.Context;

import java.io.File;

/**
 * @author yanry
 *
 *         2016年7月13日
 */
abstract class AbstractCropHelper {
	/**
	 * 
	 * @param src
	 *            file to crop.
	 * @param dest
	 *            the crop output file.
	 * @return return an {@link ImageCrop} object used to crop image, or null if
	 *         you don't need to crop image.
	 */
	ImageCrop getImageCrop(Context context, File src, File dest) {
		if (getCropImageRequestCode() > 0 && src != null && dest != null) {
			return customizeImageCrop(new ImageCrop(context, src, dest) {

				@Override
				protected int getRequestCode() {
					return getCropImageRequestCode();
				}
			});
		} else {
			return null;
		}
	}

	File createTempFile() {
		File dir = getTempFolder();
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		return new File(dir, System.currentTimeMillis() + ".jpg");
	}

	/**
	 * 
	 * @return the returned value must be >=0, other wise image crop is disable.
	 */
	protected abstract int getCropImageRequestCode();

	protected abstract ImageCrop customizeImageCrop(ImageCrop rawCrop);

	protected abstract File getTempFolder();
}
