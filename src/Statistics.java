import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private List<String[]> values;
    private BufferedReader br;

    // returns the total tax paid for an area specified by the eircode key routing
    public double totalTaxPaid(String eC, String filename) {
        Double ttp = 0.0;
        // converts the ArrayList to a 2d Array so you can access all the values
        // indivdually
        values = getValues(filename);
        String[][] arr = new String[values.size()][0];
        values.toArray(arr);
        String[] three = eC.split(" ");
        for (int i = 0; i < arr.length; i++) {
            String[] cmp = arr[i][2].split(" ");
            if (cmp[0].equalsIgnoreCase(three[0])) {
                ttp = ttp + Double.parseDouble(arr[i][4]);
            }
        }
        return ttp;
    }

    // returns the average tax paid for an area specified by the eircode key routing
    public double averageTaxPaid(String eC, String filename) {
        Double ttp = 0.0;
        Double atp = 0.0;
        // converts the ArrayList to a 2d Array so you can access all the values
        // indivdually
        values = getValues(filename);
        String[][] arr = new String[values.size()][0];
        values.toArray(arr);
        String[] three = eC.split(" ");
        for (int i = 0; i < arr.length; i++) {
            String[] cmp = arr[i][2].split(" ");
            if (cmp[0].equalsIgnoreCase(three[0])) {
                ttp = ttp + Double.parseDouble(arr[i][4]);
            }
        }
        atp = ttp / arr.length;
        return atp;
    }

    // returns the percentage of Taxes paid for an area specified by the eircode key
    // routing
    public double percentageOfTaxesPaid(String eC, String filename) {
        Double ttp = 0.0;
        // converts the ArrayList to a 2d Array so you can access all the values
        // indivdually
        values = getValues(filename);
        String[][] arr = new String[values.size()][0];
        values.toArray(arr);
        String[] three = eC.split(" ");
        for (int i = 0; i < arr.length; i++) {
            String[] cmp = arr[i][2].split(" ");
            if (cmp[0].equalsIgnoreCase(three[0])) {
                ttp = ttp + Double.parseDouble(arr[i][4]);
            }
        }
        // we need a method probably in payments which calculate unpaid taxes. used here
        // with ttp to calc percentage ofTaxes Paid
        return ttp;// temporary
    }

    // returns a List of String[] for use by the other methods of this file
    private List<String[]> getValues(String filename) {
        String line = "";
        values = new ArrayList<String[]>();
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                values.add(line.split(","));// adds all the values of the CSV to an ArrayList
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

}
