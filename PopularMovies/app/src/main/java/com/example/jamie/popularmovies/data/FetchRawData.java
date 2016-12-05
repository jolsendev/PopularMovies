package com.example.jamie.popularmovies.data;

import com.example.jamie.popularmovies.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by a5w5nzz on 9/16/2016.
 */
public class FetchRawData {
    String urlString;
    String jsonData;

    public FetchRawData(String url) {
        this.urlString = url;
    }

    public String fetch(){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        if(urlString == null)
            return null;

        try {
            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if(inputStream == null) {
                return null;
            }

            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            setJsonData(buffer.toString());
            return getJsonData();

        } catch(IOException e) {
            return null;

            /**
             * This finally cleans up by disconnecting the url connection and closing the BuferedReader
             */
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch(final IOException e) {
                }
            }
        }
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
