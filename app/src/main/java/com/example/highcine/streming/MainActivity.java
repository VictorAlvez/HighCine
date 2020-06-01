package com.example.highcine.streming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.highcine.adapters.MovieAdapter;
import com.example.highcine.adapters.MovieItemClick;
import com.example.highcine.R;
import com.example.highcine.adapters.SlidePagerAdapt;
import com.example.highcine.models.Movie;
import com.example.highcine.models.Slide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MovieItemClick {

    private List<Slide> slideList;
    private ViewPager sliderPager;
    private TabLayout indicator;
    private RecyclerView MoviesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderPager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_mov);

        slideList = new ArrayList<>();
        slideList.add(new Slide(R.drawable.slide1,"Os oito odiados"));
        slideList.add(new Slide(R.drawable.slide2,"Operação Dragão"));
        slideList.add(new Slide(R.drawable.slide3,"King Kong"));
        slideList.add(new Slide(R.drawable.slide4,"Exterminador do Futuro"));
        slideList.add(new Slide(R.drawable.slide5,"Sin City"));

        SlidePagerAdapt adapter = new SlidePagerAdapt(this, slideList);
        sliderPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainActivity.SlideTimer(), 4000,6000);
        indicator.setupWithViewPager(sliderPager,true);


        List<Movie> MovieList = new ArrayList<>();
        MovieList.add(new Movie("11 Homens 1 Segredo",R.drawable.onze,R.drawable.onzecov));
        MovieList.add(new Movie("John Wick 3",R.drawable.johnwick3,R.drawable.johnwick3cov));
        MovieList.add(new Movie("Vingadores Guerra Infinita",R.drawable.avengers,R.drawable.ainfinitywcov));
        MovieList.add(new Movie("Polar",R.drawable.polar,R.drawable.polarcov));
        MovieList.add(new Movie("Sniper Americano",R.drawable.sniperamericano,R.drawable.amsnipercov));

        MovieAdapter movieAdapter = new MovieAdapter(this,MovieList, this);
        MoviesRV.setAdapter(movieAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));



    }

    @Override
    public void OnMovieClick(Movie movie, ImageView movieImageView) {

        Intent intent = new Intent(this, MDActivity.class);
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbnail());
        intent.putExtra("imgCover",movie.getCoverPhoto());


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,movieImageView, "sName");

        startActivity(intent,options.toBundle());

    }

    class SlideTimer extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderPager.getCurrentItem()<slideList.size()-1){
                        sliderPager.setCurrentItem(sliderPager.getCurrentItem()+1);

                    }
                    else
                        sliderPager.setCurrentItem(0);
                }
            });

        }
    }
}
