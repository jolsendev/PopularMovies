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

    //BASE_CONTENT_URI = content://com.example.jamie.popularmovies
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);

    //possible paths
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_TRAILER = "trailers";
    public static final String PATH_REVIEW = "reviews";

    private static final String LOG_TAG = MovieContract.class.getSimpleName();


    /************************
    MovieEntry class
     ************************/
    public static final class MovieEntry implements BaseColumns{

        private static final String LOG_TAG = MovieEntry.class.getSimpleName();

        // MovieEntry.CONTENT_URI = content://com.example.jamie.popularmovies/movie
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_MOVIE;


        //review foreign key
        public static final String MOVIE_REVIEW_KEY = "review_id";

        //video foreign key
        public static final String MOVIE_VIDEO_KEY = "video_id";

        //movie _id


        //table name;
        public static final String TABLE_NAME = PATH_MOVIE;
        public static final String MOVIE_ID = "_id";
        public static final String POSTER_PATH = "poster_path";
        public static final String IS_ADULT = "is_adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String TITLE = "title";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String IS_VIDEO = "is_video";
        public static final String IS_FAVORITE = "is_favorite";
        public static final String VOTE_AVERAGE = "vote_average";

//        /*
//            Student: This is the buildWeatherLocation function you filled in.
//         */
//        public static Uri buildWeatherLocation(String locationSetting) {
//            return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
//        }
//
//        public static Uri buildWeatherLocationWithStartDate(
//                String locationSetting, long startDate)
//        {
//            long normalizedDate = normalizeDate(startDate);
//            return CONTENT_URI.buildUpon().appendPath(locationSetting)
//                    .appendQueryParameter(COLUMN_DATE, Long.toString(normalizedDate)).build();
//        }
        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieReview(long movie_id){

            String _id = String.valueOf(movie_id);
            Uri retUri = CONTENT_URI.buildUpon().appendPath(_id).appendPath(PATH_REVIEW).build();
            return retUri;
        }

        public static Uri buildMovieTrailer(long movie_id){

            String _id = String.valueOf(movie_id);
            Uri retUri = CONTENT_URI.buildUpon().appendPath(_id).appendPath(PATH_TRAILER).build();
            return retUri;
        }


        public static String getMovieIdFromPath(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    /*********************
    TrailerEntry Class
     *********************/
    public static final class TrailerEntry implements BaseColumns{


        private static final String LOG_TAG = TrailerEntry.class.getSimpleName();
        //CONTENT_URI = content://com.example.jamie.popularmovies/video
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+ PATH_TRAILER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+ PATH_TRAILER;

        public static final String TABLE_NAME = PATH_TRAILER;
        public static final String MOVIE_ID = "movie_id";
        public static final String VIDEO_NAME = "video_name";
        public static final String VIDEO_SITE = "video_site";
        public static final String VIDEO_SIZE= "video_size";
        public static final String VIDEO_TYPE = "video_type";

//        public static Uri buildVideoUri(long id){
//            Uri retURI = ContentUris.withAppendedId(CONTENT_URI, id);
//            retURI.withAppendedPath(retURI, "videos");
//
//            return retURI;
//        }

    }

    /**********************
    ReviewEntry class
     **********************/
    public static final class ReviewEntry implements BaseColumns{

        private static final String LOG_TAG = ReviewEntry.class.getSimpleName();
        //CONTENT_URI = content://com.example.jamie.popularmovies/review
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+ PATH_REVIEW;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+ PATH_REVIEW;


        public static final String TABLE_NAME = PATH_REVIEW;
        public static final String MOVIE_ID = "review_key";
        public static final String REVIEW_AUTHOR = "review_author";
        public static final String REVIEW_CONTENT = "review_content";
        public static final String REVIEW_URL = "review_url";

//        public static Uri buildReviewUri(long id){
//            Uri retURI = ContentUris.withAppendedId(CONTENT_URI, id);
//            retURI.withAppendedPath(retURI, "reviews");
//            return retURI;
//        }
    }
}
