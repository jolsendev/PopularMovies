package com.example.jamie.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.fragments.MainMovieFragment;
import com.example.jamie.popularmovies.fragments.MovieDetailFragment;
import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;

import static com.example.jamie.popularmovies.fragments.MovieDetailFragment.DETAIL_URI;


public class MainActivity extends AppCompatActivity implements MainMovieFragment.Callback ,FetchReviewTask.Callback, FetchTrailerTask.Callback, MainMovieFragment.SetPositionCallBack {


    public static final String DETAIL_FRAGMENT_TAG = "DFT";
    private boolean mTwoPane;
    private String mPreference;
    private MovieDetailFragment mDF;
    private int mPosition;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreference = Utility.getSharedPreference(this);
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(new DumperPluginsProvider() {
                    @Override
                    public Iterable<DumperPlugin> get() {
                        return new Stetho.DefaultDumperPluginsBuilder(getApplicationContext())
                                .provide(new MyDumperPlugin())
                                .finish();
                    }
                })
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

        if(isOnline()){
            setContentView(R.layout.activity_main);
        }

        if(findViewById(R.id.movie_detail_container) != null){
            mTwoPane = true;
            if (savedInstanceState != null) {
                if(isOnline()){
                    MovieDetailFragment mDF = (MovieDetailFragment)getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
                    if(mDF != null){
                        Bundle args = mDF.getArguments();
                        String potentialUri = args.getString(DETAIL_URI);
                        if(potentialUri != null){
                            Uri uri = Uri.parse(potentialUri);
                            onItemSelected(uri);
                        }

                    }else{
                        Uri uri = Utility.getFirstMovieFromPreference(this, mPreference);
                        onItemSelected(uri);
                    }
                }
            }
        }else{
            mTwoPane = false;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPosition = savedInstanceState.getInt(MovieContract.MovieEntry.POSITION);
    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }

    @Override
    protected void onResume() {

        super.onResume();
        MainMovieFragment mMF = (MainMovieFragment) getSupportFragmentManager().findFragmentById(R.id.movie_fragment);
        String preference = Utility.getSharedPreference(this);

        if (mMF != null) {
            mMF.setSelection(mPosition);
            if (preference != null && mTwoPane){
                mMF.onSortPreferenceChanged();
                mPreference = preference;
            }
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Uri detailUri) {

        mUri = detailUri;

        if(mTwoPane){
            Bundle args = new Bundle();
            args.putParcelable(DETAIL_URI, detailUri);
            mDF = new MovieDetailFragment();
            mDF.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, mDF, DETAIL_FRAGMENT_TAG).commit();
        }else{
            Intent intent = new Intent(this, DetailActivity.class).
            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).
            setData(detailUri);
            intent.putExtra(MovieContract.MovieEntry.POSITION, mPosition);
            startActivity(intent);
        }
    }

    @Override
    public void RestartReviewLoader() {
        if(mTwoPane){
            if(mDF != null){
                mDF.RestartReviewLoader();
            }
        }
    }

    @Override
    public void RestartTrailerLoader() {
        if(mTwoPane){
            if(mDF != null){
                mDF.RestartTrailerLoader();
            }
        }
    }

    @Override
    public void setPosition(int Position) {
        mPosition = Position;
    }

    private class MyDumperPlugin implements DumperPlugin {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public void dump(DumperContext dumpContext) throws DumpException {

        }
    }
}
