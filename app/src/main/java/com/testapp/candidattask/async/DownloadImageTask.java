package com.testapp.candidattask.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.testapp.candidattask.storage.cache.ImagesCache;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private String imageUrl;
    private ImagesCache cache;
    private Bitmap image = null;

    private ImageView ivImageView;
    private ProgressBar pbar;

    public DownloadImageTask(ImagesCache cache, String imageUrl, ImageView ivImageView, ProgressBar pbar) {

        this.imageUrl = imageUrl;
        this.cache = cache;
        this.ivImageView = ivImageView;
        this.pbar = pbar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pbar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return getImage(imageUrl);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result != null) {
            //Проверяем наличие view и обрабатываем
            if (ivImageView != null) {
                ivImageView.setImageBitmap(result);
            }
            //Перезаписываем изображение в кеш для актуальности
            cache.addImageToWarehouse(imageUrl, result);
            pbar.setVisibility(View.GONE);
        }
    }

    private Bitmap getImage(String imageUrl) {
        //Проверяем кеш на наличие изображения по url
        if (cache.getImageFromWarehouse(imageUrl) == null) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                image = BitmapFactory.decodeStream(stream);
                return image;
            } catch (Exception e) {
                Log.e("getImage", e.toString());
            }
        } else {
            image = cache.getImageFromWarehouse(imageUrl);
        }
        return image;
    }
}