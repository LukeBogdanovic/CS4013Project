import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GraphicalManagementMenu extends Application {

    public void start(Stage primaryStage) {
        Button exitbtn = new Button("EXIT");
        exitbtn.setOnAction(e -> Platform.exit());
        VBox root = new VBox();
        root.getChildren().add(exitbtn);
        Scene scene = new Scene(root, 200, 250);
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}