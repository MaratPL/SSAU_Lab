package ssau.web.server;


import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClient {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ServerClient(@NotNull Socket socket){
        this.socket = socket;
    }

    public ServerClient(@NotNull Socket socket, @NotNull ObjectOutputStream oos, @NotNull ObjectInputStream ois){
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public ObjectOutputStream getThisObjectOutputStream() {
        return this.oos;
    }

    public ObjectInputStream getThisObjectInputStream() {
        return this.ois;
    }

    public void setThisObjectOutputStream(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public void setThisObjectInputStream(ObjectInputStream ois) {
        this.ois = ois;
    }
}
