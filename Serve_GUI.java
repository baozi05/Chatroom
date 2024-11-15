package GUI_Socket;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class Serve_GUI extends JFrame {
    private final JTextPane textPane;
    private final StyledDocument doc;
    public Serve_GUI() {
        setTitle("服务器后台");
        setBounds(100, 150, 450, 650);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel ps=new JLabel("服务器状态监视");
        ps.setBounds(160,15,200,30);
        ps.setFont(new Font("宋体",Font.BOLD,17));
        ps.setForeground(new Color(57, 182, 120));
        panel.add(ps);

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.setBounds(50, 50, 340, 450);
        textPane.setVisible(true);
        doc = textPane.getStyledDocument();
        panel.add(textPane);

        JButton refresh=new JButton("清屏");
        refresh.setFocusable(false);
        refresh.setBounds(170,530,100,28);
        refresh.setBackground(new Color(165, 169, 222, 255));
        refresh.setFont(new Font("华文宋体",Font.BOLD,15));
        refresh.addActionListener(e->{
            textPane.setText("");
        });
        panel.add(refresh);

        add(panel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
       Serve_GUI gui = new Serve_GUI();
    }
    public void appendMessage(String msg,int size,Color color,int alignment){
        // 添加一个能够把字符添加到TextPane中的方法,并设置样式
            Style style=textPane.addStyle("MyStyle",null);
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
