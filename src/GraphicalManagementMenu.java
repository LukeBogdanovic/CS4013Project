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
    private TaxCalculator tC = new TaxCalculator();
    private Owner owner;
    private LocalDate date;
    private Statistics st = new Statistics();
    private ArrayList<String> ownerList, properties;
    Stage window;
    TreeView<String> tree1, tree2;
    String output, ownerName, eC, year, temp;
    float x;
    double y;
    int k;
    double fixedCost, flatPprCharge, annualPenalty;
    String[] Locations, tempArr, tempArr1, tempArr2;
    double[] pValues, locationsCharge, propertyRates;
    static boolean answer;
    boolean result;

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Tax calculator");

        // Scene 1
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

        // Scene 2

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

        GridPane scene2Layout = new GridPane();
        scene2Layout.setPadding(new Insets(10, 10, 10, 10));
        scene2Layout.setVgap(8);
        scene2Layout.setHgap(10);

        scene2Layout.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton, newUser);
        Scene scene2 = new Scene(scene2Layout, 450, 300);

        ownerLogin.setOnAction(e -> window.setScene(scene2));

        // Scene 3

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

        GridPane scene3Layout = new GridPane();
        scene3Layout.setPadding(new Insets(10, 10, 10, 10));
        scene3Layout.setVgap(8);
        scene3Layout.setHgap(10);

        scene3Layout.getChildren().addAll(nameLabel2, nameInput2, passLabel2, passInput2, loginButton2);
        Scene scene3 = new Scene(scene3Layout, 450, 300);

        managerLogin.setOnAction(e -> window.setScene(scene3));

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
        StackPane layout = new StackPane();
        layout.getChildren().add(tree1);
        Scene scene4 = new Scene(layout, 300, 250);
        tree1.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getValue().equalsIgnoreCase("quit")) {
                    result = displayConfirmBox("Quiting to interface select?",
                            "Are you sure you wish to return tointerface select?");

                    if (result == true) {
                        InterfaceSelect iSelect = new InterfaceSelect();
                        window.close();
                        try {
                            iSelect.run();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (result == false) {
                        window.setScene(scene4);
                    }

                }
                // scene 5 Owner Menu-property tax
                else if (newValue.getValue().equalsIgnoreCase("Pay Tax")) {
                    Label paymentLabel = new Label("Enter the Property you want to pay for:");
                    GridPane.setConstraints(paymentLabel, 0, 0);

                    TextField paymentInput = new TextField();
                    paymentInput.setPromptText("0.0");
                    GridPane.setConstraints(paymentInput, 1, 0);

                    Button paymentEnter = new Button("Enter");
                    GridPane.setConstraints(paymentEnter, 1, 7);

                }
                // scene 6
                else if (newValue.getValue().equalsIgnoreCase("List Paid Properties")) {
                    try {
                        getPaidProperties(owner.getName());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                // scene 7
                else if (newValue.getValue().equalsIgnoreCase("List Unpaid properties")) {
                    try {
                        getUnPaidProperties(owner.getName());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                // scene 8
                else if (newValue.getValue().equalsIgnoreCase("Property Eircode")) {
                    Label EircodeLabel = new Label("Enter the Property Eircode for query:");
                    GridPane.setConstraints(EircodeLabel, 0, 0);

                    TextField EircodeInput = new TextField();
                    EircodeInput.setPromptText("");
                    GridPane.setConstraints(EircodeInput, 1, 0);
                }
                // scene 9
                else if (newValue.getValue().equalsIgnoreCase("All Properties")) {
                    Label AllLabel = new Label("Enter the year for query:");
                    GridPane.setConstraints(AllLabel, 0, 0);

                    TextField AllInput = new TextField();
                    AllInput.setPromptText("yyyy-mm-dd");
                    GridPane.setConstraints(AllInput, 1, 0);
                }
                // scene 10
                else if (newValue.getValue().equalsIgnoreCase("Register Properties")) {
                    Label RegisterLabel = new Label("Enter the Property you want to register:");
                    GridPane.setConstraints(RegisterLabel, 0, 0);

                    TextField property = new TextField();
                    property.setPromptText(
                            "owner,address,Eircode,Location,market value,primary property,date(yyyy-mm-dd)");
                    GridPane.setConstraints(property, 1, 0);

                    Button propertyEntry = new Button("Enter");
                    GridPane.setConstraints(propertyEntry, 1, 1);

                    Button exit = new Button("Back To Menu");
                    GridPane.setConstraints(exit, 2, 1);

                    GridPane scene10Layout = new GridPane();
                    scene10Layout.getChildren().addAll(RegisterLabel, property, propertyEntry, exit);
                    Scene scene10 = new Scene(scene10Layout, 450, 300);
                    window.setScene(scene10);

                    scene10Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene10Layout.setVgap(8);
                    scene10Layout.setHgap(10);

                    propertyEntry.setOnAction(e -> {
                        try {
                            registerProperty(property.getText(), owner.getName());

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });

                    exit.setOnAction(e -> {
                        window.setScene(scene4);
                    });
                }
            }
        });

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

        // scene 11 - Management Menu
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
        StackPane layout1 = new StackPane();
        layout1.getChildren().add(tree2);
        Scene scene11 = new Scene(layout1, 400, 300);
        tree2.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue);
                // scene 10
                if (newValue.getValue().equalsIgnoreCase("quit")) {
                    result = displayConfirmBox("Quiting to interface select?",
                            "Are you sure you wish to return tointerface select?");

                    if (result == true) {
                        InterfaceSelect iSelect = new InterfaceSelect();
                        window.close();
                        try {
                            iSelect.run();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (result == false) {
                        window.setScene(scene11);
                    }

                }
                // scene 12
                else if (newValue.getValue().equalsIgnoreCase("Investigate the impact of changes on tax system")) {

                    Label fixedCostLabel = new Label("Enter new fixed Cost");
                    GridPane.setConstraints(fixedCostLabel, 0, 0);

                    TextField fixedCostInput = new TextField();
                    fixedCostInput.setPromptText("100");
                    GridPane.setConstraints(fixedCostInput, 1, 0);

                    Label flatPprChargeLabel = new Label("Enter new flat principal private property");
                    GridPane.setConstraints(flatPprChargeLabel, 0, 1);

                    TextField flatPprChargeInput = new TextField();
                    flatPprChargeInput.setPromptText("80");
                    GridPane.setConstraints(flatPprChargeInput, 1, 1);

                    Label annualPenaltyLabel = new Label("Enter new annual penalty");
                    GridPane.setConstraints(annualPenaltyLabel, 0, 2);

                    TextField annualPenaltyInput = new TextField();
                    annualPenaltyInput.setPromptText("0.07");
                    GridPane.setConstraints(annualPenaltyInput, 1, 2);

                    Label locationsLabel = new Label("Enter Locations in the format as follows");
                    GridPane.setConstraints(locationsLabel, 0, 3);

                    TextField locationsInput = new TextField();
                    locationsInput.setPromptText("CountrySide,Village,Small Town,Large Town,City");
                    GridPane.setConstraints(locationsInput, 1, 3);

                    Label locationsChargeLabel = new Label("Enter new Location charges");
                    GridPane.setConstraints(locationsChargeLabel, 0, 4);

                    TextField locationsChargeInput = new TextField();
                    locationsChargeInput.setPromptText(" 25,50,60,80,100");
                    GridPane.setConstraints(locationsChargeInput, 1, 4);

                    Label pValuesLabel = new Label("Enter Property value ranges in the following format");
                    GridPane.setConstraints(pValuesLabel, 0, 5);

                    TextField pValuesInput = new TextField();
                    pValuesInput.setPromptText("0,150000,400000,650000");
                    GridPane.setConstraints(pValuesInput, 1, 5);

                    Label propertyRatesLabel = new Label("Enter corresponding Property Rates as follows");
                    GridPane.setConstraints(propertyRatesLabel, 0, 6);

                    TextField propertyRatesInput = new TextField();
                    propertyRatesInput.setPromptText("0,0.01,0.02,0.04");
                    GridPane.setConstraints(propertyRatesInput, 1, 6);

                    Button dataEnter = new Button("Enter");
                    GridPane.setConstraints(dataEnter, 1, 7);

                    GridPane scene12Layout = new GridPane();
                    scene12Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene12Layout.setVgap(8);
                    scene12Layout.setHgap(10);

                    scene12Layout.getChildren().addAll(fixedCostLabel, fixedCostInput, flatPprChargeLabel,
                            flatPprChargeInput, annualPenaltyLabel, annualPenaltyInput, locationsLabel, locationsInput,
                            locationsChargeLabel, locationsChargeInput, pValuesLabel, pValuesInput, propertyRatesLabel,
                            propertyRatesInput, dataEnter);
                    Scene scene12 = new Scene(scene12Layout, 500, 300);
                    window.setScene(scene12);

                    dataEnter.setOnAction(e -> {
                        fixedCost = Double.parseDouble(fixedCostInput.getText());
                        flatPprCharge = Double.parseDouble(flatPprChargeInput.getText());
                        annualPenalty = Double.parseDouble(annualPenaltyInput.getText());
                        temp = locationsInput.getText();
                        Locations = temp.split(",");

                        temp = locationsChargeInput.getText();
                        tempArr = temp.split(",");
                        locationsCharge = new double[tempArr.length];
                        for (k = 0; k < tempArr.length; k++) {
                            locationsCharge[k] = Double.parseDouble(tempArr[k]);
                        }

                        temp = pValuesInput.getText();
                        tempArr1 = temp.split(",");
                        for (k = 0; k < tempArr1.length; k++) {
                            pValues[k] = Double.parseDouble(tempArr1[k]);
                        }

                        temp = propertyRatesInput.getText();
                        tempArr2 = temp.split(",");
                        for (k = 0; k < tempArr2.length; k++) {
                            propertyRates[k] = Double.parseDouble(tempArr2[k]);

                        }
                        tC = new TaxCalculator(fixedCost, flatPprCharge, annualPenalty, Locations, pValues,
                                locationsCharge, propertyRates);// creates a new taxcalculator for comparing the tax
                                                                // statistics
                    });

                }
                // scene 13 / 14 works
                else if (newValue.getValue().equalsIgnoreCase("Number of Taxes Paid")) {
                    Label routingLabel = new Label("Enter the routing code for the Eircode:");
                    GridPane.setConstraints(routingLabel, 0, 0);

                    TextField routingInput = new TextField();
                    routingInput.setPromptText("xxx");
                    GridPane.setConstraints(routingInput, 1, 0);

                    GridPane scene13Layout = new GridPane();
                    scene13Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene13Layout.setVgap(8);
                    scene13Layout.setHgap(10);

                    Button routingEnter = new Button("Enter");
                    GridPane.setConstraints(routingEnter, 1, 1);

                    scene13Layout.getChildren().addAll(routingEnter, routingInput, routingLabel);
                    Scene scene13 = new Scene(scene13Layout, 450, 300);
                    window.setScene(scene13);

                    routingEnter.setOnAction(e -> {
                        try {
                            k = st.numberOfPayments(routingInput.getText());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        Label outputPercentageLabel = new Label("The Number of Properties is:" + k);
                        GridPane.setConstraints(outputPercentageLabel, 0, 0);

                        Button exit = new Button("Back To Menu");
                        GridPane.setConstraints(exit, 1, 1);

                        GridPane scene14Layout = new GridPane();
                        scene14Layout.setPadding(new Insets(10, 10, 10, 10));
                        scene14Layout.setVgap(8);
                        scene14Layout.setHgap(10);

                        scene14Layout.getChildren().addAll(outputPercentageLabel, exit);
                        Scene scene14 = new Scene(scene14Layout, 450, 300);
                        window.setScene(scene14);
                        exit.setOnAction(f -> window.setScene(scene11));
                    });
                }
                // scene 15 / 16 WORKS
                else if (newValue.getValue().equalsIgnoreCase("Percentage of Taxes Paid")) {
                    Label routingLabel = new Label("Enter the routing code for the Eircode:");
                    GridPane.setConstraints(routingLabel, 0, 0);

                    TextField routingInput = new TextField();
                    routingInput.setPromptText("xxx");
                    GridPane.setConstraints(routingInput, 1, 0);

                    GridPane scene15Layout = new GridPane();
                    scene15Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene15Layout.setVgap(8);
                    scene15Layout.setHgap(10);

                    Button routingEnter = new Button("Enter");
                    GridPane.setConstraints(routingEnter, 1, 1);

                    scene15Layout.getChildren().addAll(routingEnter, routingInput, routingLabel);
                    Scene scene15 = new Scene(scene15Layout, 450, 300);
                    window.setScene(scene15);

                    routingEnter.setOnAction(e -> {
                        try {
                            x = st.percentageOfTaxesPaid(routingInput.getText());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        Label outputPercentageLabel = new Label("The percentage is" + x);
                        GridPane.setConstraints(outputPercentageLabel, 0, 0);

                        Button exit = new Button("Back To Menu");
                        GridPane.setConstraints(exit, 1, 1);

                        GridPane scene16Layout = new GridPane();
                        scene16Layout.setPadding(new Insets(10, 10, 10, 10));
                        scene16Layout.setVgap(8);
                        scene16Layout.setHgap(10);

                        scene16Layout.getChildren().addAll(outputPercentageLabel, exit);
                        Scene scene16 = new Scene(scene16Layout, 450, 300);
                        window.setScene(scene16);
                        exit.setOnAction(f -> window.setScene(scene11));
                    });

                }
                // scene 17 / 18 WORKS
                else if (newValue.getValue().equalsIgnoreCase("Average Tax Paid")) {
                    Label routingLabel = new Label("Enter the routing code for the Eircode:");
                    GridPane.setConstraints(routingLabel, 0, 0);

                    TextField routingInput = new TextField();
                    routingInput.setPromptText("xxx");
                    GridPane.setConstraints(routingInput, 1, 0);

                    GridPane scene17Layout = new GridPane();
                    scene17Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene17Layout.setVgap(8);
                    scene17Layout.setHgap(10);

                    Button routingEnter = new Button("Enter");
                    GridPane.setConstraints(routingEnter, 1, 1);

                    scene17Layout.getChildren().addAll(routingEnter, routingInput, routingLabel);
                    Scene scene17 = new Scene(scene17Layout, 450, 300);
                    window.setScene(scene17);

                    routingEnter.setOnAction(e -> {
                        try {
                            y = st.averageTaxPaid(routingInput.getText());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        Label outputPercentageLabel = new Label("The Average Tax for the Eircode key is:" + y);
                        GridPane.setConstraints(outputPercentageLabel, 0, 0);

                        Button exit = new Button("Back To Menu");
                        GridPane.setConstraints(exit, 1, 1);

                        GridPane scene18Layout = new GridPane();
                        scene18Layout.setPadding(new Insets(10, 10, 10, 10));
                        scene18Layout.setVgap(8);
                        scene18Layout.setHgap(10);

                        scene18Layout.getChildren().addAll(outputPercentageLabel, exit);
                        Scene scene18 = new Scene(scene18Layout, 450, 300);
                        window.setScene(scene18);
                        exit.setOnAction(f -> window.setScene(scene11));
                    });
                }
                // scene 19 / 20 WORKS
                else if (newValue.getValue().equalsIgnoreCase("Total Tax Paid")) {
                    Label routingLabel = new Label("Enter the routing code for the Eircode:");
                    GridPane.setConstraints(routingLabel, 0, 0);

                    TextField routingInput = new TextField();
                    routingInput.setPromptText("xxx");
                    GridPane.setConstraints(routingInput, 1, 0);

                    GridPane scene19Layout = new GridPane();
                    scene19Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene19Layout.setVgap(8);
                    scene19Layout.setHgap(10);

                    Button routingEnter = new Button("Enter");
                    GridPane.setConstraints(routingEnter, 1, 1);

                    scene19Layout.getChildren().addAll(routingEnter, routingInput, routingLabel);
                    Scene scene19 = new Scene(scene19Layout, 450, 300);
                    window.setScene(scene19);

                    routingEnter.setOnAction(e -> {
                        try {
                            y = st.totalTaxPaid(routingInput.getText());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        Label outputPercentageLabel = new Label("The Total Tax for the Eircode key is:" + y);
                        GridPane.setConstraints(outputPercentageLabel, 0, 0);

                        Button exit = new Button("Back To Menu");
                        GridPane.setConstraints(exit, 1, 1);

                        GridPane scene20Layout = new GridPane();
                        scene20Layout.setPadding(new Insets(10, 10, 10, 10));
                        scene20Layout.setVgap(8);
                        scene20Layout.setHgap(10);

                        scene20Layout.getChildren().addAll(outputPercentageLabel, exit);
                        Scene scene20 = new Scene(scene20Layout, 450, 300);
                        window.setScene(scene20);
                        exit.setOnAction(f -> window.setScene(scene11));
                    });
                }
                // scene 21-
                else if (newValue.getValue().equalsIgnoreCase("Query a year")) {
                    Label yearLabel = new Label("Enter the Year");
                    GridPane.setConstraints(yearLabel, 0, 0);

                    TextField yearInput = new TextField();
                    yearInput.setPromptText("1954");
                    GridPane.setConstraints(yearInput, 1, 0);

                    GridPane scene21Layout = new GridPane();
                    scene21Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene21Layout.setVgap(8);
                    scene21Layout.setHgap(10);

                    Button yearEnter = new Button("Enter");
                    GridPane.setConstraints(yearEnter, 1, 1);

                    scene21Layout.getChildren().addAll(yearLabel, yearInput, yearEnter);
                    Scene scene21 = new Scene(scene21Layout, 450, 300);
                    window.setScene(scene21);
                }
                // scene 22 / 23- gets overdue tax for a Year and Area
                else if (newValue.getValue().equalsIgnoreCase("Query an Area and Year")) {
                    Label areaLabel = new Label("Enter the area's Eircode key");
                    GridPane.setConstraints(areaLabel, 0, 0);

                    TextField areaInput = new TextField();
                    areaInput.setPromptText("V95");
                    GridPane.setConstraints(areaInput, 1, 0);

                    Label yearLabel = new Label("Enter the Year");
                    GridPane.setConstraints(yearLabel, 0, 1);

                    TextField yearInput = new TextField();
                    yearInput.setPromptText("1954");
                    GridPane.setConstraints(yearInput, 1, 1);

                    GridPane scene22Layout = new GridPane();
                    scene22Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene22Layout.setVgap(8);
                    scene22Layout.setHgap(10);

                    Button dataEnter = new Button("Enter");
                    GridPane.setConstraints(dataEnter, 1, 2);

                    scene22Layout.getChildren().addAll(areaInput, yearLabel, yearInput, areaLabel, dataEnter);
                    Scene scene22 = new Scene(scene22Layout, 450, 300);
                    window.setScene(scene22);

                    GridPane scene23Layout = new GridPane();
                    scene23Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene23Layout.setVgap(8);
                    scene23Layout.setHgap(10);

                    dataEnter.setOnAction(e -> {
                        try {
                            output = overdueTax(yearInput.getText(), areaLabel.getText());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        Label overdueInfo = new Label(output);
                        GridPane.setConstraints(overdueInfo, 0, 0);

                        Button backToMenu = new Button("Back To Menu");
                        GridPane.setConstraints(backToMenu, 1, 1);

                        scene23Layout.getChildren().addAll(overdueInfo, backToMenu);
                        Scene scene23 = new Scene(scene23Layout, 450, 300);
                        window.setScene(scene23);
                        backToMenu.setOnAction(f -> window.setScene(scene11));
                    });

                }

                // Scene 24 / 25 THIS WORKS
                else if (newValue.getValue().equalsIgnoreCase("Owners Tax")) {
                    Label ownerLabel = new Label("Enter the Owners name:");
                    GridPane.setConstraints(ownerLabel, 0, 0);

                    TextField ownerInput = new TextField();
                    ownerInput.setPromptText("John Smith");
                    GridPane.setConstraints(ownerInput, 1, 0);

                    GridPane scene24Layout = new GridPane();
                    scene24Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene24Layout.setVgap(8);
                    scene24Layout.setHgap(10);

                    Button ownerEnter = new Button("Enter");
                    GridPane.setConstraints(ownerEnter, 1, 1);

                    scene24Layout.getChildren().addAll(ownerLabel, ownerInput, ownerEnter);
                    Scene scene24 = new Scene(scene24Layout, 450, 300);
                    window.setScene(scene24);

                    GridPane scene25Layout = new GridPane();
                    scene25Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene25Layout.setVgap(8);
                    scene25Layout.setHgap(10);

                    ownerEnter.setOnAction(e -> {
                        try {
                            output = ownersTax(ownerInput.getText());

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        Label ownerInfo = new Label(output);
                        GridPane.setConstraints(ownerInfo, 0, 0);

                        Button backToMenu = new Button("Back To Menu");
                        GridPane.setConstraints(backToMenu, 1, 1);

                        scene25Layout.getChildren().addAll(ownerInfo, backToMenu);
                        Scene scene25 = new Scene(scene25Layout, 450, 300);
                        window.setScene(scene25);
                        backToMenu.setOnAction(f -> window.setScene(scene11));

                    });
                }
                // scene 26 / 27 works
                else if (newValue.getValue().equalsIgnoreCase("Property Tax")) {
                    Label propertyLabel = new Label("Enter the eircode of the property");
                    GridPane.setConstraints(propertyLabel, 0, 0);

                    TextField eirInput = new TextField();
                    eirInput.setPromptText("Eircode");
                    GridPane.setConstraints(eirInput, 1, 0);

                    GridPane scene26Layout = new GridPane();
                    scene26Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene26Layout.setVgap(8);
                    scene26Layout.setHgap(10);

                    Button eirEnter = new Button("Enter");
                    GridPane.setConstraints(eirEnter, 1, 1);
                    scene26Layout.getChildren().addAll(propertyLabel, eirInput, eirEnter);
                    Scene scene26 = new Scene(scene26Layout, 450, 300);
                    window.setScene(scene26);

                    GridPane scene27Layout = new GridPane();
                    scene27Layout.setPadding(new Insets(10, 10, 10, 10));
                    scene27Layout.setVgap(8);
                    scene27Layout.setHgap(10);

                    eirEnter.setOnAction(e -> {
                        try {
                            output = propertyTax(eirInput.getText());

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        Label propertyInfo = new Label(output);
                        GridPane.setConstraints(propertyInfo, 0, 0);

                        Button backToMenu = new Button("Back To Menu");
                        GridPane.setConstraints(backToMenu, 1, 1);
                        backToMenu.setOnAction(f -> window.setScene(scene11));

                        scene27Layout.getChildren().addAll(propertyInfo, backToMenu);
                        Scene scene27 = new Scene(scene27Layout, 450, 300);
                        window.setScene(scene27);
                        System.out.println(eirInput.getText());

                    });
                }
            }
        });

        loginButton2.setOnAction(e -> {
            if (nameInput2.getText().equalsIgnoreCase("MANAGER") && passInput2.getText().equalsIgnoreCase("password")) {
                System.out.println("login success");
                window.setScene(scene11);
            }
        });

    }

    /**
     * @param text
     * @param p
     * @return String
     * @throws IOException
     */
    // payment function for owners
    private String payment(String text, Property p) throws IOException {
        String[] textArray = text.split(" ");
        Payment pay = new Payment(Double.parseDouble(textArray[0]), LocalDate.now(), owner, p);
        writeToPayments("src/payments.csv", pay);
        double toPay = Double.parseDouble(textArray[0]);
        String paid = pay.payTax(p, toPay, tC.overallTax(p));
        return "Paid:" + paid + " For Property:" + p;
    }

    /**
     * @param property
     * @param name
     * @throws IOException
     */
    // registers property for owner menu
    private void registerProperty(String property, String name) throws IOException {
        String[] propArray = property.split(",");
        Property p = new Property(propArray[0], propArray[1], propArray[2], propArray[3],
                Double.parseDouble(propArray[4]), Boolean.parseBoolean(propArray[5]), LocalDate.now());
        writeToProperties("src/properties.csv", p, name);
    }

    /**
     * @param eircode
     * @return String
     * @throws IOException
     */
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

    /**
     * @param ownerName
     * @return String
     * @throws IOException
     */
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

    /**
     * @param y
     * @return String
     * @throws IOException
     */
    // gets the overdue tax for a year
    private String overdueTax(String y) throws IOException {
        LocalDate year = LocalDate.parse(y);
        return (df.format(tC.overdueTax(year)));
    }

    /**
     * @param y
     * @param eC
     * @return String
     * @throws IOException
     */
    // gets the overdue tax for an area and year
    private String overdueTax(String y, String eC) throws IOException {
        LocalDate year = LocalDate.parse(y);
        String key = eC;
        return (df.format(tC.overdueTax(year, key)));
    }

    /**
     * @param username
     * @param password
     * @param newuser
     * @return boolean
     * @throws IOException
     */
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

    /**
     * @param filename
     * @param pay
     */
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

    /**
     * @param filename
     * @param p
     * @param name
     * @throws IOException
     */
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

    /**
     * @param owner
     * @return ArrayList<String>
     * @throws IOException
     */
    private ArrayList<String> getPaidProperties(String owner) throws IOException {
        ArrayList<String> fin = new ArrayList<String>();
        ArrayList<String> properties = new ArrayList<String>();
        ArrayList<String> payments = new ArrayList<String>();
        String[] payment, property;
        properties = csvReader("src/properties.csv");
        payments = csvReader("src/payments.csv");
        for (int i = 0; i < properties.size(); i++) {
            property = properties.get(i).split(",");
            payment = payments.get(i).split(",");
            if (property[0].equalsIgnoreCase(owner) && payment[0].equalsIgnoreCase(owner)) {
                fin.add(properties.get(i));
            }
        }
        return fin;
    }

    /**
     * @param owner
     * @return ArrayList<String>
     * @throws IOException
     */
    private ArrayList<String> getUnPaidProperties(String owner) throws IOException {
        ArrayList<String> fin = new ArrayList<String>();
        ArrayList<String> properties = new ArrayList<String>();
        ArrayList<String> payments = new ArrayList<String>();
        String[] payment, property;
        properties = csvReader("src/properties.csv");
        payments = csvReader("src/payments.csv");
        for (int i = 0; i < properties.size(); i++) {
            property = properties.get(i).split(",");
            payment = payments.get(i).split(",");
            if (property[0].equalsIgnoreCase(owner) && payment[0] != owner) {
                fin.add(properties.get(i));
            }
        }
        return fin;
    }

    /**
     * @param filename
     * @return ArrayList<String>
     * @throws IOException
     */
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

    /**
     * @param title
     * @param parent
     * @return TreeItem<String>
     */
    // produces the Tree for the GUI
    public static TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    /**
     * @param title
     * @param message
     * @return boolean
     */
    public static boolean displayConfirmBox(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

}
