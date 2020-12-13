import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private List<String[]> values;
    private List<String> compare1;
    private List<String> compare2;
    private List<String> hold;
    private BufferedReader br;

    /**
     * @param eC
     * @return double
     */
    // returns the total tax paid for an area specified by the eircode key routing
    public double totalTaxPaid(String eC) {
        Double ttp = 0.0;
        String path = "src/payments.csv";
        // converts the ArrayList to a 2d Array so you can access all the values
        // indivdually
        values = getValues(path);
        String[][] arr = new String[values.size()][0];
        values.toArray(arr);
        for (int i = 0; i < arr.length; i++) {
            String[] cmp = arr[i][3].split(" ");
            if (cmp[0].equalsIgnoreCase(eC)) {
                ttp = ttp + Double.parseDouble(arr[i][5]);
            }
        }
        return ttp;
    }

    /**
     * @param eC
     * @return double
     */
    // returns the average tax paid for an area specified by the eircode key routing
    public double averageTaxPaid(String eC) {
        String path = "src/payments.csv";
        hold = new ArrayList<String>();
        values = getValues(path);
        String[][] arr = new String[values.size()][0];
        values.toArray(arr);
        for (int i = 0; i < arr.length; i++) {
            String[] cmp = arr[i][3].split(" ");
            if (cmp[0].equalsIgnoreCase(eC)) {
                String temp = arr[i][5];
                hold.add(temp);
            }
        }
        double ttp = totalTaxPaid(eC);
        double atp = ttp / hold.size();
        return atp;
    }

    /**
     * @param eC
     * @return float
     */
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

    /**
     * @param eC
     * @return int
     */
    // Returns how many payments have been made for an area specified by the eircode
    // key routing
    public int numberOfPayments(String eC) {
        String path = "src/payments.csv";
        hold = new ArrayList<String>();
        values = getValues(path);
        String[][] arr = new String[values.size()][0];
        values.toArray(arr);
        for (int i = 0; i < arr.length; i++) {
            String[] cmp = arr[i][3].split(" ");
            if (cmp[0].equalsIgnoreCase(eC)) {
                hold.add(arr[i][3]);
            }
        }
        int num = hold.size();
        return num;
    }

    /**
     * @param filename
     * @return List<String[]>
     */
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
