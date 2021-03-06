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

    public static final String PATH_MOVIE = "movie";
    public static final String PATH_TRAILER = "trailers";
    public static final String PATH_REVIEW = "reviews";


    private static final String LOG_TAG = MovieContract.class.getSimpleName();


    /************************
    MovieEntry class
     ************************/
    public static final class MovieEntry implements BaseColumns{

        private static final String LOG_TAG = MovieEntry.class.getSimpleName();

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_MOVIE;
        public static final String POSITION = "position";
        public static final String TOP_RATED = "top_rated";
        public static final String MOST_POPULAR = "popular";
        public static final String FAVORITE = "favorite";
        public static final String TABLE_NAME = PATH_MOVIE;
        public static final String MOVIE_ID = "movie_id";
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
        public static final String IS_VIDEO = "video";
        public static final String IS_FAVORITE = FAVORITE;
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String IS_MOST_POPULAR = MOST_POPULAR;
        public static final String IS_TOP_RATED = TOP_RATED;

        public static Uri buildFavoriteUri(){
            return CONTENT_URI.buildUpon().appendPath(IS_FAVORITE).build();
        }

        public static Uri buildPopularUri(){
            return CONTENT_URI.buildUpon().appendPath(MOST_POPULAR).build();
        }

        public static Uri buildTopRatedUri(){
            return CONTENT_URI.buildUpon().appendPath(TOP_RATED).build();
        }

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
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+ PATH_TRAILER;
        public static final String TABLE_NAME = PATH_TRAILER;
        public static final String MOVIE_ID = "movie_id";
        public static final String TRAILER_NAME = "trailer_name";
        public static final String TRAILER_SIZE = "trailer_size";
        public static final String TRAILER_TYPE = "trailer_type";
        public static final String TRAILER_SOURCE = "trailer_source";
        public static final String INDEX = "trailer_index";
    }

    /**********************
    ReviewEntry class
     **********************/
    public static final class ReviewEntry implements BaseColumns{

        private static final String LOG_TAG = ReviewEntry.class.getSimpleName();
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+ PATH_REVIEW;
        public static final String TABLE_NAME = PATH_REVIEW;
        public static final String MOVIE_ID = "movie_id";
        public static final String REVIEW_AUTHOR = "review_author";
        public static final String REVIEW_CONTENT = "review_content";
        public static final String REVIEW_URL = "review_url";
    }
}
