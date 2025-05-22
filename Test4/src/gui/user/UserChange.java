package gui.user;

import console.DataProcessing;
import console.User;
import gui.Tool;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UserChange extends JPanel {
    private UserList father;
    private User user;
    private boolean reset;
    private JButton passwd_reset;
    public UserChange(UserList list, User x) {
        father = list;
        user = x;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        //title
        Box title_box = Box.createHorizontalBox();
        JLabel title = Tool.getLabel("修改用户", 100, 25, 20, SwingConstants.CENTER);
        title_box.add(title);
        add(title_box);
        add(Box.createVerticalStrut(15));

        //user_name
        JLabel name = Tool.getLabel("用户名", 100, 30, 15, SwingConstants.CENTER);
        JTextField name_input = Tool.getTextField(300, 30, user.getName());
        name_input.setEnabled(false);
        Box name_box = Tool.getInputCombine(name, name_input, 10);
        add(name_box);
        add(Box.createVerticalStrut(15));

        //role
        Box role_box = Box.createHorizontalBox();
        role_box.add(Box.createHorizontalGlue());
        JLabel role = Tool.getLabel("身份", 100, 30, 15, SwingConstants.CENTER);
        role_box.add(role);
        role_box.add(Box.createHorizontalStrut(10));
        JComboBox<String> role_input = new JComboBox<>();
        role_input.addItem("Administrator");role_input.addItem("Browser");role_input.addItem("Operator");
        role_input.setSelectedItem(user.getRole());
        role_input.setMaximumSize(new Dimension(300, 30));
        role_input.setPreferredSize(new Dimension(300, 30));
        role_box.add(role_input);
        role_box.add(Box.createHorizontalGlue());
        add(role_box);
        add(Box.createVerticalStrut(15));

        //passwd
        Box passwd_box = Box.createHorizontalBox();
        passwd_box.add(Box.createHorizontalGlue());
        JLabel passwd = Tool.getLabel("密码", 100, 30, 15, SwingConstants.CENTER);
        passwd_reset = new JButton("重置密码");
        passwd_reset.setPreferredSize(new Dimension(300, 30));
        passwd_box.add(passwd);
        passwd_box.add(Box.createHorizontalStrut(10));
        passwd_box.add(passwd_reset);
        passwd_box.add(Box.createHorizontalGlue());
        add(passwd_box);
        add(Box.createVerticalStrut(15));

        //ok_button
        Box button_box = Box.createHorizontalBox();
        button_box.add(Box.createHorizontalGlue());
        JButton ok = new JButton("修改");
        button_box.add(ok);
        button_box.add(Box.createHorizontalGlue());
        add(button_box);
        add(Box.createVerticalGlue());

        //button_event
        passwd_reset.addActionListener(e -> {
            reset = true;
            passwd_reset.setText("密码已重置");
            passwd_reset.setEnabled(false);
        });
        ok.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(null, "确认修改该用户吗", "确认修改", JOptionPane.OK_CANCEL_OPTION);
            if(op == JOptionPane.CANCEL_OPTION) return;
            String role1 = role_input.getSelectedItem().toString();
            String passwd1;
            if(reset) passwd1 = "123";
            else passwd1 = user.getPassword();
            User after = new User(user.getName(), passwd1, role1);
            try {
                DataProcessing.update(after);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "数据库错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            passwd_reset.setEnabled(true);passwd_reset.setText("重置密码");
            father.update_row();
            father.refresh_table();
            DataProcessing.save();
            JOptionPane.showMessageDialog(null, "修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
