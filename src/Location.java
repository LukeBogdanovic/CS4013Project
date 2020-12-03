public class Location {
    private String location;
    private String[][] locations = { { "City", "100" }, { "Large Town", "80" }, { "Small Town", "60" },
            { "Village", "50" }, { "countryside", "25" } };

    public Location() {

    }

    public Location(String location) {
        this.location = location;
    }

    public double getCharge() {
        double charge = 0.0;
        for (int i = 0; i < locations.length; i++) {
            if (locations[i][0].equalsIgnoreCase(location)) {
                charge = Double.parseDouble(locations[i][1]);
                break;
            }
        }
        return charge;
    }

    public String toString() {
        return location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[][] getLocations() {
        return locations;
    }

    public void setLocations(String[][] locations) {
        this.locations = locations;
    }

}
