package javaapplication1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    
    private Map game_genre;         // связь сущностей ИГРА и ЖАНР.
                                    // представляет из себя HashMap<String, List<String>>
                                    // id Игры и Жанра является уникальным ключём
                                    // для доступа к списку жанров конкретной игры и 
                                    //               списку игр этого жанра соответственно
    
    private List<Game> gamelist;    // все игры
    private List<Genre> genrelist;  // все жанры

    public Model() {
        game_genre = new HashMap<String, List<String>>();
        gamelist = new ArrayList<>();
        genrelist = new ArrayList<>();
    }

    public Game createGame(String game_name, String game_data) {
        Game new_game = new Game((game_name + game_data).hashCode(), game_name, game_data);
        gamelist.add(new_game);
        return new_game;
    }

    public Genre createGenre(String genre_name) {
        Genre new_genre = new Genre(genre_name.hashCode(), genre_name);
        genrelist.add(new_genre);
        return new_genre;
    }

    public void setGameGenre(String game_id, String genre_id) {

        if (game_genre.get(game_id) == null) {
            List<String> temp = new ArrayList<String>();
            temp.add(genre_id);
            game_genre.put(game_id, temp);
        } else {
            List temp = (List) game_genre.get(game_id);
            temp.add(genre_id);
        }

        if (game_genre.get(genre_id) == null) {
            List<String> temp = new ArrayList<String>();
            temp.add(game_id);
            game_genre.put(genre_id, temp);
        } else {
            List temp = (List) game_genre.get(genre_id);
            temp.add(game_id);
            game_genre.put(genre_id, temp);
        }
    }

    // возввращает List всех жанров игры
    public List getGameGenre(String game_id) {
        return (List) game_genre.get(game_id);
    }

    // возввращает List всех игр этого жанра
    public List getGenreGame(String genre_id) {
        return (List) game_genre.get(genre_id);
    }

    
    public void removeGenreForGame(String game_id, String genre_id) {
        List temp = getGameGenre(game_id);
        if (temp.contains(genre_id) == false) {
            return;
        } else {
            temp.remove(genre_id);
            List temp2 = getGenreGame(genre_id);
            temp2.remove(game_id);
            return;
        }
    }

}
