package org.academiadecodigo.bootcamp;

import java.io.File;


public class FileHandler {

    private static String filePath = "resources/file.txt";
    private File file;

    public static byte[] receiveFile(){

        return null;
    }

    public static byte[] sendFile(){


        return null;
    }

    static void listFiles(){
        File folder = new File("resources/");
        File[] listOfFiles = folder.listFiles();
        int fileId = 0;

        for (int i = 0; i <listOfFiles.length ; i++) {
            if(listOfFiles[i].isFile()){
                fileId ++;
                System.out.println("File Id: " + fileId + " - " + listOfFiles[i].getName());
            }else if (listOfFiles[i].isDirectory()){
                System.out.println("Directory: " + listOfFiles[i].getName());
            }
        }

    }

}
