package com.example.hello.hotmovie.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hello on 2016/12/6
 * Http Url Handler
 * .
 */

public class MovieHttpUtil {


    public static String getJson(String httpUrl) {
       StringBuilder sb=new StringBuilder("");
        InputStream is=null;
        InputStreamReader isr=null;

        BufferedReader bfr = null;
        try {
            URL url=new URL(httpUrl);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.connect();
           if( conn.getResponseCode()==200){
               is=conn.getInputStream();
               isr=new InputStreamReader(is);
               bfr=new BufferedReader(isr);
               String str=null;
               while((str=bfr.readLine())!=null){

                   sb.append(str);

               }

           }
            bfr.close();
            isr.close();
            is.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return sb.toString();
    }
}
