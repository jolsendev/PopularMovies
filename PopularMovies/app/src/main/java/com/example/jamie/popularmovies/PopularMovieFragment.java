package com.example.jamie.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.jamie.popularmovies.Adapters.MovieCursorAdapter;
import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.movie_objects.Movie;

import java.util.List;


public class PopularMovieFragment extends Fragment {
    public List<Movie> mMovies;
    public GridView gridview;
    public MovieCursorAdapter mAdapter;
    private Uri mPopularUri;
    private String sortValue;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_main,container, false);
        gridview = (GridView) v.findViewById(R.id.gridview);

        Cursor cur = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        mAdapter = new MovieCursorAdapter(getActivity(), cur, 0);

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

        if(mMovies == null || sortValue != sortBy ) {
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
