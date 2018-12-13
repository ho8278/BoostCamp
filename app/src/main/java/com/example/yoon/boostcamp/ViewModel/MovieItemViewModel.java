package com.example.yoon.boostcamp.ViewModel;

import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.text.Html;
import android.view.View;

import com.example.yoon.boostcamp.Model.Movie;
import com.example.yoon.boostcamp.View.ViewControl;

public class MovieItemViewModel {
    public ObservableField<String> bindTitle=new ObservableField<>();
    public ObservableField<String> bindLink=new ObservableField<>();
    public ObservableField<String> bindImageUrl=new ObservableField<>();
    public ObservableField<String> bindPubDate=new ObservableField<>();
    public ObservableField<String> bindDirector=new ObservableField<>();
    public ObservableField<String> bindActor=new ObservableField<>();
    public ObservableFloat bindUserRating=new ObservableFloat();
    private ViewControl view;

    public MovieItemViewModel(ViewControl view){
        this.view=view;
    }

    public void setMovieItem(Movie item){
        bindTitle.set(item.getTitle());
        bindLink.set(item.getLink());
        bindImageUrl.set(item.getImageUrl());
        bindPubDate.set(item.getPubDate());
        bindDirector.set(item.getDirector());
        bindActor.set(item.getActor());
        bindUserRating.set(item.getUserRating()/(float)2.0);
    }

    public void onItemClick(View view){
        this.view.startActivity(bindLink.get());
    }
}
