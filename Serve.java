package GUI_Socket;

import java.awt.*;
import java.io.*;
import java.net.*;
public class Serve{
    private final Serve_GUI S_gui;
    private final ChatRoom room;
    Serve(Serve_GUI gui, ChatRoom room){
        this.S_gui =gui;
        this.room=room;
        try{
            ServerSocket socket=new ServerSocket(12345);
            gui.appendMessage("服务端已启动，等待客户端连接....",15,Color.BLACK,1);
            int id=1;
            while(true){
                Socket clientSocket = socket.accept();//让服务器等待客户端连接，期间处于“阻塞”状态
                // 当有客户端发起连接后，状态变为“就绪”，与客户端成功连接，准备通信，返回一个Socket对象
                G_CilentHander client_p=new G_CilentHander(clientSocket,id);
                gui.appendMessage("客户端"+id+"已连接",15,Color.BLACK,0);

                new Thread(client_p).start();
                id++;
                G_CilentHander.clientNum++;
            }
        }catch (IOException e){
            gui.appendMessage("错误!无法启动服务器！ ",17, Color.RED, 1);
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        Serve_GUI s_gui =new Serve_GUI();
        ChatRoom room=new ChatRoom();
        new Serve(s_gui,room);
    }
    class G_CilentHander extends Thread{
        // 将原来的多线程客户端修改为适应GUI的形式
        public static int clientNum=0;
        private final Socket clientsocket;
        private final int cilentid;
        private String name;
        public G_CilentHander(Socket clientsocket, int cilentid) {
            this.clientsocket = clientsocket;
            this.cilentid = cilentid;
        }
        public void run(){
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientsocket.getOutputStream(), true);
                out.println("已连接到服务端");
                String name=in.readLine();
                S_gui.appendMessage("客户端"+cilentid+"已修改昵称为："+name,15,new Color(0x3D3D95),0);
                room.appendMessage(name+"进入聊天室",15,new Color(0x93739F),1);
                this.name=name;
                String message,datatime;

                while(true){
                    message=in.readLine();
                    datatime=in.readLine();
                    if(message.equalsIgnoreCase("exit")){
                        clientNum--;
                        S_gui.appendMessage(name+"("+"客户端"+cilentid+")"+"断开连接",15,new Color(0xE8A335),0);
                        room.appendMessage(name+"离开聊天室",15,new Color(0xE8A335),1);
                        if(clientNum==0){
                            S_gui.appendMessage("所有客户端已断开连接，服务器关闭",15,new Color(0x9BA1F4),1);
                           // System.exit(0);
                        }
                        break;
                    }
                    room.appendMessage(datatime,13,Color.BLACK,1);
                    room.appendMessage(name+":",14,new Color(0xE5695E),0);
                    room.appendMessage("   "+message,16,Color.BLACK,0);
                    out.println("发送成功");

                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}