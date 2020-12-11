import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;

public class OwnerMenu {
    private Scanner in;
    private Owner owner;
    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter pw;
    private TaxCalculator tC;
    private LocalDate date;

    // initialises scanner for the CLI menu
    public OwnerMenu() {
        in = new Scanner(System.in);
    }

    public void run() throws IOException {
        boolean more = true;
        System.out.println("Name of User:");
        String name = in.nextLine();
        owner = new Owner(name);
        tC = new TaxCalculator();
        while (more) {
            System.out.println(
                    "P)ay tax // L)ist properties and Tax Due // B)alancing Statements // R)egister properties // Q)uit");
            String command = in.nextLine().toUpperCase();
            // allows user to pay the tax due on their property-year check of some sort
            // needed
            if (command.equals("P")) {
                System.out.println("What property do you want to pay tax for:\n");
                Property p = getProperties(owner.getProperties());
                System.out.println("To be Paid:" + tC.propertyTax(p));// needs extra work done for getting penalty
                double toPay = in.nextDouble();
                in.nextLine();
                Payment pay = new Payment(toPay, LocalDate.now(), owner, p, true);
                writeToPayments("src/payments.csv", pay);
                String paid = pay.payTax(p, toPay, tC.propertyTax(p));
                System.out.println("Paid:" + paid + " For Property:" + p);
            }
            // lists the properties and taxes due on each of properties
            else if (command.equals("L")) {
                System.out.println(getProperty(owner.getProperties()));
            }
            // gets balancing statements based on the Owner or Property as decided by the
            // user
            else if (command.equals("B")) {
                System.out.println("P)roperty Eircode// A)ll properties");
                String com = in.nextLine();
                // property balancing statement
                if (com.equals("P")) {
                    String eircode = in.nextLine();
                    for (int i = 0; i < owner.getProperties().size(); i++) {
                        if (eircode.equals(owner.getProperties().get(i).getEircode())) {
                            System.out.println(tC.balancingStatement(owner.getProperties().get(i)));
                            break;
                        }
                    }
                }
                // Owner balancing statement
                else if (com.equals("A")) {
                    System.out.println(tC.balancingStatement(owner));
                }
            }
            // allows a user to register a property
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
                date = LocalDate.of(year, month, day);
                System.out.println("Address Of Property:");
                String ad = in.nextLine().toUpperCase();
                System.out.println("Eircode Of Property:");
                String eC = in.nextLine().toUpperCase();
                System.out.println("Location Of Property:");
                String lC = in.nextLine().toUpperCase();
                System.out.println("Estimated market value Of Property:");
                double emv = in.nextDouble();
                System.out.println("Principal Primary Residence of Owner: Yes//No");
                String Ppr = in.nextLine().toUpperCase();
                in.nextLine();
                boolean Pp = false;
                if (Ppr.equals("YES")) {
                    Pp = true;
                }
                Property p = new Property(name, ad, eC, lC, emv, Pp, date);
                owner.addProperty(p);
                writeToProperties("src/properties.csv", p, name);
            }
            // quits the program
            else if (command.equals("Q")) {
                more = false;
            }
        }
        InterfaceSelect iSelect = new InterfaceSelect();// returns the user to interface selection screen
        iSelect.run();// allows use of software by other users and through gui
    }

    // gets and lists properties for the pay tax function
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

    // gets properties and the tax due for payment on properties for list function
    private Property getProperty(ArrayList<Property> PChoices) {
        if (PChoices.size() == 0) {
            return null;
        }
        while (true) {
            char c = 'A';
            for (Property PChoice : PChoices) {
                System.out.println(c + ") " + PChoice + " Taxes Due:" + tC.propertyTax(PChoice));
                c++;
            }
            String input = in.nextLine();
            int n = input.toUpperCase().charAt(0) - 'A';
            if (0 <= n && n < PChoices.size()) {
                return PChoices.get(n);
            }
        }
    }

    // reads in csv file for names of owners already in the system
    private String csvReader(String filename) throws IOException {
        Path pathToFile = Paths.get(filename);
        String name = "";
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                name = attributes[0];
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return name;
    }

    // writes properties resgistered to properties.csv- this is a nice method and I
    // like it, I don't know if we need to use it though
    private void writeToProperties(String filename, Property p, String name) throws IOException {
        try {
            String path = filename;
            fw = new FileWriter(path, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            pw.print("\n" + name + "," + p.getAddress() + "," + p.getEircode() + "," + p.getLocation() + ","
                    + p.getDate() + "," + p.isPpr());
            pw.flush();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // writes entries to the payments file after a payment has been logged
    private void writeToPayments(String filename, Payment pay) {
        try {
            String path = filename;
            fw = new FileWriter(path, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            pw.print("\n" + pay.getOwner().getName() + "," + pay.getProperty().getAddress() + ","
                    + pay.getProperty().getLocation() + "," + pay.getProperty().getEircode() + "," + pay.getDate() + ","
                    + pay.getAmount());
            pw.flush();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
