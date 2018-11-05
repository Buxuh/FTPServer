package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.*;
import java.net.Socket;

public class Client {

    private Prompt prompt;
    private BufferedReader inMsg;
    private PrintWriter outMsg;
    private Socket serverSocket;
    private String host;
    private int port;

    public Client(String host, int port) {
        prompt = new Prompt(System.in, System.out);
        this.host = host;
        this.port = port;

    }

    public void init() {
        try {
            System.out.println("Welcome to our server");
            serverSocket = new Socket(host, port);
            inMsg = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            outMsg = new PrintWriter(serverSocket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        String[] optionsInMenu = {"View List", "Download File", "Upload File", "Exit"};
        MenuInputScanner menuInputScanner = new MenuInputScanner(optionsInMenu);

        while (serverSocket.isBound()) {
            int choice = prompt.getUserInput(menuInputScanner);
            System.out.println("\n" + "You chose option: " + optionsInMenu[choice - 1] + "\n");
            outMsg.println(choice);

            switch (choice) {
                case 1:
                    listFiles();
                    break;
                case 2:
                    FileHandler.receiveFile(serverSocket);
                    break;
                case 3:
                    FileHandler.sendFile(serverSocket, "resources/strings.txt");
                    break;
                case 4:
                    close();
                    System.exit(1);
                    break;
            }
        }
    }

    private void close() throws IOException {
        System.out.println("Bye!");
        serverSocket.close();
    }

    private void listFiles() throws IOException {
        String line;
        String result = "";

        while ((line = inMsg.readLine()) != null && !line.isEmpty()) {
            result += (line + "\n");
        }

        if (line == null) {
        }

        if (result.equals("EXIT")) {
            serverSocket.close();
        }

        System.out.println(result);
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 8888);
        client.init();

        try {
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
