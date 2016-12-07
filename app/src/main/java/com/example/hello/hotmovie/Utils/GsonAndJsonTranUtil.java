package com.example.hello.hotmovie.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.util.ArrayList;
import java.util.Iterator;

import com.example.hello.hotmovie.Beans.MovieItemBean;

/**
 * Created by Hello on 2016/12/6.
 */

public class GsonAndJsonTranUtil {
    public static ArrayList<MovieItemBean> parseMovieJsonFromNet(String data){
       ArrayList<MovieItemBean> list=new ArrayList<>();
        Gson gson=new Gson();
        JsonParser parser=new JsonParser();
        JsonElement element=parser.parse(data);
        JsonObject movieListObject=null;
        if(element.isJsonObject())
               movieListObject=element.getAsJsonObject();
        JsonArray movieItems=movieListObject.getAsJsonArray("results");
        Iterator iterator=movieItems.iterator();
       while(iterator.hasNext()){
        JsonElement ele= (JsonElement) iterator.next();
           if(ele.isJsonObject())
           {
               MovieItemBean  temp=new MovieItemBean();
                temp=gson.fromJson(ele,MovieItemBean.class);
               list.add(temp);

           }



       }
return list;
    }
    public static ArrayList<MovieItemBean> parseMovieJsonFromCache(String data){
        ArrayList<MovieItemBean> list=new ArrayList<>();
        Gson gson=new Gson();
        JsonParser parser=new JsonParser();
        JsonElement element=parser.parse(data);
        JsonArray movieItems=null;
        if(element.isJsonArray())
          movieItems=element.getAsJsonArray();
        Iterator iterator=movieItems.iterator();
        while(iterator.hasNext()){
            JsonElement ele= (JsonElement) iterator.next();
            if(ele.isJsonObject())
            {
                MovieItemBean  temp=new MovieItemBean();
                temp=gson.fromJson(ele,MovieItemBean.class);
                list.add(temp);

            }



        }
        return list;
    }


}
