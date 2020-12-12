import java.util.ArrayList;

public class Owner {
    private String name;// name of the owner of the properties
    private ArrayList<Property> properties;// arraylist of properties owned by an owner
    private ArrayList<Property> paidProperties;// arraylist of properties tax has been paid for

    // creates an owner object using the name of the owner
    public Owner(String n) {
        this.name = n;
        properties = new ArrayList<Property>();
        paidProperties = new ArrayList<Property>();
    }

    // adds a property to the properties arraylist
    public void addProperty(Property p) {
        properties.add(p);
    }

    // adds the property to the paid properties
    public void addPaidProperty(Property p) {
        paidProperties.add(p);
    }

    // removes the property from the unpaid properties
    public void removePaidProperty(Property p) {
        paidProperties.remove(p);
    }

    // returns the name of an owner
    public String getName() {
        return name;
    }

    // returns the arraylist of unpaid properties
    public ArrayList<Property> getProperties() {
        return properties;
    }

    // returns the arraylist of paid properties
    public ArrayList<Property> getPaidProperties() {
        return paidProperties;
    }

}
