package javaapplication1;

import java.util.Random;

public class Genre {

    private int genre_id;
    private String genre_name;

    public Genre() {
        Random rand = new Random();
        genre_id = rand.nextInt(10000);
        genre_name = "test" + Integer.toString(genre_id);
    }

    public Genre(int id, String name) {
        genre_id = id;
        genre_name = name;
    }

    public int getGenreId() {
        return (genre_id);
    }

    public String getGenreName() {
        return (genre_name);
    }

    public void setGenreName(String name) {
        genre_name = name;
    }

}
