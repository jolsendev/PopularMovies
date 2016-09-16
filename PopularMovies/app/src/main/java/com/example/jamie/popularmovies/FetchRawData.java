package com.example.jamie.popularmovies;

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

            return buffer.toString();

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
}
