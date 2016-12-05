package com.example.jamie.popularmovies.extracting_json_data;

import android.content.ContentValues;
import android.content.Context;

import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

/**
 * Created by Jamie Olsen on 11/26/2016.
 */
public class ExtractJSONTrailerData {
    String jsonString;
    Context mContext;
    public ExtractJSONTrailerData(String jsonString, Context mContext){
        this.mContext = mContext;
        this.jsonString = jsonString;
    }

    public void putTrailersInDatabase(){

        final String MOVIE_ID = "id";
        final String TRAILER_YOUTUBE = "youtube";
        final String TRAILER_NAME = "name";
        final String TRAILER_SIZE = "size";
        final String TRAILER_TYPE = "type";
        final String TRAILER_SOURCE = "source";

//        "name": "Doctor Strange Official Trailer 2",
//                "size": "HD",
//                "source": "HSzx-zryEgM",
//                "type": "Trailer"

        try {

            JSONObject jsonData = new JSONObject(jsonString);
            Vector<ContentValues> cVVector = new Vector(jsonData.length());
            long movie_id = jsonData.getLong(MOVIE_ID);
            JSONArray itemsArray = jsonData.getJSONArray(TRAILER_YOUTUBE);
            for(int i = 0; i < itemsArray.length(); i++){
                ContentValues trailerValues = new ContentValues();
                JSONObject jObj = itemsArray.getJSONObject(i);
                trailerValues.put(TrailerEntry.MOVIE_ID, movie_id);
                trailerValues.put(TrailerEntry.TRAILER_NAME, jObj.getString(TRAILER_NAME));
                trailerValues.put(TrailerEntry.TRAILER_SIZE, jObj.getString(TRAILER_SIZE));
                trailerValues.put(TrailerEntry.TRAILER_SOURCE, jObj.getString(TRAILER_SOURCE));
                trailerValues.put(TrailerEntry.TRAILER_TYPE, jObj.getString(TRAILER_TYPE));
                cVVector.add(trailerValues);
            }

            int inserted = 0;
            //add to database
            if ( cVVector.size() > 0 ) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(TrailerEntry.CONTENT_URI, cvArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
