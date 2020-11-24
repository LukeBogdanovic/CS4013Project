public class Property {
    private Owner owner;
    private String address;
    private String eircode;
    private int EMV;
    private String location;
    private boolean Ppr;

    public Property(Owner o, String a, String e, int E, String l, boolean P) {
        this.owner = o;
        this.address = a;
        this.eircode = e;
        this.EMV = E;
        this.location = l;
        this.Ppr = P;
    }

    public String toString() {
        return owner + address + eircode + EMV + location + Ppr;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
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

    public int getEMV() {
        return EMV;
    }

    public void setEMV(int eMV) {
        EMV = eMV;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isPpr() {
        return Ppr;
    }

    public void setPpr(boolean ppr) {
        Ppr = ppr;
    }

}
