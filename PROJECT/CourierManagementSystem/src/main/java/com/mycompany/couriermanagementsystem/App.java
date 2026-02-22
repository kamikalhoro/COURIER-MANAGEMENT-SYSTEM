package com.mycompany.couriermanagementsystem;

import javafx.application.Application;
import view.Login;
import view.Registration;
import view.Dashboard;
import javafx.stage.Stage;

public class App extends Application {

    private Stage primaryStage;
    
@Override
public void start(Stage stage) {
    this.primaryStage = stage;
    this.primaryStage.setTitle("Courier Management System");
    
    // This calls your method below to actually show the UI
    showLoginScreen(); 
}

    public void showLoginScreen() {
        Login loginView = new Login(this); 
        primaryStage.setScene(loginView.createScene());
        Scene scene = new Scene(loginView, 400, 500);
    
    // APPLY CSS HERE
    scene.getStylesheets().add(getClass().getResource("/view/style.css").toExternalForm());
    
    primaryStage.setScene(scene);
    primaryStage.show();
        
    }
    
    public void showRegistrationScreen() {
        Registration RegistrationView = new Registration(this);
        primaryStage.setScene(RegistrationView.createScene());
        primaryStage.show();
    }
    
    public void showDashboard() {
        Dashboard DashboardView = new Dashboard(this,this.primaryStage);
        primaryStage.setScene(DashboardView.createScene());
        primaryStage.show();
    }
       
    public static void main(String[] args) {
        launch();
    }

}