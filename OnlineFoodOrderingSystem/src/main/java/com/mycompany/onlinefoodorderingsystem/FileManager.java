
package com.mycompany.onlinefoodorderingsystem;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static void ensureFileExists(String filename){
        File file=new File(filename);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(IOException e){
            System.out.println("Error Creating file "+filename);
            e.printStackTrace();
        }
    }
    
    public static void writeToFile(String fileName,String data,boolean append){
        ensureFileExists(fileName);
        try(FileWriter fw=new FileWriter(fileName,append);
                BufferedWriter bw=new BufferedWriter(fw);
                PrintWriter pr=new PrintWriter(bw)){
            pr.println(data);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static List<String> readFromFile(String filename){
        ensureFileExists(filename);
        List<String> data=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(filename))){
            String line;
            while((line=br.readLine())!=null){
                data.add(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return data;
    
    }
    public static int readOrderIDFromFile(String filename) {
        ensureFileExists(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            return (line != null && !line.isEmpty()) ? Integer.parseInt(line.trim()) : 1000; 
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 1000;
        }
    }
    public static void writeOrderIDToFile(String filename, int orderID) {
        ensureFileExists(filename);
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, false))) {
            pw.println(orderID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
