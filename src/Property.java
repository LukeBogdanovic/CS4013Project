import java.time.LocalDate;

public class Property {
    private String owner, address, eircode, location;// eircode,owner,address,location of the property
    private double EMV;// Estimated market value variable
    private boolean Ppr;// Principal private residence variable
    private LocalDate date;// year of the resgistry of the property

    public Property(String owner, String address, String eircode, String location, double EMV, boolean Ppr,
            LocalDate date) {
        this.owner = owner;// property can have more than one owner
        this.address = address;
        this.eircode = eircode;
        this.EMV = EMV;
        this.location = location;
        this.Ppr = Ppr;
        this.date = date;
    }

    // adds a property to the property arraylist in Owner
    public void addProperty(Owner o, Property p) {
        o.addProperty(p);
    }

    @Override
    public String toString() {
        return owner + " " + address + " " + eircode;
    }

    // getter for the eircode
    public String getEircode() {
        return eircode;
    }

    // getter for the estimated market value
    public double getEMV() {
        return EMV;
    }

    // getter for the address
    public String getAddress() {
        return address;
    }

    // getter for the location of the property
    public String getLocation() {
        return location;
    }

    public boolean isPpr() {
        return Ppr;
    }

    public LocalDate getDate() {
        return date;
    }

}