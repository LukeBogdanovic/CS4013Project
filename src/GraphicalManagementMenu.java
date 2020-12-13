import java.time.LocalDate;
import java.util.ArrayList;
import java.io.*;
import java.text.DecimalFormat;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class GraphicalManagementMenu extends Application {
    private DecimalFormat df = new DecimalFormat("0.00");
    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter pw;
    private TaxCalculator tC;
    private Owner owner;
    private LocalDate date;
    private Statistics st;
    private ArrayList<String> ownerList, properties;
    Stage window;
    TreeView<String> tree1, tree2;
    String output;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Tax calculator");
        GridPane startUp = new GridPane();
        startUp.setPadding(new Insets(10, 10, 10, 10));
        startUp.setVgap(8);
        startUp.setHgap(3);

        Label startupLabel = new Label("Please choose whether you wish to login as a Property Owner or a Manager");
        GridPane.setConstraints(startupLabel, 0, 0);

        Button ownerLogin = new Button("Property Owner");
        GridPane.setConstraints(ownerLogin, 0, 1);

        Button managerLogin = new Button("Manager");
        GridPane.setConstraints(managerLogin, 0, 2);

        startUp.getChildren().addAll(startupLabel, ownerLogin, managerLogin);
        Scene scene1 = new Scene(startUp, 435, 200);

        // Scene 3

        // name label
        Label nameLabel = new Label("Username");
        GridPane.setConstraints(nameLabel, 0, 0);

        // name input
        TextField nameInput = new TextField();
        nameInput.setPromptText("username");
        GridPane.setConstraints(nameInput, 1, 0);

        // password label
        Label passLabel = new Label("Password");
        GridPane.setConstraints(passLabel, 0, 1);

        // password input
        PasswordField passInput = new PasswordField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 1, 1);

        CheckBox newUser = new CheckBox("New User");
        GridPane.setConstraints(newUser, 1, 2);

        Button loginButton = new Button("Log In");
        GridPane.setConstraints(loginButton, 2, 2);

        GridPane scene3Layout = new GridPane();
        scene3Layout.setPadding(new Insets(10, 10, 10, 10));
        scene3Layout.setVgap(8);
        scene3Layout.setHgap(10);

        scene3Layout.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton, newUser);
        Scene scene3 = new Scene(scene3Layout, 300, 200);

        ownerLogin.setOnAction(e -> window.setScene(scene3));

        // Scene 2

        Label nameLabel2 = new Label("Username");
        GridPane.setConstraints(nameLabel2, 0, 0);

        TextField nameInput2 = new TextField("Manager");
        GridPane.setConstraints(nameInput2, 1, 0);

        // password label
        Label passLabel2 = new Label("Password");
        GridPane.setConstraints(passLabel2, 0, 1);

        // password input
        PasswordField passInput2 = new PasswordField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput2, 1, 1);

        Button loginButton2 = new Button("Log In");
        GridPane.setConstraints(loginButton2, 2, 2);

        GridPane scene2Layout = new GridPane();
        scene2Layout.setPadding(new Insets(10, 10, 10, 10));
        scene2Layout.setVgap(8);
        scene2Layout.setHgap(10);

        scene2Layout.getChildren().addAll(nameLabel2, nameInput2, passLabel2, passInput2, loginButton2);
        Scene scene2 = new Scene(scene2Layout, 300, 200);

        managerLogin.setOnAction(e -> window.setScene(scene2));

        window.setScene(scene1);
        window.show();

        // scene4-Owner Menu
        TreeItem<String> ownerMenu, listProperties, balancingStatments;

        // root
        ownerMenu = new TreeItem<>();
        ownerMenu.setExpanded(true);

        // PayTax
        makeBranch("Pay Tax", ownerMenu);

        // ListProperties
        listProperties = makeBranch("List Properties", ownerMenu);
        makeBranch("List Paid Properties", listProperties);
        makeBranch("List Unpaid properties", listProperties);

        // BalancingStatments
        balancingStatments = makeBranch("Balancing Statements", ownerMenu);
        makeBranch("Property Eircode", balancingStatments);
        makeBranch("All Properties", balancingStatments);

        // RegisterProperties
        makeBranch("Register Properties", ownerMenu);

        // Quit
        makeBranch("Quit", ownerMenu);

        // create tree
        tree1 = new TreeView<>(ownerMenu);
        tree1.setShowRoot(false);
        tree1.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {

            }
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(tree1);
        Scene scene4 = new Scene(layout, 300, 250);

        loginButton.setOnAction(e -> {
            try {
                if (userLogin(nameInput.getText(), passInput.getText(), newUser)) {
                    System.out.println("login success");
                    window.setScene(scene4);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // scene 5 - Management Menu
        TreeItem<String> managementMenu, overdueTax, statistics;

        // root
        managementMenu = new TreeItem<>();
        managementMenu.setExpanded(true);

        // propertyTax
        makeBranch("Property Tax", managementMenu);

        // ownersTax
        makeBranch("Owners Tax", managementMenu);

        // overdueTax
        overdueTax = makeBranch("Overdue Tax", managementMenu);
        makeBranch("Query an Area and Year", overdueTax);
        makeBranch("Query a year", overdueTax);

        // statistics
        statistics = makeBranch("Statistics", managementMenu);
        makeBranch("Total Tax Paid", statistics);
        makeBranch("Average Tax Paid", statistics);
        makeBranch("Percentage of Taxes Paid", statistics);
        makeBranch("Number of Taxes Paid", statistics);

        // investigate
        makeBranch("Investigate the impact of changes on tax system", managementMenu);

        // quit
        makeBranch("Quit", managementMenu);

        // create tree
        tree2 = new TreeView<>(managementMenu);
        tree2.setShowRoot(false);
        tree2.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue);
                if (newValue.getValue().equalsIgnoreCase("quit")) {
                    InterfaceSelect iSelect = new InterfaceSelect();

                } else if (newValue.getValue().equalsIgnoreCase("Investigate the impact of changes on tax system")) {

                } else if (newValue.getValue().equalsIgnoreCase("Number of Taxes Paid")) {

                } else if (newValue.getValue().equalsIgnoreCase("Percentage of Taxes Paid")) {

                } else if (newValue.getValue().equalsIgnoreCase("Average Tax Paid")) {

                } else if (newValue.getValue().equalsIgnoreCase("Total Tax Paid")) {

                } else if (newValue.getValue().equalsIgnoreCase("Query a year")) {

                } else if (newValue.getValue().equalsIgnoreCase("Query an Area and Year")) {

                } else if (newValue.getValue().equalsIgnoreCase("Owners Tax")) {

                } else if (newValue.getValue().equalsIgnoreCase("Property Tax")) {
                    Label propertyLabel = new Label("Enter the eircode of the property");
                    GridPane.setConstraints(propertyLabel, 0, 0);

                    TextField eirInput = new TextField();
                    eirInput.setPromptText("Eircode");
                    GridPane.setConstraints(eirInput, 1, 0);
                    System.out.println(eirInput.getText());

                    GridPane scene19Layout = new GridPane();
                    scene19Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene19Layout.setVgap(8);
                    scene19Layout.setHgap(10);

                    Button eirEnter = new Button("Enter");
                    GridPane.setConstraints(eirEnter, 1, 1);
                    scene19Layout.getChildren().addAll(propertyLabel, eirInput, eirEnter);
                    Scene scene19 = new Scene(scene19Layout, 300, 200);
                    window.setScene(scene19);

                    GridPane scene20Layout = new GridPane();
                    scene20Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene20Layout.setVgap(8);
                    scene20Layout.setHgap(10);

                    Label propertyInfo = new Label(output);
                    GridPane.setConstraints(propertyInfo, 0, 0);

                    scene20Layout.getChildren().addAll(propertyInfo);
                    Scene scene20 = new Scene(scene20Layout, 300, 200);
                    eirEnter.setOnAction(e -> {
                        try {
                            output = propertyTax(eirInput.getText());
                            window.setScene(scene20);
                            System.out.println(eirInput.getText());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    });
                }
            }
        }

        );

        StackPane layout1 = new StackPane();
        layout1.getChildren().add(tree2);
        Scene scene5 = new Scene(layout1, 400, 300);

        loginButton2.setOnAction(e -> {
            if (nameInput2.getText().equalsIgnoreCase("MANAGER") && passInput2.getText().equalsIgnoreCase("password")) {
                System.out.println("login success");
                window.setScene(scene5);
            }
        });

        // scene 6 Owner Menu-property tax

        // scene 7 Owner Menu-List Properties

        // scene 8 Owner Menu-Balancing Statements

        // scene 9 Owner Menu-Register Property

        // scene 10 Owner Menu-Quit

    }

    // payment function for owners
    private String payment(String text, Property p) throws IOException {
        String[] textArray = text.split(" ");
        Payment pay = new Payment(Double.parseDouble(textArray[0]), LocalDate.now(), owner, p);
        writeToPayments("src/payments.csv", pay);
        double toPay = Double.parseDouble(textArray[0]);
        String paid = pay.payTax(p, toPay, tC.overallTax(p));
        return "Paid:" + paid + " For Property:" + p;
    }

    // registers property for owner menu
    private void registerProperty(String property, String name) throws IOException {
        String[] propArray = property.split(",");
        Property p = new Property(propArray[0], propArray[1], propArray[2], propArray[3],
                Double.parseDouble(propArray[4]), Boolean.parseBoolean(propArray[5]), LocalDate.now());
        writeToProperties("src/properties.csv", p, name);
    }

    // gets the propertyTax for managament menu and owner menu
    private String propertyTax(String eircode) throws IOException {
        properties = new ArrayList<String>();
        properties = csvReader("src/properties.csv");
        for (int i = 0; i < properties.size(); i++) {
            String[] property = properties.get(i).split(",");
            System.out.println(eircode);
            if (eircode.equalsIgnoreCase(property[2])) {
                Property p = new Property(property[0], property[1], property[2], property[3],
                        Double.parseDouble(property[4]), Boolean.parseBoolean(property[5]),
                        LocalDate.parse(property[6]));
                return "All Taxes for this property equal:" + df.format(tC.overallTax(p));
            }
        }
        return "NOpe";
    }

    // gets ownerTax for management menu
    private String ownersTax(String ownerName) throws IOException {
        ownerList = new ArrayList<String>();
        properties = new ArrayList<String>();
        ownerList = csvReader("src/systemLogins.csv");
        properties = csvReader("src/properties.csv");
        Double[] tax = new Double[256];
        double taxes = 0;
        for (int i = 0; i < ownerList.size(); i++) {
            if (ownerName.equals(ownerList.get(i).split(",")[0]) && ownerName.equals(ownerList.get(i).split(",")[0])) {
                Owner owner = new Owner(ownerName);
                for (int j = 0; j < properties.size(); j++) {
                    String[] property = properties.get(j).split(",");
                    Property p = new Property(property[0], property[1], property[2], property[3],
                            Double.valueOf(property[4]), Boolean.parseBoolean(property[5]),
                            LocalDate.parse(property[6]));
                    owner.addProperty(p);
                    tax[j] = tC.overallTax(p);
                }
            }
        }
        for (int i = 0; i < tax.length; i++) {
            if (tax[i] != null) {
                taxes = taxes + tax[i];
            } else {
                break;
            }
        }
        return "All the taxes for this owner equals:" + df.format(taxes);
    }

    // gets the overdue tax for a year
    private String overdueTax(String y) throws IOException {
        LocalDate year = LocalDate.parse(y);
        return (df.format(tC.overdueTax(year)));
    }

    // gets the overdue tax for an area and year
    private String overdueTax(String y, String eC) throws IOException {
        LocalDate year = LocalDate.parse(y);
        String key = eC;
        return (df.format(tC.overdueTax(year, key)));
    }

    // login for users and checks if the user is a newuser
    private boolean userLogin(String username, String password, CheckBox newuser) throws IOException {
        owner = new Owner(username);
        if (newuser.isSelected()) {
            fw = new FileWriter("src/systemLogins.csv", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            pw.print("\n" + username + "," + password);
            pw.flush();
            pw.close();
            return true;
        } else {
            ArrayList<String> systemLogins = new ArrayList<String>();
            String[] login = new String[2], propArray = new String[7];
            systemLogins = csvReader("src/systemLogins.csv");
            ArrayList<String> properties = new ArrayList<String>();
            properties = csvReader("src/properties.csv");
            int i = 0, k = 0;
            for (int j = 0; j < systemLogins.size(); j++) {
                for (int h = 0; h < properties.size() + 1; h++) {
                    while (i < systemLogins.size()) {
                        login = systemLogins.get(i).split(",");
                        break;
                    }
                    if (login[0].equals(username) && login[1].equals(password)) {
                        while (i < systemLogins.size()) {
                            while (k < properties.size()) {
                                propArray = properties.get(k).split(",");
                                if (propArray[0].equals(username.toString())) {
                                    Property p = new Property(username.toString(), propArray[1], propArray[2],
                                            propArray[3], Double.parseDouble(propArray[4]),
                                            Boolean.parseBoolean(propArray[5]), LocalDate.parse(propArray[6]));
                                    owner.addProperty(p);
                                }
                                break;
                            }
                            if (k == properties.size()) {
                                return true;
                            }
                            break;
                        }
                    }
                    k++;
                }
                i++;
            }
            return false;
        }
    }

    // writes the payments to payments.csv file
    private void writeToPayments(String filename, Payment pay) {
        try {
            String path = filename;
            fw = new FileWriter(path, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            pw.print("\n" + pay.getOwner().getName() + "," + pay.getProperty().getAddress() + ","
                    + pay.getProperty().getLocation() + "," + pay.getProperty().getEircode() + "," + pay.getDate() + ","
                    + pay.getAmount());
            pw.flush();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // writes the property to the properties.csv file
    private void writeToProperties(String filename, Property p, String name) throws IOException {
        try {
            String path = filename;
            fw = new FileWriter(path, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            pw.print("\n" + name + "," + p.getAddress() + "," + p.getEircode() + "," + p.getLocation() + "," + p.isPpr()
                    + "," + p.getDate());
            pw.flush();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // reads the csv files
    private ArrayList<String> csvReader(String filename) throws IOException {
        Path pathToFile = Paths.get(filename);
        ArrayList<String> attributes = new ArrayList<String>();
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
            String line = br.readLine();
            while (line != null) {
                attributes.add(line);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return attributes;
    }

    // produces the Tree for the GUI
    public static TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }
}
