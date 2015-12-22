package ssau.lab;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

public class Model extends AbstractModel implements Serializable{

    
    // связь сущностей ИГРА и ЖАНР.
                                                            // представляет из себя HashMap<String, List<String>>
                                                            // id Игры и Жанра является уникальным ключём
                                                            // для доступа к списку жанров конкретной игры и
                                                            // списку игр этого жанра соответственно
    
    private Map<String, Game> gameMap;      // все игры в модели
    private Map<String, Genre> genreMap;    // все жанры в модели


    public Model() {
        gameMap = new HashMap<String, Game>();
        genreMap = new HashMap<String, Genre>();
    }

    private synchronized void update() {
        for(@NotNull ssau.view.Observer observer : observerList) {
            observer.update();
        }
    }

    @NotNull
    public synchronized Game createGame(@Nullable final String gameName, @Nullable final String gameCompany, @NotNull final List<Genre> genreList) {
        Game game = new Game(gameName, gameCompany, genreList);
        gameMap.put(game.getGameId(), game);
        update();
        return game;
    }

    @NotNull
    public synchronized Genre createGenre(@Nullable final String genreName) {
        Genre genre = new Genre(genreName);
        genreMap.put(genre.getGenreId(), genre);
        update();
        return genre;
    }


    @Nullable
    public synchronized Game getGameById(@NotNull final String gameId) {
        if(gameMap.containsKey(gameId)) {
            return gameMap.get(gameId);
        }

        return null;
    }

    @Nullable
    public synchronized Genre getGenreById(@NotNull final String genreId) {
        if(genreMap.containsKey(genreId)) {
            return genreMap.get(genreId);
        }

        return null;
    }

    // возввращает List всех жанров игры
    @NotNull
    public synchronized List<Genre> getGameGenreList(@NotNull final String gameId) {
        return gameMap.get(gameId).getGenreList();
    }

    // возввращает List всех игр этого жанра
    @NotNull
    public List<Game> getGenreGameList(@NotNull final String genreId) {
        final List<Game> resultList = new ArrayList<Game>();

        for(final Game game : getAllGames()) {
            if(game.getGenreList().contains(genre)){
                resultList.add(game);
            }
        }

        return resultList;
    }

    @NotNull
    public synchronized Collection<Game> getAllGames() {
        return gameMap.values();
    }

    @NotNull
    public synchronized Collection<Genre> getAllGenre() {
        return genreMap.values();
    }

    @Nullable
    public synchronized Game updateGame(
            @NotNull final String gameId,
            @Nullable final String gameName,
            @Nullable final String gameCompany,
            @NotNull final List<Genre> genreList
    ) {
        final Game game = gameMap.get(gameId);

        if(game != null) {
            game.setGameName(gameName);
            game.setGameCompany(gameCompany);
            game.setGenreList(genreList);
            update();
            return game;
        }

        return null;
    }

    @Nullable
    public synchronized Genre updateGenre(
            @NotNull final String genreId,
            @Nullable final String genreName
    ) {
        final Genre genre = genreMap.get(genreId);

        if(genre != null) {
            genre.setGenreName(genreName);
            update();
            return genre;
        }

        return null;
    }

    @Nullable
    public synchronized Game removeGameById(@NotNull final String gameId) {
        final Game game = gameMap.remove(gameId);

        if(game == null) {
            return null;
        }
        update();
        return game;
    }

    @Nullable
    public synchronized Genre removeGenreById(@NotNull final String genreId) {
        final Genre genre = genreMap.remove(genreId);

        if(genre == null) {
            return null;
        }

        for(final Game game : getAllGames()) {
            if(game.getGenreList().contains(genre)) {
                game.getGenreList().remove(genre);
            }
        }
        update();
        return genre;
    }

    @NotNull
    private synchronized List<String> getGenreIds(@NotNull final List<Genre> genrelist) {

        if(genrelist.isEmpty()||genrelist==null) {
            return new ArrayList<String>();
        }

        final List<String> resultList = new ArrayList<String>();

        for(final Genre genre : genrelist) {
            resultList.add(genre.getGenreId());
        }

        return resultList;
    }

}