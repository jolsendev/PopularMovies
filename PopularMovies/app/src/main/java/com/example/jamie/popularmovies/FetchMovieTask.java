package com.example.jamie.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.example.jamie.popularmovies.data.FetchRawData;
import com.example.jamie.popularmovies.extracting_json_data.ExtractJSONMovieData;
import com.example.jamie.popularmovies.movie_objects.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie Olsen on 11/26/2016.
 */
public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<ContentValues>> {

    Context mContext;
    CustomMovieAdapter mAdapter;
        public FetchMovieTask(Context mContext){
            this.mContext = mContext;
            mAdapter = new CustomMovieAdapter(mContext, new ArrayList<Movie>());
        }
        @Override
        protected void onPostExecute(ArrayList<ContentValues> movies) {
            super.onPostExecute((ArrayList<ContentValues>) movies);

//            if(movies != null){
//                mAdapter.clear();
//                for(Movie movie : movies) {
//                    mAdapter.add(movie);
//                }
//                mAdapter.notifyDataSetChanged();
//                gridview.setAdapter(mAdapter);
            //}
        }

        @Override
        protected ArrayList<ContentValues> doInBackground(String... params) {
            FetchRawData mRawData = new FetchRawData(params[0]);
            ExtractJSONMovieData mData = new ExtractJSONMovieData(mRawData.fetch(), mContext);
            return mData.getWeatherContentValues();
        }
}
