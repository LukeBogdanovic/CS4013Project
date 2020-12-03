import java.util.Scanner;
import java.util.ArrayList;

public class ManagementMenu {
    private Scanner in;

    public ManagementMenu() {
        in = new Scanner(System.in);
    }

    public void run() {
        boolean more = true;
        OwnerDB Owners = new OwnerDB();
        Owner owner = new Owner();
        while (more) {
            System.out.println("L)ist Owners S)tatistics R)egister property Q)uit ");
            String command = in.nextLine().toUpperCase();
            if (command.equals("L")) {
                Owner o = getOwners(Owners.getOwners());
                System.out.println("L)ist Properties B)ack");
                String com = in.nextLine();
                if (com.equals("L")) {

                }
            } else if (command.equals("S")) {
                System.out.println(
                        "Choose Statistic\nT)otal Tax Paid,A)verage Tax Paid,N)umber and Percentage of Property Taxes Paid B)ack");
                String com = in.nextLine().toUpperCase();
                if (com.equals("T")) {

                } else if (com.equals("A")) {

                } else if (com.equals("N")) {

                } else if (com.equals("B")) {
                }
            } else if (command.equals("R")) {
                System.out.println(
                        "Property(Owner(s) Address Eircode Estimated_Property_Value Location Principal_Primary_Residence)");
                System.out.println("Owner Input Format:owner1_owner2 etc... // Eircode Input Format:X00_XXXX");
                String com = in.nextLine();
                Property p = new Property(com);
                owner.addProperty(p);
            } else if (command.equals("Q")) {
                more = false;
            }
        }
    }

    private Owner getOwners(ArrayList<Owner> OChoices) {
        if (OChoices.size() == 0) {
            return null;
        }
        while (true) {
            char c = 'A';
            for (Owner OChoice : OChoices) {
                System.out.println(c + ") " + OChoice.toString());
                c++;
            }
            String input = in.nextLine();
            int n = input.toUpperCase().charAt(0) - 'A';
            if (0 <= n && n < OChoices.size()) {
                return OChoices.get(n);
            }
        }
    }

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
