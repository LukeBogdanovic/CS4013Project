import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class TaxCalculator {
    private double fixedCost, flatPprCharge, annualPenalty;
    private String[] Locations;// array for locations and respective charges
    private double[] pValues, locationsCharge, propertyRates;// array for property values and respective charges

    // default constructor for TaxCalculator class
    public TaxCalculator() {
        this.fixedCost = 100;
        this.flatPprCharge = 100;
        this.annualPenalty = 0.07;
        this.Locations = new String[] { "Countryside", "Village", "Small town", "Large town", "City" };
        this.locationsCharge = new double[] { 25, 50, 60, 80, 100 };
        this.pValues = new double[] { 0, 150000, 400000, 650000 };
        this.propertyRates = new double[] { 0, 0.01, 0.02, 0.04 };
    }

    // variable constructor for TaxCalculator Class
    public TaxCalculator(double fixedCost, double flatPprCharge, double annualPenalty, String[] Locations,
            double[] pValues, double[] locationsCharge, double[] propertyRates) {
        this.fixedCost = fixedCost;
        this.flatPprCharge = flatPprCharge;
        this.annualPenalty = annualPenalty;
        this.Locations = Locations;
        this.pValues = pValues;
        this.locationsCharge = locationsCharge;
        this.propertyRates = propertyRates;
    }

    // checks the outstanding tax of a single property
    public double propertyTax(Property p) {
        if (getPenalty(p)) {

        }
        // this returns the propertyTax without penalties applied if no unpaid years are
        // found
        return fixedCost + p.getEMV() * getMVTRate(p) + getLocationCharge(p)
                + ((p.isPpr() == true) ? flatPprCharge : 0);
    }

    // calculates the outstanding tax of properties owned by an Owner
    public double propertyTax(Owner o) {
        double taxO = 0;
        for (int i = 0; i < o.getProperties().size(); i++) {
            taxO = taxO + propertyTax(o.getProperties().get(i));
        }
        return taxO;
    }

    // gets the market value tax rate of the property supplied
    public double getMVTRate(Property p) {
        double rate = 0;
        for (int i = 0; i < pValues.length; i++) {
            if (p.getEMV() <= pValues[i]) {
                rate = propertyRates[i];
                break;
            } else {
                rate = propertyRates[i];
            }
        }
        return rate;
    }

    // gets the location tax charge associated with the given location
    public double getLocationCharge(Property p) {
        double value = 0;
        for (int i = 0; i < Locations.length; i++) {
            if (p.getLocation().equalsIgnoreCase(Locations[i])) {
                value = locationsCharge[i];
                break;
            }
        }
        return value;
    }

    // gets the annual penalty rate for properties that have not had tax paid on
    // them for different years
    public boolean getPenalty(Property p) {
        boolean penalty = false;
        if(){
            penalty = true;
        }
        return penalty;
    }

    // returns a balancing statement for a particular Owner
    public String balancingStatement(Owner o) {
        return "This owner accumulated " + propertyTax(o) + " in taxes on their properties this year";
    }

    /*
     * returns a balancing statement for a particular Owner on a particular year
     * public String balancingStatement(Owner o, LocalDate year) { return
     * "This owner owes "+ }
     */

    // returns a balancing statement for a particular property for
    public String balancingStatement(Property p) {
        return "This property has " + propertyTax(p) + " due in taxes this year";
    }

    // checks how many times the date has passed jan 1st since the registration of
    // property
    private int dateCheck(Property p) {
        int count = 0;
        count = (int) java.time.temporal.ChronoUnit.YEARS.between(p.getDate(), LocalDate.now());
        return count;
    }

    // compounds tax years together
    private double compoundTax(Property p, double yearPrevious) {
        return (yearPrevious * (1 + annualPenalty)) + propertyTax(p);
    }

    // reads in data from a csv file
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
}
