package client.gui.doc;

import client.Client;
import server.console.Doc;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class DocDownload extends JPanel {

    private DocList father;

    public DocDownload(Doc doc) throws IOException, ClassNotFoundException {
        JFileChooser save = new JFileChooser();
        save.setDialogTitle("选择存储位置");
        save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = save.showSaveDialog(father);
        if(res == JFileChooser.APPROVE_OPTION){
            Client.download(doc.getFilename(), save.getSelectedFile().getPath() + "\\" + doc.getFilename());
        }
    }
}
