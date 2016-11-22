package com.example.jamie.popularmovies;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;
import com.example.jamie.popularmovies.data.MovieDBHelper;
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
        MovieDBHelper dbHelper = new MovieDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues movieValues = TestUtilities.createMovieValues();
        long locationRowId = TestUtilities.insertMovieValues(mContext);

        // Fantastic.  Now that we have a location, add some weather!
//        ContentValues Values = TestUtilities.createWeatherValues(locationRowId);
//
//        long weatherRowId = db.insert(WeatherEntry.TABLE_NAME, null, weatherValues);
//        assertTrue("Unable to Insert WeatherEntry into the Database", weatherRowId != -1);
//
//        db.close();
//
//        // Test the basic content provider query
//        Cursor weatherCursor = mContext.getContentResolver().query(
//                WeatherEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Make sure we get the correct cursor out of the database
//        TestUtilities.validateCursor("testBasicWeatherQuery", weatherCursor, weatherValues);

    }
}
