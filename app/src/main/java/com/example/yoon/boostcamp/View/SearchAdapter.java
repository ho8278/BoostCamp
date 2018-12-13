package com.example.yoon.boostcamp.View;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yoon.boostcamp.Model.Movie;
import com.example.yoon.boostcamp.R;
import com.example.yoon.boostcamp.ViewModel.MovieItemViewModel;
import com.example.yoon.boostcamp.ViewModel.MovieViewModel;
import com.example.yoon.boostcamp.databinding.MovieItemBinding;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private ArrayList<Movie> arrayList;
    private MovieItemBinding binding;
    private ViewControl view;

    public SearchAdapter(ViewControl view){
        arrayList=new ArrayList<>();
        this.view=view;
    }

    public void setArr(ArrayList<Movie> arr){
        arrayList=arr;
        notifyDataSetChanged();
    }
    public void addArr(ArrayList<Movie>arr){
        arrayList.addAll(arr);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(arrayList.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_item,parent,false);
        binding.setMovie(new MovieItemViewModel(view));
        return new ViewHolder(binding.getRoot(),binding.getMovie());
    }

    @Override
    public int getItemCount() {
        if(arrayList==null)
            return 0;
        else
            return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private MovieItemViewModel viewModel;
        public ViewHolder(View itemView, MovieItemViewModel viewModel){
            super(itemView);
            this.viewModel=viewModel;
        }
        public void setItem(Movie item){
            viewModel.setMovieItem(item);
        }
    }

}
