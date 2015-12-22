package ssau.client;

import ssau.lab.Game;
import ssau.lab.Genre;
import ssau.view.NewJFrame;
import ssau.web.ObjectType;
import ssau.web.OperationType;
import ssau.web.Protocol;
import ssau.web.server.Server;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
* Created by kolesnikov on 21.12.15.
*/
public class ServerResponseListner extends Thread {

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private NewJFrame frame;

    public ServerResponseListner(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, NewJFrame frame){
        this.frame = frame;
        this.socket = socket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void run(){
        while (socket.isConnected()){
            try {
                Object object = objectInputStream.readObject();
                if (object != null){
                    Protocol protocol = (Protocol) object;
                    switch (protocol.getOperationType()){
                        case CREATE_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                frame.getClient().getModel().addGame(game.getGameName(), game.getGameCompany(), game.getGenreList());
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                frame.getClient().getModel().addGenre(genre.getGenreName());
                                frame.updateGenresTable();
                            }
                            break;
                        case DELETE_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                if (frame.getClient().getModel().getGameById(game.getGameId()) != null){
                                    frame.getClient().getModel().removeGameById(game.getGameId());
                                }
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                if (frame.getClient().getModel().getGenreById(genre.getGenreId()) != null){
                                    frame.getClient().getModel().removeGenreById(genre.getGenreId());
                                }
                            }
                            break;
                        case BEGIN_EDITING_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                final Game gameInMyModel = frame.getClient().getModel().getGameById(game.getGameId());

                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                final Genre genreInMyModel = frame.getClient().getModel().getGenreById(genre.getGenreId());
                            }
                            break;
                        case END_EDITING_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                //получаем табличку и там заменяем редактируемую строку на то что в рехультате получилось
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                            }
                            break;
                        case ERROR:
                            ;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
