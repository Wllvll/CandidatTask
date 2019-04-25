package com.testapp.candidattask.presentation.ui.activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testapp.candidattask.R;
import com.testapp.candidattask.async.UsersRequest;
import com.testapp.candidattask.presentation.ui.others.SimpleDividerItemDecoration;
import com.testapp.candidattask.storage.cache.ImagesCache;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    LinearLayout user_check;
    public ImagesCache cache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_custom);

        TextView tvTitle = findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        tvTitle.setText(getString(R.string.users_title));

        recyclerView = findViewById(R.id.recycler);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        //Запрошиваем список пользователей
        UserRequest();
        //Создаем кеш для фотографий
        CacheInitialize();

        user_check = findViewById(R.id.user_check);
        View.OnClickListener user_checkClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRequest();
            }
        };
        user_check.setOnClickListener(user_checkClk);
    }

    private void UserRequest() {
        new UsersRequest(UsersActivity.this, recyclerView).execute();
    }

    private void CacheInitialize() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int availMemInBytes = am.getMemoryClass() * 1024 * 1024;
        cache = ImagesCache.getInstance();
        cache.initializeCache(availMemInBytes);
        //Toast.makeText(this, "Cache size = " + String.valueOf(availMemInBytes / 8), Toast.LENGTH_SHORT).show();
    }
}
