package com.gallery.unsplashapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadHandler extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownloadHandler(ImageView imageView) {
        this.imageView = imageView;
        //Toast.makeText(getApplicationContext(), "Please wait, it may take a few seconds...",Toast.LENGTH_SHORT).show();
    }

    protected Bitmap doInBackground(String... urls) {
        System.out.println("Download has been started...");
        String imageURL = urls[0];
        Bitmap bimage = null;
        try {
            InputStream in = new java.net.URL(imageURL).openStream();
            bimage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            //Log.e("Error Message", e.getMessage());
            e.printStackTrace();
        }
        return bimage;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
