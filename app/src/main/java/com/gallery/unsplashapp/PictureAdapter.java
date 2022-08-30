package com.gallery.unsplashapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder>{

    private static int viewHolderCount;
    private int numberOfItems;

    public PictureAdapter(int numberOfItems) {
        this.numberOfItems = numberOfItems;
        viewHolderCount = 0;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForPictureListItem = R.layout.picture_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForPictureListItem, parent, false);

        PictureViewHolder pictureViewHolder = new PictureViewHolder(view);
        pictureViewHolder.ownerName.setText("NAME OF OWNER");
        //assign picture

        viewHolderCount++;

        return pictureViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberOfItems;
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {

    TextView ownerName;
    ImageView smallPicture;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);

            ownerName = itemView.findViewById(R.id.tv_owner_name);
            ownerName.setText("Owner");
            smallPicture = itemView.findViewById(R.id.iv_small_picture);
        }

        void bind(int listIndex){
            ownerName.setText(String.valueOf(listIndex));
        }
    }
}
