package com.example.jamie.popularmovies;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;
import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;
import com.example.jamie.popularmovies.data.MovieProvider;

/**
 * Created by Jamie Olsen on 11/21/2016.
 */
public class TestUriMatcher extends AndroidTestCase {

    //private static final String TEST_MOVIE_TRAILER = "trailers";
    //private static final String TEST_MOVIE_REVIEW = "reviews";
    private static final long TEST_MOVIE_ID = 284052L;

    // content://com.example.android.sunshine.app/weather"
    private static final Uri TEST_MOVIE_DIR = MovieEntry.buildMovieUri(TEST_MOVIE_ID);
    private static final Uri TEST_MOVIE_WITH_REVIEW = MovieEntry.buildMovieReview(TEST_MOVIE_ID);
    //private static final Uri TEST_WEATHER_WITH_LOCATION_AND_DATE_DIR = WeatherContract.WeatherEntry.buildWeatherLocationWithDate(LOCATION_QUERY, TEST_DATE);
    // content://com.example.android.sunshine.app/location"
    //private static final Uri TEST_LOCATION_DIR = WeatherContract.LocationEntry.CONTENT_URI;

    /*
        Students: This function tests that your UriMatcher returns the correct integer value
        for each of the Uri types that our ContentProvider can handle.  Uncomment this when you are
        ready to test your UriMatcher.
     */
    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        assertEquals("Error: The TEST_MOVIE_WITH_REVIEW URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_WITH_REVIEW), MovieProvider.MOVIE_WITH_REVIEWS);

        //Tests a single movie
        assertEquals("Error: The MOVIE URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_DIR), MovieProvider.MOVIE);
//        assertEquals("Error: The WEATHER WITH LOCATION AND DATE URI was matched incorrectly.",
//                testMatcher.match(TEST_WEATHER_WITH_LOCATION_AND_DATE_DIR), WeatherProvider.WEATHER_WITH_LOCATION_AND_DATE);
//        assertEquals("Error: The LOCATION URI was matched incorrectly.",
//                testMatcher.match(TEST_LOCATION_DIR), WeatherProvider.LOCATION);
    }
}
