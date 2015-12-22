package ssau.client;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ssau.controller.ModelController;
import ssau.lab.Game;
import ssau.lab.Genre;
import ssau.web.ObjectType;
import ssau.web.OperationType;
import ssau.web.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Morozov on 12/21/2015.
 */
public class Client {
    private String id = UUID.randomUUID().toString();
    private ModelController modelController = new ModelController();
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    public Client (ObjectOutputStream oos, ObjectInputStream ois){
            this.oos = oos;
            this.ois = ois;
    }

    public ModelController getModel(){
        return modelController;
    }

    public String getId(){
        return id;
    }

    @NotNull
    public void addGame(
            @NotNull final String gameName,
            @NotNull final String gameCompany,
            @NotNull final List<Genre> genrelist
    ) throws IOException {
        Game game = new Game(gameName, gameCompany, genrelist);
        Protocol protocol = new Protocol(id, OperationType.CREATE_ENTITY, ObjectType.GAME, game);
        oos.writeObject(protocol);
        oos.flush();
    }

    @NotNull
    public void addGenre(
            @NotNull final String genreName
    ) throws IOException, ClassNotFoundException {
        Genre genre = new Genre(genreName);
        Protocol protocol = new Protocol(id, OperationType.CREATE_ENTITY, ObjectType.GENRE, genre);
        oos.writeObject(protocol);
        oos.flush();
    }

    @Nullable
    public void getGameById(
            @NotNull final String gameId
    ) throws IOException, ClassNotFoundException {
        Protocol protocol = new Protocol(id, OperationType.GET_ENTITY, ObjectType.GAME, gameId);
        oos.writeObject(protocol);
        oos.flush();
    }

    @Nullable
    public Genre getGenreById(
            @NotNull final String genreId
    ) throws IOException, ClassNotFoundException {
        Protocol protocol = new Protocol(id, OperationType.GET_ENTITY, ObjectType.GENRE, genreId);
        oos.writeObject(protocol);
        oos.flush();
        Genre result = (Genre) ois.readObject();
        return result;
    }

    public void sendRequestForEditGame(@NotNull final String gameId) throws IOException {
        Game game = modelController.getGameById(gameId);
        Protocol protocol = new Protocol(id, OperationType.BEGIN_EDITING_ENTITY, ObjectType.GAME, game);
        oos.writeObject(protocol);
        oos.flush();
    }

    public void sendRequestForEditGenre(@NotNull final String gameId) throws IOException {
        Game game = modelController.getGameById(gameId);
        Protocol protocol = new Protocol(id, OperationType.BEGIN_EDITING_ENTITY, ObjectType.GAME, game);
        oos.writeObject(protocol);
        oos.flush();
    }

    @Nullable
    public void updateGame(
            @NotNull final String gameId,
            @NotNull final String gameName,
            @NotNull final String gameCompany,
            @NotNull final List<Genre> genreList
    ) throws IOException{
        Game game = modelController.getGameById(gameId);
        game.setGameCompany(gameCompany);
        game.setGenreList(genreList);
        game.setGameName(gameName);
        Protocol protocol = new Protocol(id, OperationType.END_EDITING_ENTITY, ObjectType.GAME, game);
        oos.writeObject(protocol);
        oos.flush();
    }

    @Nullable
    public void updateGenre(
            @NotNull final String genreId,
            @NotNull final String genreName
    ) throws IOException{
        Genre genre = modelController.getGenreById(genreId);
        genre.setGenreName(genreName);
        Protocol protocol = new Protocol(id, OperationType.END_EDITING_ENTITY, ObjectType.GENRE, genre);
        oos.writeObject(protocol);
        oos.flush();
    }

    @Nullable
    public Game removeGameById(
            @NotNull final String gameId
    ) throws IOException, ClassNotFoundException {
        Game game = modelController.getGameById(gameId);
        if (game != null){
            Protocol protocol = new Protocol(id, OperationType.DELETE_ENTITY, ObjectType.GAME, game);
            oos.writeObject(protocol);
            oos.flush();
            Protocol response = (Protocol)ois.readObject();
            if (!response.isError()){
                return modelController.removeGameById(gameId);
            }
        }
        return null;
    }

    @Nullable
    public Genre removeGenreById(
            @NotNull final String genreId
    ) throws IOException, ClassNotFoundException {
        Genre genre = modelController.getGenreById(genreId);
        if (genre != null){
            Protocol protocol = new Protocol(id, OperationType.DELETE_ENTITY, ObjectType.GENRE, genre);
            oos.writeObject(protocol);
            oos.flush();
            Protocol response =
                    (Protocol)ois.readObject();
            if (!response.isError()){
                return modelController.removeGenreById(genreId);
            }
        }
        return null;
    }

    @NotNull
    private List<Game> getAllGames() throws IOException, ClassNotFoundException {
        Protocol protocol = new Protocol(id, OperationType.GET_LIST_ENTITY, ObjectType.GAME, null);
        oos.writeObject(protocol);
        oos.flush();
        Protocol response = (Protocol)ois.readObject();
        if (!response.isError()){

        }
        return new ArrayList<Game>(modelController.getAllGames());
    }

    @NotNull
    public List<Genre> getAllGenres() throws IOException, ClassNotFoundException {
        Protocol protocol = new Protocol(id, OperationType.GET_LIST_ENTITY, ObjectType.GENRE, null);
        oos.writeObject(protocol);
        oos.flush();
        Protocol response = (Protocol)ois.readObject();
        if (!response.isError()){

        }
        return new ArrayList<Genre>(modelController.getAllGenres());
    }
}
