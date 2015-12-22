package ssau.web.server;


import org.jetbrains.annotations.NotNull;
import ssau.lab.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    @NotNull
    private final static Users users = new Users();

    @NotNull
    private final static Model model = new Model();

    @NotNull
    private final static List<String> editableEntityList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            //������� ���������
            ServerSocket socketListener = new ServerSocket(4444);
            System.out.println("Start server...");

            while (true) {
                Socket client = null;
                while (client == null) {
                    client = socketListener.accept();
                }
                (new ClientThread(client)).start(); //������� ����� �����, �������� �������� �����
            }
        } catch (SocketException e) {
            System.err.println("Socket exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O exception");
            e.printStackTrace();
        }
    }

    @NotNull
    public static Model getModel() {
        return model;
    }

    @NotNull
    public static Users getUsers() {
        return users;
    }

    @NotNull
    public synchronized static List<String> getEditableEntityList() {
        return editableEntityList;
    }
}
