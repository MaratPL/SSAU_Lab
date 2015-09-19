package ssau.lab;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

public class Model implements Serializable{

    
    private Map<String,  List<String>> gameGenreMap;        // связь сущностей ИГРА и ЖАНР.
                                                            // представляет из себя HashMap<String, List<String>>
                                                            // id Игры и Жанра является уникальным ключём
                                                            // для доступа к списку жанров конкретной игры и
                                                            // списку игр этого жанра соответственно
    
    private Map<String, Game> gameMap;      // все игры в модели
    private Map<String, Genre> genreMap;    // все жанры в модели


    public Model() {
        gameGenreMap = new HashMap<String, List<String>>();
        gameMap = new HashMap<String, Game>();
        genreMap = new HashMap<String, Genre>();
    }

    @NotNull
    public Game createGame(@NotNull final String gameName, @NotNull final String gameCompany, @NotNull final List<Genre> genrelist) {
        Game game = new Game(gameName, gameCompany, genrelist);
        gameMap.put(game.getGameId(), game);
        gameGenreMap.put(game.getGameId(), getGenreIds(game.getGenreList()));
        return game;
    }

    @NotNull
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

    @Nullable
    public Game getGameById(@NotNull final String gameId) {
        if(gameMap.containsKey(gameId)) {
            return gameMap.get(gameId);
        }

        return null;
    }

    @Nullable
    public Genre getGenreById(@NotNull final String genreId) {
        if(genreMap.containsKey(genreId)) {
            return genreMap.get(genreId);
        }

        return null;
    }

    public void setGenreInMap(@NotNull final String genreId) {
        gameGenreMap.put(genreId, new ArrayList<String>());
    }

    // возввращает List всех жанров игры
    @NotNull
    public List<Genre> getGameGenreList(@NotNull final String gameId) {
        return gameMap.get(gameId).getGenreList();
    }

    // возввращает List всех игр этого жанра
    @NotNull
    public List<Game> getGenreGameList(@NotNull final String genreId) {
        final List<Game> resultList = new ArrayList<Game>();

        for(final String gameId : gameGenreMap.get(genreId)) {
            resultList.add(gameMap.get(gameId));
        }

        return resultList;
    }

    @NotNull
    public Collection<Game> getAllGames() {
        return gameMap.values();
    }

    @NotNull
    public Collection<Genre> getAllGenre() {
        return genreMap.values();
    }

    @Nullable
    public Game updateGame(
            @NotNull final String gameId,
            @NotNull final String gameName,
            @NotNull final String gameCompany,
            @NotNull final List<Genre> genreList
    ) {
        final Game game = gameMap.get(gameId);

        if(game != null) {
            game.setGameName(gameName);
            game.setGameCompany(gameCompany);
            game.setGenreList(genreList);
            return game;
        }

        return null;
    }

    @Nullable
    public Genre updateGenre(
            @NotNull final String genreId,
            @NotNull final String genreName
    ) {
        final Genre genre = genreMap.get(genreId);

        if(genre != null) {
            genre.setGenreName(genreName);
            return genre;
        }

        return null;
    }
    
    public void removeGenreForGame(@NotNull final String gameId, @NotNull final  String genreId) {
        List<String> genreForGameList = gameGenreMap.get(gameId);

        if(genreForGameList.contains(genreId)){
            genreForGameList.remove(genreId);
            gameGenreMap.get(genreId).remove(gameId);
        }
    }

    @Nullable
    public Game removeGameById(@NotNull final String gameId) {
        final Game game = gameMap.remove(gameId);

        if(game == null) {
            return null;
        }

        for(final String genreId : gameGenreMap.get(gameId)) {
            gameGenreMap.get(genreId).remove(gameId);
        }

        gameGenreMap.remove(gameId);

        return game;
    }

    @Nullable
    public Genre removeGenreById(@NotNull final String genreId) {
        final Genre genre = genreMap.remove(genreId);

        if(genre == null) {
            return null;
        }

        for(final String gameId : gameGenreMap.get(genreId)) {
            gameGenreMap.get(gameId).remove(genreId);
        }

        gameGenreMap.remove(genreId);

        return genre;
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
