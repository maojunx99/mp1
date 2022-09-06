package server;

import api.Grep;
import api.impl.GrepImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Processer extends Thread{
    private Socket socket = null;

    public Processer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            while(true){
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                StringBuilder stringBuilder = new StringBuilder();
                String str = null;
                while(in.available()!=0){
                    str = in.readUTF();
                    stringBuilder.append(str);
                    String[] args = stringBuilder.toString().trim().split(" ");
                    for(String i:args){
                        System.out.println("args: "+i);
                        out.writeUTF("args: "+i);
                    }
                    String resGrep = new GrepImpl().grep(args);
                    System.out.println("resGrep: ");
                    System.out.println(resGrep);

                    out.writeUTF(resGrep);
                    out.flush();
                    System.out.println("write over");
                }
                Thread.sleep(100);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
