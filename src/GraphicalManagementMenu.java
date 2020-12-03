import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GraphicalManagementMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        Button btExit = new Button("Exit");
        Button btOwners = new Button("Owners");
        Button btProperties = new Button("Properties");
        Button btRegister = new Button("Register Property");
        hbox.getChildren().add(btExit);
        hbox.getChildren().add(btOwners);
        hbox.getChildren().add(btProperties);
        hbox.getChildren().add(btRegister);

        BorderPane borderPane = new BorderPane();

    }

}