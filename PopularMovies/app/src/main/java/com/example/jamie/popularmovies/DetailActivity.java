package com.example.jamie.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.fragments.MovieDetailFragment;

public class DetailActivity extends AppCompatActivity implements FetchReviewTask.Callback, FetchTrailerTask.Callback  {

    private MovieDetailFragment mDF;
    private Bundle arguments;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("this", "that");
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent detailIntent = getIntent();
            Intent intent = new Intent(this, MainActivity.class);
            Uri uri = detailIntent.getData();
            intent.setData(uri);
            int position = detailIntent.getIntExtra(MovieContract.MovieEntry.POSITION, 0);
            intent.putExtra(MovieContract.MovieEntry.POSITION,position);
            this.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null){

            arguments = new Bundle();
            arguments.putParcelable(MovieDetailFragment.DETAIL_URI, getIntent().getData());

            mDF = new MovieDetailFragment();
            mDF.setArguments(arguments);

            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, mDF, MainActivity.DETAIL_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void RestartReviewLoader() {
        if(mDF != null){
            mDF.RestartReviewLoader();
        }
    }

    @Override
    public void RestartTrailerLoader() {
        if(mDF != null){
            mDF.RestartTrailerLoader();
        }
    }
}
