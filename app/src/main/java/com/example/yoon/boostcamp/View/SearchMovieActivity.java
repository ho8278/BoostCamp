package com.example.yoon.boostcamp.View;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoon.boostcamp.Model.Movie;
import com.example.yoon.boostcamp.R;
import com.example.yoon.boostcamp.ViewModel.MovieViewModel;
import com.example.yoon.boostcamp.databinding.SearchMovieBinding;

import java.util.ArrayList;

public class SearchMovieActivity extends AppCompatActivity implements ViewControl{
    private SearchAdapter adapter;
    private SearchMovieBinding binding;
    private MovieViewModel movieViewModel;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.search_movie);
        checkNetInfo();
        movieViewModel=new MovieViewModel(this);
        binding.setVm(movieViewModel);
        adapter=new SearchAdapter(this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration divider=new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        binding.recycler.addItemDecoration(divider);
        binding.recycler.addItemDecoration(new MarginDecoration(40));   //movie_item margin 추가 CustomDecoration
        dialog=new ProgressDialog(this);
        binding.recycler.addOnScrollListener(new RecyclerViewScrollLisnter() {  //무한 스크롤
            @Override
            public void loadItem() {
                movieViewModel.loadAgain();
            }
        });
    }


    public void checkNetInfo(){
        ConnectivityManager conn=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info=conn.getActiveNetworkInfo();
        if(info!=null)
            return;
        showToast("네트워크 연결을 확인해 주세요.");
    }

    @Override
    public void showProgress(boolean onoff) {
        if(onoff) {
            dialog.show();
            dialog.setMessage("잠시만 기다려 주십시오");
            dialog.setCancelable(true);
        }
        else{
            if(dialog.isShowing())
                dialog.dismiss();
        }
    }
    public void hideKeyboard(){
        InputMethodManager manager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
    }
    @Override
    public void startActivity(String url) {
        CustomTabsIntent.Builder builder=new CustomTabsIntent.Builder();
        CustomTabsIntent intent=builder.build();
        builder.setToolbarColor(Color.rgb(3,207,93));
        intent.launchUrl(this, Uri.parse(url));
    }

    @Override
    public void showToast(String msg) {
        Toast toast=Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        TextView textView = new TextView(this);
        textView.setBackgroundColor(Color.rgb(3,207,93));
        textView.setText(" "+msg+" ");
        textView.setTextColor(Color.WHITE);
        toast.setView(textView);
        toast.show();
    }

    @Override
    public void setItemList(ArrayList<Movie> arr) {
        //adapter에 array설정
        adapter.setArr(arr);
    }

    @Override
    public void addItemList(ArrayList<Movie> arr) {
        //추가로 데이터를 불러올떄 adapter에 array추가
        adapter.addArr(arr);
    }
}
