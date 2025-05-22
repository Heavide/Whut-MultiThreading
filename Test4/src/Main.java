import console.DataProcessing;
import gui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        new LoginFrame();
        DataProcessing.connect();
        DataProcessing.save();
    }
}