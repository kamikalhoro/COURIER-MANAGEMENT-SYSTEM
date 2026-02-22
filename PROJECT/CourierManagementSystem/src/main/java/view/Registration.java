package view;

import com.mycompany.couriermanagementsystem.App;
import controller.SignupController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Registration {
    
    private final SignupController controller = new SignupController();
    private final App app;
    
    public Registration (App app) {
        this.app = app;
    }
    
    public Scene createScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label title = new Label("New Employee Registration");
        title.setStyle("-fx-font-size: 16pt; -fx-font-weight: bold;");

        
        // Full Name
        Label nameLabel = new Label("Full Name:");
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Enter full name");


        // Phone Number
        Label phoneLabel = new Label("Phone Number:");
        
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter phone number");
        
        
        // Username
        Label userLabel = new Label("Username (ID):");
        TextField userTextField = new TextField();
        userTextField.setPromptText("Choose a username");
        

        // Password
        Label pwLabel = new Label("Password:");
        
        PasswordField pwField = new PasswordField();
        
        
        Button backToLogin = new Button("Back");
        Button registerButton = new Button("Register Account");
        
        HBox hbBtn = new HBox(10);
        hbBtn.setSpacing(150);
        hbBtn.getChildren().addAll(backToLogin,registerButton);
        

        // Message Label (for success/failure feedback)
        Label messageLabel = new Label();
  
        grid.add(title, 0, 0, 2, 1);
        grid.add(nameLabel, 0, 1); grid.add(fullNameField, 1, 1);
        grid.add(phoneLabel, 0, 2); grid.add(phoneField, 1, 2);
        grid.add(userLabel, 0, 3); grid.add(userTextField, 1, 3);
        grid.add(pwLabel, 0, 4); grid.add(pwField, 1, 4);
        grid.add(hbBtn, 0, 5, 2, 1);
        grid.add(messageLabel, 0, 6, 2, 1);
        
        registerButton.setOnAction(e -> {          
            boolean success = controller.registerNewEmployee(
                userTextField.getText(),
                pwField.getText(),
                fullNameField.getText(),
                phoneField.getText()
            );

            if (success) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Registration successful! You can now log in.");
                fullNameField.clear();
                phoneField.clear();
                userTextField.clear();
                pwField.clear();
                
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Registration failed. Check if username is taken or fields are blank.");
            }
        });

        backToLogin.setOnAction(e -> { 
            app.showLoginScreen();
        });
        
        VBox root = new VBox(grid);
        root.setAlignment(Pos.CENTER);
        
        return new Scene(root, 400, 350);
    }
}
