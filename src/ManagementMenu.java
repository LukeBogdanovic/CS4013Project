import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ManagementMenu {
    private Scanner in;
    private ArrayList<Owner> ownerList;// need to add owners to this list at startup of program
    private TaxCalculator tC;
    private Statistics st;
    private Owner owner;

    // initialises scanner for the CLI management menu
    public ManagementMenu() {
        in = new Scanner(System.in);
        this.ownerList = new ArrayList<Owner>();
    }

    // runs the management menu for users
    public void run() throws IOException {
        boolean more = true;
        InterfaceSelect iSelect = new InterfaceSelect();
        System.out.println("Enter username for management:");
        String manager = in.nextLine().toUpperCase();
        System.out.println("Enter password for management:");
        String password = in.nextLine().toUpperCase();
        if (manager.equals("MANAGER") && password.equals("PASSWORD")) {
            tC = new TaxCalculator();
            st = new Statistics();
            while (more) {
                System.out.println("P)roperty tax // O)wners tax // OV)erdue tax // S)tatistics // Q)uit ");
                String command = in.nextLine().toUpperCase();
                // gets tax paid for a property specified by the management user
                if (command.equals("P")) {
                    Property p = getProperty(owner.getProperties());
                    System.out.println(tC.overallTax(p));
                }
                // gets all tax paid by a property Owner specified by the management user
                else if (command.equals("O")) {
                    ownerList = new ArrayList<Owner>();
                    Owner o = getOwners(ownerList);

                }
                // gets and displays the overdue tax for a year with the option to subquery an
                // Eircode routing key
                else if (command.equals("OV")) {
                    System.out.println("Enter the year you want to query: yyyy-mm-dd format");
                    String y = in.nextLine();
                    LocalDate year = LocalDate.parse(y);
                    System.out.println("Do you want to query a specific area? Yes//No");
                    String response = in.nextLine().toUpperCase();
                    // overdue tax for area specific eircode key
                    if (response.equals("YES")) {
                        System.out.println("Enter Eircode routing key:");
                        String key = in.nextLine();
                        System.out.println(tC.overdueTax(year, key));
                    }
                    // overdue tax just for year
                    else {
                        System.out.println(tC.overdueTax(year));
                    }

                }
                // gets statistics based on the tax paid on properties by owners
                else if (command.equals("S")) {
                    boolean M = true;
                    while (M) {
                        System.out.println(
                                "Choose Statistic\nT)otal Tax Paid // A)verage Tax Paid // N)umber and Percentage of Property Taxes Paid // B)ack");
                        String com = in.nextLine().toUpperCase();
                        // gets total tax paid
                        if (com.equals("T")) {
                            System.out.println("Eircode key for routing:");
                            String eC = in.nextLine().toUpperCase();
                            System.out.println(st.totalTaxPaid(eC, "src/properties.csv"));
                        }
                        // gets average tax paid
                        else if (com.equals("A")) {
                            System.out.println("Eircode key for routing:");
                            String eC = in.nextLine().toUpperCase();
                            System.out.println(st.averageTaxPaid(eC, "src/properties.csv"));
                        }
                        // gets the number and percentage of property taxes paid
                        else if (com.equals("N")) {
                            System.out.println("Eircode key for routing:");
                            String eC = in.nextLine().toUpperCase();
                            System.out.println(st.percentageOfTaxesPaid(eC));
                        }

                        else if (com.equals("B")) {
                            M = false;
                        }
                    }
                }
                // quits the program
                else if (command.equals("Q")) {
                    more = false;// ends the program for user
                }
            }
        } else {
            iSelect.run();
        }
        iSelect.run();
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
