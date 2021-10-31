package com.example.imdb_projekat;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class fetchData extends AsyncTask<Void,Void,String>
{
    String data= "";
    //public static int page=1;
    @Override
    protected String doInBackground(Void... voids) {
        try
        {
            URL url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=31c6bde9bc9913b0ebec804d326ac0c8&language=en-US&page=1");
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
            MainActivity.data= data;

        }catch(Exception e)
        {

        }
        return data;
    }


}
