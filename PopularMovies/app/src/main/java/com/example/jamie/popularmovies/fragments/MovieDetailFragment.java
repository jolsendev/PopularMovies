package com.example.jamie.popularmovies.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jamie.popularmovies.FetchReviewTask;
import com.example.jamie.popularmovies.FetchTrailerTask;
import com.example.jamie.popularmovies.R;
import com.example.jamie.popularmovies.Utility;
import com.example.jamie.popularmovies.adapters.DetailReviewAdapter;
import com.example.jamie.popularmovies.adapters.DetailTrailerAdapter;
import com.example.jamie.popularmovies.custom_views.CustomListView;
import com.example.jamie.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by a5w5nzz on 12/13/2016.
 */

public class MovieDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String DETAIL_URI = "detail_uri";
    private static ImageView imageItem;
    public static final int MOVIE_LOADER = 0;
    public static final int REVIEW_LOADER = 1;
    public final int TRAILER_LOADER = 2;

    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry.TABLE_NAME+"."+ MovieContract.MovieEntry.MOVIE_ID,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.TITLE,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.POSTER_PATH,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.OVERVIEW,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.VOTE_AVERAGE,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.FAVORITE,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.RELEASE_DATE,
            MovieContract.MovieEntry.TABLE_NAME+"."+MovieContract.MovieEntry.BACKDROP_PATH,
    };

    private static final String[] REVIEW_COLUMNS = {
            MovieContract.ReviewEntry.TABLE_NAME+"."+MovieContract.ReviewEntry._ID,
            MovieContract.ReviewEntry.TABLE_NAME+"."+MovieContract.ReviewEntry.MOVIE_ID,
            MovieContract.ReviewEntry.TABLE_NAME+"."+MovieContract.ReviewEntry.REVIEW_AUTHOR,
            MovieContract.ReviewEntry.TABLE_NAME+"."+MovieContract.ReviewEntry.REVIEW_CONTENT,
            MovieContract.ReviewEntry.TABLE_NAME+"."+MovieContract.ReviewEntry.REVIEW_URL
    };


    public static final String[] TRAILER_COLUMNS = {
            MovieContract.TrailerEntry.TABLE_NAME+"."+MovieContract.TrailerEntry._ID,
            MovieContract.TrailerEntry.TABLE_NAME+"."+MovieContract.TrailerEntry.MOVIE_ID,
            MovieContract.TrailerEntry.TABLE_NAME+"."+MovieContract.TrailerEntry.TRAILER_NAME,
            MovieContract.TrailerEntry.TABLE_NAME+"."+MovieContract.TrailerEntry.TRAILER_SIZE,
            MovieContract.TrailerEntry.TABLE_NAME+"."+MovieContract.TrailerEntry.TRAILER_SOURCE,
            MovieContract.TrailerEntry.TABLE_NAME+"."+MovieContract.TrailerEntry.TRAILER_TYPE,
            MovieContract.TrailerEntry.TABLE_NAME+"."+MovieContract.TrailerEntry.INDEX

    };

    //public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_POSTER_PATH = 2;
    public static final int COL_OVERVIEW = 3;
    public static final int COL_VOTE_AVERAGE = 4;
    public static final int COL_FAVORITE = 5;
    public static final int COL_RELEASE_DATE = 6;
    public static final int COL_BACKDROP_PATH = 7;

    public static final int COL_REVIEW_ID = 0;
    public static final int COL_REVIEW_MOVIE_ID = 1;
    public static final int COL_REVIEW_AUTHOR = 2;
    public static final int COL_REVIEW_CONTENT = 3;
    public static final int COL_REVIEW_URL = 4;


    public static final int COL_TRAILER_ID = 0;
    public static final int COL_TRAILER_MOVIE_ID = 1;
    public static final int COL_TRAILER_NAME = 2;
    public static final int COL_TRAILER_SIZE = 3;
    public static final int COL_TRAILER_SOURCE = 4;
    public static final int COL_TRAILER_TYPE = 5;
    public static final int COL_TRAILER_INDEX = 6;


    private static DetailReviewAdapter mAdapter;
    private LinearLayout titleLayout;
    private Parcelable mUri;
    private DetailTrailerAdapter trailerAdapter;
    private DetailReviewAdapter reviewAdapter;

    private CustomListView mReviewListView;
    private LinearLayout mDetailLayout;
    private TextView mMovieTitle;

    private TextView mMovieRating;
    private TextView mReleaseDate;
    private TextView mMovieOverview;
    private CustomListView mTrailerListView;
    private boolean here = false;
    private CheckBox favoriteCheckbox;
    private Uri mMovieUri;
    private Uri mReviewUri;
    private Uri mTrailerUri;
    private TextView mTrailerTitle;
    private  TextView mReviewTitle;
    private String mPreference;
    private TextView mMovieOverviewTitle;
    private ImageView mMovieImage;

    public MovieDetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        getLoaderManager().initLoader(REVIEW_LOADER, null, this);
        getLoaderManager().initLoader(TRAILER_LOADER, null, this);

        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle arguments = getArguments();
