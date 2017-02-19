/**
 *
 */
package com.example.hin.common;

import android.app.Activity;

import com.example.hin.Consts.Config;
import com.example.hin.common.imageselect.ImageSelectActivityHelper;
import com.example.hin.common.select.interfaces.LeadItem;
import com.example.hin.system.R;

/**
 * @author yanry
 *         <p>
 *         2016年5月4日
 */
public abstract class LHImageSelectActivityHelper extends ImageSelectActivityHelper {

    public LHImageSelectActivityHelper(Activity activity) {
        super(activity, LHImageSelectHandler.get());
    }

    @Override
    protected LeadItem[] getLeadItems() {
        return new LeadItem[]{new StandardImageCaptureItem() {

            @Override
            public int getViewId() {
                return R.layout.item_imageselect_camera;
            }
        }};
    }

    @Override
    protected int getCropImageRequestCode() {
        return Config.REQUEST_CODE_IMAGE_CROP;
    }

    @Override
    protected int getCaptureImageRequestCode() {
        return Config.REQUEST_CODE_IMAGE_CAPTURE;
    }

    @Override
    protected String getImageTempFolderName() {
        return "ImageTemp";
    }

}
