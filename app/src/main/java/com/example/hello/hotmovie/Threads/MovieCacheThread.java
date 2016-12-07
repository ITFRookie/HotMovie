package com.example.hello.hotmovie.Threads;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

import com.example.hello.hotmovie.Beans.MovieItemBean;

/**
 * Created by Hello on 2016/12/7.
 * //將傳遞進來的list對象轉化爲json字符串並保存到sharepreference中來實現離綫緩存
 */

public class MovieCacheThread extends Thread {
    protected Context context;
    protected ArrayList<MovieItemBean> list;
    public MovieCacheThread(Context context,ArrayList<MovieItemBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public void run() {
        String data=null;
        //將list對象轉化為json字符串
        Gson gson=new Gson();
        if(list!=null)
         data =gson.toJson(list);
        //獲取應用的sharedpreference，編輯並保存
        SharedPreferences preferences=context.getSharedPreferences("HotMovie",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor=preferences.edit();
        if(data!=null)
        editor.putString("movieListInfo",data);
        editor.commit();

    }
}
