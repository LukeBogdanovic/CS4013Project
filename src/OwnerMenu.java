import java.io.*;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class OwnerMenu {
    private Scanner in;

    // initialises scanner for the CLI menu
    public OwnerMenu() {
        in = new Scanner(System.in);
    }

    public void run() throws IOException, ParseException {
        boolean more = true;
        System.out.println("Name of User:");
        String name = in.nextLine();
        Owner owner = new Owner(name);
        ManagementMenu menu = new ManagementMenu();
        menu.ownerList.add(owner);
        TaxCalculator tC = new TaxCalculator();
        while (more) {
            System.out.println("P)ay tax // L)ist properties and Tax Due // R)egister properties // Q)uit");
            String command = in.nextLine().toUpperCase();

            if (command.equals("P")) {
                System.out.println("What property do you want to pay tax for:\n");
                Property p = getProperties(owner.getProperties());
                System.out.println("To be Paid:" + tC.propertyTax(p));
                double toPay = in.nextDouble();
                in.nextLine();
                Payment pay = new Payment(toPay, LocalDate.now(), owner, p);
                try {
                    String path = "src/payments.csv";
                    FileWriter fw = new FileWriter(path, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw);

                    pw.print("\n" + pay.getOwner().getName() + "," + pay.getProperty().getAddress() + ","
                            + pay.getProperty().getLocation() + "," + pay.getProperty().getEircode() + ","
                            + pay.getDate() + "," + pay.getAmount());
                    pw.flush();
                    pw.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                String paid = pay.payTax(p, toPay, tC.propertyTax(p));
                System.out.println("Paid:" + paid + " For Property:" + p);
            }

            else if (command.equals("L")) {
                System.out.println(getProperties(owner.getProperties()));

            }

            else if (command.equals("R")) {
                System.out.println("Date Of Registry:Day");
                int day = in.nextInt();
                in.nextLine();
                System.out.println("Month");
                int month = in.nextInt();
                in.nextLine();
                System.out.println("Year");
                int year = in.nextInt();
                in.nextLine();
                LocalDate date = LocalDate.of(year, month, day);
                System.out.println("Address Of Property:");
                String ad = in.nextLine().toUpperCase();
                System.out.println("Eircode Of Property:");
                String eC = in.nextLine().toUpperCase();
                System.out.println("Location Of Property:");
                String lC = in.nextLine().toUpperCase();
                System.out.println("Estimated market value Of Property:");
                double emv = in.nextDouble();
                System.out.println("Principal Primary Residence of Owner:Enter 1 for Yes//Enter 0 for No");
                int Ppr = in.nextInt();
                in.nextLine();
                boolean Pp = false;
                if (Ppr == 1) {
                    Pp = true;
                }

                Property p = new Property(name, ad, eC, lC, emv, Pp, date);
                owner.addProperty(p);
            }

            else if (command.equals("Q")) {
                more = false;
            }
        }
        InterfaceSelect iSelect = new InterfaceSelect();
        iSelect.run();
    }

    // gets and lists properties from the arraylist provided to it
    private Property getProperties(ArrayList<Property> PChoices) {
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
