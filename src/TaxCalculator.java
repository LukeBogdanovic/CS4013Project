import java.util.Date;
import java.time.LocalDate;
import java.io.*;

public class TaxCalculator {
    private double fixedCost, flatPprCharge, annualPenalty;
    private String path = "tax.csv";
    private String[] Locations;// array for locations and respective charges
    private double[] pValues, locationsCharge, propertyRates;// array for property values and respective charges

    public TaxCalculator() {
        this.fixedCost = 100;
        this.flatPprCharge = 100;
        this.annualPenalty = 0.07;
        this.Locations = new String[] { "Countryside", "Village", "Small town", "Large town", "City" };
        this.locationsCharge = new double[] { 25, 50, 60, 80, 100 };
        this.pValues = new double[] { 0, 150000, 400000, 650000 };
        this.propertyRates = new double[] { 0, 0.01, 0.02, 0.04 };
    }

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
        return fixedCost + p.getEMV() * getMVTRate(p) + getLocationCharge(p)
                + ((p.isPpr() == true) ? flatPprCharge : 0);
    }

    // calculates the outstanding tax of properties owned by an Owner
    public double propertyTax(Owner o) {
        double taxO = 0;
        for (Property ob : o.getProperties()) {
            taxO = taxO + fixedCost;
            // taxO = taxO + getLocationCharge(p);
            // taxO = taxO + getMVTRate(p);
            taxO = taxO + flatPprCharge;
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
    public double getPenalty(Property p, double d) {
        LocalDate now = LocalDate.now();
        
        double penalty = 0;
        for(int i = 0; i < )
        return penalty;
    }

    public void writeCSV() {
        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.print("\n" + year + "," + owner + "," + property + "," + value + "," + eircode);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.println("bruh u r spaz innit");
        }

    }
}
