package com.example.jamie.popularmovies.movie_objects;

/**
 * Created by Jamie Olsen on 11/26/2016.
 */
public class Review{

    private long movieId;
    private String reviewAuthor;
    private String reviewContent;
    private String reviewUrl;

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setReviewUrl(String reviewUri) {
        this.reviewUrl = reviewUri;
    }

    public long getMovieId() {

        return movieId;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }
}
