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

    // pays tax for the property specified
    public String payTax(Property p, double payment, double toBePaid) {
        return payment + " paid on property " + p;
    }

    public int isPaid(Owner o) {
        int count = 0;
        for (int i = 0; i < o.getProperties().size(); i++) {

        }
        return count;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Owner getOwner() {
        return Owner;
    }

    public void setOwner(Owner owner) {
        Owner = owner;
    }

    @Override
    public String toString() {
        return "Amount Paid:" + amount + " Date Paid:" + date + "Property Paid For:" + property.toString();
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

}