//155
        mPreference = Utility.getSharedPreference(getActivity());
        if(arguments != null){
            mUri = arguments.getParcelable(DETAIL_URI);
            if(mUri == null){
                mUri = getActivity().getIntent().getData();
            }
            if(mUri != null){
                String movie_id_string = MovieContract.MovieEntry.getMovieIdFromPath((Uri)mUri);
                long movie_id = Long.parseLong(movie_id_string);
                if(mPreference.equals("popular")||mPreference.equals("top_rated")){
                    UpdateReview(movie_id);
                    UpdateTrailer(movie_id);
                }
                setUris(movie_id);
            }else{
                mUri = Utility.getFirstMovieFromPreference(getActivity(),Utility.getSharedPreference(getActivity()));
                if(mUri != null){
                    Long movie_id = Long.parseLong(MovieContract.MovieEntry.getMovieIdFromPath((Uri)mUri));
                    setUris(movie_id);
                    restartLoader();

                }
            }
        }

        View rootView = inflater.inflate(R.layout.activity_movie_detail_view, container, false);

        mDetailLayout = (LinearLayout) rootView.findViewById(R.id.movie_container);
        mMovieTitle = (TextView)  mDetailLayout.findViewById(R.id.movie_title);
        mMovieImage = (ImageView) mDetailLayout.findViewById(R.id.detail_movie_image);
        mMovieRating = (TextView) mDetailLayout.findViewById(R.id.movie_rating);
        mReleaseDate = (TextView) mDetailLayout.findViewById(R.id.movie_release_date);
        mMovieOverview = (TextView) mDetailLayout.findViewById(R.id.movie_overview);
        mMovieOverviewTitle = (TextView) mDetailLayout.findViewById(R.id.synopsys_title);
        mTrailerListView = (CustomListView) mDetailLayout.findViewById(R.id.trailer_list_view);
        mTrailerListView.setExpanded(true);
        mTrailerTitle = (TextView) mDetailLayout.findViewById(R.id.trailer_title);
        mReviewListView = (CustomListView) mDetailLayout.findViewById(R.id.review_list_view);
        mReviewListView.setExpanded(true);
        mReviewTitle = (TextView) mDetailLayout.findViewById(R.id.review_title);
        favoriteCheckbox = (CheckBox) mDetailLayout.findViewById(R.id.favorite_checkbox);

        return rootView;
    }

    private void restartLoader() {
        RestartTrailerLoader();
        RestartReviewLoader();
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        reviewAdapter = new DetailReviewAdapter(getContext(), null, 0);
        trailerAdapter = new DetailTrailerAdapter(getContext(), null, 0);
        if(savedInstanceState != null){
            mUri = savedInstanceState.getParcelable(DETAIL_URI);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if(mUri == null){
            return null;
        }
        CursorLoader cursor = null;
        String movie = MovieContract.MovieEntry.getMovieIdFromPath((Uri) mUri);
        int movie_id = Integer.parseInt(movie);
        setUris(movie_id);

        switch (id){
            case MOVIE_LOADER:{
                cursor = new CursorLoader(
                        getActivity(),
                        mMovieUri,
                        MOVIE_COLUMNS,
                        null, //this was what I was missing
                        null, //this was what I was missing
                        null
                );
                break;
            }
            case REVIEW_LOADER:{
                cursor = new CursorLoader(
                        getActivity(),
                        mReviewUri,
                        REVIEW_COLUMNS,
                        null, //this was what I was missing
                        null, //this was what I was missing
                        null
                );
                break;
            }

            case TRAILER_LOADER:{
                cursor = new CursorLoader(
                        getActivity(),
                        mTrailerUri,
                        TRAILER_COLUMNS,
                        null, //this was what I was missing
                        null, //this was what I was missing
                        null
                );
                break;
            }
            default:
                break;
        }

        return cursor;
    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        int size = cursor.getCount();
        int id = loader.getId();
        switch (id){
            case TRAILER_LOADER:{

                if(cursor.moveToFirst()){
                    mTrailerTitle.setVisibility(View.VISIBLE);

                }else{
                    mTrailerTitle.setVisibility(View.GONE);
                }
                trailerAdapter.swapCursor(cursor);
                mTrailerListView.setAdapter(trailerAdapter);
                break;
            }
            case REVIEW_LOADER:{

                if(cursor.moveToFirst()){
                    mReviewTitle.setVisibility(View.VISIBLE);
                }else{
                    mReviewTitle.setVisibility(View.GONE);
                }
                reviewAdapter.swapCursor(cursor);
                mReviewListView.setAdapter(reviewAdapter);
                break;
            }

            case MOVIE_LOADER:{

                if(cursor.moveToFirst()){
                    setViewsVisible();
                    mMovieTitle.setText(cursor.getString(COL_TITLE));
                    mMovieRating.setText(Double.toString(cursor.getDouble(COL_VOTE_AVERAGE))+"/10");
                    String date = cursor.getString(COL_RELEASE_DATE);
                    String[] year = date.split("-");
                    mReleaseDate.setText(year[0]);
                    mMovieOverview.setText(cursor.getString(COL_OVERVIEW));

                    Picasso.with(getContext())
                            .load(Utility.getImagePathBackDrop(cursor.getString(COL_BACKDROP_PATH)))
                            .into(mMovieImage);

                    final int movie_id = cursor.getInt(COL_MOVIE_ID);
                    final int is_favorite = cursor.getInt(COL_FAVORITE);
                    if(is_favorite == 0){
                        favoriteCheckbox.setText("Favorite");
                    }
                    else{
                        favoriteCheckbox.setText("Favorite");
                        favoriteCheckbox.setChecked(true);
                    }

                    favoriteCheckbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(is_favorite == 0){
                                Utility.addMovieToFavorite(getActivity(), movie_id);
                                favoriteCheckbox.setText("Favorite");
                            }else{
                                Utility.removeMovieToFavorite(getActivity(), movie_id);
                                favoriteCheckbox.setText("Favorite");
                            }
                        }
                    });

                }
                break;
            }
        }
    }

    private void setViewsVisible() {
        mMovieOverviewTitle.setVisibility(View.VISIBLE);
        mDetailLayout.setVisibility(View.VISIBLE);
        mMovieTitle.setVisibility(View.VISIBLE);
        favoriteCheckbox.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        reviewAdapter.swapCursor(null);
        trailerAdapter.swapCursor(null);
    }

    public void setUris(long movie_id) {
        mMovieUri = MovieContract.MovieEntry.buildMovieUri(movie_id);
        mTrailerUri = MovieContract.MovieEntry.buildMovieTrailer(movie_id);
        mReviewUri = MovieContract.MovieEntry.buildMovieReview(movie_id);
    }

    private void UpdateReview(long movie_id){
        mReviewUri = Utility.getUrlByIdForType(Long.toString(movie_id), MovieContract.ReviewEntry.TABLE_NAME);
        FetchReviewTask reviewTask = new FetchReviewTask(getContext());
        reviewTask.execute(mReviewUri.toString());
    }

    private void UpdateTrailer(long movie_id){
        mTrailerUri = Utility.getUrlByIdForType(Long.toString(movie_id), MovieContract.TrailerEntry.TABLE_NAME);
        FetchTrailerTask trailerTask = new FetchTrailerTask(getContext());
        trailerTask.execute(mTrailerUri.toString());
    }

    public void RestartTrailerLoader() {
        if(getLoaderManager().getLoader(TRAILER_LOADER) != null){
            getLoaderManager().restartLoader(TRAILER_LOADER, null, this);
        }
    }

    public void RestartReviewLoader() {
        if(getLoaderManager().getLoader(REVIEW_LOADER) != null){
            getLoaderManager().restartLoader(REVIEW_LOADER, null, this);
        }
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }
}