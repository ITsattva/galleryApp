package com.gallery.unsplashapp;

import static com.gallery.unsplashapp.utils.NetworkUtils.generateUrl;
import static com.gallery.unsplashapp.utils.NetworkUtils.getResponseFromURL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gallery.unsplashapp.entity.Picture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    RecyclerView picturesList;
    PictureAdapter pictureAdapter;
    EditText searchField;
    Button searchButton;
    Picture[] pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picturesList = findViewById(R.id.rv_pictures);
        searchField = findViewById(R.id.et_search_field);
        searchButton = findViewById(R.id.b_search);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        picturesList.setLayoutManager(layoutManager);
        picturesList.setHasFixedSize(true);
        pictureAdapter = new PictureAdapter(10, new Picture[]{});

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL generatedURL = generateUrl(searchField.getText().toString());
                new UnsplashQueryTask().execute(generatedURL);
            }
        };

        searchButton.setOnClickListener(onClickListener);
    }

    class UnsplashQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try{
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response){
            if(response != null && !response.equals("")) {
                try {
                    System.out.println("Getting json object");
                    JSONObject object = new JSONObject(response);
                    System.out.println("Getting json array");
                    JSONArray array = object.getJSONArray("results");
                    System.out.println("parsing json array");
                    pictures = convertJsonToPictureArray(array);
                    showResultImages();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showResultImages();
            } else {
                showErrorTextView();
            }
        }

        private void showErrorTextView(){
            //todo
        }

        private void showResultImages() {
            pictureAdapter = new PictureAdapter(10, pictures);
            System.out.println("adapter created");
            picturesList.setAdapter(pictureAdapter);
            System.out.println("adapter assigned");
        }

        private Picture getPictureEntity(JSONObject jsonObject) throws JSONException {
            System.out.println("Getting picture entity");
            JSONObject userObject = jsonObject.getJSONObject("user");
            String author = userObject.getString("first_name") + " " + userObject.getString("last_name");
            JSONObject urlsObject = jsonObject.getJSONObject("urls");
            String linkToSmallSize = urlsObject.getString("small");
            String linkToBigSize = urlsObject.getString("full");;

            System.out.println(author+"\n"+linkToSmallSize+"\n"+linkToBigSize+"\n");

            return new Picture(author, linkToSmallSize, linkToBigSize);
        }

        private Picture[] convertJsonToPictureArray(JSONArray jsonArray) throws JSONException {
            System.out.println("Json Array size is: " + jsonArray.length());
            Picture[] pictures = new Picture[jsonArray.length()];
            for(int i = 0; i<jsonArray.length(); i++){
                pictures[i] = getPictureEntity(jsonArray.getJSONObject(i));
            }

            return pictures;
        }


    }

}