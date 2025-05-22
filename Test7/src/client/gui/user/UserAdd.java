package client.gui.user;

import client.Client;
import client.gui.Tool;
import server.console.DataProcessing;
import server.console.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class UserAdd extends JPanel {

    private UserList father;
    public UserAdd(UserList list){
        father = list;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        //title
        Box title_box = Box.createHorizontalBox();
        JLabel title = Tool.getLabel("新增用户", 100, 25, 20, SwingConstants.CENTER);
        title_box.add(title);
        add(title_box);
        add(Box.createVerticalStrut(15));

        //name_input
        JLabel name = Tool.getLabel("用户名", 100, 30, 15, SwingConstants.CENTER);
        JTextField name_input = Tool.getTextField(300, 30, "");
        Box name_box = Tool.getInputCombine(name, name_input, 10);
        add(name_box);
        add(Box.createVerticalStrut(15));

        //role_choose
        Box role_box = Box.createHorizontalBox();
        role_box.add(Box.createHorizontalGlue());
        JLabel role = Tool.getLabel("身份", 100, 30, 15, SwingConstants.CENTER);
        role_box.add(role);
        role_box.add(Box.createHorizontalStrut(10));
        JComboBox<String> role_input = new JComboBox<>();
        role_input.addItem("-null-");role_input.addItem("Administrator");role_input.addItem("Browser");role_input.addItem("Operator");
        role_input.setMaximumSize(new Dimension(300, 30));
        role_input.setPreferredSize(new Dimension(300, 30));
        role_box.add(role_input);
        role_box.add(Box.createHorizontalGlue());
        add(role_box);
        add(Box.createVerticalStrut(15));

        //passwd_input
        JLabel passwd = Tool.getLabel("密码", 100, 30, 15, SwingConstants.CENTER);
        JPasswordField passwd_input = Tool.getPasswordField(300, 30);
        Box passwd_box = Tool.getInputCombine_Passwd(passwd, passwd_input, 10);
        add(passwd_box);
        add(Box.createVerticalStrut(15));

        //passwd_check
        JLabel check = Tool.getLabel("确认密码", 100, 30, 15, SwingConstants.CENTER);
        JPasswordField check_input = Tool.getPasswordField(300, 30);
        Box check_box = Tool.getInputCombine_Passwd(check, check_input, 10);
        add(check_box);
        add(Box.createVerticalStrut(15));

        //ok_button
        Box ok_box = Box.createHorizontalBox();
        JButton ok = new JButton("添加");
        ok_box.add(ok);
        add(ok_box);
        add(Box.createVerticalGlue());

        //ok_event
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = name_input.getText();
                String role = role_input.getSelectedItem().toString();
                String passwd = new String(passwd_input.getPassword());
                String check = new String(check_input.getPassword());

                if(name.isEmpty() || passwd.isEmpty() || check.isEmpty()){
                    JOptionPane.showMessageDialog(null, "用户名或密码为空","警告",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(role.equals("-null-")){
                    JOptionPane.showMessageDialog(null, "未选择身份 O.o?","警告",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(!passwd.equals(check)){
                    JOptionPane.showMessageDialog(null, "密码不一致 x.x","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    if(Client.searchUser(name) != null) {
                        JOptionPane.showMessageDialog(null, "该用户已存在 O.O","警告",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Client.insert(new User(name, passwd, role));
                } catch (IOException | ClassNotFoundException x) {
                    System.out.println(x.getMessage());
                    JOptionPane.showMessageDialog(null, "数据库错误", "Worry", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                name_input.setText("");role_input.setSelectedIndex(0);passwd_input.setText("");check_input.setText("");
                father.update_row();
                father.refresh_table();
                JOptionPane.showMessageDialog(null, "添加成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
