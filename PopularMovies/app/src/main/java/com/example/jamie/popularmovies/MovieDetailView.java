package com.example.jamie.popularmovies;

import android.content.Intent;
import android.database.Cursor;
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

    public static final int COL_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_POSTER_PATH = 2;
    public static final int COL_OVERVIEW = 3;
    public static final int COL_VOTE_AVERAGE = 4;
    public static final int COL_FAVORITE = 5;
    public static final int COL_RELEASE_DATE = 6;


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



    public static class MovieDetailFragment extends Fragment implements LoaderCallbacks<Cursor> {

        private static DetailReviewAdapter mAdapter;
        private LinearLayout titleLayout;
        private TextView mMovieTitle;
        private TextView mMovieRating;
        private TextView mReleaseDate;
        private TextView mOverView;
        private Parcelable mUri;
        private View trailerListView;
        private DetailTrailerAdapter trailerAdapter;
        private ListView trailerItemListView;

        public MovieDetailFragment() {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.detail_layout, container, false);
            trailerListView = rootView.findViewById(R.id.trailer_list_view);
            trailerItemListView = (ListView) trailerListView.findViewById(R.id.trailer_list_view);


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
            getLoaderManager().initLoader(MOVIE_LOADER, null, this);
            getLoaderManager().initLoader(REVIEW_LOADER, null, this);
            getLoaderManager().initLoader(TRAILER_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Intent intent = getActivity().getIntent();
            String movie_id = MovieContract.MovieEntry.getMovieIdFromPath(intent.getData());
            if (intent == null) {
                return null;
            }

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

            int id = loader.getId();
            switch (id){
                case TRAILER_LOADER:{
                    trailerAdapter = new DetailTrailerAdapter(getContext(), cursor, 0);
                    trailerItemListView.setAdapter(trailerAdapter);
                    break;
                }
            }

            String title = cursor.getString(MovieDetailView.COL_TITLE);
            mMovieTitle.setText(title);


            String rating = Double.parseDouble(String.format("%.1f", cursor.getDouble(MovieDetailView.COL_VOTE_AVERAGE))) + "/10";
            mMovieRating.setText(rating);


            String[] splitDate = cursor.getString(MovieDetailView.COL_RELEASE_DATE).split("-");
            mReleaseDate.setText(splitDate[0]);


            mOverView.setText(cursor.getString(MovieDetailView.COL_OVERVIEW));
            String imagePath = cursor.getString(MovieDetailView.COL_POSTER_PATH);
            String processedPath = Utility.getImagePath(imagePath);
            Picasso.with(getActivity())
                    .load(processedPath)
                    .placeholder(R.drawable.popcorntime)
                    .into(imageItem);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }
}
