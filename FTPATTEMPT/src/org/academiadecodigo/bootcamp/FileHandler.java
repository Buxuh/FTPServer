package org.academiadecodigo.bootcamp;

import java.io.*;
import java.net.Socket;


public class FileHandler {

    //private static String filePath = "resources/test.txt";
    //private static File file;

    /**
     *  Sends a file to the specified socket
     *
     * @param socket  the socket to send to
     * @param file the file to send
     */
    public static void sendFile(Socket socket, String file) {
        byte[] data = new byte[1024];

        try {

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(file)));

            while (bufferedInputStream.read(data) > 0 ) {
                bufferedOutputStream.write(data);
            }

            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            bufferedInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used
     *
     * @param socket the socket connection to receive the file from
     */
    public static void receiveFile(Socket socket) {
        byte[] data = new byte[1024];

        try {
            File newFile = new File("downloads/myFile.txt");
            newFile.createNewFile();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());

            while (bufferedInputStream.read(data) > 0 ) {
                bufferedOutputStream.write(data);
            }

            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            bufferedInputStream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String[] listFiles() {

        File folder = new File("resources/");
        File[] listOfFiles = folder.listFiles();

        int fileId = 0;
        String[] files = new String[listOfFiles.length];

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {

                fileId++;
                files[i] = "File Id: " + fileId + " - " + listOfFiles[i].getName();

            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory: " + listOfFiles[i].getName());
            }
        }
        return files;
    }
}
