package GUI_Socket;

import javax.swing.*;
import java.io.*;
import java.net.*;
public class Client {
    public static String c_name;
    public static String c_message;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    Client(){
        try{
            socket=new Socket("10.136.15.161",12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            if(in.readLine()!=null)
                JOptionPane.showMessageDialog(null,"已连接到服务端","提示",JOptionPane.INFORMATION_MESSAGE);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
