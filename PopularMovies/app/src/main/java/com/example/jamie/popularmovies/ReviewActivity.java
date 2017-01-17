package com.example.jamie.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jamie.popularmovies.data.MovieContract;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TextView fullReviewTextView = (TextView) findViewById(R.id.full_review_view);
        String review = getIntent().getExtras().getString(MovieContract.ReviewEntry.REVIEW_CONTENT);
        fullReviewTextView.setText(review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
