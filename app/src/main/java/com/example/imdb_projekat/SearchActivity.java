package com.example.imdb_projekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import java.io.InputStream;
import java.util.LinkedList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    public static String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String kw = getIntent().getStringExtra("KEYWORD");
        kw.replace(" ", "%20");

        LinkedList<Movie> list = MovieApi.getMoviesByName(kw);

        LinearLayout mainScrollView = (LinearLayout) findViewById(R.id.mainSearchScrollView);

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


    }

    @Override
    public void onClick(View view) {
        String id = Integer.toString(view.getId());

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("MOVIE_ID", id);
        startActivity(intent);
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