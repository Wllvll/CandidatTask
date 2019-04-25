package com.testapp.candidattask.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.testapp.candidattask.network.HttpRequest;
import com.testapp.candidattask.presentation.ui.adapters.PhotosAdapter;
import com.testapp.candidattask.storage.models.Photo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.testapp.candidattask.network.AppConfig.URL_PHOTOS;

public class PhotosRequest extends AsyncTask<String, String, String> {

    public Context context;
    private String[] albumList;
    public RecyclerView recycle;
    private final int jsoncode = 1;
    ArrayList<Photo> photosModelArrayList;
    public PhotosAdapter photosAdapter;

    public PhotosRequest(Context context, String[] albumList, RecyclerView recycle) {
        this.recycle = recycle;
        this.context = context;
        this.albumList = albumList;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = "";
        String data = "";
        //Получаем массив альбомов пользователя, обрабатываем массив и по номерам альбомов делаем GET запрос на фотографии
        if(albumList.length == 0){
            return null;
        } else {
            data = HttpRequest.dataconvert(albumList);
        }
        String url = URL_PHOTOS+data;
        try {
            HttpRequest req = new HttpRequest(url);
            response = req.prepare().sendAndReadString();
        } catch (Exception e) {
            response = e.getMessage();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        onTaskCompleted(result,jsoncode);
    }

    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {
            case jsoncode:
                photosModelArrayList = getPhotos(response);
                photosAdapter = new PhotosAdapter(context, photosModelArrayList);
                recycle.setAdapter(photosAdapter);
                recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        }
    }

    public ArrayList<Photo> getPhotos(String response) {
        ArrayList<Photo> photoList = new ArrayList<>();
        try {
            JSONArray photos = new JSONArray(response);
            int size = photos.length();
            for (int i = 0; i < size; i++) {
                Photo photo = new Photo();
                JSONObject photoId = photos.getJSONObject(i);
                photo.setId(photoId.getString("id"));
                photo.setTitle(photoId.getString("title"));
                photo.setImgURL(photoId.getString("url"));
                photoList.add(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoList;
    }
}
