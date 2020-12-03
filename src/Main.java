import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;

public class Main {
    private static Scanner in;

    public static void main(String[] args) throws IOException {
        in = new Scanner(System.in);
        System.out.println("Change Tax System? Y)es // N)o");
        String com = in.nextLine().toUpperCase();
        if (com.equals("Y")) {
            System.out.println("Enter each location following this format: 'City, Large Town, Small Town, etc...'");
            String l = in.nextLine();
            String[] arrOfLoc = l.split(", ");
            System.out.println(
                    "Enter each locations charge in the corresponding order following this format:'100, 80, 60");
            String c = in.nextLine();
            String[] arrOfCha = c.split(", ");
            // TaxCalculator tC = new TaxCalculator(fC, fPC, aP);
        } else {
        }
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
