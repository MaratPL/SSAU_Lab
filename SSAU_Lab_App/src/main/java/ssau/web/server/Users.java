package ssau.web.server;

import ssau.web.server.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class Users {

    private Map<String, Client> onlineUsers = new HashMap<>();

    public synchronized void addUser(String login, Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
        System.out.println( login +" connected." );

        if (!this.onlineUsers.containsKey(login)) {
            this.onlineUsers.put(login , new Client(socket, oos, ois));
        } else {
            int i = 1;
            while(this.onlineUsers.containsKey(login)) {
                login = login + i;
                i++;
            }
            this.onlineUsers.put(login , new Client(socket, oos, ois));
        }
    }

    public synchronized void deleteUser(String login) {
        this.onlineUsers.remove(login);
    }

    public synchronized String[] getUsers() {
        Set<String> strings = this.onlineUsers.keySet();
        return strings.toArray(new String[strings.size()]);
    }

    public synchronized List<Client> getClientList() {
        List<Client> clientsList = new ArrayList<>();

        for(Map.Entry<String, Client> map : onlineUsers.entrySet()){
            clientsList.add(map.getValue());
        }

        return clientsList;
    }

}
