package GUI_Socket;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Creat_name1 extends JFrame {
    private String name;
    Creat_name1() {
        setTitle("登录");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        Font f=new Font("宋体",Font.BOLD,17);
        Font f1=new Font("黑体",Font.PLAIN,15);

        JLabel ps=new JLabel("用户名 ：");
        JLabel ps1=new JLabel("聊天室用户注册");
        ps1.setBounds(140, 20, 200, 30);
        ps1.setFont(f);
        ps1.setForeground(new Color(51, 125, 48));
        ps.setBounds(92, 80, 200, 30);
        ps.setFont(f1);

        panel.add(ps);
        panel.add(ps1);

        JButton con,exit;
        con=new JButton("确认");
        exit=new JButton("退出");
        con.setBounds(110,160,60,25);
        con.setFocusable(false);
        exit.setBounds(230,160,60,25);
        exit.setFocusable(false);
        panel.add(con);
        panel.add(exit);


        JTextField username=new JTextField();
        username.setBounds(170, 80, 120, 26);
        username.setFont(f1);
        panel.add(username);

        exit.addActionListener(e -> {
            System.exit(0);
        });
        // 继续按钮的 Action
        con.addActionListener(e->{
            name=username.getText();
            if(name.isEmpty()){
                JOptionPane.showMessageDialog(null,"用户名不能为空，请重新输入","提示",JOptionPane.ERROR_MESSAGE);
            }
            else{
                Client.c_name=name;
                new Client_GUI();
                dispose();
            }

        });
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(700, 200, 400, 250);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Creat_name1();
    }
}

class Client_GUI1 extends JFrame {
    private String message;
    Client client=new Client();
    Client_GUI1() {
        setTitle("用户界面");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        Font f=new Font("宋体",Font.BOLD,20);
        JLabel ps=new JLabel("聊天窗口");
        ps.setBounds(775, 20, 200, 30);
        ps.setFont(f);
        ps.setForeground(new Color(173, 75, 199));
        panel.add(ps);

        Font f1=new Font("宋体",Font.PLAIN,15);
        JTextPane text=new JTextPane();
        text.setBounds(80,50,300,140);
        text.setFont(f1);
        panel.add(text);

        JButton send, exit;
        send=new JButton("发送");
        exit =new JButton("退出");
        send.setBounds(130,210,60,25);
        exit.setBounds(270,210,60,25);
        send.setFocusable(false);
        exit.setFocusable(false);
        panel.add(send);
        panel.add(exit);

        exit.addActionListener(e->{
            client.out.println("exit");
            try {
                client.socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        });
        client.out.println(Client.c_name);
        send.addActionListener(e->{
            message=text.getText();
            if(message.isEmpty()){
                JOptionPane.showMessageDialog(null,"消息不能为空","提示",JOptionPane.ERROR_MESSAGE);
            }
            else {
                Client.c_message =message;
                text.setText("");
                String out_time;
                Date data=new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                out_time=formatter.format(data);

                client.out.println(Client.c_message);client.out.println(out_time);
                try{
                    String response = client.in.readLine();
                    JOptionPane.showMessageDialog(null,"服务器相应："+response,"提示",JOptionPane.INFORMATION_MESSAGE);
                }catch (IOException ex){
                    ex.printStackTrace();
                }


            }
        });

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 200, 450, 300);
        setVisible(true);
    }

}
