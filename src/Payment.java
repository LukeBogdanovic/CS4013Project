import java.time.LocalDate;

public class Payment {
    private double amount;
    private LocalDate date;
    private Owner Owner;
    private Property property;

    // creates a payment object that is then stored in payments.csv
    public Payment(double amount, LocalDate date, Owner owner, Property property) {
        this.amount = amount;
        this.date = date;
        this.Owner = owner;
        this.property = property;
    }

    /**
     * @param p
     * @param payment
     * @param toBePaid
     * @return String
     */
    // pays tax for the property specified
    public String payTax(Property p, double payment, double toBePaid) {
        return payment + " paid on property " + p;
    }

    /**
     * @return double
     */
    // getter for the amount paid
    public double getAmount() {
        return amount;
    }

    /**
     * @return LocalDate
     */
    // getter for the date paid
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return Owner
     */
    // getter for the owner who has paid
    public Owner getOwner() {
        return Owner;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "Amount Paid:" + amount + " Date Paid:" + date + "Property Paid For:" + property.toString();
    }

    /**
     * @return Property
     */
    // getter for the property being paid for
    public Property getProperty() {
        return property;
    }

}
