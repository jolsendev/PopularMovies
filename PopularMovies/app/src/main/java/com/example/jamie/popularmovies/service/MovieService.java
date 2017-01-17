//package com.example.jamie.popularmovies.service;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//
//import com.example.jamie.popularmovies.data.FetchRawData;
//import com.example.jamie.popularmovies.extracting_json_data.ExtractJSONMovieData;
//
///**
// * Created by jamie on 1/16/17.
// */
//
//public class MovieService extends IntentService {
//    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
//     * @param name Used to name the worker thread, important only for debugging.
//     */
//    public MovieService(String name) {
//        super(name);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        String uri = intent.getStringExtra("URL");
//        FetchRawData mRawData = new FetchRawData(uri);
//        ExtractJSONMovieData mData = new ExtractJSONMovieData(mRawData.fetch(), getApplicationContext());
//        mData.extractMovieAndPlaceInDatabase();
//        //return mRawData.getJsonData();
//    }
//}
