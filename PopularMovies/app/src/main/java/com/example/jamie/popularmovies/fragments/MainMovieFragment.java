package com.example.jamie.popularmovies.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.jamie.popularmovies.FetchMovieTask;
import com.example.jamie.popularmovies.MovieDetailView;
import com.example.jamie.popularmovies.MovieSettings;
import com.example.jamie.popularmovies.R;
import com.example.jamie.popularmovies.Utility;
import com.example.jamie.popularmovies.adapters.MainMovieAdapter;
import com.example.jamie.popularmovies.data.MovieContract;


public class MainMovieFragment extends Fragment implements LoaderCallbacks<Cursor>{
    public GridView gridview;
    public MainMovieAdapter mAdapter;
    private Uri mPopularUri;
    private String sortValue;
    public static final int LOADER_ID = 0;
    public static final int LOADER_ID1 = 1;




    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.MOVIE_ID,
            MovieContract.MovieEntry.POSTER_PATH,
            MovieContract.MovieEntry.IS_ADULT,
            MovieContract.MovieEntry.OVERVIEW,
            MovieContract.MovieEntry.RELEASE_DATE,
            MovieContract.MovieEntry.ORIGINAL_TITLE,
            MovieContract.MovieEntry.ORIGINAL_LANGUAGE,
            MovieContract.MovieEntry.TITLE,
            MovieContract.MovieEntry.BACKDROP_PATH,
            MovieContract.MovieEntry.POPULARITY,
            MovieContract.MovieEntry.VOTE_COUNT,
            MovieContract.MovieEntry.IS_VIDEO,
            MovieContract.MovieEntry.IS_FAVORITE,
            MovieContract.MovieEntry.VOTE_AVERAGE,
            MovieContract.MovieEntry.IS_MOST_POPULAR,
            MovieContract.MovieEntry.IS_TOP_RATED
    };

    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_POSTER_PATH = 2;
    public static final int COL_IS_ADULT = 3;
    public static final int COL_OVERVIEW = 4;
    public static final int COL_RELEASE_DATE = 5;
    public static final int COL_ORIGINAL_TITLE = 6;
    public static final int COL_ORIGINAL_LANGUAGE = 7;
    public static final int COL_TITLE = 8;
    public static final int COL_BACKDROP_PATH = 9;
    public static final int COL_POPULARITY = 10;
    public static final int COL_VOTE_COUNT = 11;
    public static final int COL_IS_VIDEO = 12;
    public static final int COL_IS_FAVORITE = 13;
    public static final int COL_VOTE_AVERAGE = 14;
    public static final int COL_IS_MOST_POPULAR = 15;
    public static final int COL_IS_TOP_RATED = 16;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null, this);
        getLoaderManager().initLoader(LOADER_ID1, null, this);
        super.onActivityCreated(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        updateMovieData();

        createAdapterWithCursor();

        View v = inflater.inflate(R.layout.activity_main,container, false);
        gridview = (GridView) v.findViewById(R.id.gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int movieId = cursor.getInt(COL_MOVIE_ID);
                Uri uri = MovieContract.MovieEntry.buildMovieDetailUri(movieId);
                Intent intent = new Intent(getActivity(), MovieDetailView.class).
                        setData(uri);
                startActivity(intent);
            }
        });

        gridview.setAdapter(mAdapter);
        return v;
    }

    private void createAdapterWithCursor() {
        String sortBy = Utility.getSharedPreference(getActivity());
        Uri uri = null;
        switch(sortBy){

            case MovieContract.MovieEntry.TOP_RATED:{
                uri = MovieContract.MovieEntry.buildTopRatedUri();
                break;
            }
            case MovieContract.MovieEntry.MOST_POPULAR:{
                uri = MovieContract.MovieEntry.buildPopularUri();
                break;
            }
            case MovieContract.MovieEntry.FAVORITE:{
                uri = MovieContract.MovieEntry.buildFavoriteUri();
                break;
            }
        }

        if(uri != null){
            Cursor cur = getActivity().getContentResolver().query(
                    uri,
                    MOVIE_COLUMNS,
                    null,
                    null,
                    null);

            mAdapter = new MainMovieAdapter(getActivity(), cur, 0);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortValue = pref.getString(getString(R.string.pref_sort_key), getString(R.string.pref_default_sort_value));
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.sort_by_setting){
            Intent intent = new Intent(getActivity(),MovieSettings.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {

        updateMovieData();
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = pref.getString(getString(R.string.pref_sort_key), getString(R.string.pref_default_sort_value));
        if(sortValue != sortBy ) {
            updateMovieData();
        } else {
            // do nothing
        }
    }

    private void updateMovieData() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = pref.getString(getString(R.string.pref_sort_key), getString(R.string.pref_default_sort_value));
        String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/"+sortBy;

        String MOVIE_API_KEY = "api_key";
        String API_KEY = Utility.MOVIE_API_KEY;

        mPopularUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(MOVIE_API_KEY, API_KEY).build();

        FetchMovieTask moviesTask = new FetchMovieTask(getContext());
        moviesTask.execute(mPopularUri.toString());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortBy = Utility.getSharedPreference(getContext());
        String selection = "";
        switch (sortBy){
            case MovieContract.MovieEntry.IS_MOST_POPULAR:{
                selection = MovieContract.MovieEntry.IS_MOST_POPULAR+" = 1";
                break;
            }
            case MovieContract.MovieEntry.IS_TOP_RATED:{
                selection = MovieContract.MovieEntry.TOP_RATED+" = 1";
                break;
            }
            case MovieContract.MovieEntry.IS_FAVORITE:{
                selection = MovieContract.MovieEntry.FAVORITE+" = 1";
                break;
            }
            default:
                break;
        }

        return new CursorLoader(
                getActivity(),
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                selection,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //loader.getId();
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
