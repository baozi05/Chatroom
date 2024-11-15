package GUI_Socket;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.*;

public class ChatRoom extends JFrame {
    private final JTextPane ChatArea;
    private final StyledDocument doc;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public JLabel ShowTime =new JLabel();
    public JLabel PersonNum =new JLabel();
    public ChatRoom() {
        setTitle("聊天室");
        setBounds(400, 160, 450, 600);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel ps=new JLabel(); //欢迎
        JLabel ps1=new JLabel();//时间
        Font f=new Font("宋体", Font.BOLD, 14);
        Font f1=new Font("等线", Font.BOLD, 20);
        ps.setFont(f1);
        ps.setText("欢迎进入聊天室");
        ps.setHorizontalAlignment(SwingConstants.CENTER);
        ps.setForeground(new Color(75, 187, 188));
        ps.setBounds(70,10,300,30);
        ps1.setFont(f);
        ps1.setText("当前时间:");
        ps1.setBounds(110,50,160,20);
        ps1.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(ps1);
        panel.add(ps);

        PersonNum.setFont(f);
        PersonNum.setForeground(new Color(100, 95, 193));
        PersonNum.setBounds(140,80,250,20);
        panel.add(PersonNum);
        Timer P_Timer = new Timer(1000, e->updatePerson());
        P_Timer.start();

        ShowTime.setFont(f);
        ShowTime.setBounds(190,50,160,20);
        panel.add(ShowTime);
        Timer timer = new Timer(1000, e->updateTime());// 一个定时刷新器，每隔一秒刷新时间
        timer.start();

        // 文本显示
        ChatArea = new JTextPane();
        Font f2=new Font("等线", Font.BOLD, 12);
        ChatArea.setEditable(false);
        ChatArea.setFont(f2);
        ChatArea.setBounds(65,110,320,360);
        ChatArea.setFocusable(false);
        ChatArea.setForeground(new Color(108, 89, 185));
        doc=ChatArea.getStyledDocument();
        panel.add(ChatArea);

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void updateTime() {
        String currentTime = sdf.format(new Date());
        ShowTime.setText(currentTime);
    }
    private void updatePerson(){
        PersonNum.setText("当前聊天室在线人数："+Serve.G_CilentHander.clientNum+"人");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setVisible(true);
        });

    }
    public void appendMessage(String msg,int size,Color color,int alignment){
        // 添加一个能够把字符添加到TextPane中的方法,并设置样式
        Style style=ChatArea.addStyle("MyStyle",null);
        StyleConstants.setForeground(style,color);
        StyleConstants.setFontSize(style,size);
        StyleConstants.setAlignment(style,alignment);
        StyleConstants.setFontSize(style,size);
        try{
            doc.insertString(doc.getLength(),msg+'\n',style);
            doc.setParagraphAttributes(doc.getLength() - msg.length() - 1, msg.length() + 1, style, false);
            // 设置段落对齐方式，不然默认为左对齐，且插入方法内无法直接设置对齐方式
        }catch (BadLocationException e){
            e.printStackTrace();
        }
    }
}

