import java.util.ArrayList;

public class OwnerDB {
    private ArrayList<Owner> ownerList;

    public OwnerDB() {
        ownerList = new ArrayList<Owner>();
    }

    public void addOwner(Owner c) {
        ownerList.add(c);
    }

    public ArrayList<Property> getProperties(Owner O) {
        return O.getProperties();
    }

    public ArrayList<Owner> getOwners() {
        return ownerList;
    }

}
