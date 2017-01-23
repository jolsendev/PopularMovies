//package com.example.jamie.popularmovies;
//
//import android.content.UriMatcher;
//import android.net.Uri;
//import android.test.AndroidTestCase;
//
//import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
//import com.example.jamie.popularmovies.data.MovieProvider;
//
///**
// * Created by Jamie Olsen on 11/21/2016.
// */
//public class TestUriMatcher extends AndroidTestCase {
//
//    private static final long TEST_MOVIE_ID = 284052L;
//
//    // content://com.example.android.sunshine.app/weather"
//    public static final Uri TEST_ALL_MOVIES = MovieEntry.CONTENT_URI;
//    private static final Uri TEST_MOVIE_DIR = MovieEntry.buildMovieUri(TEST_MOVIE_ID);
//    private static final Uri TEST_MOVIE_WITH_REVIEW = MovieEntry.buildMovieReview(TEST_MOVIE_ID);
//    private static final Uri TEST_MOVIE_WITH_TRAILER = MovieEntry.buildMovieTrailer(TEST_MOVIE_ID);
//
//    public void testUriMatcher() {
//        UriMatcher testMatcher = MovieProvider.buildUriMatcher();
//
//        assertEquals("Error: The TEST_MOVIE_WITH_REVIEW URI was matched incorrectly.",
//                testMatcher.match(TEST_MOVIE_WITH_REVIEW), MovieProvider.MOVIE_WITH_REVIEWS);
//
//        //Tests a single movie
//        assertEquals("Error: The MOVIE URI was matched incorrectly.",
//                testMatcher.match(TEST_MOVIE_DIR), MovieProvider.MOVIE);
//        assertEquals("Error: The WEATHER WITH LOCATION AND DATE URI was matched incorrectly.",
//                testMatcher.match(TEST_MOVIE_WITH_TRAILER), MovieProvider.MOVIE_WITH_TRAILERS);
//        assertEquals("Error: The LOCATION URI was matched incorrectly.",
//                testMatcher.match(TEST_ALL_MOVIES), MovieProvider.ALL_MOVIES);
//    }
//}
