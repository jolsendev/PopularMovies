package com.example.jamie.popularmovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.fragments.MainMovieFragment;
import com.example.jamie.popularmovies.fragments.MovieDetailFragment;

public class ReviewActivity extends AppCompatActivity {

    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        TextView fullReviewTextView = (TextView) findViewById(R.id.full_review_view);
        String review = getIntent().getExtras().getString(MovieContract.ReviewEntry.REVIEW_CONTENT);
        mUri = getIntent().getData();
        fullReviewTextView.setText(review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
