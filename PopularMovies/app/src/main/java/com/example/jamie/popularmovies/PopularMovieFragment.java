package com.example.jamie.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.jamie.popularmovies.adapters.MovieCursorAdapter;
import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.movie_objects.Movie;

import java.util.List;


public class PopularMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    public GridView gridview;
    public MovieCursorAdapter mAdapter;
    private Uri mPopularUri;
    private String sortValue;
    public static final int LOADER_ID = 0;


    private static final String[] MOVIE_COLUMNS = {
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
            MovieContract.MovieEntry._ID
    };

    public static final int COL_MOVIE_ID = 0;
    public static final int COL_POSTER_PATH = 1;
    public static final int COL_IS_ADULT = 2;
    public static final int COL_OVERVIEW = 3;
    public static final int COL_RELEASE_DATE = 4;
    public static final int COL_ORIGINAL_TITLE = 5;
    public static final int COL_ORIGINAL_LANGUAGE = 6;
    public static final int COL_TITLE = 7;
    public static final int COL_BACKDROP_PATH = 8;
    public static final int COL_POPULARITY = 9;
    public static final int COL_VOTE_COUNT = 10;
    public static final int COL_IS_VIDEO = 11;
    public static final int COL_IS_FAVORITE = 12;
    public static final int COL_VOTE_AVERAGE = 13;
    public static final int COL_ID = 15;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Cursor cur = getActivity().getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                MOVIE_COLUMNS,
                null,
                null,
                null);

        mAdapter = new MovieCursorAdapter(getActivity(), cur, 0);

        View v = inflater.inflate(R.layout.activity_main,container, false);
        gridview = (GridView) v.findViewById(R.id.gridview);
        gridview.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //mAdapter = new CustomMovieAdapter(getActivity(), new ArrayList<Movie>());
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
//
//        moviesTask.execute(mPopularUri.toString());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                getActivity(),
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


//    public class FetchPopularMoviesTask extends AsyncTask<String, Void, List<Movie>>{
//
//        @Override
//        protected void onPostExecute(List<Movie> movies) {
//            super.onPostExecute(movies);
//            if(movies != null){
//                mAdapter.clear();
//                for(Movie movie : movies) {
//                    mAdapter.add(movie);
//                }
//                mAdapter.notifyDataSetChanged();
//                gridview.setAdapter(mAdapter);
//            }
//        }
//
//        @Override
//        protected List<Movie> doInBackground(String... params) {
//            FetchRawData mRawData = new FetchRawData(params[0]);
//            ExtractJSONMovieData mData = new ExtractJSONMovieData(mRawData.fetch());
//            return mData.getMovieObjects();
//        }
//
//    }


}
