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
//            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            DataInputStream in = new DataInputStream(socket.getInputStream());
            StringBuilder stringBuilder = new StringBuilder();
            String str = null;
            while(in.available()!=0){
                str = in.readUTF();
                stringBuilder.append(str);
            }
            socket.shutdownInput();
            String[] args = stringBuilder.toString().trim().split(" ");
            for(String i:args){
                System.out.println("args: "+i);
            }
            String resGrep = new GrepImpl().grep(args);
            System.out.println("resGrep: " + resGrep);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(resGrep);
            out.flush();
            out.close();
//            new DataOutputStream(socket.getOutputStream()).writeUTF("Request accepted");
            System.out.println("write over");
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
