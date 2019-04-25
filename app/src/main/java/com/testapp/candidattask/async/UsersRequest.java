package com.testapp.candidattask.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.testapp.candidattask.network.HttpRequest;
import com.testapp.candidattask.presentation.ui.adapters.UsersAdapter;
import com.testapp.candidattask.storage.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.testapp.candidattask.network.AppConfig.URL_USERS;

public class UsersRequest extends AsyncTask<String, String, String> {

    public Context context;
    public RecyclerView recycle;
    private final int jsoncode = 1;
    ArrayList<User> userModelArrayList;
    public UsersAdapter userAdapter;

    public UsersRequest(Context context, RecyclerView recycle) {
        this.recycle = recycle;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = "";
        try {
            HttpRequest req = new HttpRequest(URL_USERS);
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
                userModelArrayList = getNames(response);
                userAdapter = new UsersAdapter(context, userModelArrayList);
                recycle.setAdapter(userAdapter);
                recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        }
    }

    public ArrayList<User> getNames(String response) {
        ArrayList<User> userList = new ArrayList<>();
        try {
            JSONArray users = new JSONArray(response);
            int size = users.length();
            for (int i = 0; i < size; i++) {
                User user = new User();
                JSONObject userId = users.getJSONObject(i);
                user.setName(userId.getString("name"));
                user.setId(userId.getString("id"));
                userList.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
