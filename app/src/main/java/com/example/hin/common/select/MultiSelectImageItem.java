/**
 * 
 */
package com.example.hin.common.select;

import android.content.Intent;
import android.view.View;

import com.example.hin.common.select.interfaces.ImageItem;

import java.io.File;
import java.util.List;

/**
 * @author yanry
 *
 * 2016年6月7日
 */
public abstract class MultiSelectImageItem implements ImageItem {
	private int numLimit;

	public MultiSelectImageItem(int numLimit) {
		this.numLimit = numLimit;
	}


	public int getNumLimit() {
		return numLimit;
	}

	@Override
	public boolean onClick(View itemView, File imageFile) {
		List<File> selected = getImageSelectHandler().getSelectedImages();
		if (selected.size() == numLimit && !selected.contains(imageFile)) {
			// selected number has reached the limit.
			return false;
		}
		if (!selected.remove(imageFile)) {
			selected.add(imageFile);
		}
		onSelectedNumberChange();
		return true;
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		return false;
	}

	protected abstract ImageSelectHandler getImageSelectHandler();
	
	protected abstract void onSelectedNumberChange();
}
