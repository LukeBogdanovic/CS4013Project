public class TaxCalculator {
    private double fixedCost;
    private double flatPprCharge;
    private double annualPenalty;

    public TaxCalculator() { // taxCalculator takes a property object and calculates the tax per annum on
                             // that property
        this.fixedCost = 100;
        this.flatPprCharge = 100;
        this.annualPenalty = .07;
    }

    public TaxCalculator(double fC, double fPC, double aP) {
        this.fixedCost = fC;
        this.flatPprCharge = fPC;
        this.annualPenalty = aP;
    }

    public void setFixedCost(double f) {
        this.fixedCost = f;
    }

    public void setFlatPprCharge(double p) {
        this.flatPprCharge = p;
    }

    public void setAnnualPenalty(double a) {
        this.annualPenalty = a;
    }

    public double getCharge() {
        double charge = 0;
        return charge;
    }

}
