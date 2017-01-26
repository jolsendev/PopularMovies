package com.example.jamie.popularmovies.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
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
import android.widget.TextView;

import com.example.jamie.popularmovies.MainActivity;
import com.example.jamie.popularmovies.MovieSettings;
import com.example.jamie.popularmovies.R;
import com.example.jamie.popularmovies.Utility;
import com.example.jamie.popularmovies.adapters.MainMovieAdapter;
import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.sync.MovieSyncAdapter;
import com.facebook.stetho.common.Util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class MainMovieFragment extends Fragment implements LoaderCallbacks<Cursor>{
    private static int mPosition;
    private SharedPreferences pref;
    private String sortValue;

    private Menu mMenu;


    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        void onItemSelected(Uri detailUri);
    }

    public interface SetPositionCallBack {
        void setPosition(int Position);
    }
    public GridView gridview;
    public MainMovieAdapter mAdapter;

    public static final int MAIN_MOVIE_LOADER = 0;
    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.MOVIE_ID,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.POSTER_PATH,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.IS_ADULT,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.OVERVIEW,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.RELEASE_DATE,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.ORIGINAL_TITLE,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.ORIGINAL_LANGUAGE,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.TITLE,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.BACKDROP_PATH,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.POPULARITY,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.VOTE_COUNT,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.IS_VIDEO,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.IS_FAVORITE,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.VOTE_AVERAGE,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.IS_MOST_POPULAR,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.IS_TOP_RATED
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



    public void addFirstMovieToDetail() {
        Uri stringUri = Utility.getFirstMovieFromPreference(getContext(), Utility.getSharedPreference(getContext()));
        ((Callback)getContext()).onItemSelected(stringUri);
        restartLoader();
    }

    public void restartLoader() {
        getLoaderManager().restartLoader(MAIN_MOVIE_LOADER, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mMenu = menu;
        inflater.inflate(R.menu.main, menu);
        if(menu != null){
            if(menu.findItem(R.id.action_share) != null){
                menu.findItem(R.id.action_share).setVisible(false);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(MAIN_MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        if(mMenu != null){
            if(mMenu.findItem(R.id.action_share) != null){
                mMenu.findItem(R.id.action_share).setVisible(false);
            }
        }
        View v = inflater.inflate(R.layout.activity_movie,container, false);
        TextView emptyView = (TextView) v.findViewById(R.id.movie_empty_view);
        gridview = (GridView) v.findViewById(R.id.gridview);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int movieId = cursor.getInt(COL_MOVIE_ID);
                Uri uri = MovieContract.MovieEntry.buildMovieUri(movieId);
                mPosition = position;
                ((Callback) getActivity()).onItemSelected(uri);
            }
        });

        gridview.setEmptyView(emptyView);
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
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortValue = pref.getString(getString(R.string.pref_sort_key), getString(R.string.pref_default_sort_value));
        createAdapterWithCursor();
        updateMovieData();
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
        super.onStart();

    }

    private void updateMovieData() {
        MovieSyncAdapter.syncImmediately(getActivity());
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
        setSelection(mPosition);
        mAdapter.swapCursor(data);
        mAdapter.swapCursor(data);
        updateEmptyView();
    }

    public void setSelection(int position) {
        if(position != gridview.INVALID_POSITION){
            gridview.setSelection(position);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public Menu getMenu() {
        return mMenu;
    }

    public void updateEmptyView(){

        if(mAdapter.getCount() == 0){
            TextView emptyView = (TextView) getView().findViewById(R.id.movie_empty_view);
            if(emptyView != null){
                int message = R.string.movie_empty_text;
                if(!Utility.isOnline(getContext())){
                    message = R.string.movie_empty_text_no_network_data;
                }
                emptyView.setText(message);
            }
        }
    }
}
