package com.example.jamie.popularmovies.movie_objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by jamie on 8/29/16.
 */
public class Movie implements Parcelable{//Serializable {

    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private int[] genreIDs;
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private Double popularity;
    private Double voteCount;
    private boolean video;
    private Double voteAverage;
    private String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/w185/";



    public Movie(){
        super();
    }

    public Movie(Parcel parcel){

        this.posterPath = parcel.readString();
        parcel.writeValue(adult);
        this.overview= parcel.readString();
        this.releaseDate = parcel.readString();
        this.id = parcel.readInt();
        this.originalTitle = parcel.readString();
        this.originalLanguage = parcel.readString();
        this.title = parcel.readString();
        this.backdropPath = parcel.readString();
        this.popularity = parcel.readDouble();
        this.voteCount = parcel.readDouble();
        parcel.writeValue(video);
        this.voteAverage = parcel.readDouble();
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenreIDs(int[] genreIDs) {
        this.genreIDs = genreIDs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void setVoteCount(Double voteCount) {
        this.voteCount = voteCount;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {

        return IMAGE_BASE_PATH+posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int[] getGenreIDs() {
        return genreIDs;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return IMAGE_BASE_PATH+backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Double getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "posterPath='" + posterPath + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", genreIDs=" + Arrays.toString(genreIDs) +
                ", id=" + id +
                ", originalTitle='" + originalTitle + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", title='" + title + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString(this.posterPath);
        parcel.writeValue(this.adult);
        parcel.writeString(this.overview);
        parcel.writeString(this.releaseDate);
        parcel.writeInt(this.id);
        parcel.writeString(this.originalTitle);
        parcel.writeString(this.originalLanguage);
        parcel.writeString(this.title);
        parcel.writeString(this.backdropPath);
        parcel.writeDouble(this.popularity);
        parcel.writeDouble(this.voteCount);
        parcel.writeValue(this.video);
        parcel.writeDouble(this.voteAverage);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[0];
        }
    };
}
