import java.util.Scanner;

public class ManagementMenu {
    private Scanner in;

    public ManagementMenu() {
        in = new Scanner(System.in);
    }

    public void run() {
        boolean more = true;
        while (more) {
            System.out.println("S)how Owners Q)uit");
            String command = in.nextLine().toUpperCase();
            if (command.equals("S")) {
                System.out.println("Owners");
            }
            if (command.equals("Q")) {
                more = false;
            }
        }
    }

}
