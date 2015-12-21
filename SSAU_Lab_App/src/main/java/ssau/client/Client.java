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
    private Socket socket;
    private ModelController modelController = new ModelController();
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    public Client (Socket socket){
        this.socket = socket;
        try {
            oos = (ObjectOutputStream)socket.getOutputStream();
            ois = (ObjectInputStream)socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
//������� ���������� ���������
        }
    }

    public ModelController getModel(){
        return modelController;
    }

    @NotNull
    private void addGame(
            @NotNull final String gameName,
            @NotNull final String gameCompany,
            @NotNull final List<Genre> genrelist
    ) throws IOException {
        Protocol protocol = new Protocol(id, OperationType.CREATE_ENTITY, ObjectType.GAME, modelController.addGame(gameName, gameCompany, genrelist));
        oos.writeObject(protocol);
        oos.flush();
    }

    @NotNull
    private void addGenre(
            @NotNull final String genreName
    ) throws IOException {
        Protocol protocol = new Protocol(id, OperationType.CREATE_ENTITY, ObjectType.GENRE, modelController.addGenre(genreName));
        oos.writeObject(protocol);
        oos.flush();
    }

    @Nullable
    private Game getGameById(
            @NotNull final String gameId
    ) throws IOException, ClassNotFoundException {
        Protocol protocol = new Protocol(id, OperationType.GET_ENTITY, ObjectType.GAME, gameId);
        oos.writeObject(protocol);
        oos.flush();
        Game result = (Game) ois.readObject();
        return result;
    }

    @Nullable
    private Genre getGenreById(
            @NotNull final String genreId
    ) throws IOException, ClassNotFoundException {
        Protocol protocol = new Protocol(id, OperationType.GET_ENTITY, ObjectType.GENRE, genreId);
        oos.writeObject(protocol);
        oos.flush();
        Genre result = (Genre) ois.readObject();
        return result;
    }

    @Nullable
    private Game updateGame(
            @NotNull final String gameId,
            @NotNull final String gameName,
            @NotNull final String gameCompany,
            @NotNull final List<Genre> genreList
    ) throws IOException{
        Game game = modelController.updateGame(gameId, gameName, gameCompany, genreList);
        Protocol protocol = new Protocol(id, OperationType.BEGIN_EDITING_ENTITY, ObjectType.GAME, game);
        oos.writeObject(protocol);
        oos.flush();
        return game;
    }

    @Nullable
    private Genre updateGenre(
            @NotNull final String genreId,
            @NotNull final String genreName
    ) throws IOException{
        Genre genre = modelController.updateGenre(genreId, genreName);
        Protocol protocol = new Protocol(id, OperationType.BEGIN_EDITING_ENTITY, ObjectType.GENRE, genre);
        oos.writeObject(protocol);
        oos.flush();
        return genre;
    }

    @Nullable
    private Game removeGameById(
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
    private Genre removeGenreById(
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
    private List<Genre> getAllGenres() throws IOException, ClassNotFoundException {
        Protocol protocol = new Protocol(id, OperationType.GET_LIST_ENTITY, ObjectType.GENRE, null);
        oos.writeObject(protocol);
        oos.flush();
        Protocol response = (Protocol)ois.readObject();
        if (!response.isError()){

        }
        return new ArrayList<Genre>(modelController.getAllGenres());
    }
}
