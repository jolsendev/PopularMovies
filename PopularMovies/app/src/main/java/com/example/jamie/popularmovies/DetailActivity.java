package com.example.jamie.popularmovies;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.fragments.MovieDetailFragment;

public class DetailActivity extends AppCompatActivity implements FetchReviewTask.Callback, FetchTrailerTask.Callback  {

    private MovieDetailFragment mDF;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    private void SavePreferences(){
//        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(POSITION, );
//        editor.commit();   // I missed to save the data to preference here,.
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(savedInstanceState == null){

            Bundle arguments = new Bundle();
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
