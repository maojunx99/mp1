package server;

import api.Grep;
import api.impl.GrepImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Processer extends Thread{
    private Socket socket = null;
    private boolean flag = true;
    public Processer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            StringBuilder stringBuilder = new StringBuilder();
            String str = null;
            while (in.available() != 0) {
                str = in.readUTF();
//                stringBuilder.append("sh -c ");
                stringBuilder.append(str);
//                String[] args = stringBuilder.toString().trim().split(" ");

                String [] args={"sh","-c",stringBuilder.toString()};
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
            socket.close();
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}