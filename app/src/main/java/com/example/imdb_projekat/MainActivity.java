package com.example.imdb_projekat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static String data;
    //public static LinkedList<Integer>  favaourites = new LinkedList<Integer>();
    public static String SHERED_PREFERENCES_PREF = "FavouritesList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //findViewById(R.id.nextPageButton).setOnClickListener(this);
        setContentView(R.layout.activity_main);
        LinkedList<Movie> list = MovieApi.getAllMovies();

        LinearLayout mainScrollView = (LinearLayout) findViewById(R.id.MainScrollView);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);




        for (Movie movie: list)
        {

            RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.movie_layout_view, null);

            ((TextView) item.findViewById(R.id.movieTitleFillable)).setText(movie.getName());
            ((TextView) item.findViewById(R.id.scoreFillable)).setText(movie.getScore());
            ((TextView) item.findViewById(R.id.genresFillable)).setText(movie.getGenres());
            ((TextView) item.findViewById(R.id.releaseDateFillable)).setText(movie.getReleaseDate());

            item.findViewById(R.id.moreDetailsButton).setOnClickListener(this);
            item.findViewById(R.id.moreDetailsButton).setId(movie.getId());

            String url = "https://image.tmdb.org/t/p/w500"+  movie.getPosterPath();

            ImageView poster = (ImageView) item.findViewById(R.id.imageView);

            new DownloadImageTask(poster).execute(url);

            mainScrollView.addView(item);
        }

        findViewById(R.id.buttonSearch).setOnClickListener(this);
        findViewById(R.id.buttonFavourites).setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.buttonSearch)
        {
            String keyword = ((EditText)findViewById(R.id.searchBar)).getText().toString();
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("KEYWORD", keyword);
            startActivity(intent);
        }
        else if (view.getId()==R.id.buttonFavourites)
        {
            Intent intent = new Intent(this, FavouritesActivity.class);
            startActivity(intent);
        }else
        {
            String id = Integer.toString(view.getId());

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("MOVIE_ID", id);
            startActivity(intent);
        }

    }

}