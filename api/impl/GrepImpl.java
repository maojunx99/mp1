package api.impl;

import api.Grep;

import java.io.*;

public class GrepImpl implements Grep {
    private static Process process = null;
    StringBuilder stringBuilder = new StringBuilder();
    @Override
    public String grep(String[] args) {
        try{
            process = Runtime.getRuntime().exec(args);
            DataInputStream error = new DataInputStream(process.getErrorStream());
            System.out.println("error: ");
            while(error.available() != 0){
                System.out.println(error.readUTF());
            }
            DataInputStream in = new DataInputStream(process.getInputStream());
            while(in.available() != 0){
                stringBuilder.append(in.readUTF());
                stringBuilder.append("\n");
            }
            process.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
