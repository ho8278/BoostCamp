package com.example.yoon.boostcamp.Utils;

import android.os.AsyncTask;

import com.example.yoon.boostcamp.Model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchAPI {
    private TaskFinish finished;
    private String _URL="https://openapi.naver.com/v1/search/movie.json?query=";
    private ArrayList<Movie> resultArr;
    private String title;
    private boolean loadMoreFlag=false;
    public static int ITEM_DISPLAY=100;
    public SearchAPI(String title,TaskFinish task){
        finished=task;
        this.title=title;
        _URL+=title+"&display="+ITEM_DISPLAY;
        resultArr=new ArrayList<>();
    }
    public void execute(){
        loadMoreFlag=false;
        finished.start();
        new APITask().execute();
    }

    public void execute(int start){
        loadMoreFlag=true;
        resultArr.clear();
        _URL="https://openapi.naver.com/v1/search/movie.json?query="+title+"&display="+ITEM_DISPLAY+"&start="+start;
        new APITask().execute();
    }

    public ArrayList<Movie> getResultArr(){
        return resultArr;
    }


    public class APITask extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray items = object.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    Movie movieItem = new Movie();
                    movieItem.setTitle(items.getJSONObject(i).getString("title"));
                    movieItem.setLink(items.getJSONObject(i).getString("link"));
                    movieItem.setImageUrl(items.getJSONObject(i).getString("image"));
                    movieItem.setPubDate(items.getJSONObject(i).getString("pubDate"));
                    movieItem.setDirector(items.getJSONObject(i).getString("director"));
                    movieItem.setActor(items.getJSONObject(i).getString("actor"));
                    movieItem.setUserRating((float)items.getJSONObject(i).getDouble("userRating"));
                    resultArr.add(movieItem);
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            if(loadMoreFlag)
                finished.loadAgainFinish();
            else
                finished.finish();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            finished.NetworkError();
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder builder=new StringBuilder();
            try {
                URL url = new URL(_URL);
                HttpURLConnection connection =(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("X-Naver-Client-Id","hyYiv86MBTv496p2xfDu");
                connection.setRequestProperty("X-Naver-Client-Secret","eXFnWHiNtt");
                connection.setRequestProperty("Content-Type","applicaton/json");
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                    BufferedReader reader= new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                    String line="";
                    while((line=reader.readLine()) != null) {
                        builder.append(line);
                    }
                }
                else{
                    finished.error();
                }
            }catch(Exception e) {
                e.printStackTrace();
                publishProgress();
            }
            return builder.toString();
        }
    }
}
