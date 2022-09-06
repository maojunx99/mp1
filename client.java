import java.io.*;
import java.net.Socket;

public class client {
    public static void main(String[] args) throws IOException, InterruptedException {
//        fa22-cs425-3801.cs.illinois.edu
        Socket socket = new Socket("fa22-cs425-3801.cs.illinois.edu",8866);
        OutputStream outToServer = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
//        findstr "1" C:\Users\XUMAOJUN\Desktop\TE461\1.txt
//        grep -c 11 ~/mp1/test1.txt
        String cmd = "ls";
        System.out.println(cmd);
        out.writeUTF(cmd);
        out.flush();
        InputStream inFromServer = socket.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        while(in.available()==0){
        }
        while(in.available()!=0){
            System.out.println("服务器响应： " + in.readUTF());
        }
        socket.close();
    }
}
