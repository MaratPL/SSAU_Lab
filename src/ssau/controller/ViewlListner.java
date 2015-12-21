//package ssau.controller;
//
//import com.sun.istack.internal.NotNull;
//import com.sun.istack.internal.Nullable;
//import ssau.lab.Game;
//import ssau.lab.Genre;
//import ssau.lab.Model;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public interface ViewlListner {
//    public void writeModelInFile(@NotNull final String fileName)  throws IOException;
//
//
//    @NotNull
//    public Model readModelFromFile(@NotNull final String fileName)  throws IOException, ClassNotFoundException;
//
//    @NotNull
//    public Game addGame(
//            @NotNull final String gameName,
//            @NotNull final String gameCompany,
//            @Nullable final List<Genre> genrelist
//    );
//
//    @NotNull
//    public Genre addGenre(
//            @NotNull final String genreName
//    );
//
//    @Nullable
//    public Game getGameById(
//            @NotNull final String gameId
//    );
//
//    @Nullable
//    public Genre getGenreById(
//            @NotNull final String genreId
//    );
//
//    @Nullable
//    public Game updateGame(
//            @NotNull final String gameId,
//            @NotNull final String gameName,
//            @NotNull final String gameCompany,
//            @NotNull final List<Genre> genreList
//    );
//
//    @Nullable
//    public Genre updateGenre(
//            @NotNull final String genreId,
//            @NotNull final String genreName
//    );
//
//    @Nullable
//    public Game removeGameById(
//            @NotNull final String gameId
//    );
//
//    @Nullable
//    public Genre removeGenreById(
//            @NotNull final String genreId
//    );
//
//    public void removeGenreForGame(@NotNull final String gameId, @NotNull final  String genreId);
//
//    @NotNull
//    public List<Game> getAllGames();
//
//    @NotNull
//    public List<Genre> getAllGenres();
//
//}
