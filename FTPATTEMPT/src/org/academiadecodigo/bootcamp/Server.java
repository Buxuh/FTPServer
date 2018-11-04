package org.academiadecodigo.bootcamp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ExecutorService executorService;
    private ServerSocket serverSocket;


    public Server(int port) {
        try {
            executorService = Executors.newCachedThreadPool();
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for clients");

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void start() {
        while (true) {
            listen();

        }
    }

    private void listen() {
        try {
            Socket client = serverSocket.accept();
            Clientconnection clientHandler = new Clientconnection(client);
            executorService.submit(clientHandler);
            System.out.println("New client connected");

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        Server server = new Server(8888);
        while (true) {
            server.start();

        }
    }
}
