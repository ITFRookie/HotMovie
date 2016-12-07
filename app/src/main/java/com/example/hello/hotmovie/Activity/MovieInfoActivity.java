package com.example.hello.hotmovie.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hello.hotmovie.R;
import com.squareup.picasso.Picasso;

import com.example.hello.hotmovie.Beans.MovieItemBean;

public class MovieInfoActivity extends AppCompatActivity {
    MovieItemBean info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        info= (MovieItemBean) getIntent().getSerializableExtra("info");
        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        TextView title= (TextView) findViewById(R.id.info_title);
        TextView date= (TextView) findViewById(R.id.info_date);
        TextView vote= (TextView) findViewById(R.id.info_vote);
        TextView overView= (TextView) findViewById(R.id.info_overview);
        ImageView iv= (ImageView) findViewById(R.id.info_iv);
        title.setText(info.getTitle());
        date.setText(info.getRelease_date());
        vote.setText(info.getVote_average()+"分");
        Picasso.with(MovieInfoActivity.this).load(info.getPoster_path()).placeholder(R.drawable.placeholder).into(iv);
        overView.setText(info.getOverview());

    }

}
