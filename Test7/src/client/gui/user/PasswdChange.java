package client.gui.user;

import client.gui.LoginFrame;
import client.gui.MainFrame;
import client.gui.Tool;
import server.console.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class PasswdChange extends JFrame {

    public PasswdChange(User user, MainFrame pre, SelfInfo father){
        setTitle("修改密码");
        setSize(512,350);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        add(Box.createVerticalStrut(30));

        //title
        Box title_box = Box.createHorizontalBox();
        JLabel title = Tool.getLabel("修改密码", 250, 30, 25, SwingConstants.CENTER);
        title_box.add(title);
        getContentPane().add(title_box);
        getContentPane().add(Box.createVerticalStrut(30));

        //old_passwd
        JLabel old = Tool.getLabel("原密码", 50, 30, 15, SwingConstants.CENTER);
        JPasswordField old_input = Tool.getPasswordField(300, 30);
        Box old_box = Tool.getInputCombine(old, old_input, 20);
        getContentPane().add(old_box);
        getContentPane().add(Box.createVerticalStrut(30));

        //new_passwd
        JLabel _new_ = Tool.getLabel("新密码", 50, 30, 15, SwingConstants.CENTER);
        JPasswordField new_input = Tool.getPasswordField(300, 30);
        Box new_box = Tool.getInputCombine(_new_, new_input, 20);
        getContentPane().add(new_box);
        getContentPane().add(Box.createVerticalStrut(30));

        //ok_button
        Box ok_box = Box.createHorizontalBox();
        JButton ok = new JButton("确认修改");
        ok.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        ok_box.add(ok);
        getContentPane().add(ok_box);
        getContentPane().add(Box.createVerticalGlue());

        //ok_event
        ok.addActionListener(e -> {
            String old_passwd = new String(old_input.getPassword());
            String new_passwd = new String(new_input.getPassword());
            if(old_passwd.equals(user.getPassword())){
                try {
                    user.changeSelfInfo(new_passwd);
                } catch (SQLException | IOException ex) {
                    JOptionPane.showMessageDialog(null, "修改失败，请稍后再试", "修改失败", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "原密码错误，请重新输入", "修改失败", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "修改成功,请重新登录", "成功", JOptionPane.INFORMATION_MESSAGE);
            pre.dispose();father.dispose();this.dispose();
            new LoginFrame();
        });

        setVisible(true);

    }
}
