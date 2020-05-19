/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author ARZavier
 */
public class ReadWrite {
    
    /**
     * Reads the file at the given file path
     * @param filePath the path to the file
     * @return a String representation of the file
     */
    
    public String readEntireFile(String filePath){
        // Reads the File
        File file = new File(filePath);
        return readEntireFile(file);
    }
    
    /**
     * Reads the Entire File. If the file does not exist, 
     * return "File Not Found"
     * @param file the file to be read
     * @return a String representation of the contents of a file
     */
    
    public String readEntireFile(File file){
        // Reads the File
        String retString = "";
        if (file.exists()){
            try {
                Scanner scan = new Scanner(file);
                scan.useDelimiter("\\Z");
                if(scan.hasNext()){
                    retString = scan.next();
                }
                scan.close();
            } catch(FileNotFoundException Ignored){
                return ("File Not Found for Path: " + file);
            }
        }
        else
        {
            System.out.println("File Does Not Exist");
        }
        return retString;
    }
    
    /**
     * Stores the contents of a file in an array split along
     * a given delimiter.
     * @param filePath file path
     * @param delimiter delimiter to split the file
     * @return 
     */
    
    public String[] readNstoreArray(String filePath, String delimiter){
        return readEntireFile(filePath).split(delimiter);
    }
    
    /**
     * Stores the contents of a file split on the comma
     * @param filePath file path
     * @return a string array
     */
    
    public String[] readNstoreArray(String filePath){
        return readNstoreArray(filePath, ",");
    }
    
    /**
     * Creates a new file at the given path
     * @param filePath file path
     * @return the file created
     */
    
    public File createFile(String filePath){
        // Creates a File
        File file = new File(filePath);
        if(file.exists()){
            System.out.println("File Already Exists");
            return null;
        }
        else
        {
            try {
                file.createNewFile();
            } catch(IOException Ignored) {
                Ignored.printStackTrace();
            }
            return file;
        }
    }
    
    /**
     * Creates a file if there does not exist one already, then returns
     * the file at the file path.
     * @param filePath file path
     * @return the file (either old or newly created)
     */
    
    public File createFileIfNotExists(String filePath){
        // Creates a File
        File file = new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException Ignored){
                Ignored.printStackTrace();
            }
        }
        return file;
    }
    
    /**
     * Adds the string to the end of a file
     * @param line string to be added
     * @param file the file to be added to
     */
    
    public void appendToFile(String line, File file){
        // Adds on to File
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.append(line);
            writer.close();
        } catch(IOException Ignored){}
    }
    
    /**
     * Erases the old content of a file and replaces it
     * with the new string
     * @param contents the string with which to fill the file
     * @param file the file to be overwritten
     */
    
    public void overwriteFileWithString(String contents, File file){
        // Erases all the text in the file and writes new text
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(contents);
            writer.close();
        } catch (IOException Ignored){}
    }
}
