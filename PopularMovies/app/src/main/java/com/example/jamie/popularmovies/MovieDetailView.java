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
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jamie.popularmovies.adapters.MovieCursorAdapter;
import com.example.jamie.popularmovies.adapters.ReviewCursorAdapter;
import com.example.jamie.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

public class MovieDetailView extends AppCompatActivity {

    private static ImageView imageItem;
    private static final int LOADER_ID = 1;

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
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.IS_MOST_POPULAR,
            MovieContract.MovieEntry.IS_TOP_RATED
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
    public static final int COL_IS_MOST_POPULAR = 16;
    public static final int COL_IS_TOP_RATED = 17;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_movie_detail_view);

        Intent intent = getIntent();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.movie_container, new MovieDetailFragment()).commit();
        }
    }



    public static class MovieDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
        private static ReviewCursorAdapter mAdapter;
        public MovieDetailFragment(){

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_movie_detail_view, container, false);
            //createAdapterWithCursor();
//
//            imageItem = (ImageView) rootView.findViewById(R.id.detail_movie_image);
//            LinearLayout titleLayout = (LinearLayout) rootView.findViewById(R.id.movie_container);
//
//            TextView mMovieTitle = (TextView) titleLayout.findViewById(R.id.movie_title);
//            mMovieTitle.setText(mMovie.getTitle());
//
//            TextView mMovieRating = (TextView) rootView.findViewById(R.id.movie_rating);
//            String rating = Double.parseDouble(String.format("%.1f",mMovie.getVoteAverage()))+"/10";
//            mMovieRating.setText(rating);
//
//            TextView mReleaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
//            String[] splitDate = mMovie.getReleaseDate().split("-");
//            mReleaseDate.setText(splitDate[0]);
//
//            TextView mOverView = (TextView) rootView.findViewById(R.id.movie_overview);
//            mOverView.setText(mMovie.getOverview());
//
//            Picasso.with(getActivity())
//                    .load(mMovie.getPosterPath())
//                    .placeholder(R.drawable.popcorntime)
//                    .into(imageItem);
           return rootView;
        }
        private void createAdapterWithCursor() {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String sortBy = pref.getString(getString(R.string.pref_sort_key), getString(R.string.pref_default_sort_value));
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

            Cursor cur = getActivity().getContentResolver().query(
                    uri,
                    MOVIE_COLUMNS,
                    null,
                    null,
                    null);

            mAdapter = new ReviewCursorAdapter(getActivity(), cur, 1);
        }
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Intent intent = getActivity().getIntent();
            if (intent == null) {
                return null;
            }

            return new CursorLoader(
                    getActivity(),
                    intent.getData(),
                    MOVIE_COLUMNS,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }
}
