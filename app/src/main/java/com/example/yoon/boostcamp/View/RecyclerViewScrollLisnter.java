package com.example.yoon.boostcamp.View;

import android.support.v7.widget.RecyclerView;

public abstract class RecyclerViewScrollLisnter extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(!recyclerView.canScrollVertically(1)){
            //아래쪽으로 더이상 스크롤 할 수 없다면
            loadItem();
        }
    }
    public abstract void loadItem();
}
