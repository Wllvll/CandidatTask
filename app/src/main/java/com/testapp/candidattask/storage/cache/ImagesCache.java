package com.testapp.candidattask.storage.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImagesCache {

    private LruCache<String, Bitmap> imagesWarehouse;
    private static ImagesCache cache;
    private int cacheSize;

    //Создаем кеш при его отсутствии
    public static ImagesCache getInstance() {
        if(cache == null) {
            cache = new ImagesCache();
        }
        return cache;
    }

    public void initializeCache(int cacheSize) {
        this.cacheSize = cacheSize;
        imagesWarehouse = new LruCache<String, Bitmap>(cacheSize/8) {
            protected int sizeOf(String key, Bitmap value) {
                // The cache size will be measured in kilobytes rather than number of items.
                int bitmapByteCount = value.getRowBytes() * value.getHeight();
                return bitmapByteCount / 1024;
            }
        };
    }

    //Добавляем изображение в кеш при его отсутствии
    public void addImageToWarehouse(String key, Bitmap value) {
        if(imagesWarehouse != null && imagesWarehouse.get(key) == null) {
            imagesWarehouse.put(key, value);
        }
    }

    //Получаем изображение из кеша по ключу
    public Bitmap getImageFromWarehouse(String key) {
        if(key != null) {
            return imagesWarehouse.get(key);
        } else {
            return null;
        }
    }

    //Для удаления изображения из кеша
    public void removeImageFromWarehouse(String key) {
        imagesWarehouse.remove(key);
    }

    //Очистить кеш
    public void clearCache() {
        if(imagesWarehouse != null) {
            imagesWarehouse.evictAll();
        }
    }
}