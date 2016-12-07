package com.example.hello.hotmovie.Threads;

import android.content.Context;
import android.content.pm.PackageManager;

import com.example.hello.hotmovie.HotInterfaces.MovieJsonCallBack;

/**
 * Created by Hello on 2016/12/6.
 *
 * ALl threads that need callback will extends it;
 */

public class HotThread extends Thread {
    protected MovieJsonCallBack callback;
    protected  String api_key;
    public Context context;
    public  HotThread(Context context) throws PackageManager.NameNotFoundException {
       this.context=context;
        api_key=context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA).metaData.getString("api_key");

   }
    public MovieJsonCallBack getCallback() {
        return callback;
    }

    public void setCallback(MovieJsonCallBack callback) {
        this.callback = callback;
    }

    public void storeInfo(){}

}
