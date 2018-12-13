package com.example.yoon.boostcamp.ViewModel;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.example.yoon.boostcamp.Model.Movie;
import com.example.yoon.boostcamp.View.SearchAdapter;

import java.util.ArrayList;

public class BindingFunction {
    @BindingAdapter({"loadImage"})
    public static void loadImage(final ImageView view, final String url){
        Glide.with(view).load(url).into(view);
    }
}
