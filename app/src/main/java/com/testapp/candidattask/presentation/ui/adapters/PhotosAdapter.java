package com.testapp.candidattask.presentation.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.testapp.candidattask.R;
import com.testapp.candidattask.async.DownloadImageTask;
import com.testapp.candidattask.storage.cache.CacheManager;
import com.testapp.candidattask.storage.cache.ImagesCache;
import com.testapp.candidattask.storage.models.Photo;

import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    public CacheManager cacheManager;
    public ImagesCache cache;
    private LayoutInflater inflater;
    private ArrayList<Photo> photosModelArrayList;

    public PhotosAdapter(Context ctx, ArrayList<Photo> photosModelArrayList) {
        cacheManager = new CacheManager(ctx);
        inflater = LayoutInflater.from(ctx);
        this.photosModelArrayList = photosModelArrayList;
        cache = ImagesCache.getInstance();
    }

    @Override
    public PhotosAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.photo_item, parent, false);
        PhotosAdapter.MyViewHolder holder = new PhotosAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PhotosAdapter.MyViewHolder holder, final int position) {

        new DownloadImageTask(cache, photosModelArrayList.get(position).getImgURL(), holder.mImage, holder.pbar).execute();
        holder.mTitle.setText(photosModelArrayList.get(position).getTitle());

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return photosModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTitle;
        ImageView mImage;
        ProgressBar pbar;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.mTitle);
            mImage = itemView.findViewById(R.id.mImage);
            pbar = itemView.findViewById(R.id.mProgress);
        }
    }
}
