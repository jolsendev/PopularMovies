package com.example.jamie.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This is and example of the api call to get latest movies still in theators.

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

            if(isOnline()){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new PopularMovieFragment())
                        .commit();
            }

        }
    }

    //I got this from the stack overflow link that was in the project implementation guild. 
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create manu layout and getInflator().inflate it
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //This is where we set the preference for sorting


        return super.onOptionsItemSelected(item);
    }
}
