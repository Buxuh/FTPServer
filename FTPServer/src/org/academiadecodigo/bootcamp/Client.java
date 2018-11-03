package org.academiadecodigo.bootcamp;


import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerRangeInputScanner;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;;
import java.io.InputStreamReader;
import java.net.Socket;


public class Client {

    private Socket clientSocket;
    private DataOutputStream outMsg;
    private BufferedReader inMsg;

    private void run(String host, int port) {
        try {
            clientSocket = new Socket(host, port);
            start();
        } catch (IOException e) {
            System.out.println(Message.ERROR_CONNECTING);
            System.exit(0);

        }
    }

    public void greetings() {
        System.out.println(Message.WELCOME_MESSAGE + "\n");
        run("localhost", 8080);

    }

    public void start() throws IOException {
        outMsg = new DataOutputStream(clientSocket.getOutputStream());
        inMsg = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Prompt prompt = new Prompt(System.in, System.out);
        IntegerInputScanner scanner = new IntegerRangeInputScanner(1, 4);
        scanner.setMessage("1 - Download File\n2 - Upload File\n3 - List Files\n4 - Exit");
        scanner.setError(Message.INVALID_OPTION + "\n");

        int choice = prompt.getUserInput(scanner);
        System.out.println("\n" + "You chose option " + choice + "\n");

        if (choice == 1) {
            download();
            start();

        }
        if (choice == 2) {
            upload();
            start();

        }
        if (choice == 3) {
            list();
            start();

        }
        if (choice == 4) {
            System.out.println(Message.BYE_MESSAGE);
            System.exit(0);

        }
    }

    private void download() throws IOException {
        outMsg.writeInt(1);
    }

    private void upload() throws IOException {

    }

    private void list() throws IOException {

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.greetings();

    }
}