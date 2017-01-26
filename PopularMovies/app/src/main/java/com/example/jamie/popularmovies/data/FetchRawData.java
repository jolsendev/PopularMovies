package com.example.jamie.popularmovies.data;

import android.content.Context;

import com.example.jamie.popularmovies.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by a5w5nzz on 9/16/2016.
 */
public class FetchRawData {
    String urlString;
    String jsonData;
    Context mContext;

    public FetchRawData(String url, Context mContext) {
        this.urlString = url;
        this.mContext = mContext;
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
            Utility.setMovieStatus(mContext, Utility.MOVIE_STATUS_OK);
            return getJsonData();

        } catch(IOException e) {
            Utility.setMovieStatus(mContext, Utility.MOVIE_STATUS_DOWN);
            return null;

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
