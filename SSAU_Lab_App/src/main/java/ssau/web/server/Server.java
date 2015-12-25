package ssau.web.server;


import com.thoughtworks.xstream.XStream;
import org.jetbrains.annotations.NotNull;
import ssau.lab.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    @NotNull
    private final static Users users = new Users();

    @NotNull
    private static Model model = new Model();

    @NotNull
    private final static XStream stream = new XStream();

    private static final ExecutorService pool = Executors.newCachedThreadPool();



    @NotNull
    private final static Set<String> editableEntitySet = new HashSet<>();

    public static void main(String[] args) {
        try {
            ServerSocket socketListener = new ServerSocket(4444);
            System.out.println("Start server...");
            stream.fromXML(new FileInputStream("server-model.xml"), model);

            pool.submit(new ServerThread(socketListener));

            Scanner sc = new Scanner(System.in);
            String stop = null;

            while (!"stop".equals(stop)) {
                stop = sc.next();
            }

            pool.shutdownNow();
            System.out.println("Stop server...");

        } catch (SocketException e) {
            System.err.println("Socket exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O exception");
            e.printStackTrace();
        } finally {
            try {
                stream.toXML(model, new FileOutputStream("server-model.xml"));

            }  catch (IOException e) {
                e.printStackTrace();
            }

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
    public synchronized static Set<String> getEditableEntitySet() {
        return editableEntitySet;
    }

    public static ExecutorService getPool() {
        return pool;
    }
}
