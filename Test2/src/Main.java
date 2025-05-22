import console.DataProcessing;
import console.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User obj = new User();
        while(obj != null) {
            obj = obj.showMenu();
        }
    }
}