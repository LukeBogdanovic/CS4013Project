import javafx.application.Application;

import java.io.IOException;
import java.util.Scanner;

public class InterfaceSelect {
    Scanner in;

    public void run() throws IOException {
        in = new Scanner(System.in);
        System.out.println("C)ommand Line Interface // G)raphical User Interface // Q)uit");
        String command = in.nextLine().toUpperCase();
        if (command.equals("C")) {
            System.out.println("P)roperty Owner // D)epartment Management");
            String com = in.nextLine().toUpperCase();
            if (com.equals("P")) {
                OwnerMenu oMenu = new OwnerMenu();
                oMenu.run();
            } else if (com.equals("D")) {
                ManagementMenu menu = new ManagementMenu();
                menu.run();
            }
        }
        if (command.equals("G")) {
            Application.launch(GraphicalManagementMenu.class);// starts the GUI
        }
        if (command.equals("Q")) {
            return;
        }
    }

}
