package com.example.imdb_projekat;


import android.widget.Switch;

import java.util.LinkedList;
import java.util.ArrayList;

public class MovieApi {

    public static LinkedList<Movie> getAllMoviesFake()
    {

        LinkedList<Movie> list = new LinkedList<>();
        String[] genres = new String[4];
        genres[0]="Action";
        genres[1]="Fantasy";
        genres[2]="Mystery";
        genres[3]="Romance";
        list.add(new Movie(1,"Movie1","/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg","/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg","Something very cool happens","2020-02-01",4.4,genres));
        list.add(new Movie(2,"Movie2","/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg","/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg","Something very cool happens again","2020-02-02",4.5,genres));
        list.add(new Movie(3,"Movie3","/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg","/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg","Something very cool happens yet again","2020-02-03",4.6,genres));
        list.add(new Movie(4,"Movie4","/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg","/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg","Something very cool happens one last time","2020-02-04",4.7,genres));
        list.add(new Movie(5,"Movie3","/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg","/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg","Something very cool happens yet again","2020-02-03",4.6,genres));
        list.add(new Movie(6,"Movie4","/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg","/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg","Something very cool happens one last time","2020-02-04",4.7,genres));
        list.add(new Movie(7,"Movie3","/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg","/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg","Something very cool happens yet again","2020-02-03",4.6,genres));
        list.add(new Movie(8,"Movie4","/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg","/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg","Something very cool happens one last time","2020-02-04",4.7,genres));

        return list;
    }

