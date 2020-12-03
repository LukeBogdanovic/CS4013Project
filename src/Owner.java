import java.util.ArrayList;

public class Owner extends OwnerDB {
    private String name;
    ArrayList<Property> properties;

    public Owner() {
    }

    public Owner(String n) {
        this.name = n;
        this.properties = new ArrayList<Property>();
    }

    public void addProperty(Property p) {
        properties.add(p);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }

}
