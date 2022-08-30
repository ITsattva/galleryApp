package com.gallery.unsplashapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    RecyclerView picturesList;
    PictureAdapter pictureAdapter;
    EditText searchField;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picturesList = findViewById(R.id.rv_pictures);
        searchField = findViewById(R.id.et_search_field);
        searchField = findViewById(R.id.et_search_field);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        picturesList.setLayoutManager(layoutManager);

        picturesList.setHasFixedSize(true);

        pictureAdapter = new PictureAdapter(100);
        picturesList.setAdapter(pictureAdapter);
    }
}