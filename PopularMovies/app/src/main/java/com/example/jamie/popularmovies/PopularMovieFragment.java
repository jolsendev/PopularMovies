package com.example.jamie.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class PopularMovieFragment extends Fragment {
    public List<Movie> mMovies;
    public GridView gridview;
    public CustomMovieAdapter mAdapter;
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

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie myMovie = mAdapter.getItem(i);
                Intent intent = new Intent(getActivity(), MovieDetailView.class);
                intent.putExtra(Utility.MOVIE_KEY, myMovie);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mAdapter = new CustomMovieAdapter(getActivity(), new ArrayList<Movie>());
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
            updateRawData();
        } else {
            // do nothing
        }
    }


    private void updateRawData() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = pref.getString(getString(R.string.pref_sort_key), getString(R.string.pref_default_sort_value));
        String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/"+sortBy;
        String MOVIE_API_KEY = "api_key";
        String API_KEY = Utility.MOVIE_API_KEY;

        mPopularUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(MOVIE_API_KEY, API_KEY).build();
        FetchPopularMoviesTask moviesTask = new FetchPopularMoviesTask();

        moviesTask.execute(mPopularUri.toString());
    }


    public class FetchPopularMoviesTask extends AsyncTask<String, Void, List<Movie>>{

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            if(movies != null){
                mAdapter.clear();
                for(Movie movie : movies) {
                    mAdapter.add(movie);
                }
                mAdapter.notifyDataSetChanged();
                gridview.setAdapter(mAdapter);
            }
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            FetchRawData mRawData = new FetchRawData(params[0]);
            ExtractJSONMovieData mData = new ExtractJSONMovieData(mRawData.fetch());
            return mData.getMovieObjects();
        }

    }


}
