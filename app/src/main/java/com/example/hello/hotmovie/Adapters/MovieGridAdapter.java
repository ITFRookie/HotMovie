package com.example.hello.hotmovie.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.hello.hotmovie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.hello.hotmovie.Beans.MovieItemBean;

/**
 * Created by Hello on 2016/12/7.
 */

public class MovieGridAdapter extends BaseAdapter {
    private Context context;
    private List<MovieItemBean> list;
    public MovieGridAdapter(Context context,List list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View showView=null;
        MovieItemBean bean=list.get(i);
        LayoutInflater inflater=LayoutInflater.from(context);
        showView=inflater.inflate(R.layout.movielistitem,null);
        ImageView iv= (ImageView) showView.findViewById(R.id.item_view);
       //使用Picasso框架加载图片到imageview
        Picasso.with(context).load(bean.getPoster_path()).placeholder(R.drawable.placeholder).into(iv);

        return showView;
    }
}
