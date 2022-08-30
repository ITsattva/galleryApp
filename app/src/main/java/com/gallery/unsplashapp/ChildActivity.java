package com.gallery.unsplashapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gallery.unsplashapp.utils.DownloadHandler;

public class ChildActivity extends AppCompatActivity {
    ImageView fullSizePicture;
    TextView waitMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        fullSizePicture = findViewById(R.id.iv_big_picture);
        waitMessage = findViewById(R.id.tv_wait_message);

        Intent previousIntent = getIntent();
        if(previousIntent.hasExtra(Intent.EXTRA_REFERRER)){
            new DownloadHandlerForBigSize(fullSizePicture).execute(previousIntent.getExtras().getString(Intent.EXTRA_REFERRER));
        }
    }

    class DownloadHandlerForBigSize extends DownloadHandler{

        public DownloadHandlerForBigSize(ImageView imageView) {
            super(imageView);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            showResult();
        }
    }

    void showResult(){
        waitMessage.setVisibility(View.INVISIBLE);
    }
}