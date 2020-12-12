import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private List<String[]> values;
    private List<String> compare1;
    private List<String> compare2;
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
        Double ttp = 0.0, atp = 0.0;
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
    public float percentageOfTaxesPaid(String eC) {
        float pot = 0;
        String path1 = "src/properties.csv";
        String path2 = "src/payments.csv";
        compare1 = new ArrayList<String>();
        compare2 = new ArrayList<String>();

        // converts the ArrayList to a 2d Array so you can access all the values
        // indivdually
        values = getValues(path1);
        String[][] arrayProperties = new String[values.size()][0];
        values.toArray(arrayProperties);
        for (int i = 0; i < arrayProperties.length; i++) {
            String[] cmp = arrayProperties[i][2].split(" ");
            if (cmp[0].equalsIgnoreCase(eC)) {
                String temp = arrayProperties[i][2];
                compare1.add(temp);
            }
        }
        values = getValues(path2);
        String[][] arrayPayments = new String[values.size()][0];
        values.toArray(arrayPayments);

        for (int i = 0; i < arrayPayments.length; i++) {
            String temp2 = arrayPayments[i][3];
            compare2.add(temp2);
        }
        compare2.retainAll(compare1);
        pot = (compare2.size() * 100) / compare1.size();
        return pot;
    }

    // returns a List of String[] for use by the other methods of this class
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
