package ssau.web.server;

import org.jetbrains.annotations.NotNull;
import ssau.web.db.DataBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

   private ServerSocket serverSocket;
    private DataBase dataBase;

    public ServerThread(@NotNull final ServerSocket serverSocket, @NotNull final DataBase dataBase) {
        this.serverSocket = serverSocket;
        this.dataBase = dataBase;
    }
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket client = serverSocket.accept();
                Server.getPool().submit(new ClientThread(client, dataBase));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
