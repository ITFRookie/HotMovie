package com.example.hello.hotmovie.Beans;

import java.io.Serializable;

/**
 * Created by Hello on 2016/12/6.
 *
 * the bean to store movie's information
 */

public class MovieItemBean implements Serializable{
    private String title;//name
    private String poster_path;//photo
    private String overview;//descript
    private String  vote_average;//vote average
    private String release_date;//release_date
    private static final long serialVersionUID = 1L;//序列化使用
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
      //獲取絕對路徑
    public String getPoster_path() {
        return "https://image.tmdb.org/t/p/w185"+poster_path;
    }


    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
