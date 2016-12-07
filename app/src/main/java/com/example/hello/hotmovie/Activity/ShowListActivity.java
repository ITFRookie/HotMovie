package com.example.hello.hotmovie.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.hello.hotmovie.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.example.hello.hotmovie.Adapters.MovieGridAdapter;
import com.example.hello.hotmovie.Beans.MovieItemBean;
import com.example.hello.hotmovie.HotInterfaces.MovieJsonCallBack;

import com.example.hello.hotmovie.Threads.MovieCacheThread;

import com.example.hello.hotmovie.Utils.GsonAndJsonTranUtil;

public class ShowListActivity extends AppCompatActivity implements MovieJsonCallBack, AdapterView.OnItemClickListener {
    public static final int FLAG_MOVIE_GET_NET =100;
    public static final int FLAG_MOVIE_GET_CACHE =200;
    public static final int FLAG_SHARE_TYPE =300;
    public static final int FLAG_SHARE_DATA =400;
    private ArrayList<MovieItemBean> list;
    private GridView gv;
    public  Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if(msg.what== FLAG_MOVIE_GET_NET) flateGridView(msg.getData().getString("jsonData"),true);//網絡獲取方式
        }
    };

    private void flateGridView(String data,boolean flag) {
        //handler json data  and show information in gridview;
        //利用gson+sharepreference來實現離綫緩存。
        //根據flag判斷從net還是cache獲取   true網絡獲取  false 從cache獲取
        if(data!=null) {
                if(flag)
                 list = GsonAndJsonTranUtil.parseMovieJsonFromNet(data);//網絡方式 得到包含有電影信息的list對象
               else list=GsonAndJsonTranUtil.parseMovieJsonFromCache(data);//cache 方式  得到包含有電影信息的list對象
            if (list!=null) {
                //获取到电影信息要做的处理  缓存到本地并显示到gridview中
                new MovieCacheThread(ShowListActivity.this, list).start();//缓存电影列表信息到preference
                MovieGridAdapter adapter=new MovieGridAdapter(ShowListActivity.this,list);
                 gv.setAdapter(adapter);
            }
            }else
            Toast.makeText(ShowListActivity.this,"刷新電影信息失敗",Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            reFresh();
        } catch (Exception e) {
        printInfo();
        }
    }

    private void init() {
        gv= (GridView) findViewById(R.id.grid_show_list);
        gv.setOnItemClickListener(this);
 //       TopMovieThread tmt= null;
//        try {
//            tmt = new TopMovieThread(ShowListActivity.this);
//            tmt.setCallback(ShowListActivity.this);
//            tmt.start();
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       switch (id){
           case R.id.action_settings:
               setTing();//響應setting事件
           case  R.id.action_refresh:
               try {
                   reFresh();//刷新電影
               } catch (Exception e) {

                  printInfo();
               }
       }


        return super.onOptionsItemSelected(item);
    }
//點擊設置相應的事件
    private void setTing() {
        Intent intent=new Intent(ShowListActivity.this,SettingActivity.class);
        startActivity(intent);

    }

    //刷新電影
    private void reFresh() throws Exception {

        String className= getShareValue(FLAG_SHARE_TYPE);//獲取設置的搜索電影的類型  按Hot還是Top  反射調用  默認為按Hot搜索
        Log.e("SelectName",className);
        Object object=null;

        if(className==null)return ;
        Class cls=Class.forName(className);
        Constructor construct=cls.getDeclaredConstructor(Context.class);
        Method setCallBack=cls.getMethod("setCallback",new Class[]{MovieJsonCallBack.class});
        Method tStart=cls.getMethod("start");
        object=construct.newInstance(ShowListActivity.this);
        setCallBack.invoke(object,ShowListActivity.this);

        if(isOnline()){
            //若是有網絡  則從網絡獲取
            tStart.invoke(object);
        }
        else if(getShareValue(FLAG_SHARE_DATA).length()>0){
          //否則查看本地緩存
            Log.e("SHRinfo","HelloWorld");
          getFromCache();
        }
        else{
      printInfo();
        }


    }

    private void getFromCache() {
    //從本地cache中獲取movie數據
        String data=getShareValue(FLAG_SHARE_DATA);
        if(data.length()>0){

        flateGridView(data,false);
        }

    }

    private String getShareValue(int flag) {

        switch (flag){
            case FLAG_SHARE_TYPE:
               return  PreferenceManager.getDefaultSharedPreferences(ShowListActivity.this).getString("MovieType","com.example.hello.hotmovie.Threads.TopMovieThread");
            case FLAG_SHARE_DATA:
                SharedPreferences preferences=ShowListActivity.this.getSharedPreferences("HotMovie", Context.MODE_PRIVATE);
                return preferences.getString("movieListInfo","");

       default:return null;
        }


    }

    @Override
    public void getMovieResult(String data) {
        //call back when get json data;
        Message message=new Message();
        message.what= FLAG_MOVIE_GET_NET;
        Bundle bundle=new Bundle();
        bundle.putString("jsonData",data);
        message.setData(bundle);
        mHandler.sendMessage(message);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //實現gridview的item點擊事件處理
        Intent intent=new Intent(ShowListActivity.this,MovieInfoActivity.class);
         intent.putExtra("info",list.get(i));
          startActivity(intent);

    }
    private boolean isOnline(){
        ConnectivityManager cm= (ConnectivityManager) ShowListActivity.this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();
        return ni!=null&&ni.isConnected();
    }
    private void printInfo(){
        Toast.makeText(ShowListActivity.this,"刷新失敗",Toast.LENGTH_SHORT).show();
    }
}
