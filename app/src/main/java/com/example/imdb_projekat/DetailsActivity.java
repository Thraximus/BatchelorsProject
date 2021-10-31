package com.example.imdb_projekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{
    public static String data;
    private Button buttonFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        buttonFavourite = (Button) findViewById(R.id.favouriteButton);




        String movId = getIntent().getStringExtra("MOVIE_ID");


        Movie m = MovieApi.getMovieById(Integer.valueOf(movId));

        buttonFavourite.setId(m.getId());


        ((TextView) findViewById(R.id.nameDetailedFillable)).setText(m.getName());
        ((TextView) findViewById(R.id.genresDetailedFillable2)).setText(m.getGenres());
        ((TextView) findViewById(R.id.synopsisFillable)).setText(m.getOverview());
        ((TextView) findViewById(R.id.scoreDetailedFillable)).setText(m.getScore());
        ((TextView) findViewById(R.id.releaseDateDetailedFillable)).setText(m.getReleaseDate());
        ImageView poster = (ImageView) findViewById(R.id.imageDetailedFillable);

        String url = "https://image.tmdb.org/t/p/original"+  m.getBackdropPath();
        new DownloadImageTask(poster).execute(url);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHERED_PREFERENCES_PREF,0);
        int iter = sharedPreferences.getInt("Iterator",0);
        boolean favourite = false;
        for(int i = 0 ;i<iter;i++)
        {
            if (m.getId()==sharedPreferences.getInt("Movie:"+iter,0)) {
                favourite=true;
            }
        }
        if (favourite)
        {
            buttonFavourite.setText("Movie already in favourites");
        }
        else
        {
            buttonFavourite.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        saveFavourite();
    }

    private void saveFavourite()
    {
        //MainActivity.favaourites.add();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHERED_PREFERENCES_PREF,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Iterator",0);

        //editor.clear();
        //editor.commit();
        int iterator = sharedPreferences.getInt("Iterator",0);
        boolean existing = false;
        int buttonId = buttonFavourite.getId();
        for (int i = 0; i<= iterator;i++) {
            if(buttonId==sharedPreferences.getInt("Movie:"+iterator,0))
            {
                existing=true;
            }
        }
        if (!existing)
        {
        iterator++;
        editor.putInt("Iterator", iterator);
        editor.putInt("Movie:" + iterator, buttonFavourite.getId());
        editor.commit();
        buttonFavourite.setText("Movie already in favourites");
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