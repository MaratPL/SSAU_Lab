package ssau.client;

import com.thoughtworks.xstream.XStream;
import ssau.lab.Game;
import ssau.lab.Genre;
import ssau.protocol.ObjectType;
import ssau.protocol.Protocol;
import ssau.view.NewJFrame;

import java.io.*;
import java.net.Socket;

public class ServerResponseListener extends Thread {

    private Socket socket;
    private NewJFrame frame;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private XStream xStream = new XStream();

    public ServerResponseListener(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, NewJFrame frame){
        this.frame = frame;
        this.socket = socket;
        inputStream = objectInputStream;
        outputStream = objectOutputStream;

    }

    @Override
    public void run(){
        while (socket.isConnected()){
            try {
                Object object = xStream.fromXML((String) inputStream.readObject());
                if (object != null){
                    Protocol protocol = (Protocol) object;
                    switch (protocol.getOperationType()){
                        case CREATE_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                frame.getClient().getModel().addGame(game);
                                frame.updateGamesTable();
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                frame.getClient().getModel().addGenre(genre);
                                frame.updateGenresTable();
                            }
                            break;
                        case DELETE_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                if (frame.getClient().getModel().getGameById(game.getGameId()) != null){
                                    frame.getClient().getModel().removeGameById(game.getGameId());
                                    frame.updateGamesTable();
                                }
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                if (frame.getClient().getModel().getGenreById(genre.getGenreId()) != null){
                                    frame.getClient().getModel().removeGenreById(genre.getGenreId());
                                    frame.updateGenresTable();
                                }
                            }
                            break;
                        case BEGIN_EDITING_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                final Game gameInMyModel = frame.getClient().getModel().getGameById(game.getGameId());
                                frame.beginUpdateGame(gameInMyModel);
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                final Genre genreInMyModel = frame.getClient().getModel().getGenreById(genre.getGenreId());
                                frame.beginUpdateGenre(genreInMyModel);
                            }
                            break;
                        case END_EDITING_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                Game myGame = frame.getClient().getModel().getGameById(game.getGameId());
                                myGame.setGameCompany(game.getGameCompany());
                                myGame.setGenreList(game.getGenreList());
                                myGame.setGameName(game.getGameName());
                                frame.updateGamesTable();
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                Genre myGenre = frame.getClient().getModel().getGenreById(genre.getGenreId());
                                myGenre.setGenreName(genre.getGenreName());
                                frame.updateGenresTable();
                            }
                            break;
                        case SAVE_MODEL:
                            FileWriter fileWriter = new FileWriter("model.xml");
                            fileWriter.write((String) protocol.getValue());
                            fileWriter.flush();
                            fileWriter.close();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

}
