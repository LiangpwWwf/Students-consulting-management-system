package com.example.hin;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hin.common.DraftPref;
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
        DraftPref.get().init(StudentSystemApp.getApp());
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

    public static View getView(int id) {
        return getView(id, null);
    }

    public static View getView(int id, ViewGroup root) {
        LayoutInflater layoutInflater = getLayoutInflater();
        return layoutInflater.inflate(id, root);
    }

    public static LayoutInflater getLayoutInflater() {
        LayoutInflater layoutInflater = (LayoutInflater) app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater;
    }
}
