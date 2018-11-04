package org.academiadecodigo.bootcamp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public ClientConnection(Socket client) {
        this.client = client;
    }

    private void openStreams() {
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen(Socket socket) throws IOException {
        Integer message = Integer.parseInt(in.readLine());

        switch (message) {
            case 1:
                displayFileList();
                break;
            case 2:
                FileHandler.sendFile(socket,"resources/strings.txt");
                break;
            case 3:
                FileHandler.receiveFile(socket);
                break;
            case 4:
                out.close();
                in.close();
                break;
        }
    }

    public void displayFileList() {
        String fileList = "";

        for (String file : FileHandler.listFiles()) {
            fileList += (file + "\n");
        }
        out.println(fileList);
    }

    @Override
    public void run() {
        openStreams();

        while (!client.isClosed()) {
            try {
                listen(client);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
