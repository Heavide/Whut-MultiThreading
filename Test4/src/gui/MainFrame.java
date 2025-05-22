package gui;

import console.User;
import gui.doc.DocList;
import gui.user.SelfInfo;
import gui.user.UserList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MainFrame extends JFrame{

    private User user;

    private JPanel center;
    public MainFrame(User x){
        user = x;
        //frame
        setTitle("档案管理系统");
        setSize(812, 624);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        //center_panel
        center = new JPanel();
        add(center, BorderLayout.CENTER);

        //menubar
        JMenuBar menu_bar = new JMenuBar();
        JMenu user_menu = new JMenu("用户");//我的信息，退出登录
        JMenuItem my_info = new JMenuItem("个人中心");//修改密码放这里面
        JMenuItem exit_log = new JMenuItem("退出登录");
        user_menu.add(my_info);
        user_menu.add(exit_log);
        menu_bar.add(user_menu);

        JMenu help_menu = new JMenu("帮助");
        JMenuItem about = new JMenuItem("关于");
        help_menu.add(about);
        menu_bar.add(help_menu);

        setJMenuBar(menu_bar);
        //tree_catalog
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("操作");
        JTree catalog = new JTree(top);
        catalog.setMinimumSize(new Dimension(200, 624));
        catalog.setMinimumSize(new Dimension(200, 624));
        catalog.setPreferredSize(new Dimension(200, 624));

        DefaultMutableTreeNode user_op = new DefaultMutableTreeNode("用户管理");
        DefaultMutableTreeNode doc_op = new DefaultMutableTreeNode("档案管理");
        top.add(doc_op);

        DefaultMutableTreeNode user_list = new DefaultMutableTreeNode("用户列表");
        user_op.add(user_list);
        DefaultMutableTreeNode doc_list = new DefaultMutableTreeNode("档案列表");
        doc_op.add(doc_list);

        String role = user.getRole();
        if(role.equals("Administrator")){
            top.add(user_op);
        }
        add(catalog, BorderLayout.WEST);
        catalog.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode x1 = (DefaultMutableTreeNode) catalog.getLastSelectedPathComponent();
            if(x1 == null) return;
            String pick = x1.toString();
            switch (pick) {
                case "用户管理", "用户列表" -> {
                    remove(center);
                    center = new UserList(user);
                    add(center, BorderLayout.CENTER);
                }
                case "档案管理", "档案列表" -> {
                    remove(center);
                    center = new DocList(user);
                    add(center, BorderLayout.CENTER);
                }
            }
        });

        //menu_event
        exit_log.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        my_info.addActionListener(e -> {
            new SelfInfo(user, this);
        });

        setVisible(true);
    }

}
