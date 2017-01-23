//package com.example.jamie.popularmovies;
//
//import android.content.ComponentName;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.pm.PackageManager;
//import android.content.pm.ProviderInfo;
//import android.database.Cursor;
//import android.database.DatabaseUtils;
//import android.graphics.*;
//import android.net.Uri;
//import android.test.AndroidTestCase;
//import android.util.Log;
//
//import com.example.jamie.popularmovies.data.MovieContract;
//import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
//import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;
//import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;
//import com.example.jamie.popularmovies.data.MovieDBHelper;
//import com.example.jamie.popularmovies.data.MovieProvider;
//
///**
// * Created by a5w5nzz on 11/21/2016.
// */
//
//public class TestProvider extends AndroidTestCase {
//
//    public void testProviderRegistry() {
//
//        PackageManager pm = mContext.getPackageManager();
//
//        // We define the component name based on the package name from the context and the
//        // WeatherProvider class.
//        ComponentName componentName = new ComponentName(mContext.getPackageName(),
//                MovieProvider.class.getName());
//        try {
//            // Fetch the provider info using the component name from the PackageManager
//            // This throws an exception if the provider isn't registered.
//            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
//
//            System.out.println("Authority is at: "+ providerInfo.authority);
//            System.out.println("My authority is at: "+MovieContract.CONTENT_AUTHORITY);
//            // Make sure that the registered authority matches the authority from the Contract.
//            assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
//                            " instead of authority: " + MovieContract.CONTENT_AUTHORITY,
//                    providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
//        } catch (PackageManager.NameNotFoundException e) {
//            // I guess the provider isn't registered correctly.
//            assertTrue("Error: MovieProvider not registered at " + mContext.getPackageName(),
//                    false);
//        }
//    }
//
//    public void testGetType() {
//        // content://com.example.jamie.popularmovies/movie/
//        String type = mContext.getContentResolver().getType(MovieEntry.CONTENT_URI);
//        // vnd.android.cursor.dir/com.example.jamie.popularmovies/movie
//        assertEquals("Error: the MovieEntry CONTENT_URI should return MovieEntry.CONTENT_TYPE",
//                MovieEntry.CONTENT_TYPE, type);
//
//        long movieId = 284052L;
//        // content://com.example.jamie.popularmovies/movie/284052
//        type = mContext.getContentResolver().getType(
//                MovieEntry.buildMovieUri(movieId));
//        // vnd.android.cursor.item/com.example.jamie.popularmovies/movie
//        assertEquals("Error: the MovieEntry CONTENT_URI with movie should return MovieEntry.CONTENT_TYPE",
//                MovieEntry.CONTENT_ITEM_TYPE, type);
//
//        // content://com.example.jamie.popularmovies/movie/284052/trailers
//        type = mContext.getContentResolver().getType(
//                MovieEntry.buildMovieTrailer(movieId));
//        // vnd.android.cursor.dir/com.example.jamie.popularmovies/movie/284052/trailer
//        assertEquals("Error: the WeatherEntry CONTENT_URI with movie and trailers should return WeatherEntry.CONTENT_TYPE",
//                TrailerEntry.CONTENT_TYPE, type);
//
//        // content://com.example.jamie.popularmovies/movie/284052/reviews
//        type = mContext.getContentResolver().getType(MovieEntry.buildMovieReview(movieId));
//        // vnd.android.cursor.dir/com.example.jamie.popularmovies/movie/284052/reviews
//        assertEquals("Error: the LocationEntry CONTENT_URI should return LocationEntry.CONTENT_TYPE",
//                ReviewEntry.CONTENT_TYPE, type);
//    }
//
//    public void testBasicMovieQuery(){
//        // insert our test records into the database
//        long locationRowId = TestUtilities.insertMovieValues(mContext);
//        ContentValues movieValues = TestUtilities.createMovieValues();
//
//        assertTrue("Unable to Insert MovieEntries into the Database", locationRowId != -1);
//
//        System.out.println("COntent Uri:"+ MovieEntry.CONTENT_URI);
////        // Test the basic content provider query
//        Cursor movieCursor = mContext.getContentResolver().query(
//                MovieEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
////
////        // Make sure we get the correct cursor out of the database
//        TestUtilities.validateCursor("testBasicMovieQuery", movieCursor, movieValues);
//
//    }
//
//    public void testMovieWithReviewQuery(){
//        ContentValues val = TestUtilities.createMovieValues();
//        int movieRowId = (int) val.get(MovieEntry.MOVIE_ID);
//
//        TestUtilities.insertReviewValues(mContext, movieRowId);
//
//        String[] columns = {
//            MovieEntry.TABLE_NAME+"."+MovieEntry.MOVIE_ID,
//            MovieEntry.TABLE_NAME+"."+MovieEntry.ORIGINAL_TITLE,
//            ReviewEntry.TABLE_NAME+"."+ReviewEntry.REVIEW_CONTENT,
//            ReviewEntry.TABLE_NAME+"."+ReviewEntry.REVIEW_AUTHOR
//        };
//        assertTrue("Unable to Insert MovieEntries into the Database", movieRowId != -1);
//        TestUtilities.insertReviewValues(mContext, movieRowId);
//        ContentValues movieWithReviewValues = TestUtilities.createMovieWithReviewValues(movieRowId);
//        Uri uri = MovieEntry.buildMovieReview(movieRowId);
//        Cursor reviewCursor = mContext.getContentResolver().query(
//                uri,
//                columns,
//                null,
//                new String[]{String.valueOf(movieRowId)},
//                null
//        );
//
//        TestUtilities.validateCursor("testMovieWithReviewQuery", reviewCursor, movieWithReviewValues);
//
//    }
//
//    public void testMovieWithTrailers(){
//        ContentValues val = TestUtilities.createMovieValues();
//        int movieRowId = (int) val.get(MovieEntry.MOVIE_ID);
//
//        TestUtilities.insertTrailerValues(mContext, movieRowId);
//
//        String[] columns = {
//                MovieEntry.TABLE_NAME+"."+MovieEntry.MOVIE_ID,
//                MovieEntry.TABLE_NAME+"."+MovieEntry.ORIGINAL_TITLE,
//                TrailerEntry.TABLE_NAME+"."+TrailerEntry.TRAILER_TYPE
//        };
//
//        TestUtilities.insertTrailerValues(mContext, movieRowId);
//        ContentValues movieWithTrailerValues = TestUtilities.createMovieWithTrailerValues(movieRowId);
//        Uri uri = MovieEntry.buildMovieTrailer(movieRowId);
//        Cursor reviewCursor = mContext.getContentResolver().query(
//                uri,
//                columns,
//                null,
//                new String[]{String.valueOf(movieRowId)},
//                null
//        );
//
//        TestUtilities.validateCursor("testMovieWithReviewQuery", reviewCursor, movieWithTrailerValues);
//
//    }
//
//
//    public void testUpdateMovie(){
//        // Create a new map of values, where column names are the keys
//        ContentValues values = TestUtilities.createMovieValues();
//
//        Uri locationUri = mContext.getContentResolver().
//                insert(MovieEntry.CONTENT_URI, values);
//        long movieRowId = ContentUris.parseId(locationUri);
//
//        // Verify we got a row back.
//        assertTrue(movieRowId != -1);
//
//        ContentValues updatedValues = new ContentValues(values);
//        //updatedValues.put(MovieEntry._ID, movieRowId);
//        updatedValues.put(MovieEntry.ORIGINAL_TITLE, "Santa's Village");
//
//        // Create a cursor with observer to make sure that the content provider is notifying
//        // the observers as expected
//        Cursor locationCursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI, null, null, null, null);
//
//        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
//        locationCursor.registerContentObserver(tco);
//
//        int count = mContext.getContentResolver().update(
//                MovieEntry.CONTENT_URI, updatedValues, MovieEntry._ID + "= ?",
//                new String[] { Long.toString(movieRowId)});
//        assertEquals(count, 1);
//
//        // Test to make sure our observer is called.  If not, we throw an assertion.
//        //
//        // Students: If your code is failing here, it means that your content provider
//        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
//        tco.waitForNotificationOrFail();
//
//        locationCursor.unregisterContentObserver(tco);
//        locationCursor.close();
//
//        // A cursor is your primary interface to the query results.
//        Cursor cursor = mContext.getContentResolver().query(
//                MovieEntry.CONTENT_URI,
//                null,   // projection
//                MovieEntry._ID + " = " + movieRowId,
//                null,   // Values for the "where" clause
//                null    // sort order
//        );
//
//        TestUtilities.validateCursor("testUpdateMovie.  Error validating movie entry update.",
//                cursor, updatedValues);
//
//        cursor.close();
//
//    }
//
//    public void testUpdateReview(){
//        long rowId = TestUtilities.insertMovieValues(mContext);
//        ContentValues values = TestUtilities.createReviewValues(rowId);
//
//        Uri reviewUri = mContext.getContentResolver().
//                insert(ReviewEntry.CONTENT_URI, values);
//        Long what = Long.parseLong(MovieEntry.getMovieIdFromPath(reviewUri));
//        long movieRowId = what;
//
//        // Verify we got a row back.
//        assertTrue(movieRowId != -1);
//        String newMovieReview = "This Movie Sucked!";
//        ContentValues updatedValues = new ContentValues(values);
//        //updatedValues.put(MovieEntry._ID, movieRowId);
//        updatedValues.put(ReviewEntry.REVIEW_CONTENT,newMovieReview );
//
//        // Create a cursor with observer to make sure that the content provider is notifying
//        // the observers as expected
//        Cursor locationCursor = mContext.getContentResolver().query(ReviewEntry.CONTENT_URI, null, null, null, null);
//
//        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
//        locationCursor.registerContentObserver(tco);
//
//        int count = mContext.getContentResolver().update(
//                ReviewEntry.CONTENT_URI, updatedValues, ReviewEntry.MOVIE_ID + "= ?",
//                new String[] { Long.toString(movieRowId)});
//        assertEquals(count, count);
//
//        // Test to make sure our observer is called.  If not, we throw an assertion.
//        //
//        // Students: If your code is failing here, it means that your content provider
//        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
//        tco.waitForNotificationOrFail();
//
//        locationCursor.unregisterContentObserver(tco);
//        locationCursor.close();
//
//        // A cursor is your primary interface to the query results.
//        Cursor cursor = mContext.getContentResolver().query(
//                ReviewEntry.CONTENT_URI,
//                null,   // projection
//                ReviewEntry.MOVIE_ID + " = " + movieRowId,
//                null,   // Values for the "where" clause
//                null    // sort order
//        );
//
//        TestUtilities.validateCursor("testUpdateMovie.  Error validating review entry update.",
//                cursor, updatedValues);
//
//        cursor.close();
//
//    }
//
//    public void testUpdateTrailer(){
//
//    }
//
//    // Make sure we can still delete after adding/updating stuff
//    //
//    // Student: Uncomment this test after you have completed writing the insert functionality
//    // in your provider.  It relies on insertions with testInsertReadProvider, so insert and
//    // query functionality must also be complete before this test can be used.
//    public void testInsertReadProvider() {
//        ContentValues movieValues = TestUtilities.createMovieValues();
//
//        // Register a content observer for our insert.  This time, directly with the content resolver
//        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
//        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, tco);
//
//        Uri movieUri = mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, movieValues);
//
//        // Did our content observer get called?  Students:  If this fails, your insert location
//        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
//        tco.waitForNotificationOrFail();
//        mContext.getContentResolver().unregisterContentObserver(tco);
//
//        long movieRowId = ContentUris.parseId(movieUri);
//
//        // Verify we got a row back.
//        assertTrue(movieRowId != -1);
//
//        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
//        // the round trip.
//
//        // A cursor is your primary interface to the query results.
//        Cursor cursor = mContext.getContentResolver().query(
//                MovieEntry.CONTENT_URI,
//                null, // leaving "columns" null just returns all the columns.
//                null, // cols for "where" clause
//                null, // values for "where" clause
//                null  // sort order
//        );
//
//        TestUtilities.validateCursor("testInsertReadProvider. Error validating LocationEntry.",
//                cursor, movieValues);
//
//        // Fantastic.  Now that we have a location, add some weather!
//        ContentValues reviewValues = TestUtilities.createReviewValues(movieRowId);
//        // The TestContentObserver is a one-shot class
//        tco = TestUtilities.getTestContentObserver();
//
//        mContext.getContentResolver().registerContentObserver(ReviewEntry.CONTENT_URI, true, tco);
//
//        Uri reviewInsertUri = mContext.getContentResolver()
//                .insert(ReviewEntry.CONTENT_URI, reviewValues);
//        assertTrue(reviewInsertUri != null);
//
//        // Did our content observer get called?  Students:  If this fails, your insert weather
//        // in your ContentProvider isn't calling
//        // getContext().getContentResolver().notifyChange(uri, null);
//        tco.waitForNotificationOrFail();
//        mContext.getContentResolver().unregisterContentObserver(tco);
//
//        // A cursor is your primary interface  the query results.
//        Cursor reviewCursor = mContext.getContentResolver().query(
//                ReviewEntry.CONTENT_URI,  // Table to Query
//                null, // leaving "columns" null just returns all the columns.
//                ReviewEntry.MOVIE_ID+" = "+movieRowId, // cols for "where" clause
//                null, // values for "where" clause
//                null // columns to group by
//        );
//
//        String values = DatabaseUtils.dumpCursorToString(reviewCursor);
//
//        TestUtilities.validateCursor("testInsertReadProvider. Error validating ReviewEntry insert.",
//                reviewCursor, reviewValues);
//
//
//        ContentValues trailerValues = TestUtilities.createTrailerValues(movieRowId);
//        // The TestContentObserver is a one-shot class
//        tco = TestUtilities.getTestContentObserver();
//
//        mContext.getContentResolver().registerContentObserver(TrailerEntry.CONTENT_URI, true, tco);
//
//        Uri trailerInsertUri = mContext.getContentResolver()
//                .insert(TrailerEntry.CONTENT_URI, trailerValues);
//        assertTrue(trailerInsertUri != null);
//
//        // Did our content observer get called?  Students:  If this fails, your insert weather
//        // in your ContentProvider isn't calling
//        // getContext().getContentResolver().notifyChange(uri, null);
//        tco.waitForNotificationOrFail();
//        mContext.getContentResolver().unregisterContentObserver(tco);
//
//        // A cursor is your primary interface  the query results.
//        Cursor trailerCursor = mContext.getContentResolver().query(
//                TrailerEntry.CONTENT_URI,  // Table to Query
//                null, // leaving "columns" null just returns all the columns.
//                TrailerEntry.MOVIE_ID+" = "+movieRowId, // cols for "where" clause
//                null, // values for "where" clause
//                null // columns to group by
//        );
//
//        String trailerDumpValues = DatabaseUtils.dumpCursorToString(trailerCursor);
//
//        TestUtilities.validateCursor("testInsertReadProvider. Error validating TrailerEntry insert.",
//                trailerCursor, trailerValues);
//
//    }
//
//    // Make sure we can still delete after adding/updating stuff
//    //
//    // Student: Uncomment this test after you have completed writing the delete functionality
//    // in your provider.  It relies on insertions with testInsertReadProvider, so insert and
//    // query functionality must also be complete before this test can be used.
//    public void testDeleteRecords() {
//        testInsertReadProvider();
//
//        // Register a content observer for our location delete.
//        TestUtilities.TestContentObserver movieObserver = TestUtilities.getTestContentObserver();
//        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, movieObserver);
//
//        // Register a content observer for our weather delete.
//        TestUtilities.TestContentObserver reviewObserver = TestUtilities.getTestContentObserver();
//        mContext.getContentResolver().registerContentObserver(ReviewEntry.CONTENT_URI, true, reviewObserver);
//
//        TestUtilities.TestContentObserver trailerObserver = TestUtilities.getTestContentObserver();
//        mContext.getContentResolver().registerContentObserver(TrailerEntry.CONTENT_URI, true, trailerObserver);
//
//        deleteAllRecordsFromProvider();
//
//        // Students: If either of these fail, you most-likely are not calling the
//        // getContext().getContentResolver().notifyChange(uri, null); in the ContentProvider
//        // delete.  (only if the insertReadProvider is succeeding)
//        movieObserver.waitForNotificationOrFail();
//        reviewObserver.waitForNotificationOrFail();
//        trailerObserver.waitForNotificationOrFail();
//
//        mContext.getContentResolver().unregisterContentObserver(movieObserver);
//        mContext.getContentResolver().unregisterContentObserver(reviewObserver);
//        mContext.getContentResolver().unregisterContentObserver(trailerObserver);
//    }
//
//    public void deleteAllRecordsFromProvider() {
//
//        Cursor cursor = mContext.getContentResolver().query(
//                ReviewEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        int c = cursor.getCount();
//        cursor.close();
//
//        mContext.getContentResolver().delete(
//                MovieEntry.CONTENT_URI,
//                null,
//                null
//        );
//
//        mContext.getContentResolver().delete(
//                ReviewEntry.CONTENT_URI,
//                null,
//                null
//        );
//        mContext.getContentResolver().delete(
//                TrailerEntry.CONTENT_URI,
//                null,
//                null
//        );
//
//        cursor = mContext.getContentResolver().query(
//                MovieEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from movie table during delete", 0, cursor.getCount());
//        cursor.close();
//
//        cursor = mContext.getContentResolver().query(
//                ReviewEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        int f =cursor.getCount();
//        assertEquals("Error: Records not deleted from review table during delete", 0, cursor.getCount());
//        cursor.close();
//
//        cursor = mContext.getContentResolver().query(
//                TrailerEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from trailer table during delete", 0, cursor.getCount());
//        cursor.close();
//    }
//}
