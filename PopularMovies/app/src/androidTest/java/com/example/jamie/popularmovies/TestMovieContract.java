package com.example.jamie.popularmovies;

import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by Jamie Olsen on 11/20/2016.
 */
public class TestMovieContract  extends AndroidTestCase{


    private static final String TEST_MOVIE_TRAILER = "trailers";
    private static final String TEST_MOVIE_REVIEW = "reviews";
    private static final long TEST_MOVIE_ID = 284052L;

    /*
        Students: Uncomment this out to test your weather location function.
     */
    public void testBuildMovieTrailer() {
        Uri trailerUri = MovieEntry.buildMovieTrailer(TEST_MOVIE_ID);
        System.out.println("THIS IS THE TRAILER URI: "+ trailerUri);
        assertNotNull("Error: Null Uri returned.  You must fill-in buildMovieTrailer in " +
                        "MovieContract.",
                trailerUri);
        assertEquals("Error: Movie trailer not properly appended to the end of the Uri",
                TEST_MOVIE_TRAILER, trailerUri.getLastPathSegment());
        assertEquals("Error: Movie trailer Uri doesn't match our expected result",
                trailerUri.toString(),
                "content://com.example.jamie.popularmovies/movie/284052/trailers");
    }

    public void testBuildMovieReview() {
        Uri reviewUri = MovieEntry.buildMovieReview(TEST_MOVIE_ID);
        System.out.println("THIS IS THE TRAILER URI: "+ reviewUri);
        assertNotNull("Error: Null Uri returned.  You must fill-in buildReviewTrailer in " +
                        "MovieContract.",
                reviewUri);
        assertEquals("Error: Movie review not properly appended to the end of the Uri",
                TEST_MOVIE_REVIEW, reviewUri.getLastPathSegment());
        assertEquals("Error: Movie review Uri doesn't match our expected result",
                reviewUri.toString(),
                "content://com.example.jamie.popularmovies/movie/284052/reviews");
    }

    public void testGetMovieIdFromPath(){
        Uri uri = MovieEntry.buildMovieUri(TEST_MOVIE_ID);
        String x =  MovieEntry.getMovieIdFromPath(uri);
        assertEquals("ERROR: Movie id was not retrieved from path", String.valueOf(TEST_MOVIE_ID),x);
    }
}
