package com.example.hin.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hin.entity.User;

/**
 * Created by user on 2016/6/16.
 */
public class UserPref {

    public static volatile UserPref instance = null;
    public static String KEY_UID = "objectId";
    public static String KEY_USERNAME = "username";
    public static String KEY_MOBILE = "mobilePhoneNumber";
    public static String KEY_PWD = "password";
    public static String KEY_NICKNAME = "nickname";
    public static String KEY_AVATAR = "avatar";
    public static String KEY_DEPARTMENT = "department";
    public static String KEY_GRADE = "grade";
    public static String KEY_SEX = "sex";
    public static String KEY_EMAIL = "email";
    public SharedPreferences prefer;

    private UserPref() {
    }

    public synchronized static UserPref get() {
        if (instance == null) {
            synchronized (UserPref.class) {
                if (instance == null) {
                    instance = new UserPref();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        prefer = context.getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    public synchronized boolean syncProfile(User user) {
        if (prefer != null) {
            clearInfo();
            if (user != null) {
                return prefer.edit()
                        .putString(KEY_AVATAR, user.getAvatar())
                        .putString(KEY_USERNAME, user.getUsername())
                        .putString(KEY_MOBILE, user.getMobilePhoneNumber())
                        .putString(KEY_UID, user.getObjectId())
                        .putString(KEY_NICKNAME, user.getNickname())
                        .putString(KEY_DEPARTMENT, user.getDepartment())
                        .putString(KEY_GRADE, user.getGrade())
                        .putString(KEY_SEX, user.getSex())
                        .putString(KEY_EMAIL, user.getEmail())
                        .commit();
            }
        }
        return false;
    }

    public boolean set(String key, String value) {
        if (prefer != null) {
            return prefer.edit().putString(key, value).commit();
        }
        return false;
    }

    public String get(String key) {
        if (prefer != null) {
            return prefer.getString(key, "");
        }
        return null;
    }

    public boolean clearInfo() {
        if (prefer != null) {
            return prefer.edit().clear().commit();
        }
        return false;
    }
}
