package ssau.web.server;

import com.thoughtworks.xstream.XStream;
import org.jetbrains.annotations.NotNull;
import ssau.lab.Game;
import ssau.lab.Genre;
import ssau.protocol.ObjectType;
import ssau.protocol.OperationType;
import ssau.protocol.Protocol;
import ssau.web.db.DataBase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {
    private Socket socket;
    private Protocol protocol;
    private String id;

    public ClientThread(@NotNull final Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            final ObjectInputStream inputStream = new ObjectInputStream(this.socket.getInputStream());
            final ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            final XStream xStream = new XStream();

            protocol = (Protocol) xStream.fromXML((String) inputStream.readObject());
            id = protocol.getId();

            if (protocol.getOperationType() == OperationType.SUBSCRIBE) {
                Server.getUsers().addUser(id, socket, outputStream, inputStream);
            } else {
                outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                outputStream.flush();
                inputStream.close();
                outputStream.close();
                return;
            }


            while (!Thread.currentThread().isInterrupted()) {

                protocol = (Protocol) xStream.fromXML((String) inputStream.readObject());

                if (protocol.getOperationType() != null) {
                    switch (protocol.getOperationType()) {
                        case CREATE_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                final Game result = Server.getServerModel().createGame(game);
                                if(result == null){
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                                broadcast(Server.getUsers().getClientList(), new Protocol(id, OperationType.CREATE_ENTITY, ObjectType.GAME, result, false));
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                final Genre result = Server.getServerModel().createGenre(genre);
                                if(result == null){
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                                broadcast(Server.getUsers().getClientList(), new Protocol(id, OperationType.CREATE_ENTITY, ObjectType.GENRE, result, false));
                            }
                            break;
                        case DELETE_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                if (Server.getEditableEntitySet().contains(game.getGameId())) {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                                final Game result = Server.getServerModel().removeGame(game);

                                if (result != null) {
                                    broadcast(Server.getUsers().getClientList(), new Protocol(id, OperationType.DELETE_ENTITY, ObjectType.GAME, result, false));
                                } else {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                if (Server.getEditableEntitySet().contains(genre.getGenreId())) {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                                final Genre result =  Server.getServerModel().removeGenre(genre);

                                if (result != null) {
                                    broadcast(Server.getUsers().getClientList(), new Protocol(id, OperationType.DELETE_ENTITY, ObjectType.GENRE, result, false));
                                } else {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                            }
                            break;
                        case BEGIN_EDITING_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                if (Server.getEditableEntitySet().contains(game.getGameId())) {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                                Game result = Server.getServerModel().getGame(game.getGameId());

                                if (result != null) {
                                    Server.getEditableEntitySet().add(result.getGameId());
                                    broadcast(Server.getUsers().getClientList(), new Protocol(id, OperationType.BEGIN_EDITING_ENTITY, ObjectType.GAME, result, false));
                                } else {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;

                                }
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                if (Server.getEditableEntitySet().contains(genre.getGenreId())) {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                                Genre result = Server.getServerModel().getGenre(genre.getGenreId());
                                if (result != null) {
                                    Server.getEditableEntitySet().add(result.getGenreId());
                                    broadcast(Server.getUsers().getClientList(), new Protocol(id, OperationType.BEGIN_EDITING_ENTITY, ObjectType.GENRE, result, false));
                                } else {
                                        outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                        outputStream.flush();
                                        break;

                                }
                            }
                            break;
                        case END_EDITING_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                final Game game = (Game) protocol.getValue();
                                if (!Server.getEditableEntitySet().contains(game.getGameId())) {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                                final Game result =  Server.getServerModel().updateGame(game);

                                if (result != null) {
                                    Server.getEditableEntitySet().remove(result.getGameId());
                                    broadcast(Server.getUsers().getClientList(), new Protocol(id, OperationType.END_EDITING_ENTITY, ObjectType.GAME, result, false));
                                } else {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                            } else {
                                final Genre genre = (Genre) protocol.getValue();
                                if (!Server.getEditableEntitySet().contains(genre.getGenreId())) {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                                final Genre result = Server.getServerModel().updateGenre(genre);

                                if (result != null) {
                                    Server.getEditableEntitySet().remove(result.getGenreId());
                                    broadcast(Server.getUsers().getClientList(), new Protocol(id, OperationType.END_EDITING_ENTITY, ObjectType.GENRE, result, false));
                                } else {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                            }
                            break;
                        case GET_ENTITY:
                            final String entityId = (String) protocol.getValue();
                            if (protocol.getObjectType() == ObjectType.GAME) {
                                Game result = Server.getServerModel().getGame(entityId);

                                if (result != null) {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.GET_ENTITY, ObjectType.GAME, result, false)));
                                    outputStream.flush();
                                } else {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                            } else {
                                Genre result = Server.getServerModel().getGenre(entityId);

                                if (result != null) {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.GET_ENTITY, ObjectType.GENRE, result, false)));
                                    outputStream.flush();
                                   } else {
                                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                                    outputStream.flush();
                                    break;
                                }
                            }
                            break;
                        case GET_LIST_ENTITY:
                            if (protocol.getObjectType() == ObjectType.GAME_LIST) {
                                final List<Game> result = new ArrayList<>(Server.getServerModel().getAllGames());
                                outputStream.writeObject(xStream.toXML(new Protocol(OperationType.GET_LIST_ENTITY, ObjectType.GAME_LIST, result, false)));
                                outputStream.flush();
                            } else {
                                final List<Genre> result = new ArrayList<>(Server.getServerModel().getAllGenres());
                                outputStream.writeObject(xStream.toXML(new Protocol(OperationType.GET_LIST_ENTITY, ObjectType.GENRE_LIST, result, false)));
                                outputStream.flush();
                            }
                            break;
                        case SAVE_MODEL:
                            outputStream.writeObject(xStream.toXML(new Protocol(OperationType.SAVE_MODEL, null, xStream.toXML(Server.getServerModel()), false)));
                            outputStream.flush();
                            break;
                    }
                } else {
                    outputStream.writeObject(xStream.toXML(new Protocol(OperationType.ERROR, true)));
                    outputStream.flush();
                    break;
                }

                if (protocol.getOperationType() == OperationType.UNSUBSCRIBE) {
                    Server.getUsers().deleteUser(id);
                    break;
                }
            }

            inputStream.close();
            outputStream.close();
        } catch (SocketException e) {
            System.out.println(id + " disconnected!");
            Server.getUsers().deleteUser(id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void broadcast(@NotNull final List<ServerClient> serverClientList, @NotNull final Protocol protocol) {
        try {
            for (final ServerClient client : serverClientList) {
                client.getThisObjectOutputStream().writeObject(new XStream().toXML(protocol));
            }
        } catch (SocketException e) {
            System.out.println("In broadcast: " + id + " disconnected!");
            Server.getUsers().deleteUser(id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}