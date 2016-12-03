package com.example.jamie.popularmovies;

/**
 * Created by a5w5nzz on 9/16/2016.
 */
public final class Utility {
    public static final String MOVIE_KEY = "movie";
    public static final String MOVIE_API_KEY = BuildConfig.API_KEY;
    private static String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/w185/";

    public static String getImagePath(String imageID){
        return IMAGE_BASE_PATH+imageID;
    }
}