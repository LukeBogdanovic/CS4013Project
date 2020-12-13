import javafx.application.Application;
import java.io.IOException;
import java.util.Scanner;

public class InterfaceSelect {
    Scanner in;

    // runs the program and allows the user to select between gui and the cli
    // implmentations
    public void run() throws IOException {
        in = new Scanner(System.in);
        System.out.println("C)ommand Line Interface // G)raphical User Interface // Q)uit");
        String command = in.nextLine().toUpperCase();
        // runs command line interface
        if (command.equals("C")) {
            System.out.println("P)roperty Owner // D)epartment Management");
            String com = in.nextLine().toUpperCase();
            // runs the Owner Menu part of program
            if (com.equals("P")) {
                OwnerMenu oMenu = new OwnerMenu();
                oMenu.run();
            }
            // runs the Management Menu part of program
            else if (com.equals("D")) {
                ManagementMenu menu = new ManagementMenu();
                menu.run();
            }
        }
        // launches the gui implementation of program
        if (command.equals("G")) {
            Application.launch(GraphicalManagementMenu.class);
        }
        // Quits the System
        if (command.equals("Q")) {
            return;
        }
    }

}
