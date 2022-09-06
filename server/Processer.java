package server;

import api.Grep;
import api.impl.GrepImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Processer extends Thread {
    private Socket socket = null;
    private boolean flag = true;

    public Processer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            socket.setSoTimeout(3000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            StringBuilder stringBuilder = new StringBuilder();
            String str = null;
            while (true) {
                str = in.readUTF();
                stringBuilder.append(str);
                String[] args = {"sh", "-c", stringBuilder.toString()};
                for (String i : args) {
                    System.out.println("args: " + i);
                    out.writeUTF("args: " + i);
                }
                String resGrep = new GrepImpl().grep(args);
                System.out.println("resGrep: ");
                System.out.println(resGrep);
                out.writeUTF(resGrep);
                out.flush();
                System.out.println("write over");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}