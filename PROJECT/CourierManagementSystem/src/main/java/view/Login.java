package view;

import com.mycompany.couriermanagementsystem.App;
import controller.LoginController;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


public class Login {
    private final App app;
    private final LoginController controller;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;
    
    public Login (App app) {
        this.app = app;
        this.controller = new LoginController();
    }
    
    public Scene createScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Label title = new Label("Employee Login");

        usernameField = new TextField();
        passwordField = new PasswordField();
        
        Button loginButton = new Button("Login");
        Button signupRedirectButton = new Button("Need to Register?");
        messageLabel = new Label();
     
        
        grid.add(title, 0, 0, 2, 1);
        grid.add(new Label("Username:"), 0, 1); grid.add(usernameField, 1, 1);
        grid.add(new Label("Password:"), 0, 2); grid.add(passwordField, 1, 2);
        grid.add(loginButton, 0, 3, 2, 1);
        grid.add(messageLabel, 0, 4, 2, 1);
        grid.add(signupRedirectButton, 0, 5, 2, 1);

        loginButton.setOnAction(e -> {
            boolean authenticated = controller.Authenticate(
                usernameField.getText(), 
                passwordField.getText()
            );

            if (authenticated) {
                messageLabel.setText("Valid username or password.");
                messageLabel.setStyle("-fx-text-fill: green;");
                app.showDashboard(); 
            } else {
                messageLabel.setText("Invalid username or password.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });
        
        signupRedirectButton.setOnAction(e -> app.showRegistrationScreen());

        return new Scene(grid, 400, 350);
    }

    
}