    public static LinkedList<Movie> getAllMovies()
    {
        int id=0;
        String name="";
        String posterPath="";
        String backdropPath="";
        String overview="";
        String releaseDate="";
        double score=0.0;
        String unparsedGenres="";
        ArrayList<Integer> numGenres = new ArrayList<Integer>();


        LinkedList<Movie> list = new LinkedList<>();
        fetchData process = new fetchData();
        process.execute();

        while (MainActivity.data == null)
        {

        }
        String object="";
        LinkedList<String> objects = new LinkedList<>();
        String data = MainActivity.data;
        for (int i = 24; i<data.length();i++)
        {
            if (data.charAt(i)=='{')
            {
                while(data.charAt(i)!='}')
                {
                    object+=data.charAt(i);
                    i++;
                }
                objects.add(object);
                object = "";
            }
        }
        object = "";
        for(String s: objects)
        {
            for(int i=0;i<s.length();i++)
            {
                object+=s.charAt(i);
                if (object.contains("poster_path"))
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!='\"')
                    {
                        if(s.charAt(i)!='\"')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    posterPath=object;
                    posterPath.replace("\\","/");
                    object="";
                }
                else if (object.contains("id\":"))
                {
                    i+=1;
                    object="";
                    while(s.charAt(i)!=',')
                    {
                        if(s.charAt(i)!=',')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    id=Integer.valueOf(object);
                    object="";
                }else if (object.contains("backdrop_path"))
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!='\"')
                    {
                        if(s.charAt(i)!='\"')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    backdropPath=object;
                    backdropPath.replace("\\","/");
                    object="";
                } else if (object.contains("original_title"))
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!='\"')
                    {
                        if(s.charAt(i)!='\"')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    name=object;
                    object="";
                } else if (object.contains("genre_ids"))
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!=']')
                    {
                        if(s.charAt(i)!=']')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    unparsedGenres=object;
                    object="";
                }else if (object.contains("vote_average"))
                {
                    i+=3;
                    object="";
                    while(s.charAt(i)!=',')
                    {
                        if(s.charAt(i)!=',')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    score=Double.valueOf(object);
                    object="";
                }else if (object.contains("overview"))
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!='"')
                    {
                        if(s.charAt(i)!='"')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    overview=object;
                    object="";
                }else if (object.contains("release_date"))
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!='"')
                    {
                        if(s.charAt(i)!='"')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    releaseDate=object;
                    object="";
                }


            }

            object = "";
            for (int j=0;j<unparsedGenres.length();j++)
            {
                while(!object.contains(",")&&j<unparsedGenres.length())
                {
                    object+= unparsedGenres.charAt(j);
                    j++;
                }
                if(object.contains(",")) {
                    object = object.substring(0,object.length()-1);
                    numGenres.add(Integer.valueOf(object));
                    object="";
                    object+= unparsedGenres.charAt(j);
                }else {
                    numGenres.add(Integer.valueOf(object));
                    object = "";
                }

            }
            String[] genres = new String[numGenres.size()];
            int k = 0;
            for(int i : numGenres)
            {

                switch(i)
                {
                    case 28:
                        genres[k]="Action";
                        break;
                    case 12:
                        genres[k]="Adventure";
                        break;
                    case 16:
                        genres[k]="Animation";
                        break;
                    case 35:
                        genres[k]="Comedy";
                        break;
                    case 80:
                        genres[k]="Crime";
                        break;
                    case 99:
                        genres[k]="Documentary";
                        break;
                    case 18:
                        genres[k]="Drama";
                        break;
                    case 10751:
                        genres[k]="Familly";
                        break;
                    case 14:
                        genres[k]="Fantasy";
                        break;
                    case 36:
                        genres[k]="History";
                        break;
                    case 27:
                        genres[k]="Horror";
                        break;
                    case 10402:
                        genres[k]="Music";
                        break;
                    case 9648:
                        genres[k]="Mystery";
                        break;
                    case 10749:
                        genres[k]="Romance";
                        break;
                    case 878:
                        genres[k]="Science Fiction";
                        break;
                    case 100770:
                        genres[k]="TV Movie";
                        break;
                    case 53:
                        genres[k]="Thriller";
                        break;
                    case 10752:
                        genres[k]="War";
                        break;
                    case 37:
                        genres[k]="Western";
                        break;
                    default:
                        genres[k]="Unknown";
                }
                k++;
            }
            for (int l =0;l<numGenres.size();l++) {
                numGenres.remove(l);
            }
            list.add(new Movie(id,name,posterPath,backdropPath,overview,releaseDate,score,genres));
            genres=null;
        }
        return list;

    }

    public static Movie getMovieById(int id)  {

        String name="";
        String posterPath="";
        String backdropPath="";
        String overview="";
        String releaseDate="";
        double score=0.0;
        String unparsedGenres="";
        ArrayList<Integer> numGenres = new ArrayList<Integer>();
        Movie m;


        LinkedList<Movie> list = new LinkedList<>();
        FetchMovieById process2 = new FetchMovieById();
        process2.id = id;
        process2.execute();
        try {

            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while (DetailsActivity.data == null )
        {

        }

        String object="";
        LinkedList<String> objects = new LinkedList<>();
        String s = DetailsActivity.data;
        //DetailsActivity.data=null;
            for(int i=0;i<s.length();i++)
            {
                object+=s.charAt(i);
                if (object.contains("poster_path")&& (posterPath == "" || posterPath=="ull"))
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!='\"')
                    {
                        if(s.charAt(i)!='\"')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    posterPath=object;
                    //posterPath.replace("\\","/");
                    object="";

                }else if (object.contains("backdrop_path") &&  backdropPath == "")
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!='\"')
                    {
                        if(s.charAt(i)!='\"')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    backdropPath=object;
                    //backdropPath.replace("\\","/");
                    object="";
                } else if (object.contains("original_title"))
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!='\"')
                    {
                        if(s.charAt(i)!='\"')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    name=object;
                    object="";
                } else if (object.contains("genres\":"))
                {


                    i+=3;
                    object="";
                    while(s.charAt(i)!=']' )
                    {
                        if(s.charAt(i)!=']')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    object = object;



                    String temp="";
                    for(int j = 0;j<object.length();j++)
                    {
                        temp+=object.charAt(j);
                        if(temp.contains("id\":"))
                        {
                            j++;
                            temp="";
                            while(object.charAt(j)!='"')
                            {
                                if(object.charAt(j)!='"')
                                {
                                    temp+=object.charAt(j);
                                    j++;
                                }
                            }

                            unparsedGenres+=temp;

                            temp="";
                        }
                    }



                    unparsedGenres= unparsedGenres.substring(0,unparsedGenres.length()-1);
                    object="";


                }else if (object.contains("vote_average"))
                {
                    i+=3;
                    object="";
                    while(s.charAt(i)!=',')
                    {
                        if(s.charAt(i)!=',')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    score=Double.valueOf(object);
                    object="";
                }else if (object.contains("overview"))
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!='"')
                    {
                        if(s.charAt(i)!='"')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    overview=object;
                    object="";
                }else if (object.contains("release_date"))
                {
                    i+=4;
                    object="";
                    while(s.charAt(i)!='"')
                    {
                        if(s.charAt(i)!='"')
                        {
                            object += s.charAt(i);
                            i++;
                        }
                    }
                    releaseDate=object;
                    object="";
                }


            }

            object = "";
            for (int j=0;j<unparsedGenres.length();j++)
            {
                while(!object.contains(",")&&j<unparsedGenres.length())
                {
                    object+= unparsedGenres.charAt(j);
                    j++;
                }
                if(object.contains(",")) {
                    object = object.substring(0,object.length()-1);
                    numGenres.add(Integer.valueOf(object));
                    object="";
                    object+= unparsedGenres.charAt(j);
                }else {
                    numGenres.add(Integer.valueOf(object));
                    object = "";
                }

            }
            String[] genres = new String[numGenres.size()];
            int k = 0;
            for(int i : numGenres)
            {

                switch(i)
                {
                    case 28:
                        genres[k]="Action";
                        break;
                    case 12:
                        genres[k]="Adventure";
                        break;
                    case 16:
                        genres[k]="Animation";
                        break;
                    case 35:
                        genres[k]="Comedy";
                        break;
                    case 80:
                        genres[k]="Crime";
                        break;
                    case 99:
                        genres[k]="Documentary";
                        break;
                    case 18:
                        genres[k]="Drama";
                        break;
                    case 10751:
                        genres[k]="Familly";
                        break;
                    case 14:
                        genres[k]="Fantasy";
                        break;
                    case 36:
                        genres[k]="History";
                        break;
                    case 27:
                        genres[k]="Horror";
                        break;
                    case 10402:
                        genres[k]="Music";
                        break;
                    case 9648:
                        genres[k]="Mystery";
                        break;
                    case 10749:
                        genres[k]="Romance";
                        break;
                    case 878:
                        genres[k]="Science Fiction";
                        break;
                    case 100770:
                        genres[k]="TV Movie";
                        break;
                    case 53:
                        genres[k]="Thriller";
                        break;
                    case 10752:
                        genres[k]="War";
                        break;
                    case 37:
                        genres[k]="Western";
                        break;
                    default:
                        genres[k]="Unknown";
                }
                k++;
            }
            for (int l =0;l<numGenres.size();l++) {
                numGenres.remove(l);
            }
            m = new Movie(id,name,posterPath,backdropPath,overview,releaseDate,score,genres);
            genres=null;


        return m;

    }

    public static LinkedList<Movie> getMoviesByName(String keyword)
    {
        int id=0;
        String name="";
        String posterPath="";
        String backdropPath="";
        String overview="";
        String releaseDate="";
        double score=0.0;
        String unparsedGenres="";
        ArrayList<Integer> numGenres = new ArrayList<Integer>();


        LinkedList<Movie> list = new LinkedList<>();
        FetchMovieSearch process3 = new FetchMovieSearch();
        process3.param = keyword;
        process3.execute();

        try {

            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (SearchActivity.data == null)
        {

        }
        String object="";
        LinkedList<String> objects = new LinkedList<>();
        String data = SearchActivity.data;
        for (int i = 15; i<data.length();i++)
        {
            if (data.charAt(i)=='{')
            {
                while(data.charAt(i)!='}')
                {
                    object+=data.charAt(i);
                    i++;
                }
                objects.add(object);
                object = "";
            }
        }
        object = "";
        for(String s: objects)
        {

            if(!s.contains("null")) {
                for (int i = 0; i < s.length(); i++) {
                    object += s.charAt(i);
                    if (object.contains("poster_path")) {
                        i += 4;
                        object = "";
                        while (s.charAt(i) != '\"') {
                            if (s.charAt(i) != '\"') {
                                object += s.charAt(i);
                                i++;
                            }
                        }
                        posterPath = object;
                        posterPath.replace("\\", "/");
                        object = "";
                    } else if (object.contains("id\":")) {
                        i += 1;
                        object = "";
                        while (s.charAt(i) != ',') {
                            if (s.charAt(i) != ',') {
                                object += s.charAt(i);
                                i++;
                            }
                        }
                        id = Integer.valueOf(object);
                        object = "";
                    } else if (object.contains("backdrop_path")) {
                        i += 4;
                        object = "";
                        while (s.charAt(i) != '\"') {
                            if (s.charAt(i) != '\"') {
                                object += s.charAt(i);
                                i++;
                            }
                        }
                        backdropPath = object;
                        //backdropPath.replace("\\","/");
                        object = "";
                    } else if (object.contains("original_title")) {
                        i += 4;
                        object = "";
                        while (s.charAt(i) != '\"') {
                            if (s.charAt(i) != '\"') {
                                object += s.charAt(i);
                                i++;
                            }
                        }
                        name = object;
                        object = "";
                    } else if (object.contains("genre_ids")) {
                        i += 4;
                        object = "";
                        while (s.charAt(i) != ']') {
                            if (s.charAt(i) != ']') {
                                object += s.charAt(i);
                                i++;
                            }
                        }
                        unparsedGenres = object;
                        object = "";
                    } else if (object.contains("vote_average")) {
                        i += 3;
                        object = "";
                        while (s.charAt(i) != ',') {
                            if (s.charAt(i) != ',') {
                                object += s.charAt(i);
                                i++;
                            }
                        }
                        score = Double.valueOf(object);
                        object = "";

                    } else if (object.contains("overview")) {
                        i += 4;
                        object = "";
                        while (s.charAt(i) != '"') {
                            if (s.charAt(i) != '"') {
                                object += s.charAt(i);
                                i++;
                            }
                        }
                        overview = object;
                        object = "";
                    } else if (object.contains("release_date")) {
                        i += 4;
                        object = "";
                        while (s.charAt(i) != '"') {
                            if (s.charAt(i) != '"') {
                                object += s.charAt(i);
                                i++;
                            }
                        }
                        releaseDate = object;
                        object = "";
                    }


                }

                object = "";
                if (unparsedGenres.length() > 0) {
                    for (int j = 0; j < unparsedGenres.length(); j++) {
                        while (!object.contains(",") && j < unparsedGenres.length()) {
                            object += unparsedGenres.charAt(j);
                            j++;
                        }
                        if (object.contains(",")) {
                            object = object.substring(0, object.length() - 1);
                            numGenres.add(Integer.valueOf(object));
                            object = "";
                            object += unparsedGenres.charAt(j);
                        } else {
                            numGenres.add(Integer.valueOf(object));
                            object = "";
                        }

                    }
                } else {
                    numGenres.add(69);
                }
                String[] genres = new String[numGenres.size()];
                int k = 0;
                for (int i : numGenres) {

                    switch (i) {
                        case 28:
                            genres[k] = "Action";
                            break;
                        case 12:
                            genres[k] = "Adventure";
                            break;
                        case 16:
                            genres[k] = "Animation";
                            break;
                        case 35:
                            genres[k] = "Comedy";
                            break;
                        case 80:
                            genres[k] = "Crime";
                            break;
                        case 99:
                            genres[k] = "Documentary";
                            break;
                        case 18:
                            genres[k] = "Drama";
                            break;
                        case 10751:
                            genres[k] = "Familly";
                            break;
                        case 14:
                            genres[k] = "Fantasy";
                            break;
                        case 36:
                            genres[k] = "History";
                            break;
                        case 27:
                            genres[k] = "Horror";
                            break;
                        case 10402:
                            genres[k] = "Music";
                            break;
                        case 9648:
                            genres[k] = "Mystery";
                            break;
                        case 10749:
                            genres[k] = "Romance";
                            break;
                        case 878:
                            genres[k] = "Science Fiction";
                            break;
                        case 100770:
                            genres[k] = "TV Movie";
                            break;
                        case 53:
                            genres[k] = "Thriller";
                            break;
                        case 10752:
                            genres[k] = "War";
                            break;
                        case 37:
                            genres[k] = "Western";
                            break;
                        default:
                            genres[k] = "Unknown";
                    }
                    k++;
                }
                for (int l = 0; l < numGenres.size(); l++) {
                    numGenres.remove(l);
                }
                if (posterPath != "ull" ) {
                    list.add(new Movie(id, name, posterPath, backdropPath, overview, releaseDate, score, genres));
                }
                genres = null;
            }
        }
        return list;

    }

    public static LinkedList<Movie> getFavouriteMovies(int[] ids)
    {
        LinkedList<Movie> list = new LinkedList<>();
        int id=0;
        for(int i = 0;i<ids.length;i++)
        {
            id=ids[i];
            list.add(getMovieById(id));
        }
        return list;
    }





}
