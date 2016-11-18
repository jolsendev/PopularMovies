package com.example.jamie.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jamie on 11/16/16.
 */

public class MovieContract {


    public static final String CONTENT_AUTHORITY = "com.example.jamie.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);

    //possible paths
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_MOVIE;


        //table name
        public static final String TABLE_NAME = PATH_MOVIE;
        public static final String POSTER_PATH = "poster_path";
        public static final String IS_ADULT = "is_adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String MOVIE_ID = "_id";
        public static final String ORIGIONAL_TITLE = "original_title";
        public static final String ORIGIONAL_LANGUAGE = "original_language";
        public static final String TITLE = "title";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String IS_VIDEO = "is_video";
        public static final String VOTE_AVERAGE = "vote_average";


        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class MovieVideos implements BaseColumns{

    }
    public static final class MovieReviews implements BaseColumns{

    }
}
