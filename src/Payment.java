import java.time.LocalDate;

public class Payment {
    private double amount;
    private LocalDate date;
    private Owner Owner;
    private Property property;
    private boolean paid;

    // creates a payment object that is then stored in payments.csv
    public Payment(double amount, LocalDate date, Owner owner, Property property, boolean paid) {
        this.amount = amount;
        this.date = date;
        this.Owner = owner;
        this.property = property;
        this.paid = true;
    }

    // pays tax for the property specified
    public String payTax(Property p, double payment, double toBePaid) {
        return payment + " paid on property " + p;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Owner getOwner() {
        return Owner;
    }

    @Override
    public String toString() {
        return "Amount Paid:" + amount + " Date Paid:" + date + "Property Paid For:" + property.toString();
    }

    public Property getProperty() {
        return property;
    }

}
