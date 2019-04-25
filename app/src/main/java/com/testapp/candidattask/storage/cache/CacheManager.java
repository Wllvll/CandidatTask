package com.testapp.candidattask.storage.cache;

import android.content.Context;
import java.io.File;

public class CacheManager {

    private File cacheDir;

    public CacheManager(Context context) {

        // Find the dir to save cached images
        cacheDir = context.getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }
}