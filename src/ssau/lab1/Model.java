package ssau.lab1;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    
    private Map<String,  List<String>> gameGenreMap;         // связь сущностей ИГРА и ЖАНР.
                                    // представляет из себя HashMap<String, List<String>>
                                    // id Игры и Жанра является уникальным ключём
                                    // для доступа к списку жанров конкретной игры и 
                                    //               списку игр этого жанра соответственно
    
    private Map<String, Game> gameMap;
    private Map<String, Genre> genreMap;


    public Model() {
        gameGenreMap = new HashMap<String, List<String>>();
        gameMap = new HashMap<String, Game>();
        genreMap = new HashMap<String, Genre>();
    }

    @NotNull
    public Game createGame(@NotNull final String gameName, @NotNull final String gameData, @NotNull final List<Genre> genrelist) {
        Game game = new Game(gameName, gameData, genrelist);
        gameMap.put(game.getGameId(), game);
        gameGenreMap.put(game.getGameId(), getGenreIds(game.getGenreList()));
        return game;
    }

    public Genre createGenre(@NotNull final String genreName) {
        Genre genre = new Genre(genreName);
        genreMap.put(genre.getGenreId(), genre);
        gameGenreMap.put(genre.getGenreId(), new ArrayList<String>());
        return genre;
    }

    public void setGameInMap(@NotNull final String gameId) {
        if(!gameGenreMap.containsKey(gameId)) {
            gameGenreMap.put(gameId, getGenreIds(gameMap.get(gameId).getGenreList()));
        }

        for(final Genre genre : gameMap.get(gameId).getGenreList()) {
            if(!gameGenreMap.get(genre.getGenreId()).contains(gameId)) {
                gameGenreMap.get(genre.getGenreId()).add(gameId);
            }
        }
    }

    public void setGenreInMap(@NotNull final String genreId) {
        gameGenreMap.put(genreId, new ArrayList<String>());
    }

    // возввращает List всех жанров игры
    public List<Genre> getGameGenreList(@NotNull final String gameId) {
        return gameMap.get(gameId).getGenreList();
    }

    // возввращает List всех игр этого жанра
    public List<Game> getGenreGameList(@NotNull final String genreId) {
        final List<Game> resultList = new ArrayList<Game>();

        for(final String gameId : gameGenreMap.get(genreId)) {
            resultList.add(gameMap.get(gameId));
        }

        return resultList;
    }

    
    public void removeGenreForGame(@NotNull final String gameId, @NotNull final  String genreId) {
        List<String> genreForGameList = gameGenreMap.get(gameId);

        if(genreForGameList.contains(genreId)){
            genreForGameList.remove(genreId);
            gameGenreMap.get(genreId).remove(gameId);
        }
    }

    @NotNull
    private List<String> getGenreIds(@NotNull final List<Genre> genrelist) {

        if(genrelist.isEmpty()) {
            return new ArrayList<String>();
        }

        final List<String> resultList = new ArrayList<String>();

        for(final Genre genre : genrelist) {
            resultList.add(genre.getGenreId());
        }

        return resultList;
    }

}
