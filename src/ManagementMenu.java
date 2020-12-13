import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class ManagementMenu {
    private Scanner in;
    private ArrayList<String> ownerList, properties;// arraylists to store input from csv files
    private TaxCalculator tC;// tax calculator object
    private Statistics st;// statistics object
    private DecimalFormat df = new DecimalFormat("0.00");// formats the output for decimals so as to appear like
                                                         // monetary values

    // initialises scanner for the CLI management menu
    public ManagementMenu() {
        in = new Scanner(System.in);
    }

    // runs the management menu for users
    public void run() throws IOException {
        boolean more = true;
        InterfaceSelect iSelect = new InterfaceSelect();
        System.out.println("Enter username for management:");
        String manager = in.nextLine();
        System.out.println("Enter password for management:");
        String password = in.nextLine().toLowerCase();
        if (manager.equalsIgnoreCase("MANAGER") && password.equals("password")) {
            System.out.println("Login Successful");
            tC = new TaxCalculator();
            st = new Statistics();
            while (more) {
                System.out.println(
                        "P)roperty tax // O)wners tax // OV)erdue tax // S)tatistics // I)nvestigate impact of changes to tax system // Q)uit ");
                String command = in.nextLine().toUpperCase();
                // gets tax paid for a property specified by the management user
                if (command.equals("P")) {
                    System.out.println("Enter the property Eircode you wish to query:");
                    String eircode = in.nextLine().toUpperCase();
                    properties = new ArrayList<String>();
                    properties = csvReader("src/properties.csv");
                    for (int i = 0; i < properties.size(); i++) {
                        String[] property = properties.get(i).split(",");
                        if (eircode.equals(property[2])) {
                            Property p = new Property(property[0], property[1], property[2], property[3],
                                    Double.parseDouble(property[4]), Boolean.parseBoolean(property[5]),
                                    LocalDate.parse(property[6]));
                            System.out.println("All Taxes for this property equal:" + df.format(tC.overallTax(p)));
                            break;
                        }
                    }
                }
                // gets all tax paid by a property Owner specified by the management user
                else if (command.equals("O")) {
                    System.out.println("Enter the name of the Owner you wish to query:");
                    String ownerName = in.nextLine();
                    ownerList = new ArrayList<String>();
                    properties = new ArrayList<String>();
                    ownerList = csvReader("src/systemLogins.csv");
                    properties = csvReader("src/properties.csv");
                    Double[] tax = new Double[256];
                    double taxes = 0;
                    for (int i = 0; i < ownerList.size(); i++) {
                        if (ownerName.equals(ownerList.get(i).split(",")[0])
                                && ownerName.equals(ownerList.get(i).split(",")[0])) {
                            Owner owner = new Owner(ownerName);
                            for (int j = 0; j < properties.size(); j++) {
                                String[] property = properties.get(j).split(",");
                                Property p = new Property(property[0], property[1], property[2], property[3],
                                        Double.valueOf(property[4]), Boolean.parseBoolean(property[5]),
                                        LocalDate.parse(property[6]));
                                owner.addProperty(p);
                                tax[j] = tC.overallTax(p);
                            }
                        }
                    }
                    for (int i = 0; i < tax.length; i++) {
                        if (tax[i] != null) {
                            taxes = taxes + tax[i];
                        } else {
                            break;
                        }
                    }
                    System.out.println("All the taxes for this owner equals:" + df.format(taxes));
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
                    if (response.equals("YES") || response.equals("Y")) {
                        System.out.println("Enter Eircode routing key:");
                        String key = in.nextLine();
                        System.out.println(df.format(tC.overdueTax(year, key)));
                    }
                    // overdue tax just for year
                    else {
                        System.out.println(df.format(tC.overdueTax(year)));
                    }

                }
                // gets statistics based on the tax paid on properties by owners
                else if (command.equals("S")) {
                    boolean M = true;
                    while (M) {
                        System.out.println(
                                "Choose Statistic\nT)otal Tax Paid // A)verage Tax Paid // P)ercentage of Property Taxes Paid // N)umber of Property Taxes paid // B)ack");
                        String com = in.nextLine().toUpperCase();
                        // gets total tax paid for by eircode routing key
                        if (com.equals("T")) {
                            System.out.println("Eircode key for routing:");
                            String eC = in.nextLine().toUpperCase();
                            System.out.println(st.totalTaxPaid(eC));
                        }
                        // gets average tax paid for by eircode routing key
                        else if (com.equals("A")) {
                            System.out.println("Eircode key for routing:");
                            String eC = in.nextLine().toUpperCase();
                            System.out.println(st.averageTaxPaid(eC));
                        }
                        // gets the percentage of property taxes paid for by eircode routing key
                        else if (com.equals("P")) {
                            System.out.println("Eircode key for routing:");
                            String eC = in.nextLine().toUpperCase();
                            System.out.println(st.percentageOfTaxesPaid(eC));
                        }
                        // gets the number of properties paid for by eircode routing key
                        else if (com.equals("N")) {
                            System.out.println("Eircode key for routing:");
                            String eC = in.nextLine().toUpperCase();
                            System.out.println(st.numberOfPayments(eC));
                        }
                        // exits the statistics page of the CLI
                        else if (com.equals("B")) {
                            M = false;
                        }
                    }
                    // investigates the changes in payment values based on the changing the tax
                    // system used-NB.user must check the changes manually according to the default
                    // tax system through the property tax and owner tax functions of the management
                    // CLI
                } else if (command.equals("I")) {
                    double fixedCost, flatPprCharge, annualPenalty;
                    String[] Locations;
                    double[] pValues, locationsCharge, propertyRates;
                    System.out.println("Enter new fixed cost:");
                    fixedCost = in.nextDouble();
                    in.nextLine();
                    System.out.println("Enter new Flat Principal Private Property Charge");
                    flatPprCharge = in.nextDouble();
                    in.nextLine();
                    System.out.println("Enter new annual penalty e.g. 0.08");
                    annualPenalty = in.nextDouble();
                    in.nextLine();
                    System.out.println("Enter List of Locations in this format: City,Town,Village");
                    String temp = in.nextLine();
                    Locations = temp.split(",");
                    System.out.println(
                            "Enter the corresponding charges associated with each location in this format: 100,60,40");
                    temp = in.nextLine();
                    String[] tempArr = temp.split(",");
                    locationsCharge = new double[tempArr.length];
                    for (int i = 0; i < tempArr.length; i++) {
                        locationsCharge[i] = Double.parseDouble(tempArr[i]);
                    }
                    System.out.println("Enter The market value brackets in the format: 0,150000,400000,650000");
                    temp = in.nextLine();
                    tempArr = temp.split(",");
                    pValues = new double[tempArr.length];
                    for (int i = 0; i < tempArr.length; i++) {
                        pValues[i] = Double.parseDouble(tempArr[i]);
                    }
                    System.out.println(
                            "Enter the corresponding property rates to those tax brackets in the format: 0, 0.01, 0.02, 0.04");
                    temp = in.nextLine();
                    tempArr = temp.split(",");
                    propertyRates = new double[tempArr.length];
                    for (int i = 0; i < tempArr.length; i++) {
                        propertyRates[i] = Double.parseDouble(tempArr[i]);
                    }

                    tC = new TaxCalculator(fixedCost, flatPprCharge, annualPenalty, Locations, pValues, locationsCharge,
                            propertyRates);// creates a new taxcalculator for comparing the tax statistics

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

    // reads in data from csv files
    private ArrayList<String> csvReader(String filename) throws IOException {
        Path pathToFile = Paths.get(filename);
        ArrayList<String> attributes = new ArrayList<String>();
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
            String line = br.readLine();
            while (line != null) {
                attributes.add(line);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return attributes;
    }

}
