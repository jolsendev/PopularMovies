package com.example.jamie.popularmovies;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;
import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;
import com.example.jamie.popularmovies.data.MovieProvider;

/**
 * Created by a5w5nzz on 11/21/2016.
 */

public class TestProvider extends AndroidTestCase {

    public void testProviderRegistry() {

        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                MovieProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            System.out.println("Authority is at: "+ providerInfo.authority);
            System.out.println("My authority is at: "+MovieContract.CONTENT_AUTHORITY);
            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + MovieContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: MovieProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    public void testGetType() {
        // content://com.example.jamie.popularmovies/movie/
        String type = mContext.getContentResolver().getType(MovieEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.jamie.popularmovies/movie
        assertEquals("Error: the MovieEntry CONTENT_URI should return MovieEntry.CONTENT_TYPE",
                MovieEntry.CONTENT_TYPE, type);

        long movieId = 284052L;
        // content://com.example.jamie.popularmovies/movie/284052
        type = mContext.getContentResolver().getType(
                MovieEntry.buildMovieUri(movieId));
        // vnd.android.cursor.item/com.example.jamie.popularmovies/movie
        assertEquals("Error: the MovieEntry CONTENT_URI with movie should return MovieEntry.CONTENT_TYPE",
                MovieEntry.CONTENT_ITEM_TYPE, type);

        // content://com.example.jamie.popularmovies/movie/284052/trailers
        type = mContext.getContentResolver().getType(
                MovieEntry.buildMovieTrailer(movieId));
        // vnd.android.cursor.dir/com.example.jamie.popularmovies/movie/284052/trailer
        assertEquals("Error: the WeatherEntry CONTENT_URI with movie and trailers should return WeatherEntry.CONTENT_TYPE",
                TrailerEntry.CONTENT_TYPE, type);

        // content://com.example.jamie.popularmovies/movie/284052/reviews
        type = mContext.getContentResolver().getType(MovieEntry.buildMovieReview(movieId));
        // vnd.android.cursor.dir/com.example.jamie.popularmovies/movie/284052/reviews
        assertEquals("Error: the LocationEntry CONTENT_URI should return LocationEntry.CONTENT_TYPE",
                ReviewEntry.CONTENT_TYPE, type);
    }

    public void testBasicMovieQuery(){
        // insert our test records into the database
        long locationRowId = TestUtilities.insertMovieValues(mContext);

        assertTrue("Unable to Insert MovieEntries into the Database", locationRowId != -1);

        System.out.println("COntent Uri:"+ MovieEntry.CONTENT_URI);
//        // Test the basic content provider query
        Cursor movieCursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
//
//        // Make sure we get the correct cursor out of the database
        //TestUtilities.validateCursor("testBasicMovieQuery", movieCursor, movieValues);

    }
}
