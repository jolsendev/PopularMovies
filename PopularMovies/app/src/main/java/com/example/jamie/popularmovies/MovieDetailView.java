package com.example.jamie.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailView extends AppCompatActivity {
    private static Movie mMovie;
    private static ImageView imageItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_movie_detail_view);
        Intent intent = getIntent();
        mMovie = (Movie) intent.getSerializableExtra(Utility.MOVIE_KEY);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.movie_container, new MovieDetailFragment()).commit();
        }
    }

    public static class MovieDetailFragment extends Fragment {
        public MovieDetailFragment(){

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_movie_detail_view, container, false);

            imageItem = (ImageView) rootView.findViewById(R.id.detail_movie_image);
            LinearLayout titleLayout = (LinearLayout) rootView.findViewById(R.id.title_layout);

            TextView mMovieTitle = (TextView) titleLayout.findViewById(R.id.movie_title);
            mMovieTitle.setText(mMovie.getTitle());

            TextView mMovieRating = (TextView) rootView.findViewById(R.id.movie_rating);
            String rating = Double.parseDouble(String.format("%.1f",mMovie.getVoteAverage()))+"/10";
            mMovieRating.setText(rating);

            TextView mReleaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
            String[] splitDate = mMovie.getReleaseDate().split("-");
            mReleaseDate.setText(splitDate[0]);

            TextView mOverView = (TextView) rootView.findViewById(R.id.movie_overview);
            mOverView.setText(mMovie.getOverview());

            Picasso.with(getActivity())
                    .load(mMovie.getPosterPath())
                    .placeholder(R.drawable.popcorntime)
                    .into(imageItem);
            return rootView;
        }
    }
}
