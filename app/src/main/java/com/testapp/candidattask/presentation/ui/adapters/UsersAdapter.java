package com.testapp.candidattask.presentation.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapp.candidattask.R;
import com.testapp.candidattask.storage.models.User;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<User> userModelArrayList;

    public UsersAdapter(Context ctx, ArrayList<User> userModelArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.userModelArrayList = userModelArrayList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.user_item, parent, false);
        UserViewHolder holder = new UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        holder.mName.setText(userModelArrayList.get(position).getName());
        holder.id = userModelArrayList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

}
