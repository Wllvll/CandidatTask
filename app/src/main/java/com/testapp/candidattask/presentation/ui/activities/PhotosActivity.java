package com.testapp.candidattask.presentation.ui.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.testapp.candidattask.R;
import com.testapp.candidattask.async.AlbumsRequest;
import com.testapp.candidattask.storage.cache.CacheId;

public class PhotosActivity extends AppCompatActivity {

    String userId;
    private RecyclerView photo_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        //back arrow and custom toolbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Добавим custom title
        TextView tvTitle = findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        tvTitle.setText(getString(R.string.photos_title));
        //Получаем id пользователя из кеша
        userId = new CacheId(this).getString("id");
        photo_recycler = findViewById(R.id.photo_recycler);
        photo_recycler.setHasFixedSize(true);
        photo_recycler.setDrawingCacheEnabled(true);
        photo_recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        new AlbumsRequest(PhotosActivity.this, userId, photo_recycler).execute();
    }

    @Override public void onBackPressed() {
        this.finish();
    }
    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
