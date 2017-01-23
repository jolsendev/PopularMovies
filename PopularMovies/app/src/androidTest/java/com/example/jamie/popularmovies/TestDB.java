//package com.example.jamie.popularmovies;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.test.AndroidTestCase;
//import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
//import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;
//import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;
//import com.example.jamie.popularmovies.data.MovieDBHelper;
//
//import java.util.HashSet;
//
///**
// * Created by Jamie Olsen on 11/19/2016.
// */
//public class TestDB extends AndroidTestCase {
//
//    public static final String LOG_TAG = TestDB.class.getSimpleName();
//
//    // Since we want each test to start with a clean slate
//    void deleteTheDatabase() {
//        mContext.deleteDatabase(MovieDBHelper.DATABASE_NAME);
//    }
//
//    /*
//        This function gets called before each test is executed to delete the database.  This makes
//        sure that we always have a clean test.
//     */
//    public void setUp() {
//        deleteTheDatabase();
//    }
//
//    /*
//       Students: Uncomment this test once you've written the code to create the Location
//       table.  Note that you will have to have chosen the same column names that I did in
//       my solution for this test to compile, so if you haven't yet done that, this is
//       a good time to change your column names to match mine.
//
//       Note that this only tests that the Location table has the correct columns, since we
//       give you the code for the weather table.  This test does not look at the
//    */
//    public void testCreateDb() throws Throwable {
//
//        final String[] tableNames = new String[]{
//                MovieEntry.TABLE_NAME,
//                TrailerEntry.TABLE_NAME,
//                ReviewEntry.TABLE_NAME
//        };
//
//        final HashSet<String> tableNameHashSet = new HashSet<String>();
//
//        //add tables to hash set
//        for (String table : tableNames) {
//            tableNameHashSet.add(table);
//        }
//
//        //delete the database and create it again...
//        mContext.deleteDatabase(MovieDBHelper.DATABASE_NAME);
//        SQLiteDatabase db = new MovieDBHelper(
//                this.mContext).getWritableDatabase();
//        assertEquals(true, db.isOpen());
//
//        // have we created the tables we want?
//        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
//
//
//        for (int i = 0; i < tableNames.length; i++ ) {
//            if(i == 0){
//                assertTrue("Error: This means that the database has not been created correctly (1)",
//                        c.moveToFirst());
//            }else{
//                assertTrue("Error: This means that the database has not been created correctly (2)",
//                        c.moveToNext());
//            }
//        }
//
//        //this needs to be here for the next test to work
//        c.moveToFirst();
//
//        // verify that the tables have been created
//        do {
//            tableNameHashSet.remove(c.getString(0));
//        } while( c.moveToNext() );
//
//        // if this fails, it means that your database doesn't contain both the location entry
//        // and weather entry tables
//        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
//                tableNameHashSet.isEmpty());
//
//
//
//        // Build a HashSet of all of the column names we want to look for
//        final HashSet<String> movieColumnHashSet = new HashSet();
//        movieColumnHashSet.add(MovieEntry.POSTER_PATH);
//        movieColumnHashSet.add(MovieEntry.IS_ADULT);
//        movieColumnHashSet.add(MovieEntry.OVERVIEW);
//        movieColumnHashSet.add(MovieEntry.RELEASE_DATE);
//        movieColumnHashSet.add(MovieEntry.ORIGINAL_TITLE);
//        movieColumnHashSet.add(MovieEntry.ORIGINAL_LANGUAGE);
//        movieColumnHashSet.add(MovieEntry.TITLE);
//        movieColumnHashSet.add(MovieEntry.BACKDROP_PATH);
//        movieColumnHashSet.add(MovieEntry.POPULARITY);
//        movieColumnHashSet.add(MovieEntry.VOTE_COUNT);
//        movieColumnHashSet.add(MovieEntry.IS_VIDEO);
//        movieColumnHashSet.add(MovieEntry.IS_FAVORITE);
//        movieColumnHashSet.add(MovieEntry.VOTE_AVERAGE);
//
//        final HashSet<String> videoColumnHashSet = new HashSet();
//        videoColumnHashSet.add(TrailerEntry.MOVIE_ID);
//        videoColumnHashSet.add(TrailerEntry.TRAILER_NAME);
//        videoColumnHashSet.add(TrailerEntry.TRAILER_SIZE);
//        videoColumnHashSet.add(TrailerEntry.TRAILER_TYPE);
//
//
//        final HashSet<String> reviewColumnHasSet = new HashSet();
//        reviewColumnHasSet.add(ReviewEntry.MOVIE_ID);
//        reviewColumnHasSet.add(ReviewEntry.REVIEW_AUTHOR);
//        reviewColumnHasSet.add(ReviewEntry.REVIEW_CONTENT);
//
//
//        areColumnsCorrect(db, c, MovieEntry.TABLE_NAME, movieColumnHashSet);
//        areColumnsCorrect(db, c, ReviewEntry.TABLE_NAME, reviewColumnHasSet);
//        areColumnsCorrect(db, c, TrailerEntry.TABLE_NAME, videoColumnHashSet);
//
//
////        db.close();
//    }
//
//    private void areColumnsCorrect(SQLiteDatabase db, Cursor c, String tableName, HashSet<String> columnHashSet) {
//
//            c = db.rawQuery("PRAGMA table_info(" + tableName + ")",
//                    null);
//            assertTrue("Error: This means that we were unable to query the database for "+tableName+" table.",
//                    c.moveToFirst());
//
//        int columnNameIndex = c.getColumnIndex("name");
//        do {
//            String columnName = c.getString(columnNameIndex);
//            columnHashSet.remove(columnName);
//        } while(c.moveToNext());
//
//        // if this fails, it means that your database doesn't contain all of the required location
//        // entry columns
//        assertTrue("Error: The database doesn't contain all of the required "+tableName+" entry columns",
//                columnHashSet.isEmpty());
//
//    }
//
//    public void testReviewTable(){
//        //Get reference to writable database
//        long locationRowId = insertMovie();
//
//        // Make sure we have a valid row ID.
//        assertFalse("Error: Location Not Inserted Correctly", locationRowId == -1L);
//
//        // First step: Get reference to writable database
//        // If there's an error in those massive SQL table creation Strings,
//        // errors will be thrown here when you try to get a writable database.
//        MovieDBHelper dbHelper = new MovieDBHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Second Step (Weather): Create weather values
//        ContentValues reviewValues = TestUtilities.createReviewValues(locationRowId);
//
//        // Third Step (Weather): Insert ContentValues into database and get a row ID back
//        long reviewRowId = db.insert(ReviewEntry.TABLE_NAME, null, reviewValues);
//        assertTrue(reviewRowId != -1);
//
//        // Fourth Step: Query the database and receive a Cursor back
//        // A cursor is your primary interface to the query results.
//        Cursor reviewCursor = db.query(
//                ReviewEntry.TABLE_NAME,  // Table to Query
//                null, // leaving "columns" null just returns all the columns.
//                null, // cols for "where" clause
//                null, // values for "where" clause
//                null, // columns to group by
//                null, // columns to filter by row groups
//                null  // sort order
//        );
//
//        // Move the cursor to the first valid database row and check to see if we have any rows
//        assertTrue( "Error: No Records returned from location query", reviewCursor.moveToFirst() );
//
//        // Fifth Step: Validate the location Query
//        TestUtilities.validateCurrentRecord("testInsertReadDb weatherEntry failed to validate",
//                reviewCursor, reviewValues);
//
//        // Move the cursor to demonstrate that there is only one record in the database
//        assertFalse( "Error: More than one record returned from weather query",
//                reviewCursor.moveToNext() );
//
//        // Sixth Step: Close cursor and database
//        reviewCursor.close();
//        dbHelper.close();
//        //create contentvalues to insert
//        //insert the content values get a row bock (and make suer it is not -1)
//        //query the database
//        //validate data in a resulting cursor with the original content values
//        //close cursor and database
//
//    }
//
//    private long insertMovie() {
//
//        // First step: Get reference to writable database
//        // If there's an error in those massive SQL table creation Strings,
//        // errors will be thrown here when you try to get a writable database.
//        MovieDBHelper dbHelper = new MovieDBHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Second Step: Create ContentValues of what you want to insert
//        // (you can use the createNorthPoleLocationValues if you wish)
//        ContentValues movieValues = TestUtilities.createMovieValues();
//
//        // Third Step: Insert ContentValues into database and get a row ID back
//        long movieRowId;
//        movieRowId = db.insert(MovieEntry.TABLE_NAME, null, movieValues);
//        assertTrue(movieRowId != -1);
//        if(movieRowId != -1){
//            movieRowId = (int)movieValues.get(MovieEntry.MOVIE_ID);
//        }
//
//        // Verify we got a row back.
//
//
//        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
//        // the round trip.
//
//        // Fourth Step: Query the database and receive a Cursor back
//        // A cursor is your primary interface to the query results.
//        Cursor cursor = db.query(
//                MovieEntry.TABLE_NAME,  // Table to Query
//                null, // all columns
//                null, // Columns for the "where" clause
//                null, // Values for the "where" clause
//                null, // columns to group by
//                null, // columns to filter by row groups
//                null // sort order
//        );
//
//        // Move the cursor to a valid database row and check to see if we got any records back
//        // from the query
//        assertTrue( "Error: No Records returned from location query", cursor.moveToFirst() );
//
//        // Fifth Step: Validate data in resulting Cursor with the original ContentValues
//        // (you can use the validateCurrentRecord function in TestUtilities to validate the
//        // query if you like)
//        TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed",
//                cursor, movieValues);
//
//        // Move the cursor to demonstrate that there is only one record in the database
//        assertFalse( "Error: More than one record returned from location query",
//                cursor.moveToNext() );
//
//        // Sixth Step: Close Cursor and Database
//        cursor.close();
//        db.close();
//        return movieRowId;
//    }
//
////    public void TestReviewTable(){
////
////    }
//
//    public void testVideoTable(){
//        //Get reference to writable database
//        long locationRowId = insertMovie();
//
//        // Make sure we have a valid row ID.
//        assertFalse("Error: Location Not Inserted Correctly", locationRowId == -1L);
//
//        // First step: Get reference to writable database
//        // If there's an error in those massive SQL table creation Strings,
//        // errors will be thrown here when you try to get a writable database.
//        MovieDBHelper dbHelper = new MovieDBHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Second Step (Weather): Create weather values
//        ContentValues videoValues = TestUtilities.createTrailerValues(locationRowId);
//
//        // Third Step (Weather): Insert ContentValues into database and get a row ID back
//        long videoRowId = db.insert(TrailerEntry.TABLE_NAME, null, videoValues);
//        assertTrue(videoRowId != -1);
//
//        // Fourth Step: Query the database and receive a Cursor back
//        // A cursor is your primary interface to the query results.
//        Cursor videoCursor = db.query(
//                TrailerEntry.TABLE_NAME,  // Table to Query
//                null, // leaving "columns" null just returns all the columns.
//                null, // cols for "where" clause
//                null, // values for "where" clause
//                null, // columns to group by
//                null, // columns to filter by row groups
//                null  // sort order
//        );
//
//        // Move the cursor to the first valid database row and check to see if we have any rows
//        assertTrue( "Error: No Records returned from location query", videoCursor.moveToFirst() );
//
//        // Fifth Step: Validate the location Query
//        TestUtilities.validateCurrentRecord("testInsertReadDb weatherEntry failed to validate",
//                videoCursor, videoValues);
//
//        // Move the cursor to demonstrate that there is only one record in the database
//        assertFalse( "Error: More than one record returned from weather query",
//                videoCursor.moveToNext() );
//
//        // Sixth Step: Close cursor and database
//        videoCursor.close();
//        dbHelper.close();
//        //create contentvalues to insert
//        //insert the content values get a row bock (and make suer it is not -1)
//        //query the database
//        //validate data in a resulting cursor with the original content values
//        //close cursor and database
//
//    }
//
//    public void TestMovieWithReviews(){
//
//    }
//
//    public void TestVideoWithReviews(){
//
//    }
//
//}
