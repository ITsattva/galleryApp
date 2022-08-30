package com.gallery.unsplashapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    RecyclerView picturesList;
    PictureAdapter pictureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picturesList = findViewById(R.id.rv_pictures);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        picturesList.setLayoutManager(layoutManager);

        picturesList.setHasFixedSize(true);

        pictureAdapter = new PictureAdapter(100);
        picturesList.setAdapter(pictureAdapter);

    }
}