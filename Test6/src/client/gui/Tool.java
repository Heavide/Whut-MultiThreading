package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

public class Tool {
    public static JTextField getTextField(int weight, int height, String text){
        JTextField res = new JTextField();
        res.setMaximumSize(new Dimension(weight, height));
        res.setPreferredSize(new Dimension(weight, height));
        if(!text.isEmpty()) TextFiledText(res, text);
        return res;
    }

    public static JPasswordField getPasswordField(int weight, int height){
        JPasswordField res = new JPasswordField();
        res.setMaximumSize(new Dimension(weight, height));
        res.setPreferredSize(new Dimension(weight, height));
        return res;
    }
    public static void TextFiledText(JTextField res, String text){
        res.setText(text);
        res.setForeground(Color.GRAY);
        res.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(res.getText().equals(text)){
                    res.setText("");
                    res.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(res.getText().isEmpty()){
                    res.setText(text);
                    res.setForeground(Color.GRAY);
                }
            }
        });
    }

    public static JLabel getLabel(String text, int weight, int height, int size, int pos){
        JLabel res = new JLabel(text);
        res.setFont(new Font("微软雅黑", Font.PLAIN, size));
        res.setHorizontalAlignment(pos);
        res.setMaximumSize(new Dimension(weight, height));
        res.setPreferredSize(new Dimension(weight, height));
        return res;
    }


    public static JTable getTable(Vector<Vector<String>> row, Vector<String> column, int weight, int height){
        JTable res = new JTable(row, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        res.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 15));
        res.getTableHeader().setResizingAllowed(false);
        res.getTableHeader().setReorderingAllowed(false);
        res.setPreferredScrollableViewportSize(new Dimension(weight, height));
        res.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return res;
    }

    public static Box getInputCombine(JLabel label, JTextField input, int weight){
        Box res = Box.createHorizontalBox();
        res.add(Box.createHorizontalGlue());
        res.add(label);
        res.add(Box.createHorizontalStrut(weight));
        res.add(input);
        res.add(Box.createHorizontalGlue());
        return res;
    }

    public static Box getInputCombine_Passwd(JLabel label, JPasswordField input, int weight){
        Box res = Box.createHorizontalBox();
        res.add(Box.createHorizontalGlue());
        res.add(label);
        res.add(Box.createHorizontalStrut(weight));
        res.add(input);
        res.add(Box.createHorizontalGlue());
        return res;
    }

    public static void addToFrame(Component x, int weight, int height){
        JFrame res = new JFrame();
        res.add(x);
        res.setSize(weight, height);
        res.setLocationRelativeTo(null);
        res.setVisible(true);
    }

}
