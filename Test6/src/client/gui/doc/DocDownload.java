package gui.doc;

import console.Doc;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class DocDownload extends JPanel {

    private DocList father;

    public DocDownload(Doc doc){
        JFileChooser save = new JFileChooser();
        save.setDialogTitle("选择存储位置");
        save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = save.showSaveDialog(father);
        if(res == JFileChooser.APPROVE_OPTION){
            String from = "D:\\Java_Project\\Management\\Test5\\data\\upload\\" + doc.getFilename();
            String to = save.getSelectedFile().getPath() + "\\" + doc.getFilename();
            try(FileInputStream input = new FileInputStream(from)){
                try(FileOutputStream output = new FileOutputStream(to)){
                    byte[] buf = new byte[1010];
                    while(input.read(buf) != -1) output.write(buf);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "下载错误","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "下载成功", "成功", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
