package com.example.imdb_projekat;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMovieSearch extends AsyncTask<Integer,Void,String> {
    String data= "";
    public String param;

    @Override
    protected String doInBackground(Integer... integers) {
        URL url = null;
        try {


            url = new URL("https://api.themoviedb.org/3/search/movie?api_key=31c6bde9bc9913b0ebec804d326ac0c8&language=en-US&query=" + param+ "&page=1&include_adult=false");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String row = "";
            while(row != null )
            {
                row = bufferedReader.readLine();
                if (row != null)
                {
                    data += row+"\n";
                }
            }
            SearchActivity.data= data;

        }catch(Exception e)
        {

        }
        return data;
    }
}