package org.academiadecodigo.bootcamp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    private final ServerSocket serverSocket;
    private final ExecutorService service;

    public static void main(String[] args) {

        try {
            Server server = new Server(8080);
            server.start();

        } catch (IOException e) {
            System.err.println("Error opening server socket: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.err.println("Error port must be a valid number: " + args[0]);
        }
    }

    private Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        service = Executors.newCachedThreadPool();
    }

    private void start() {
        while (true) {
            waitConnection();
        }
    }

    private void waitConnection() {
        try {
            Socket clientSocket = serverSocket.accept();

            ClientConnection connection = new ClientConnection(clientSocket);
            service.submit(connection);
            System.out.println("Client connected");

        } catch (IOException e) {
            System.err.println("Error establishing connection: " + e.getMessage());
        }
    }

    public class ClientConnection implements Runnable {

        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        private ClientConnection(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                openStreams();
                send("Welcome to our server.");

                while (!socket.isClosed()) {
                    listen(socket);
                }

            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            }
        }

        private void listen(Socket socket) throws IOException {
            Integer message = Integer.parseInt(in.readLine());

            switch (message) {
                case 1:
                    displayFileList();
                    break;
                case 2:
                    //download();
                    break;
                case 3:
                    //upload();
                    break;
                case 4:
                    send(Message.BYE_MESSAGE);
                    socket.close();
                    break;
            }
        }

        private void openStreams() throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        private void displayFileList(){
            String fileList = "";

            for (String file : FileHandler.listFiles()){
                fileList += (file + "\n");
            }
            send(fileList);
        }

        private void send(String message) {
            out.println(message);
        }
    }
}