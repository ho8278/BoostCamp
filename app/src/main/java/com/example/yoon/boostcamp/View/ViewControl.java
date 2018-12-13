package com.example.yoon.boostcamp.View;

import com.example.yoon.boostcamp.Model.Movie;

import java.util.ArrayList;

public interface ViewControl {
    /*

     */
    public void hideKeyboard();
    public void startActivity(String url);
    public void showToast(String msg);
    public void showProgress(boolean onoff);
    public void setItemList(ArrayList<Movie> arr);
    public void addItemList(ArrayList<Movie> arr);
}
