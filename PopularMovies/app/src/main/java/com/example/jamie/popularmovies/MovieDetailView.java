package com.example.jamie.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
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

        //Intent intent = getIntent();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.movie_container, new MovieDetailFragment()).commit();
        }
    }



    public static class MovieDetailFragment extends Fragment implements LoaderCallbacks<Cursor>{

        private static ReviewCursorAdapter mAdapter;
        private LinearLayout titleLayout;
        private TextView mMovieTitle;
        private TextView mMovieRating;
        private TextView mReleaseDate;
        private TextView mOverView;
        private Parcelable mUri;

        public MovieDetailFragment(){

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            Bundle arguments = getArguments();
//            if (arguments != null) {
//                mUri = arguments.getParcelable();
//            }
            View rootView = inflater.inflate(R.layout.activity_movie_detail_view, container, false);
            imageItem = (ImageView) rootView.findViewById(R.id.detail_movie_image);
            titleLayout = (LinearLayout) rootView.findViewById(R.id.movie_container);

            mMovieTitle = (TextView) titleLayout.findViewById(R.id.movie_title);
            mMovieRating = (TextView) rootView.findViewById(R.id.movie_rating);
            mReleaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
            mOverView = (TextView) rootView.findViewById(R.id.movie_overview);

           return rootView;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            getLoaderManager().initLoader(LOADER_ID, null, this);
            super.onActivityCreated(savedInstanceState);
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
            int count = data.getCount();
            if (data != null && data.moveToFirst()) {
                String title = data.getString(MovieDetailView.COL_TITLE);
                mMovieTitle.setText(title);


                String rating = Double.parseDouble(String.format("%.1f", data.getString(MovieDetailView.COL_VOTE_AVERAGE))) + "/10";
                mMovieRating.setText(rating);


                String[] splitDate = data.getString(MovieDetailView.COL_RELEASE_DATE).split("-");
                mReleaseDate.setText(splitDate[0]);


                mOverView.setText(data.getString(MovieDetailView.COL_OVERVIEW));

                Picasso.with(getActivity())
                        .load(data.getString(MovieDetailView.COL_BACKDROP_PATH))
                        .placeholder(R.drawable.popcorntime)
                        .into(imageItem);
            }


        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }
}
