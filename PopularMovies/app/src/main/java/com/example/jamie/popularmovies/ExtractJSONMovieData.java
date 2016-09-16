package com.example.jamie.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a5w5nzz on 9/16/2016.
 */
public class ExtractJSONMovieData {
    String jsonString;
    public ExtractJSONMovieData(String jsonString) {
        this.jsonString = jsonString;
    }

    public List<Movie> getMovieObjects() {
        final String MOVIE_RESULTS = "results";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_ADULT = "adult";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_GENERE_IDS = "genre_ids";
        final String MOVIE_ID = "id";
        final String MOVIE_ORIGINAL__TITLE = "original_title";
        final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
        final String MOVIE_TITLE = "title";
        final String MOVIE_BACKDROP_PATH = "backdrop_path";
        final String MOVIE_POPULARITY = "popularity";
        final String MOVIE_VOTE_COUNT = "vote_count";
        final String MOVIE_VIDEO = "video";
        final String MOVIE_VOTE_AVERAGE= "vote_average";

        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject jsonData = new JSONObject(jsonString);
            JSONArray itemsArray = jsonData.getJSONArray(MOVIE_RESULTS);
            for(int i = 0; i < itemsArray.length(); i++){
                Movie movie = new Movie();
                JSONObject jObj = itemsArray.getJSONObject(i);
                movie.setPosterPath(jObj.getString(MOVIE_POSTER_PATH));
                movie.setAdult(jObj.getBoolean(MOVIE_ADULT));
                movie.setOverview(jObj.getString(MOVIE_OVERVIEW));
                movie.setReleaseDate(jObj.getString(MOVIE_RELEASE_DATE));

//                    JSONArray jArray = jObj.getJSONArray(MOVIE_GENERE_IDS);
//
//                    int[] gen = new int[jArray.length()];
//                    for(int j = 0; j<jArray.length();j++){
//                        gen[j] = jArray.getInt(i);
//                    }
//
//                    movie.setGenreIDs(gen);
                movie.setId(jObj.getInt(MOVIE_ID));
                movie.setOriginalTitle(jObj.getString(MOVIE_ORIGINAL__TITLE));
                movie.setOriginalLanguage(jObj.getString(MOVIE_ORIGINAL_LANGUAGE));
                movie.setTitle(jObj.getString(MOVIE_TITLE));
                movie.setBackdropPath(jObj.getString(MOVIE_BACKDROP_PATH));
                movie.setPopularity(jObj.getDouble(MOVIE_POPULARITY));
                movie.setVoteCount(jObj.getDouble(MOVIE_VOTE_COUNT));
                movie.setVideo(jObj.getBoolean(MOVIE_VIDEO));
                movie.setVoteAverage(jObj.getDouble(MOVIE_VOTE_AVERAGE));
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return movies;
    }
}
