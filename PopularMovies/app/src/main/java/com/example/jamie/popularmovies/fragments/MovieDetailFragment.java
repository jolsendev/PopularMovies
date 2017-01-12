package com.example.jamie.popularmovies.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
            MovieContract.TrailerEntry.TABLE_NAME+"."+MovieContract.TrailerEntry.TRAILER_TYPE

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


    private static DetailReviewAdapter mAdapter;
    private LinearLayout titleLayout;
    private Parcelable mUri;
    private DetailTrailerAdapter trailerAdapter;
    private DetailReviewAdapter reviewAdapter;

    private CustomListView mReviewListView;
    private LinearLayout mDetailLayout;
    private TextView mMovieTitle;
    private ImageView mMovieImage;
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

        if(arguments != null){
            mUri = arguments.getParcelable(DETAIL_URI);
            if(mUri != null){
                String movie_id_string = MovieContract.MovieEntry.getMovieIdFromPath((Uri)mUri);
                long movie_id = Long.parseLong(movie_id_string);
                UpdateReview(movie_id);
                UpdateTrailer(movie_id);
                setUris(movie_id);
            }
        }

        View rootView = inflater.inflate(R.layout.activity_movie_detail_view, container, false);

        mDetailLayout = (LinearLayout) rootView.findViewById(R.id.movie_container);
        mMovieTitle = (TextView)  mDetailLayout.findViewById(R.id.movie_title);
        mMovieImage = (ImageView) mDetailLayout.findViewById(R.id.detail_movie_image);
        mMovieRating = (TextView) mDetailLayout.findViewById(R.id.movie_rating);
        mReleaseDate = (TextView) mDetailLayout.findViewById(R.id.movie_release_date);
        mMovieOverview = (TextView) mDetailLayout.findViewById(R.id.movie_overview);
        mTrailerListView = (CustomListView) mDetailLayout.findViewById(R.id.trailer_list_view);
        mTrailerListView.setExpanded(true);
        mTrailerTitle = (TextView) mDetailLayout.findViewById(R.id.trailer_title);
        mReviewListView = (CustomListView) mDetailLayout.findViewById(R.id.review_list_view);
        mReviewListView.setExpanded(true);
        mReviewTitle = (TextView) mDetailLayout.findViewById(R.id.review_title);
        favoriteCheckbox = (CheckBox) mDetailLayout.findViewById(R.id.favorite_checkbox);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        reviewAdapter = new DetailReviewAdapter(getContext(), null, 0);
        trailerAdapter = new DetailTrailerAdapter(getContext(), null, 0);
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
                //String holder = DatabaseUtils.dumpCursorToString(cursor);
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
                    mMovieRating.setText(Double.toString(cursor.getDouble(COL_VOTE_AVERAGE)));
                    mReleaseDate.setText(cursor.getString(COL_RELEASE_DATE));
                    mMovieOverview.setText(cursor.getString(COL_OVERVIEW));
                    ImageView imageItem = (ImageView) mDetailLayout.findViewById(R.id.detail_movie_image);

                    Picasso.with(getContext())
                            .load(Utility.getImagePathBackDrop(cursor.getString(COL_BACKDROP_PATH)))
                            .into(imageItem);

                    final int movie_id = cursor.getInt(COL_MOVIE_ID);
                    final int is_favorite = cursor.getInt(COL_FAVORITE);
                    if(is_favorite == 0){
                        favoriteCheckbox.setText("Favorite");

                        //checkbox
                    }
                    else{
                        favoriteCheckbox.setText("Favorite");
                        favoriteCheckbox.setChecked(true);
                        //removecheck
                    }

                    favoriteCheckbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(is_favorite == 0){
                                Utility.addMovieToFavorite(getActivity(), movie_id);
                                favoriteCheckbox.setText("Remove favorite");
                            }else{
                                Utility.removeMovieToFavorite(getActivity(), movie_id);
                                favoriteCheckbox.setText("Remove favorite");
                            }
                        }
                    });

                }
                break;
            }
        }
    }

    private void setViewsVisible() {
        mMovieTitle.setVisibility(View.VISIBLE);
        favoriteCheckbox.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        reviewAdapter.swapCursor(null);
        trailerAdapter.swapCursor(null);
    }

//    public void restartDetailLoaders(){
//        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
//        getLoaderManager().restartLoader(REVIEW_LOADER, null, this);
//        getLoaderManager().restartLoader(TRAILER_LOADER, null, this);
//    }
//    public void updateDetailWithNewPreference(Uri prefUri) {
//        if(mUri != null){
//            mUri = prefUri;
//            //restartDetailLoaders();
//        }
//    }

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
        getLoaderManager().restartLoader(TRAILER_LOADER, null, this);
    }

    public void RestartReviewLoader() {
        getLoaderManager().restartLoader(REVIEW_LOADER, null, this);
    }
}