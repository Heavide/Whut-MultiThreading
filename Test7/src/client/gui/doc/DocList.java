package client.gui.doc;

import client.Client;
import server.console.DataProcessing;
import server.console.Doc;
import server.console.User;
import client.gui.Tool;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

public class DocList extends JPanel{

    private User online;
    private JTable doc_list;
    private Vector<String> column;
    private Vector<Vector<String>> row;
    private JScrollPane table_pane;
    public DocList(User user){
        online = user;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        //title
        Box title_box = Box.createHorizontalBox();
        JLabel title = Tool.getLabel("档案列表", 100, 25, 20, SwingConstants.CENTER);
        title_box.add(title);
        add(title_box);
        add(Box.createVerticalStrut(15));

        //search
        Box search_box = Box.createHorizontalBox();
        search_box.add(Box.createHorizontalGlue());
        JTextField search_input = Tool.getTextField(450, 30, "请输入档案名");
        search_box.add(search_input);
        search_box.add(Box.createHorizontalStrut(15));

        JButton search = new JButton("搜索");
        search_box.add(search);
        search_box.add(Box.createHorizontalGlue());
        add(search_box);
        add(Box.createVerticalStrut(15));

        //table
        column = new Vector<>();
        column.add("ID");column.add("档案名");column.add("档案描述");column.add("创建者");column.add("创建时间");
        update_row();
        doc_list = Tool.getTable(row, column, 150, 300);
        table_pane = new JScrollPane(doc_list);
        add(table_pane);
        add(Box.createVerticalStrut(50));

        //button
        Box button_box = Box.createHorizontalBox();
        JButton up = new JButton("上传");
        JButton down = new JButton("下载");
        JButton re = new JButton("刷新");
        button_box.add(Box.createHorizontalGlue());
        if(user.getRole().equals("Operator")) {
            button_box.add(up);button_box.add(Box.createHorizontalStrut(70));
        }
        button_box.add(down);button_box.add(Box.createHorizontalStrut(70));
        button_box.add(re);button_box.add(Box.createHorizontalGlue());
        add(button_box);
        add(Box.createVerticalGlue());

        //button_event
        up.addActionListener(e -> {
            Tool.addToFrame(new DocUpload(user, get_this()), 480, 480);
        });

        down.addActionListener(e ->{
            int idx = doc_list.getSelectedRow();
            if(idx == -1) return;
            Vector<String> tmp = row.get(idx);
            try{
                Doc doc = Client.searchDoc(tmp.get(0));
                new DocDownload(doc);
            } catch(IOException | ClassNotFoundException x) {
                JOptionPane.showMessageDialog(null, x.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
            }

        });

        search.addActionListener(e -> {
            String key = search_input.getText();
            if(key.equals("请输入档案名")) return;
            try {
                row = Client.search_get_Docs(key);
            } catch(IOException | ClassNotFoundException x){
                JOptionPane.showMessageDialog(null, x.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            refresh_table();
        });

        re.addActionListener(e -> {
            update_row();
            refresh_table();
        });
    }

    public DocList get_this(){return this;}

    public void update_row(){
        try{
            row = Client.getAllDoc();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "数据库错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refresh_table(){
        doc_list = Tool.getTable(row, column, 150, 300);
        table_pane = new JScrollPane(doc_list);
        remove(5);
        add(table_pane, 5);
        updateUI();
    }
}
