package com.example.jamie.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jamie.popularmovies.adapters.DetailReviewAdapter;
import com.example.jamie.popularmovies.adapters.DetailTrailerAdapter;
import com.example.jamie.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

public class MovieDetailView extends AppCompatActivity {

    private static ImageView imageItem;
    private static final int MOVIE_LOADER = 0;
    private static final int REVIEW_LOADER = 1;
    private static final int TRAILER_LOADER = 2;

    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.TITLE,
            MovieContract.MovieEntry.POSTER_PATH,
            MovieContract.MovieEntry.OVERVIEW,
            MovieContract.MovieEntry.VOTE_AVERAGE,
            MovieContract.MovieEntry.FAVORITE,
            MovieContract.MovieEntry.RELEASE_DATE,
    };

    private static final String[] REVIEW_COLUMNS = {
            MovieContract.ReviewEntry._ID,
            MovieContract.ReviewEntry.TABLE_NAME+"."+MovieContract.ReviewEntry.MOVIE_ID,
            MovieContract.ReviewEntry.REVIEW_AUTHOR,
            MovieContract.ReviewEntry.REVIEW_CONTENT,
            MovieContract.ReviewEntry.REVIEW_URL
    };


    public static final String[] TRAILER_COLUMNS = {
            MovieContract.TrailerEntry._ID,
            MovieContract.TrailerEntry.TRAILER_NAME,
            MovieContract.TrailerEntry.TRAILER_SIZE,
            MovieContract.TrailerEntry.TRAILER_SOURCE,
            MovieContract.TrailerEntry.TRAILER_TYPE

    };

    public static final int COL_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_POSTER_PATH = 2;
    public static final int COL_OVERVIEW = 3;
    public static final int COL_VOTE_AVERAGE = 4;
    public static final int COL_FAVORITE = 5;
    public static final int COL_RELEASE_DATE = 6;

    public static final int COL_REVIEW_ID = 0;
    public static final int COL_REVIEW_MOVIE_ID = 1;
    public static final int COL_REVIEW_AUTHOR = 2;
    public static final int COL_REVIEW_CONTENT = 3;
    public static final int COL_REVIEW_URL = 4;


    public static final int COL_TRAILER_ID = 0;
    public static final int COL_TRAILER_NAME = 1;
    public static final int COL_TRAILER_SIZE = 2;
    public static final int COL_TRAILER_SOURCE = 3;
    public static final int COL_TRAILER_TYPE = 4;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_movie_detail_view);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.movie_container, new MovieDetailFragment()).commit();
        }
    }

    public static class MovieDetailFragment extends Fragment implements LoaderCallbacks<Cursor> {

        private static DetailReviewAdapter mAdapter;
        private LinearLayout titleLayout;
        private Parcelable mUri;
        private DetailTrailerAdapter trailerAdapter;
        private DetailReviewAdapter reviewAdapter;

        private ListView reviewListView;
        private LinearLayout movieLayout;
        private TextView mMovieTitle;
        private ImageView mMovieImage;
        private TextView mMovieRating;
        private TextView mReleaseDate;
        private TextView mMovieOverview;
        private ListView trailerListView;
        public MovieDetailFragment() {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_movie_detail_view, container, false);

            trailerListView = (ListView) rootView.findViewById(R.id.trailer_list_view);
            reviewListView = (ListView) rootView.findViewById(R.id.review_list_view);
            movieLayout = (LinearLayout) rootView.findViewById(R.id.movie_container);
            mMovieTitle = (TextView)movieLayout.findViewById(R.id.movie_title);
            mMovieImage = (ImageView) movieLayout.findViewById(R.id.detail_movie_image);
            mMovieRating = (TextView) movieLayout.findViewById(R.id.movie_rating);
            mReleaseDate = (TextView) movieLayout.findViewById(R.id.movie_release_date);
            mMovieOverview = (TextView) movieLayout.findViewById(R.id.movie_overview);

            return rootView;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            getLoaderManager().initLoader(MOVIE_LOADER, null, this);
            getLoaderManager().initLoader(REVIEW_LOADER, null, this);
            getLoaderManager().initLoader(TRAILER_LOADER, null, this);
            reviewAdapter = new DetailReviewAdapter(getContext(), null, 0);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            CursorLoader cursor = null;
            Intent intent = getActivity().getIntent();
            String movie = MovieContract.MovieEntry.getMovieIdFromPath(intent.getData());
            int movie_id = Integer.parseInt(movie);
            Uri movieUri = MovieContract.MovieEntry.buildMovieUri(movie_id);
            Uri reviewUri = MovieContract.MovieEntry.buildMovieReview(movie_id);
            Uri trailerUri = MovieContract.MovieEntry.buildMovieTrailer(movie_id);

            if (intent == null) {
                return null;
            }

            switch (id){
                case MOVIE_LOADER:{
                    cursor = new CursorLoader(
                            getActivity(),
                            movieUri,
                            MOVIE_COLUMNS,
                            null, //this was what I was missing
                            new String[]{movie}, //this was what I was missing
                            null
                    );
                    break;
                }
                case REVIEW_LOADER:{
                    cursor = new CursorLoader(
                            getActivity(),
                            reviewUri,
                            REVIEW_COLUMNS,
                            null, //this was what I was missing
                            new String[]{movie}, //this was what I was missing
                            null
                    );
                    break;
                }

                case TRAILER_LOADER:{
                    cursor = new CursorLoader(
                            getActivity(),
                            trailerUri,
                            TRAILER_COLUMNS,
                            null, //this was what I was missing
                            new String[]{movie}, //this was what I was missing
                            null
                    );
                    break;
                }
                default:
                    break;
            }

            return cursor;
        }


        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

            int id = loader.getId();
            switch (id){
                case TRAILER_LOADER:{
                    trailerAdapter = new DetailTrailerAdapter(getContext(), cursor, 0);
                    trailerListView.setAdapter(trailerAdapter);
                    break;
                }
                case REVIEW_LOADER:{

                    reviewAdapter.swapCursor(cursor);
                    reviewListView.setAdapter(reviewAdapter);
                    break;
                }
                case MOVIE_LOADER:{

                    mMovieTitle.setText(cursor.getString(COL_TITLE));
                    mMovieRating.setText(cursor.getString(COL_VOTE_AVERAGE));
                    mReleaseDate.setText(cursor.getString(COL_RELEASE_DATE));
                    mMovieOverview.setText(cursor.getString(COL_OVERVIEW));

                    ImageView imageItem = (ImageView) movieLayout.findViewById(R.id.detail_movie_image);
                    Picasso.with(getContext())
                            .load(Utility.getImagePath(cursor.getString(COL_POSTER_PATH)))
                            .into(imageItem);


                    break;
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }
}
