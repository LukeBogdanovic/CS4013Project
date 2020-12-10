import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private List<String[]> values;

    // returns the total tax paid for an area specified by the eircode key routing
    public double totalTaxPaid(String eC) {
        String path = "tax.csv";
        String line = "";
        Double ttp = 0.0;

        values = new ArrayList<String[]>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                // adds all the values of the CSV to an ArrayList
                values.add(line.split(","));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // converts the ArrayList to a 2d Array so you can access all the values
        // indivdually
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
    public double averageTaxPaid(String eC) {
        String path = "tax.csv";
        String line = "";
        Double ttp = 0.0;
        Double atp = 0.0;

        values = new ArrayList<String[]>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                // adds all the values of the CSV to an ArrayList
                values.add(line.split(","));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // converts the ArrayList to a 2d Array so you can access all the values
        // indivdually
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
    public double percentageOfTaxesPaid(String eC) {
        String path = "tax.csv";
        String line = "";
        Double ttp = 0.0;

        values = new ArrayList<String[]>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                // adds all the values of the CSV to an ArrayList
                values.add(line.split(","));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // converts the ArrayList to a 2d Array so you can access all the values
        // indivdually
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

}
