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
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class GUIGridPane {

    public static void userLogin(String title) {
        Stage window = new Stage();
        window.setTitle(title);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

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
        TextField passInput = new TextField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 1, 1);

        Button loginButton = new Button("Log In");
        loginButton.setOnAction(e -> nameInput.getText());
        GridPane.setConstraints(loginButton, 2, 2);

        grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton);

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);

        window.showAndWait();
    }

    public static String[] managerLogin(String title) {
        Stage window = new Stage();
        String[] login = { "", "" };
        window.setTitle(title);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // name label
        Label nameLabel = new Label("Username");
        GridPane.setConstraints(nameLabel, 0, 0);

        // name input
        TextField nameInput = new TextField("Manager");
        GridPane.setConstraints(nameInput, 1, 0);

        // password label
        Label passLabel = new Label("Password");
        GridPane.setConstraints(passLabel, 0, 1);

        // password input
        TextField passInput = new TextField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 1, 1);

        Button loginButton = new Button("Log In");
        loginButton.setOnAction(e -> {
            try {

            } catch (Exception e) {
                // TODO: handle exception
            }

            // login[0] = nameInput.getText();
            // login[1] = passInput.getText();
            // window.close();
        });
        GridPane.setConstraints(loginButton, 2, 2);

        grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton);

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.showAndWait();
        return login;

            // login[0] = nameInput.getText();
            // login[1] = passInput.getText();
            // window.close();
        });GridPane.setConstraints(loginButton,2,2);

    grid.getChildren().addAll(nameLabel,nameInput,passLabel,passInput,loginButton);

    Scene scene = new Scene(grid, 300, 200);window.setScene(scene);window.showAndWait();return login;
}}
