/**
 * 
 */
package com.example.hin.common.select.interfaces;

import android.view.View;

import com.example.hin.common.select.ViewHolder;

import java.io.File;

/**
 * @author yanry
 *
 *         2016年1月16日
 */
public interface ImageItem extends OnActivityResultListener {

	int getItemViewId();

	void display(ViewHolder holder, File imageFile, int viewWidth, boolean isClick);

	/**
	 * @param itemView
	 * @param imageFile
	 * @return return true to refresh the hosted adapter view.
	 */
	boolean onClick(View itemView, File imageFile);
}
