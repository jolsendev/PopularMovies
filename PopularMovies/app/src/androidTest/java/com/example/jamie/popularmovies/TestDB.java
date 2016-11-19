package com.example.jamie.popularmovies;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
import com.example.jamie.popularmovies.data.MovieContract.VideoEntry;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;
import com.example.jamie.popularmovies.data.MovieDBHelper;

import java.util.HashSet;

/**
 * Created by Jamie Olsen on 11/19/2016.
 */
public class TestDB extends AndroidTestCase {

    public static final String LOG_TAG = TestDB.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(MovieDBHelper.DATABASE_NAME);
    }

    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */
    public void setUp() {
        deleteTheDatabase();
    }

    /*
       Students: Uncomment this test once you've written the code to create the Location
       table.  Note that you will have to have chosen the same column names that I did in
       my solution for this test to compile, so if you haven't yet done that, this is
       a good time to change your column names to match mine.

       Note that this only tests that the Location table has the correct columns, since we
       give you the code for the weather table.  This test does not look at the
    */
    public void testCreateDb() throws Throwable {

        final String[] tableNames = new String[]{
                MovieEntry.TABLE_NAME,
                VideoEntry.TABLE_NAME,
                ReviewEntry.TABLE_NAME
        };

        final HashSet<String> tableNameHashSet = new HashSet<String>();

        //add tables to hash set
        for (String table : tableNames) {
            tableNameHashSet.add(table);
        }

        //delete the database and create it again...
        mContext.deleteDatabase(MovieDBHelper.DATABASE_NAME);
        SQLiteDatabase db = new MovieDBHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);


        for (int i = 0; i < tableNames.length; i++ ) {
            if(i == 0){
                assertTrue("Error: This means that the database has not been created correctly (1)",
                        c.moveToFirst());
            }else{
                assertTrue("Error: This means that the database has not been created correctly (2)",
                        c.moveToNext());
            }
        }

        //this needs to be here for the next test to work
        c.moveToFirst();

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());



        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> movieColumnHashSet = new HashSet();
        movieColumnHashSet.add(MovieEntry.MOVIE_ID);
        movieColumnHashSet.add(MovieEntry.POSTER_PATH);
        movieColumnHashSet.add(MovieEntry.IS_ADULT);
        movieColumnHashSet.add(MovieEntry.OVERVIEW);
        movieColumnHashSet.add(MovieEntry.RELEASE_DATE);
        movieColumnHashSet.add(MovieEntry.ORIGINAL_TITLE);
        movieColumnHashSet.add(MovieEntry.ORIGINAL_LANGUAGE);
        movieColumnHashSet.add(MovieEntry.TITLE);
        movieColumnHashSet.add(MovieEntry.BACKDROP_PATH);
        movieColumnHashSet.add(MovieEntry.POPULARITY);
        movieColumnHashSet.add(MovieEntry.VOTE_COUNT);
        movieColumnHashSet.add(MovieEntry.IS_VIDEO);
        movieColumnHashSet.add(MovieEntry.IS_FAVORITE);
        movieColumnHashSet.add(MovieEntry.VOTE_AVERAGE);

        final HashSet<String> videoColumnHashSet = new HashSet();
        videoColumnHashSet.add(VideoEntry.VIDEO_KEY);
        videoColumnHashSet.add(VideoEntry.VIDEO_NAME);
        videoColumnHashSet.add(VideoEntry.VIDEO_SITE);
        videoColumnHashSet.add(VideoEntry.VIDEO_SIZE);
        videoColumnHashSet.add(VideoEntry.VIDEO_TYPE);


        final HashSet<String> reviewColumnHasSet = new HashSet();
        reviewColumnHasSet.add(ReviewEntry.REVIEW_KEY);
        reviewColumnHasSet.add(ReviewEntry.REVIEW_AUTHOR);
        reviewColumnHasSet.add(ReviewEntry.REVIEW_CONTENT);


        areColumnsCorrect(db, c, MovieEntry.TABLE_NAME, movieColumnHashSet);
        areColumnsCorrect(db, c, ReviewEntry.TABLE_NAME, reviewColumnHasSet);
        areColumnsCorrect(db, c, VideoEntry.TABLE_NAME, videoColumnHashSet);


//        db.close();
    }

    private void areColumnsCorrect(SQLiteDatabase db, Cursor c, String tableName, HashSet<String> columnHashSet) {

            c = db.rawQuery("PRAGMA table_info(" + tableName + ")",
                    null);
            assertTrue("Error: This means that we were unable to query the database for "+tableName+" table.",
                    c.moveToFirst());

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            columnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required "+tableName+" entry columns",
                columnHashSet.isEmpty());

    }

}
