package org.academiadecodigo.bootcamp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ExecutorService threadPool;
    private ServerSocket serverSocket;


    private void init() {
        int port = 8080;

        try {
            serverSocket = new ServerSocket(port);

        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    private Socket listenConnection() {
        Socket client = null;

        try {
            client = serverSocket.accept();
            System.out.println("Client connected to server");

        } catch (IOException ex) {
            ex.printStackTrace();

        }
        threadPool = Executors.newCachedThreadPool();
        return client;

    }


    public static void main(String[] args) {
        Server server = new Server();
        server.init();

        while (true) {
            Socket client = server.listenConnection();
            server.dispatch(client);

        }
    }

    private void dispatch(Socket client) {
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                handleClient(client);
            }
        });
    }

    private void handleClient(Socket client) {
        try {
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
