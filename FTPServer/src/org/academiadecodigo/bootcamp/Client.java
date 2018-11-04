package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import java.io.*;
import java.net.Socket;


public class Client {

    private Socket serverSocket;
    private PrintWriter outMsg;
    private BufferedReader inMsg;
    private String filepath = "resources/";
    private Prompt prompt;


    private void run(String host, int port) {
        try {
            serverSocket = new Socket(host, port);
        } catch (IOException e) {
            System.out.println(Message.ERROR_CONNECTING);
            System.exit(0);

        }
    }

    private void init() {
        run("localhost", 8080);
        prompt = new Prompt(System.in, System.out);

        try {
            outMsg = new PrintWriter(serverSocket.getOutputStream(), true);
            inMsg = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException {

        String[] options = {MenuOptions.VIEW_LIST.getMessage(),
                MenuOptions.DOWNLOAD.getMessage(),
                MenuOptions.UPLOAD.getMessage(),
                Message.BYE_MESSAGE};

        MenuInputScanner menu = new MenuInputScanner(options);

        int choice;

        System.out.println(inMsg.readLine());

        while (serverSocket.isBound()) {

            choice = prompt.getUserInput(menu);

            System.out.println("\n" + "You chose option: " + options[choice - 1]);

            outMsg.println(choice);

            String line;
            String result = "";

            while ((line = inMsg.readLine()) != null && !line.isEmpty()) {
                result += (line+"\n");
            }

            if (line == null) {
                return;
            }

            if (result.equals(Message.BYE_MESSAGE)){
                serverSocket.close();
                return;
            }

            System.out.println(result);
        }
    }

    private void download() throws IOException {
        FileHandler.sendFile();
    }

    private void upload() throws IOException {

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.init();
    }
}