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
import android.widget.TextView;

import com.example.jamie.popularmovies.adapters.DetailReviewAdapter;
import com.example.jamie.popularmovies.data.MovieContract;

public class MovieDetailView extends AppCompatActivity {

    private static ImageView imageItem;
    private static final int LOADER_ID = 1;

    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.TITLE,
            MovieContract.MovieEntry.POSTER_PATH,
            MovieContract.MovieEntry.OVERVIEW,
            MovieContract.MovieEntry.VOTE_AVERAGE,
            MovieContract.MovieEntry.FAVORITE,
            MovieContract.MovieEntry.RELEASE_DATE,
            MovieContract.ReviewEntry.REVIEW_URL,
            MovieContract.ReviewEntry.REVIEW_CONTENT,
            MovieContract.TrailerEntry.TRAILER_SOURCE,
            MovieContract.TrailerEntry.TRAILER_NAME,
            MovieContract.TrailerEntry.TRAILER_TYPE
    };

    public static final int COL_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_POSTER_PATH = 2;
    public static final int COL_OVERVIEW = 3;
    public static final int COL_VOTE_AVERAGE = 4;
    public static final int COL_FAVORITE = 5;
    public static final int COL_RELEASE_DATE = 6;
    public static final int COL_REVIEW_URL = 7;
    public static final int COL_REVIEW_CONTENT = 8;
    public static final int COL_TRAILER_SOURCE = 9;
    public static final int COL_TRAILER_NAME = 10;
    public static final int COL_TRAILER_TYPE = 11;

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

        private static DetailReviewAdapter mAdapter;
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
            String movie_id = MovieContract.MovieEntry.getMovieIdFromPath(intent.getData());
            if (intent == null) {
                return null;
            }
            Uri d = intent.getData();
            return new CursorLoader(
                    getActivity(),
                    intent.getData(),
                    MOVIE_COLUMNS,
                    null, //this was what I was missing
                    new String[]{movie_id}, //this was what I was missing
                    null
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            int count = cursor.getCount();
            if (cursor != null && cursor.moveToFirst()) {
                while(cursor.moveToNext()){
                    String duh =  cursor.getString(COL_TRAILER_NAME);
                }

//                String title = cursor.getString(MovieDetailView.COL_TITLE);
//                mMovieTitle.setText(title);
//
//
//                String rating = Double.parseDouble(String.format("%.1f", cursor.getDouble(MovieDetailView.COL_VOTE_AVERAGE))) + "/10";
//                mMovieRating.setText(rating);
//
//
//                String[] splitDate = cursor.getString(MovieDetailView.COL_RELEASE_DATE).split("-");
//                mReleaseDate.setText(splitDate[0]);
//
//
//                mOverView.setText(cursor.getString(MovieDetailView.COL_OVERVIEW));
//                String imagePath = cursor.getString(MovieDetailView.COL_BACKDROP_PATH);
//                String processedPath = Utility.getImagePath(imagePath);
//                Picasso.with(getActivity())
//                        .load(processedPath)
//                        .placeholder(R.drawable.popcorntime)
//                        .into(imageItem);
            }


        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {


        }
    }
}
