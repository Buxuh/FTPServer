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
        threadPool = Executors.newCachedThreadPool();

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
            System.out.println("Client connected to server ");

        } catch (IOException ex) {
            ex.printStackTrace();

        }
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

    private void dispatch(Socket clientSocket) {
        threadPool.submit(new Runnable() {
            @Override
            public void run() {

                handleClient(clientSocket);
            }
        });
    }

    private void handleClient(Socket client) {

        try {

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String inputData = reader.readLine();
            Integer input = Integer.parseInt(inputData);

            while (client.isBound()) {
                /*switch (input) {
                    case 1:
                        String[] files = FileHandler.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            writer.println(files[i]);
                        }
                        break;
                    case 2:
                        System.out.println("option download");

                        break;
                    case 3:
                        System.out.println("option upload");
                        break;
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
