package com.example.hin.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hin.entity.Post;

/**
 * Created by WWF on 2017/3/26.
 */

public class DraftPref {

    public static volatile DraftPref instance = null;
    public SharedPreferences prefer;

    public static String KEY_HAS_DRAFT = "draft";
    public static String KEY_TITLE = "title";
    public static String KEY_KIND = "kind";
    public static String KEY_TOPIC = "topic";
    public static String KEY_EXPERT = "expert";
    public static String KEY_CONTENT = "content";
    public static String KEY_FILE = "file";
    public static String KEY_IMPORT = "import";
    public static String KEY_FACE = "face";
    public static String KEY_OPEN = "open";
    public static String KEY_NONAME = "noname";
    public static String KEY_TIME = "time";

    private DraftPref() {
    }

    public synchronized static DraftPref get() {
        if (instance == null) {
            synchronized (DraftPref.class) {
                if (instance == null) {
                    instance = new DraftPref();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        prefer = context.getApplicationContext().getSharedPreferences("draft", Context.MODE_PRIVATE);
    }

    public boolean syncDraft(Post post, String path) {
        if (prefer != null) {
            clearInfo();
            if (post != null) {
                return prefer.edit()
                        .putBoolean(KEY_HAS_DRAFT, true)
                        .putString(KEY_TITLE, post.getTitle())
                        .putString(KEY_KIND, post.getKind())
                        .putString(KEY_TOPIC, post.getTopic())
                        .putString(KEY_EXPERT, post.getExpert())
                        .putString(KEY_CONTENT, post.getContent())
                        .putString(KEY_FILE, path)
                        .putInt(KEY_IMPORT, post.getExigency())
                        .putBoolean(KEY_FACE, post.getInterview())
                        .putBoolean(KEY_OPEN, post.getOpen())
                        .putBoolean(KEY_NONAME, post.getAnonymity())
                        .putLong(KEY_TIME, System.currentTimeMillis())
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

    public boolean set(String key, boolean value) {
        if (prefer != null) {
            return prefer.edit().putBoolean(key, value).commit();
        }
        return false;
    }

    public String get(String key) {
        if (prefer != null) {
            return prefer.getString(key, "");
        }
        return null;
    }

    public Boolean getBoolean(String key) {
        if (prefer != null) {
            return prefer.getBoolean(key, false);
        }
        return false;
    }

    public Long getLong(String key) {
        if (prefer != null) {
            return prefer.getLong(key, 0L);
        }
        return 0L;
    }

    public int getInt(String key) {
        if (prefer != null) {
            return prefer.getInt(key, 0);
        }
        return 0;
    }

    public boolean clearInfo() {
        if (prefer != null) {
            return prefer.edit().clear().commit();
        }
        return false;
    }
}
