package com.testapp.candidattask.presentation.ui.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;

import com.testapp.candidattask.R;
import com.testapp.candidattask.presentation.ui.activities.PhotosActivity;
import com.testapp.candidattask.storage.cache.CacheId;

public class UserViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

    TextView mName;
    String id;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public UserViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mName = itemView.findViewById(R.id.mName);

    }

    @Override
    public void onClick(View view) {
        //проверка существования позиции и обработка клика
        if(selectedItems.get(getAdapterPosition(), true)){
            view.setSelected(true);
            //сохраняем user id в кеш и запускаем PhotosActivity
            new CacheId(view.getContext()).setString("id", id);
            mHolderClick(view);
        }
    }

    private void mHolderClick(View v){
        Intent intent = new Intent (v.getContext(), PhotosActivity.class);
        v.getContext().startActivity(intent);
    }
}