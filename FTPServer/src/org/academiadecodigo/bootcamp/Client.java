package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

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


   private void init() {
        System.out.println(Message.WELCOME_MESSAGE + "\n");
        run("localhost", 8080);

    }

    private void start() throws IOException {

        outMsg = new DataOutputStream(clientSocket.getOutputStream());
        inMsg = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Prompt prompt = new Prompt(System.in, System.out);

        String[] options = {MenuOptions.VIEW_LIST.getMessage(),
                             MenuOptions.DOWNLOAD.getMessage(),
                             MenuOptions.UPLOAD.getMessage(),
                             Message.BYE_MESSAGE};

        MenuInputScanner menu = new MenuInputScanner(options);

        int choice = prompt.getUserInput(menu);
        System.out.println("\n" + "You chose option: " + options[choice-1]);

        switch(choice){
            case 1: list();
                break;
            case 2:
                download();
                break;
            case 3:
                upload();
                break;
            case 4:
                clientSocket.close();
                break;
        }

    }


    private void download() throws IOException {

    }

    private void upload() throws IOException {

    }

    private void list() throws IOException {



    }

    public static void main(String[] args) {
        Client client = new Client();
        client.init();

    }
}