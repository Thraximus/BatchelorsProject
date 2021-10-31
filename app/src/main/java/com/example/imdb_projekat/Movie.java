package com.example.imdb_projekat;

public class Movie {
    private int id;
    private String name;
    private String posterPath;
    private String backdropPath;
    private String overview;
    private String releaseDate;
    private double score;
    private String[] genres;

    public String getBackdropPath() {
        return backdropPath;
    }
    public String getName() {
        return name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getScore() {

        return Double.toString(score);
    }

    public String getGenres() {
        String parsed="";
        for (int i = 0; i<genres.length; i++)
        {
            if (i==0)
            {
                parsed += genres[0];
            }
            else
            {
                parsed += " | " + genres[i];
            }
        }
         return parsed;
    }

    public int getId() {
        return id;
    }

    public Movie(int id, String name, String posterPath,String backdropPath, String overview, String releaseDate, double score, String[] genres) {
        this.backdropPath = backdropPath;
        this.id=id;
        this.name = name;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.score = score;
        this.genres = genres;
    }
}
