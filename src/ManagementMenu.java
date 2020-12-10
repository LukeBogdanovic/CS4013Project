import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class ManagementMenu {
    private Scanner in;
    ArrayList<Owner> ownerList;

    // initialises scanner for the CLI management menu
    public ManagementMenu() {
        in = new Scanner(System.in);
        this.ownerList = new ArrayList<Owner>();
    }

    // runs the management menu for users
    public void run() throws IOException {
        boolean more = true;
        TaxCalculator tC = new TaxCalculator();
        Statistics st = new Statistics();
        while (more) {
            System.out.println("P)roperty tax // O)wners tax // S)tatistics // Q)uit ");
            String command = in.nextLine().toUpperCase();
            if (command.equals("P")) {
                Property p = getProperty();
            } else if (command.equals("O")) {

            } else if (command.equals("S")) {
                boolean M = true;
                while (M) {
                    System.out.println(
                            "Choose Statistic\nT)otal Tax Paid // A)verage Tax Paid // N)umber and Percentage of Property Taxes Paid // B)ack");
                    String com = in.nextLine().toUpperCase();

                    if (com.equals("T")) {
                        System.out.println("Eircode key for routing:");
                        String eC = in.nextLine().toUpperCase();
                        System.out.println(st.totalTaxPaid(eC));
                    } else if (com.equals("A")) {
                        System.out.println("Eircode key for routing:");
                        String eC = in.nextLine().toUpperCase();
                        System.out.println(st.averageTaxPaid(eC));
                    } else if (com.equals("N")) {
                        System.out.println("Eircode key for routing:");
                        String eC = in.nextLine().toUpperCase();
                        System.out.println(st.percentageOfTaxesPaid(eC));
                    } else if (com.equals("B")) {
                        M = false;
                    }
                }
            } else if (command.equals("Q")) {
                more = false;// ends the program for user
            }
        }
    }

    // gets and list owners from the arraylist provided to it
    private Owner getOwners(ArrayList<Owner> OChoices) {
        if (OChoices.size() == 0) {
            return null;
        }
        while (true) {
            char c = 'A';
            for (Owner OChoice : OChoices) {
                System.out.println(c + ") " + OChoice);
                c++;
            }
            String input = in.nextLine();
            int n = input.toUpperCase().charAt(0) - 'A';
            if (0 <= n && n < OChoices.size()) {
                return OChoices.get(n);
            }
        }
    }

    // gets and lists properties from the arraylist provided to it
    private Property getProperty(ArrayList<Property> PChoices) {
        if (PChoices.size() == 0) {
            return null;
        }
        while (true) {
            char c = 'A';
            for (Property PChoice : PChoices) {
                System.out.println(c + ") " + PChoice);
                c++;
            }
            String input = in.nextLine();
            int n = input.toUpperCase().charAt(0) - 'A';
            if (0 <= n && n < PChoices.size()) {
                return PChoices.get(n);
            }
        }
    }

}
