package com.example.jamie.popularmovies.movie_objects;

/**
 * Created by Jamie Olsen on 11/26/2016.
 */
public class Trailer {

    private long movieId;
    private String trailerKey;
    private String trailerName;
    private String trailerSite;
    private long trailerSize;
    private String trailerType;

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public void setTrailerSite(String trailerSite) {
        this.trailerSite = trailerSite;
    }

    public void setTrailerSize(long trailerSize) {
        this.trailerSize = trailerSize;
    }

    public void setTrailerType(String trailerType) {
        this.trailerType = trailerType;
    }

    public long getMovieId() {

        return movieId;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public String getTrailerSite() {
        return trailerSite;
    }

    public long getTrailerSize() {
        return trailerSize;
    }

    public String getTrailerType() {
        return trailerType;
    }
}
