package com.example.yoon.boostcamp.ViewModel;

import android.app.ProgressDialog;
import android.databinding.ObservableField;
import android.view.View;

import com.example.yoon.boostcamp.Model.Movie;
import com.example.yoon.boostcamp.Utils.SearchAPI;
import com.example.yoon.boostcamp.Utils.TaskFinish;
import com.example.yoon.boostcamp.View.ViewControl;

import java.util.ArrayList;

public class MovieViewModel implements TaskFinish{
    public ObservableField<String> text = new ObservableField<>();
    private ViewControl view;
    private SearchAPI naverAPI;
    private int preCount;
    private boolean noMore;
    public MovieViewModel(ViewControl view){
        preCount=0;
        this.view=view;
    }
    public void onButtonClick(View view){
        naverAPI=new SearchAPI(text.get(),this);
        naverAPI.execute();
        noMore=false;
        this.view.hideKeyboard();
        //영화 리스트 가져오고 출력
    }


    public void loadAgain(){
        if(noMore)
            return;
        naverAPI=new SearchAPI(text.get(),this);
        naverAPI.execute(preCount);
    }

    public ArrayList<Movie> getArr(){
        return naverAPI.getResultArr();
    }

    @Override
    public void start() {
        view.showProgress(true);
    }

    @Override
    public void error() {
        view.showProgress(false);
        view.showToast("네트워크 오류가 발생하였습니다.");
    }
    @Override
    public void finish() {
        view.showProgress(false);
        if(getArr().size()>0) {
            this.view.setItemList(getArr());
            preCount = naverAPI.getResultArr().size()+1;
            if(getArr().size()<100)
                noMore=true;
        }
        else {
            this.view.setItemList(getArr());
            this.view.showToast("'" + text.get() + "'의 검색 결과가 없습니다.");
        }
    }

    public void loadAgainFinish(){
        if(getArr().size()>0) {
            this.view.addItemList(getArr());
            preCount += naverAPI.getResultArr().size()+1;
            if(getArr().size()<100)
                noMore=true;
        }
    }


    @Override
    public void NetworkError() {
        view.showProgress(false);
        this.view.showToast("네트워크 연결을 확인해 주세요.");
    }
}
