package ssau.web.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ssau.lab.AbstractModel;
import ssau.lab.Game;
import ssau.lab.Genre;
import ssau.web.db.DataBase;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

public class ServerModel extends AbstractModel implements Serializable {

    private Map<String, Game> gameMap;      // все игры в модели
    private Map<String, Genre> genreMap;    // все жанры в модели

    private DataBase dataBase = null;


    public ServerModel() throws SQLException, ClassNotFoundException {
        gameMap = new HashMap<>();
        genreMap = new HashMap<>();
        System.out.println("Start server...");

        dataBase = new DataBase();
    }

    public void close() throws SQLException {
        dataBase.close();
    }

    private synchronized void update() {
        for(@NotNull ssau.view.Observer observer : observerList) {
            observer.update();
        }
    }

    @Nullable
    public synchronized Game createGame(@NotNull final Game game) {
        final Game result = dataBase.createGame(game);
        if(result == null) {
            return null;
        }
        addGame(result);
        return result;
    }

    @Nullable
    public synchronized Genre createGenre(@NotNull final Genre genre) {
        final Genre result = dataBase.createGenre(genre);
        if(result == null) {
            return null;
        }
        addGanre(result);
        return result;
    }


    @Nullable
    private synchronized Game getGameById(@NotNull final String gameId) {
        if(gameMap.containsKey(gameId)) {
            return gameMap.get(gameId);
        }

        return null;
    }

    @Nullable
    public synchronized Game getGame(@NotNull final String gameId) {
        Game result = getGameById(gameId);

        if(result == null) {
            result = dataBase.getGameById(gameId);
        }

        if(result == null) {
            return null;
        }

        return result;
    }

    @Nullable
    private synchronized Genre getGenreById(@NotNull final String genreId) {
        if(genreMap.containsKey(genreId)) {
            return genreMap.get(genreId);
        }

        return null;
    }

    @Nullable
    public synchronized Genre getGenre(@NotNull final String genreId) {
        Genre result = getGenreById(genreId);

        if(result == null) {
            result = dataBase.getGenreById(genreId);
        }

        if(result == null) {
            return null;
        }

        return result;
    }

    @NotNull
    public synchronized List<Game> getAllGames() {
        return dataBase.getAllGames();
    }

    @NotNull
    public synchronized List<Genre> getAllGenres() {
        return dataBase.getAllGenres();
    }

    @Nullable
    private synchronized Game updateGame(
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
    public synchronized Game updateGame(
            @NotNull final Game game
    ) {
        final Game result = dataBase.updateGame(game);

        if(result == null) {
            return null;
        }

        updateGame(result.getGameId(), result.getGameName(), result.getGameCompany(), result.getGenreList());

        return result;
    }

    @Nullable
    private synchronized Genre updateGenre(
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
    public synchronized Genre updateGenre(
            @NotNull final Genre genre
    ) {
        final Genre result = dataBase.updateGenre(genre);

        if(result == null) {
            return null;
        }

        updateGenre(result.getGenreId(), result.getGenreName());

        return result;
    }

    @Nullable
    private synchronized Game removeGameById(@NotNull final String gameId) {
        final Game game = gameMap.remove(gameId);

        if(game == null) {
            return null;
        }
        update();
        return game;
    }

    @Nullable
    public synchronized Game removeGame(@NotNull final Game game) {
        final Game result = dataBase.deleteGame(game);

        if(result == null) {
            return null;
        }

        removeGameById(result.getGameId());
        return result;
    }

    @Nullable
    private synchronized Genre removeGenreById(@NotNull final String genreId) {
        final Genre genre = genreMap.remove(genreId);

        if(genre == null) {
            return null;
        }

        for(final Game game : gameMap.values()) {
            if(game.getGenreList().contains(genre)) {
                game.getGenreList().remove(genre);
            }
        }
        update();
        return genre;
    }

    @Nullable
    public synchronized Genre removeGenre(@NotNull final Genre genre) {
        final Genre result = dataBase.deleteGenre(genre);

        if(result == null) {
            return null;
        }

        removeGenreById(result.getGenreId());
        return result;
    }

    public void addGame(@NotNull final Game game) {
        gameMap.put(game.getGameId(), game);
    }

    public void addGanre(@NotNull final Genre genre) {
        genreMap.put(genre.getGenreId(), genre);
    }

    public void setDataBase(@NotNull  final DataBase dataBase) {
        this.dataBase = dataBase;
    }
}
