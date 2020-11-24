import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;

public class Main {
    private static Scanner in;

    public static void main(String[] args) throws IOException {
        in = new Scanner(System.in);
        System.out.println("C)ommand Line Interface // G)raphical User Interface");
        String command = in.nextLine().toUpperCase();
        if (command.equals("C")) {
            ManagementMenu menu = new ManagementMenu();
            menu.run();
        }
        if (command.equals("G")) {
            Application.launch(GraphicalManagementMenu.class, args);
        }
    }

}
