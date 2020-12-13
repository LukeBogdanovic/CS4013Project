import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.Scene;

public class GUILayouts {
    static Stage window;
    static TreeView<String> tree;

    public static void ownerMenu(String title) {
        window = new Stage();
        window.setTitle(title);

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
        tree = new TreeView<>(ownerMenu);
        tree.setShowRoot(false);

        StackPane layout = new StackPane();
        layout.getChildren().add(tree);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
    }

    public static void managementMenu(String title) {
        window = new Stage();
        window.setTitle(title);

        TreeItem<String> managementMenu, propertyTax, ownersTax, overdueTax, statistics, investigate, quit;

        // root
        managementMenu = new TreeItem<>();
        managementMenu.setExpanded(true);

        // propertyTax
        propertyTax = makeBranch("Property Tax", managementMenu);

        // ownersTax
        ownersTax = makeBranch("Owners Tax", managementMenu);

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
        investigate = makeBranch("Investigate the impact of changes on tax system", managementMenu);

        // quit
        quit = makeBranch("Quit", managementMenu);

        StackPane layout = new StackPane();
        layout.getChildren().add(tree);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
    }

    public static TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

}
