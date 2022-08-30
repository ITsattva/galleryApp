package com.gallery.unsplashapp.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;


public class NetworkUtils {
    public static final String UNSPLASH_API_BASE_URL = "https://api.unsplash.com/";
    public static final String UNSPLASH_PICTURES_GET = "search/photos/";
    public static final String PARAM_CLIENT_ID = "client_id";
    public static final String PARAM_QUERY = "query";
    public static final String PARAM_PER_PAGE = "per_page";
    public static final String PARAM_PAGE = "per_page";
    public static final String CLIENT_ID = "bqaoLYRxsrEeoSca47QexL8jICJgYF1T-ad8ZbCfZTw";

    public static URL generateUrl(String query){
        Uri builtUri = Uri.parse(UNSPLASH_API_BASE_URL + UNSPLASH_PICTURES_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_CLIENT_ID, CLIENT_ID)
                .appendQueryParameter(PARAM_QUERY, query)
                .appendQueryParameter(PARAM_PER_PAGE, "10")
                .appendQueryParameter(PARAM_PAGE, "1")
                .build();


        System.out.println("Generated URL is: " + builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromURL(URL url) throws IOException {
        System.out.println("getResponseFromURL");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try(InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in)
        ) {
            String response = "";
            while (scanner.hasNext()){
                response += scanner.next();
            }

            return response;
        } catch (UnknownHostException uhe) {
            return null;
        }
    }


}

