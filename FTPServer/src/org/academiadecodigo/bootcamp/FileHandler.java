package org.academiadecodigo.bootcamp;

import java.io.*;


public class FileHandler {

    private static String filePath = "resources/test.txt";
    private static File file;
    private static DataOutputStream out;
    private static BufferedReader in;



    public static byte[] receiveFile(){

        return null;
    }

    public static void sendFile() throws IOException {

        byte[] buffer = new byte[1024];
        FileOutputStream out = new FileOutputStream(filePath);

    }

    public static String[] listFiles(){

        File folder = new File("resources/");
        File[] listOfFiles = folder.listFiles();

        int fileId = 0;
        String[] files = new String[listOfFiles.length];

        for (int i = 0; i <listOfFiles.length ; i++) {
            if(listOfFiles[i].isFile()){

                fileId ++;
                files[i] = "File Id: " + fileId + " - " + listOfFiles[i].getName();

            }else if (listOfFiles[i].isDirectory()){
                System.out.println("Directory: " + listOfFiles[i].getName());
            }
        }
        return files;
    }

}
