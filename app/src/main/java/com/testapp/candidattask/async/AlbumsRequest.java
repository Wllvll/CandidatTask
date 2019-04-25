package com.testapp.candidattask.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import com.testapp.candidattask.network.HttpRequest;
import com.testapp.candidattask.presentation.ui.adapters.UsersAdapter;
import com.testapp.candidattask.storage.models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import static com.testapp.candidattask.network.AppConfig.URL_ALBUMS;

public class AlbumsRequest extends AsyncTask<String, String, String> {

    public Context context;
    private String userId;
    private String param;
    public RecyclerView recycle;

    private final int jsoncode = 1;
    ArrayList<User> userModelArrayList;
    String[] userAlbumList;
    public UsersAdapter userAdapter;

    public AlbumsRequest(Context context, String userId, RecyclerView recycle) {
        this.userId = userId;
        this.param = param;
        //this.textview = text;
        this.recycle = recycle;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String response = "";
        HashMap<String, String> map = new HashMap<>();
        String album_key = "userId";
        map.put(album_key, userId);
        String url = URL_ALBUMS+HttpRequest.data(map);
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
        onUserAlbumGet(result,jsoncode);
    }

    public void onUserAlbumGet(String response, int serviceCode){
        switch (serviceCode) {
            case jsoncode:
                userAlbumList = getAlbums(response);
                if(userAlbumList.length != 0){
                    new PhotosRequest(context, userAlbumList, recycle).execute();
                }
        }
    }

    public String[] getAlbums(String response) {
        String[] albumList = new String[0];
        try {
            JSONArray albums = new JSONArray(response);
            int size = albums.length();
            String[] albumsId = new String[size];
            for (int i = 0; i < size; i++) {
                JSONObject albumId = albums.getJSONObject(i);
                albumsId[i] = albumId.getString("id");
            }
            albumList = albumsId;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return albumList;
    }
}
