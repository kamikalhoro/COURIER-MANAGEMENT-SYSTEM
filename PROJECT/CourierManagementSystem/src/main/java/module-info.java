module com.mycompany.couriermanagementsystem {
    requires javafx.controls;
    requires java.desktop;
    requires java.logging;
    
    opens com.mycompany.couriermanagementsystem to javafx.fxml;
    opens model to javafx.base; 
    
    exports com.mycompany.couriermanagementsystem;
}
