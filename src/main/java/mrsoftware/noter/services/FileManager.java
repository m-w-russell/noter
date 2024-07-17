package mrsoftware.noter.services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class FileManager {
    public void makeFile(String filename) {
        try {
            File saveFile = new File(filename);
            saveFile.createNewFile();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void saveData(String text, String filename) {
        try {
            FileWriter saveWriter = new FileWriter(filename);
            saveWriter.write(text);
            saveWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public String loadData(String filename) throws IOException {
        StringBuilder output = new StringBuilder();
        int ch;

        FileReader noteReader = null;
        try {
            noteReader = new FileReader(filename);
            while((ch=noteReader.read())!=-1) {
                output.append((char)ch);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return output.toString();
    }

}
