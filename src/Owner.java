import java.util.ArrayList;

public class Owner {
    private String name;
    private ArrayList<Property> properties;

    public Owner(String n) {
        this.name = n;
    }

    public void addProperty(Property p) {
        properties.add(p);
    }

    public String toString() {
        return name;
    }

}
