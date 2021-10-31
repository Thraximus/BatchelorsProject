package com.example.imdb_projekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class FavouritesActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHERED_PREFERENCES_PREF,0);
        int iterator = sharedPreferences.getInt("Iterator",0);
        //String ids="";
        ArrayList<Integer> ids = new ArrayList<>();

        for (int i = 1; i<= iterator;i++) {
            ids.add(sharedPreferences.getInt("Movie:"+i,0));
        }
        int temp = 0;
        int[] idArray= new int[ids.size()];
        for(int integer : ids)
        {
            idArray[temp]= integer;
            temp++;
        }


        LinkedList<Movie> list = MovieApi.getFavouriteMovies(idArray);

        LinearLayout mainScrollView = (LinearLayout) findViewById(R.id.mainFavouritesScrollView);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (list!=null) {
            for (Movie movie : list) {

                RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.movie_layout_view, null);

                ((TextView) item.findViewById(R.id.movieTitleFillable)).setText(movie.getName());
                ((TextView) item.findViewById(R.id.scoreFillable)).setText(movie.getScore());
                ((TextView) item.findViewById(R.id.genresFillable)).setText(movie.getGenres());
                ((TextView) item.findViewById(R.id.releaseDateFillable)).setText(movie.getReleaseDate());

                item.findViewById(R.id.moreDetailsButton).setOnClickListener(this);
                item.findViewById(R.id.moreDetailsButton).setId(movie.getId());

                String url = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();

                ImageView poster = (ImageView) item.findViewById(R.id.imageView);

                new DownloadImageTask(poster).execute(url);

                mainScrollView.addView(item);
            }
        }else
        {

        }
        findViewById(R.id.clearFavouriteButton).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.clearFavouriteButton)
        {
            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHERED_PREFERENCES_PREF,0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Iterator",0);
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            String id = Integer.toString(view.getId());

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("MOVIE_ID", id);
            startActivity(intent);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}