package client.gui.user;

import client.Client;
import server.console.DataProcessing;
import server.console.User;
import client.gui.Tool;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

public class UserList extends JPanel {

    private User online;
    private JTable user_list;
    private Vector<String> column;
    private Vector<Vector<String>> row;
    private JScrollPane table_pane;

    public UserList(User user){
        online = user;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        //title
        Box title_box = Box.createHorizontalBox();
        JLabel title = Tool.getLabel("用户列表", 100, 25, 20, SwingConstants.CENTER);
        title_box.add(title);
        add(title_box);
        add(Box.createVerticalStrut(15));

        //search
        Box search_box = Box.createHorizontalBox();
        search_box.add(Box.createHorizontalGlue());
        JTextField search_input = Tool.getTextField(450, 30, "请输入用户名");
        search_box.add(search_input);
        search_box.add(Box.createHorizontalStrut(15));

        JButton search = new JButton("搜索");
        search_box.add(search);
        search_box.add(Box.createHorizontalGlue());
        add(search_box);
        add(Box.createVerticalStrut(15));

        //table
        column = new Vector<>();
        column.add("用户名");column.add("密码");column.add("身份");
        update_row();
        user_list = Tool.getTable(row, column, 150, 300);
        table_pane = new JScrollPane(user_list);
        add(table_pane);
        add(Box.createVerticalStrut(50));

        //button
        Box button_box = Box.createHorizontalBox();
        JButton add = new JButton("添加");
        JButton del = new JButton("删除");
        JButton change = new JButton("修改");
        JButton re = new JButton("刷新");
        button_box.add(Box.createHorizontalGlue());
        button_box.add(add);button_box.add(Box.createHorizontalStrut(70));
        button_box.add(del);button_box.add(Box.createHorizontalStrut(70));
        button_box.add(change);button_box.add(Box.createHorizontalStrut(70));
        button_box.add(re);button_box.add(Box.createHorizontalGlue());
        add(button_box);
        add(Box.createVerticalGlue());

        //button_event
        add.addActionListener(e -> Tool.addToFrame(new UserAdd(get_this()), 480, 480));

        search.addActionListener(e -> {
            String key = search_input.getText();
            if(key.equals("请输入用户名")) return;
            try {
                row = Client.search_get_Users(key);
            } catch(IOException | ClassNotFoundException x){
                JOptionPane.showMessageDialog(null, x.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            refresh_table();
        });


        del.addActionListener(e -> {
            int idx = user_list.getSelectedRow();
            if(idx == -1) return;
            Vector<String> tmp = row.get(idx);
            if(online.getName().equals(tmp.get(0)))
            {
                JOptionPane.showMessageDialog(null, "你不能删除自己 >.<", "删除错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int op = JOptionPane.showConfirmDialog(null, "确认删除该用户吗 T.T", "确认删除", JOptionPane.OK_CANCEL_OPTION);
            if(op == JOptionPane.CANCEL_OPTION) return;
            try{
                Client.delete(tmp.get(0));
                row.remove(idx);
            } catch(IOException x) {
                JOptionPane.showMessageDialog(null, x.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            refresh_table();
        });

        change.addActionListener(e -> {
            int idx = user_list.getSelectedRow();
            if(idx == -1) return;
            Vector<String> tmp = row.get(idx);
            try{
                User user1 = Client.searchUser(tmp.get(0));
                Tool.addToFrame(new UserChange(get_this(), user1), 480, 480);
            } catch(IOException | ClassNotFoundException x) {
                JOptionPane.showMessageDialog(null, x.getMessage(), "数据库错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        re.addActionListener(e -> {
            update_row();
            refresh_table();
        });
    }

    public UserList get_this() {return this;}
    public void update_row(){
        try{
            row = Client.getAllUser();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refresh_table(){
        user_list = Tool.getTable(row, column, 150, 300);
        table_pane = new JScrollPane(user_list);
        remove(5);
        add(table_pane, 5);
        updateUI();
    }

}
