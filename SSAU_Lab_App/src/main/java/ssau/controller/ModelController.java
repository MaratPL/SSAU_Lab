package ssau.controller;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ssau.lab.Game;
import ssau.lab.Genre;
import ssau.lab.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModelController {

    @NotNull
    private Model model;

    public ModelController() {
        model = new Model();
    }

    public void writeModelInFile(@NotNull final String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.flush();
        oos.close();
    }

    @NotNull
    public Model readModelFromFile(@NotNull final String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream oin = new ObjectInputStream(fis);
        model = (Model) oin.readObject();
        oin.close();
        return model;
    }

    @NotNull
    public Game addGame(
            @NotNull final String gameName,
            @NotNull final String gameCompany,
            @NotNull final List<Genre> genrelist
    ) {
        return model.createGame(gameName, gameCompany, genrelist);
    }

    @NotNull
    public Genre addGenre(
            @NotNull final String genreName
    ) {
        return model.createGenre(genreName);
    }

    @Nullable
    public Game getGameById(
            @NotNull final String gameId
    ) {
       return model.getGameById(gameId);
    }

    @Nullable
    public Genre getGenreById(
            @NotNull final String genreId
    ) {
        return model.getGenreById(genreId);
    }

    @Nullable
    public Game updateGame(
            @NotNull final String gameId,
            @NotNull final String gameName,
            @NotNull final String gameCompany,
            @NotNull final List<Genre> genreList
    ) {
        return model.updateGame(gameId, gameName, gameCompany, genreList);
    }

    @Nullable
    public Genre updateGenre(
            @NotNull final String genreId,
            @NotNull final String genreName
    ) {
        return model.updateGenre(genreId, genreName);
    }

    @Nullable
    public Game removeGameById(
            @NotNull final String gameId
    ) {
        return model.removeGameById(gameId);
    }

    @Nullable
    public Genre removeGenreById(
            @NotNull final String genreId
    ) {
        return model.removeGenreById(genreId);
    }

    public void removeGenreForGame(@NotNull final String gameId, @NotNull final  String genreId) {
        model.removeGenreForGame(gameId, genreId);
    }

    @NotNull
    public List<Game> getAllGames() {
        return new ArrayList<Game>(model.getAllGames());
    }

    @NotNull
    public List<Genre> getAllGenres() {
        return new ArrayList<Genre>(model.getAllGenre());
    }


}
