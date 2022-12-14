package com.gallery.unsplashapp;

import static com.gallery.unsplashapp.utils.NetworkUtils.generateUrl;
import static com.gallery.unsplashapp.utils.NetworkUtils.getResponseFromURL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gallery.unsplashapp.adapters.PictureAdapter;
import com.gallery.unsplashapp.entity.Picture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    RecyclerView picturesList;
    PictureAdapter pictureAdapter;
    EditText searchField;
    TextView errorMessage;
    TextView noResultMessage;
    ProgressBar loadingIndicator;
    Button searchButton;
    Picture[] pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picturesList = findViewById(R.id.rv_pictures);
        searchField = findViewById(R.id.et_search_field);
        searchButton = findViewById(R.id.b_search);
        errorMessage = findViewById(R.id.tv_error_message);
        noResultMessage = findViewById(R.id.tv_no_result_message);
        loadingIndicator = findViewById(R.id.pb_loading);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        picturesList.setLayoutManager(layoutManager);
        picturesList.setHasFixedSize(false);

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
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicator.setVisibility(View.VISIBLE);
        }


        @Override
        protected void onPostExecute(String response) {
            if (response != null && !response.equals("")) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("results");
                    pictures = convertJsonToPictureArray(array);

                    if (pictures.length == 0) {
                        showNoResultMessage();
                    } else {
                        showResultImages();
                    }
                } catch (JSONException e) {
                    showErrorTextView();
                    e.printStackTrace();
                }
            } else {
                showErrorTextView();
            }
            loadingIndicator.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        private void showErrorTextView() {
            noResultMessage.setVisibility(View.INVISIBLE);
            errorMessage.setVisibility(View.VISIBLE);
            picturesList.setVisibility(View.INVISIBLE);
        }

        private void showResultImages() {
            noResultMessage.setVisibility(View.INVISIBLE);
            errorMessage.setVisibility(View.INVISIBLE);
            picturesList.setVisibility(View.VISIBLE);
            pictureAdapter = new PictureAdapter(100, pictures);
            picturesList.setAdapter(pictureAdapter);
        }

        private void showNoResultMessage() {
            errorMessage.setVisibility(View.INVISIBLE);
            picturesList.setVisibility(View.INVISIBLE);
            noResultMessage.setVisibility(View.VISIBLE);
        }

        private Picture getPictureEntity(JSONObject jsonObject) throws JSONException {
            JSONObject userObject = jsonObject.getJSONObject("user");
            String firstName = userObject.getString("first_name");
            String lastName = userObject.getString("last_name");

            String author = firstName + (lastName.equals("null") ? "" : " " + lastName);
            JSONObject urlsObject = jsonObject.getJSONObject("urls");
            String linkToSmallSize = urlsObject.getString("small");
            String linkToBigSize = urlsObject.getString("full");
            ;

            System.out.println(author + "\n" + linkToSmallSize + "\n" + linkToBigSize + "\n");

            return new Picture(author, linkToSmallSize, linkToBigSize);
        }

        private Picture[] convertJsonToPictureArray(JSONArray jsonArray) throws JSONException {

            Picture[] pictures = new Picture[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                pictures[i] = getPictureEntity(jsonArray.getJSONObject(i));
            }
            return pictures;

        }


    }

}