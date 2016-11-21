package com.example.jamie.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;

import java.util.Map;
import java.util.Set;

/**
 * Created by Jamie Olsen on 11/20/2016.
 */
public class TestUtilities extends AndroidTestCase {


    static ContentValues createMovieValues(){
        ContentValues values = new ContentValues();

        values.put(MovieEntry.MOVIE_ID, 284052);
        values.put(MovieEntry.POSTER_PATH, "xfWac8MTYDxujaxgPVcRD9yZaul.jpg");
        values.put(MovieEntry.IS_ADULT, 0);
        values.put(MovieEntry.OVERVIEW, "After his career is destroyed, a brilliant but arrogant surgeon gets a new lease on life when a sorcerer takes him under his wing and trains him to defend the world against evil.");
        values.put(MovieEntry.RELEASE_DATE, "2016-10-25");
        values.put(MovieEntry.ORIGINAL_TITLE, "Doctor Strange");
        values.put(MovieEntry.ORIGINAL_LANGUAGE, "en");
        values.put(MovieEntry.TITLE, "Doctor Strange");
        values.put(MovieEntry.BACKDROP_PATH,"tFI8VLMgSTTU38i8TIsklfqS9Nl.jpg");
        values.put(MovieEntry.POPULARITY, 46.696892);
        values.put(MovieEntry.VOTE_COUNT, 912);
        values.put(MovieEntry.IS_VIDEO, 0);
        values.put(MovieEntry.IS_FAVORITE, 0);
        values.put(MovieEntry.VOTE_AVERAGE, 6.8);

        return values;

    }

    static ContentValues createReviewValues(long movieId){

        ContentValues values = new ContentValues();

        values.put(ReviewEntry.REVIEW_KEY, movieId);
        values.put(ReviewEntry.REVIEW_AUTHOR, "iheardthatmoviewas");
        values.put(ReviewEntry.REVIEW_CONTENT, "All continues to be well in the Marvel Comics Universe as the film adaptation of another mischievous and majestic superhero from Stan Lee’s printed page empire emerges and reigns supreme on the big screen. The latest cure from the Marvel movie bag of explosive tricks is the entry of the dazzling and decorative **Doctor Strange**. Armed with a collection of notable performers, a convincing colorful scope of visual vibrancy and a hearty touch of spiritual and reflective potency the");
        values.put(ReviewEntry.REVIEW_URL, "https://www.themoviedb.org/review/581e7bad9251410a0e01146e");
        return values;
    }

    static ContentValues createVideoValues(long movieId){

        ContentValues values = new ContentValues();
        values.put(MovieContract.TrailerEntry.VIDEO_KEY, movieId);
        values.put(TrailerEntry.VIDEO_NAME, "Doctor Strange (2016) Official Trailer 2");
        values.put(MovieContract.TrailerEntry.VIDEO_SITE, "YouTube");
        values.put(TrailerEntry.VIDEO_SIZE, 1080);
        values.put(TrailerEntry.VIDEO_TYPE, "Teaser");

        return values;
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {

            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);

            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);

            if(columnName.equals(MovieEntry.POPULARITY)){
                String expectedValue = entry.getValue().toString();
                assertEquals("Value '" + entry.getValue().toString() +
                        "' did not match the expected value '" +
                        expectedValue + "'. " + error, Double.parseDouble(expectedValue), valueCursor.getDouble(idx));
            }else{
                String expectedValue = entry.getValue().toString();
                assertEquals("Value '" + entry.getValue().toString() +
                        "' did not match the expected value '" +
                        expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
            }

        }
    }

}
