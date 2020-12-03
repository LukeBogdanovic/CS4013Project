public class Property {
    private String owner;
    private String address;
    private String eircode;
    private double EMV;
    private String location;
    private String Ppr;

    public Property(String l) {
        String[] arrOfStr = l.split(" ");
        this.owner = arrOfStr[1];// property can have more than one owner -needs to be changed to suit this
        this.address = arrOfStr[2];
        this.eircode = arrOfStr[3];
        this.EMV = Double.parseDouble(arrOfStr[4]);
        this.location = arrOfStr[5];
        this.Ppr = arrOfStr[6];
    }

    public void addProperty(Owner o, Property p) {
        o.addProperty(p);
    }

    public String toString() {
        return owner + address + eircode + EMV + location + Ppr;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEircode() {
        return eircode;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    public double getEMV() {
        return EMV;
    }

    public void setEMV(double eMV) {
        EMV = eMV;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String isPpr() {
        return Ppr;
    }

    public void setPpr(String ppr) {
        Ppr = ppr;
    }

}
