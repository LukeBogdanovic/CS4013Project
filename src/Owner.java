import java.util.ArrayList;

public class Owner {
    private String name;// name of the owner of the properties
    private ArrayList<Property> properties;// arraylist of properties owned by an owner

    // creates an owner object using the name of the owner
    public Owner(String n) {
        this.name = n;
        properties = new ArrayList<Property>();
    }

    // adds a property to the properties arraylist
    public void addProperty(Property p) {
        properties.add(p);
    }

    // returns the name of an owner
    public String getName() {
        return name;
    }

    // returns the arraylist of properties
    public ArrayList<Property> getProperties() {
        return properties;
    }

}
