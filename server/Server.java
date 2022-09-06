package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    private static ServerSocket serverSocket =null;
    private static int port = 8866;
    private static ThreadPoolExecutor threadPoolExecutor = null;
    private static int corePoolSize = 10;
    private static int maximumPoolSize = 15;

    public static void main(String [] args) throws IOException {
        serverSocket = new ServerSocket(port);
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        while(true){
            try{
                System.out.println("listening on Port: "+ port);
                Socket socket = serverSocket.accept();
                threadPoolExecutor.execute(new Processer(socket));
            }catch (SocketException e){
                e.printStackTrace();
            }
        }
    }
}
