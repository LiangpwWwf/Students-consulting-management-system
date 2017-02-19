/**
 * 
 */
package com.example.hin.common.select.interfaces;

import android.view.View;

import com.example.hin.common.select.ViewHolder;

/**
 * @author yanry
 *
 * 2016年1月16日
 */
public interface LeadItem extends OnActivityResultListener {
	
	int getViewId();
	
	boolean isClickable();
	
	/**
	 * 
	 * @param view
	 * @return return true to refresh the hosted adapter view.
	 */
	boolean onClick(View view);
	
	void display(ViewHolder holder, boolean isClick);
}
