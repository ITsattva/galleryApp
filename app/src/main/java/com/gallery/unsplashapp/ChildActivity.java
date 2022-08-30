package com.gallery.unsplashapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.gallery.unsplashapp.utils.DownloadHandler;

public class ChildActivity extends AppCompatActivity {
    ImageView fullSizePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        fullSizePicture = findViewById(R.id.iv_big_picture);
        Intent previousIntent = getIntent();
        if(previousIntent.hasExtra(Intent.EXTRA_REFERRER)){
            new DownloadHandler(fullSizePicture).execute(previousIntent.getExtras().getString(Intent.EXTRA_REFERRER));
        }
    }
}