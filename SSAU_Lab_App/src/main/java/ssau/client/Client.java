package ssau.client;

import com.thoughtworks.xstream.XStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ssau.controller.ModelController;
import ssau.lab.Game;
import ssau.lab.Genre;
import ssau.protocol.ObjectType;
import ssau.protocol.OperationType;
import ssau.protocol.Protocol;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Morozov on 12/21/2015.
 */
public class Client {
    private String id = UUID.randomUUID().toString();
    private ModelController modelController = new ModelController();
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private XStream xStream = new XStream();


    public Client (ObjectOutputStream oos, ObjectInputStream ois){
        outputStream = oos;
        inputStream = ois;
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
    ) throws IOException, JAXBException {
        Game game = new Game(gameName, gameCompany, genrelist);
        Protocol protocol = new Protocol(id, OperationType.CREATE_ENTITY, ObjectType.GAME, game);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
    }

    @NotNull
    public void addGenre(
            @NotNull final String genreName
    ) throws IOException, ClassNotFoundException, JAXBException {
        Genre genre = new Genre(genreName);
        Protocol protocol = new Protocol(id, OperationType.CREATE_ENTITY, ObjectType.GENRE, genre);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
    }

    @Nullable
    public void getGameById(
            @NotNull final String gameId
    ) throws IOException, ClassNotFoundException, JAXBException {
        Protocol protocol = new Protocol(id, OperationType.GET_ENTITY, ObjectType.GAME, gameId);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
    }

    @Nullable
    public Genre getGenreById(
            @NotNull final String genreId
    ) throws IOException, ClassNotFoundException, JAXBException {
        Protocol protocol = new Protocol(id, OperationType.GET_ENTITY, ObjectType.GENRE, genreId);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
        Protocol protocolRes = (Protocol) xStream.fromXML((String) inputStream.readObject());
        if(!protocolRes.isError()){
            return (Genre) protocolRes.getValue();
        }
        return null;
    }

    public void sendRequestForEditGame(@NotNull final String gameId) throws IOException, JAXBException {
        Game game = modelController.getGameById(gameId);
        Protocol protocol = new Protocol(id, OperationType.BEGIN_EDITING_ENTITY, ObjectType.GAME, game);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
    }

    public void sendRequestForEditGenre(@NotNull final String gameId) throws IOException, JAXBException {
        Genre genre = modelController.getGenreById(gameId);
        Protocol protocol = new Protocol(id, OperationType.BEGIN_EDITING_ENTITY, ObjectType.GENRE, genre);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
    }

    @Nullable
    public void updateGame(
            @NotNull final String gameId,
            @NotNull final String gameName,
            @NotNull final String gameCompany,
            @NotNull final List<Genre> genreList
    ) throws IOException, JAXBException {
        Game game = modelController.getGameById(gameId);
        game.setGameCompany(gameCompany);
        game.setGenreList(genreList);
        game.setGameName(gameName);
        Protocol protocol = new Protocol(id, OperationType.END_EDITING_ENTITY, ObjectType.GAME, game);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
    }

    @Nullable
    public void updateGenre(
            @NotNull final String genreId,
            @NotNull final String genreName
    ) throws IOException, JAXBException {
        Genre genre = modelController.getGenreById(genreId);
        genre.setGenreName(genreName);
        Protocol protocol = new Protocol(id, OperationType.END_EDITING_ENTITY, ObjectType.GENRE, genre);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
    }

    @Nullable
    public Game removeGameById(
            @NotNull final String gameId
    ) throws IOException, ClassNotFoundException, JAXBException {
        Game game = modelController.getGameById(gameId);
        if (game != null){
            Protocol protocol = new Protocol(id, OperationType.DELETE_ENTITY, ObjectType.GAME, game);
            outputStream.writeObject(xStream.toXML(protocol));
            outputStream.flush();
            Protocol response = (Protocol)xStream.fromXML((String) inputStream.readObject());
            if (!response.isError()){
                return modelController.removeGameById(gameId);
            }
        }
        return null;
    }

    @Nullable
    public Genre removeGenreById(
            @NotNull final String genreId
    ) throws IOException, ClassNotFoundException, JAXBException {
        Genre genre = modelController.getGenreById(genreId);
        if (genre != null){
            Protocol protocol = new Protocol(id, OperationType.DELETE_ENTITY, ObjectType.GENRE, genre);
            outputStream.writeObject(xStream.toXML(protocol));
            outputStream.flush();
            Protocol response =
                    (Protocol) xStream.fromXML((String) inputStream.readObject());
            if (!response.isError()){
                return modelController.removeGenreById(genreId);
            }
        }
        return null;
    }

    @NotNull
    private List<Game> getAllGames() throws IOException, ClassNotFoundException, JAXBException {
        Protocol protocol = new Protocol(id, OperationType.GET_LIST_ENTITY, ObjectType.GAME, null);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
        Protocol response = (Protocol) xStream.fromXML((String) inputStream.readObject());
        if (!response.isError()){

        }
        return new ArrayList<Game>(modelController.getAllGames());
    }

    @NotNull
    public List<Genre> getAllGenres() throws IOException, ClassNotFoundException, JAXBException {
        Protocol protocol = new Protocol(id, OperationType.GET_LIST_ENTITY, ObjectType.GENRE, null);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
        Protocol response = (Protocol) xStream.fromXML((String) inputStream.readObject());
        if (!response.isError()){

        }
        return new ArrayList<Genre>(modelController.getAllGenres());
    }

    public void saveModel() throws IOException {
        Protocol protocol = new Protocol(id, OperationType.SAVE_MODEL, null, null);
        outputStream.writeObject(xStream.toXML(protocol));
        outputStream.flush();
    }
}
