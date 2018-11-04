package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Prompt prompt;
    private BufferedReader inMsg;
    private PrintWriter outMsg;
    private Socket socketGivenByServer;
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
            socketGivenByServer = new Socket(host, port);
            inMsg = new BufferedReader(new InputStreamReader(socketGivenByServer.getInputStream()));
            outMsg = new PrintWriter(socketGivenByServer.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        String[] optionsInMenu = {"View List", "Download File", "Upload File", "Exit"};
        MenuInputScanner menuInputScanner = new MenuInputScanner(optionsInMenu);

        while (socketGivenByServer.isBound()) {
            int choice = prompt.getUserInput(menuInputScanner);
            System.out.println("\n" + "You chose option: " + optionsInMenu[choice - 1] + "\n");
            outMsg.println(choice);

            switch (choice) {
                case 1:
                    listFiles();
                    break;

                case 2:
                    FileHandler.receiveFile(socketGivenByServer);
                    break;

                case 3:
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
        socketGivenByServer.close();

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
            socketGivenByServer.close();

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
