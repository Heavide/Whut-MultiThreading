package gui.doc;

import console.DataProcessing;
import console.User;
import console.Doc;
import gui.Tool;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DocUpload extends JPanel {

    private DocList father;
    public DocUpload(User user, DocList list){
        father = list;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        //title
        Box title_box = Box.createHorizontalBox();
        JLabel title = Tool.getLabel("上传档案", 100, 25, 20, SwingConstants.CENTER);
        title_box.add(Box.createHorizontalGlue());
        title_box.add(title);
        title_box.add(Box.createHorizontalGlue());
        add(title_box);
        add(Box.createVerticalStrut(15));

        //id_input
        JLabel id = Tool.getLabel("档案编号", 100, 30, 15, SwingConstants.CENTER);
        JTextField id_input = Tool.getTextField(300, 30, "");
        Box id_box = Tool.getInputCombine(id, id_input, 10);
        add(id_box);
        add(Box.createVerticalStrut(15));

        //des
        JLabel des = Tool.getLabel("档案描述", 100, 30, 15, SwingConstants.CENTER);
        JTextField des_input = Tool.getTextField(300, 30, "");
        Box des_box = Tool.getInputCombine(des, des_input, 10);
        add(des_box);
        add(Box.createVerticalStrut(15));

        //file_input_button
        Box file_box = Box.createHorizontalBox();
        file_box.add(Box.createHorizontalGlue());
        JLabel file = Tool.getLabel("档案位置", 100, 30, 15, SwingConstants.CENTER);
        file_box.add(file);file_box.add(Box.createHorizontalStrut(10));
        JTextField file_input = Tool.getTextField(195, 30, "");
        file_box.add(file_input);file_box.add(Box.createHorizontalStrut(10));
        JButton file_button = new JButton("选择文件");
        file_button.setPreferredSize(new Dimension(90, 30));
        file_box.add(file_button);file_box.add(Box.createHorizontalGlue());
        add(file_box);
        add(Box.createVerticalStrut(15));

        //button
        Box ok_box = Box.createHorizontalBox();
        ok_box.add(Box.createHorizontalGlue());
        JButton ok = new JButton("上传");
        ok_box.add(ok);
        ok_box.add(Box.createHorizontalGlue());
        add(ok_box);
        add(Box.createVerticalGlue());

        //button_event
        file_button.addActionListener(e -> {
            JFileChooser open = new JFileChooser();
            open.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int res = open.showOpenDialog(father);
            if(res == JFileChooser.APPROVE_OPTION){
                String path = open.getSelectedFile().getPath();
                file_input.setText(path);
            }
        });

        ok.addActionListener(e -> {
            String id1 = id_input.getText();
            String des1 = des_input.getText();
            String path = file_input.getText();
            String file_name = (new File(path)).getName();

            if(id1.isEmpty() || des1.isEmpty()){
                JOptionPane.showMessageDialog(null, "文件编号或描述为空","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(path.isEmpty()){
                JOptionPane.showMessageDialog(null, "未选择文件","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            try(FileInputStream input = new FileInputStream(path)){
                try(FileOutputStream output = new FileOutputStream("D:\\Java_Project\\Management\\Test4\\data\\upload\\" + file_name)){
                    byte[] buf = new byte[1010];
                    while(input.read(buf) != -1) output.write(buf);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "上传错误","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                if(DataProcessing.searchDocByName(file_name)) {
                    JOptionPane.showMessageDialog(null, "该档案已存在 O.O","警告",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(DataProcessing.searchDocByID(id1)) {
                    JOptionPane.showMessageDialog(null, "该编号已存在 O.O","警告",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Doc tmp = new Doc(id_input.getText(), user.getName(), new Timestamp(System.currentTimeMillis()), des_input.getText(), file_name);
                DataProcessing.insertDoc(tmp);
            } catch (SQLException x) {
                JOptionPane.showMessageDialog(null, "数据库错误", "Worry", JOptionPane.ERROR_MESSAGE);
                return;
            }
            father.update_row();
            father.refresh_table();
            id_input.setText("");des_input.setText("");file_input.setText("");
            DataProcessing.save();
            JOptionPane.showMessageDialog(null, "上传成功", "成功", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
