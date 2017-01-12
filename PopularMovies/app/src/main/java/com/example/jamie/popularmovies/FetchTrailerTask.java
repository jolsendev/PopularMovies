package com.example.jamie.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.jamie.popularmovies.data.FetchRawData;
import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.extracting_json_data.ExtractJSONTrailerData;

/**
 * Created by jamie on 12/4/16.
 */

public class FetchTrailerTask extends AsyncTask<String, Void, Uri> {
    Context mContext;

    public FetchTrailerTask(Context mContext) {
        this.mContext = mContext;
    }

    public interface Callback {

    void RestartTrailerLoader();

    }

    protected void onPostExecute(Uri uri) {
        super.onPostExecute(uri);
        ((Callback) mContext).RestartTrailerLoader();
    }
    @Override
    protected Uri doInBackground(String... params) {
        FetchRawData mRawData = new FetchRawData(params[0]);
        ExtractJSONTrailerData mData = new ExtractJSONTrailerData(mRawData.fetch(), mContext);
        mData.putTrailersInDatabase();
        long movie_id = mData.getMovieId();
        Uri uri = MovieContract.MovieEntry.buildMovieUri(movie_id);
        return uri;
    }
}
