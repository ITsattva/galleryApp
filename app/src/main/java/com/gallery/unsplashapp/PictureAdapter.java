package com.gallery.unsplashapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gallery.unsplashapp.entity.Picture;
import com.gallery.unsplashapp.utils.DownloadHandler;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private static int viewHolderCount;
    private int numberOfItems;
    private Picture[] pictures;

    public PictureAdapter(int numberOfItems, Picture[] pictures) {
        this.numberOfItems = numberOfItems;
        this.pictures = pictures;
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
        try {
            holder.bind(position);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            smallPicture = itemView.findViewById(R.id.iv_small_picture);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    int positionIndex = getAdapterPosition();

                    Class destination = ChildActivity.class;

                    Intent fullSizeIntent = new Intent(context, destination);
                    fullSizeIntent.putExtra(Intent.EXTRA_REFERRER, pictures[positionIndex].getLinkToBigSize());

                    context.startActivity(fullSizeIntent);
                }
            });
        }

        void bind(int listIndex) throws ExecutionException, InterruptedException {
            ownerName.setText(pictures[listIndex].getAuthor());
//            smallPicture.setImageBitmap(new DownloadHandler(smallPicture).execute(pictures[listIndex].getLinkToSmallSize()).get());
            new DownloadHandler(smallPicture).execute(pictures[listIndex].getLinkToSmallSize());
        }
    }
}
