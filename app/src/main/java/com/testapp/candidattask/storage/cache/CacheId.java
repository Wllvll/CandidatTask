package com.testapp.candidattask.storage.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.testapp.candidattask.BuildConfig;

public class CacheId {
    private SharedPreferences a;
    private Editor b;

    public CacheId(Context context) {
        this.a = context.getSharedPreferences("cache", 0);
    }

    public String getString(String str) {
        return this.a.getString(str, BuildConfig.FLAVOR);
    }

    public void setString(String str, String str2) {
        this.b = this.a.edit();
        this.b.putString(str, str2);
        this.b.apply();
    }

    public void clear() {
        this.a.edit().clear().apply();
    }

    public void remove(String str) {
        this.a.edit().remove(str).apply();
    }
}
