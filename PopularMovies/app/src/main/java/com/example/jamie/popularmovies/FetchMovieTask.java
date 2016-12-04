package com.example.jamie.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.example.jamie.popularmovies.adapters.MovieCursorAdapter;
import com.example.jamie.popularmovies.data.FetchRawData;
import com.example.jamie.popularmovies.extracting_json_data.ExtractJSONMovieData;

import java.util.ArrayList;

/**
 * Created by Jamie Olsen on 11/26/2016.
 */
public class FetchMovieTask extends AsyncTask<String, Void, Void> {

    Context mContext;
        public FetchMovieTask(Context mContext){
            this.mContext = mContext;
        }
        @Override
        protected Void doInBackground(String... params) {
            FetchRawData mRawData = new FetchRawData(params[0]);
            ExtractJSONMovieData mData = new ExtractJSONMovieData(mRawData.fetch(), mContext);
            mData.extractMovieAndPlaceInDatabase();
            return null;
        }
}
