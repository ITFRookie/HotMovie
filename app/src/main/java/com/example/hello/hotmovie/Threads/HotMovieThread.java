package com.example.hello.hotmovie.Threads;

import android.content.Context;
import android.content.pm.PackageManager;

import com.example.hello.hotmovie.Utils.MovieHttpUtil;

/**
 * Created by Hello on 2016/12/6.
The thread to get HotMovieThread
 */

public class HotMovieThread extends HotThread {
    public HotMovieThread(Context context) throws PackageManager.NameNotFoundException {
        super(context);
    }
    @Override
    public void run() {
        String httpUrl="http://api.themoviedb.org/3/movie/popular?language=zh&api_key="+api_key;
        String jsonData= MovieHttpUtil.getJson(httpUrl);
        callback.getMovieResult(jsonData);
    }
}
