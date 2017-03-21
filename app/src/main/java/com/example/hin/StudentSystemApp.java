package com.example.hin;

import android.support.multidex.MultiDexApplication;

import com.example.hin.common.LHImageSelectHandler;
import com.example.hin.common.UserPref;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by WWF on 2017/2/19.
 */

public class StudentSystemApp extends MultiDexApplication {

    private static StudentSystemApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        UserPref.get().init(StudentSystemApp.getApp());
        Fresco.initialize(this);
        LHImageSelectHandler.get().initAlbum(this, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".jpg") || pathname.getName().endsWith(".jpeg")
                        || pathname.getName().endsWith(".png");
            }
        });
    }

    public static StudentSystemApp getApp() {
        return app;
    }
}
