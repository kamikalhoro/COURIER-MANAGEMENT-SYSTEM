package view;


import DAO.ShipmentIO;
import controller.ShipmentController;
import com.mycompany.couriermanagementsystem.App;
import java.util.ArrayList;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import model.Shipment;
import model.ShipmentStatus;

public class Dashboard {

    private final App app;
    private final Stage primaryStage;
    BorderPane root = new BorderPane();
    ShipmentController controller = new ShipmentController();
                    
    public Dashboard(App app, Stage primaryStage) {
        this.app = app;
        this.primaryStage = primaryStage;
    }

    
    public Scene createScene() {

        root.setPadding(new Insets(10));

        // 1. Top Section (Menu Bar and Header)
        VBox topContainer = new VBox();
        topContainer.getChildren().add(createMenuBar());
        
        Label header = new Label("Courier Management System Dashboard");
        header.setStyle("-fx-font-size: 20pt; -fx-font-weight: bold;");
        VBox.setMargin(header, new Insets(10, 0, 10, 10)); 
        topContainer.getChildren().add(header);
        
        root.setTop(topContainer);

        // 2. Left Section (Side Navigation / Employee Info)
        VBox sideMenu = createSideMenu();
        root.setLeft(sideMenu);

        // 3. Center Section (Main Content Area)
        VBox mainContent = createMainContentArea();
        root.setCenter(mainContent);
        
        // 4. Scene Setup
        Scene scene = new Scene(root, 1000, 600);
        return scene;
    }

    

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        
        // User Menu
        Menu userMenu = new Menu("User");
        
        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(e -> {
            app.showLoginScreen();
            System.out.println("Logging out...");
        });
        
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> primaryStage.close());
        userMenu.getItems().addAll(logoutItem, exitItem);
                
        menuBar.getMenus().addAll(userMenu);
        return menuBar;
    }


    
    private VBox createSideMenu() {
        VBox sideMenu = new VBox(15);
        sideMenu.setPadding(new Insets(20));
        sideMenu.setStyle("-fx-background-color: #f4f4f4;");
        sideMenu.setMinWidth(180);
        sideMenu.setAlignment(Pos.TOP_LEFT);

        Label welcomeLabel = new Label("Welcome, Employee!");
        welcomeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
        
        Hyperlink parcelsLink = new Hyperlink("📦 View Shipments");
        Hyperlink deliveryLink = new Hyperlink("🚚 Create Shipments");
//        Hyperlink customersLink = new Hyperlink("👥 Manage Customers");

        parcelsLink.setOnAction(e -> {
            root.setCenter(createShipmentsView());
        });
        
        deliveryLink.setOnAction(e -> {
            root.setCenter(createAddShipmentView());
        });

        sideMenu.getChildren().addAll(welcomeLabel, new Separator(), parcelsLink, deliveryLink);
        return sideMenu;
    }

    

    private VBox createMainContentArea() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_LEFT);
        
        Label instructionLabel = new Label("Select an option from the side menu or management bar.");
        instructionLabel.setStyle("-fx-font-size: 12pt;");
        
        content.getChildren().add(instructionLabel);
        
        return content;
    }
    
    
    
    private VBox createShipmentsView() {
        HBox controls = new HBox(15);        
        
        ChoiceBox<Shipment.Status> statusFilter = new ChoiceBox<>();
        statusFilter.getItems().add(null); statusFilter.getItems().addAll(Shipment.Status.values());
        TextField searchField = new TextField(); searchField.setPromptText("Search Tracking Number...");
        controls.getChildren().addAll(new Label("Filter by Status:"), statusFilter, searchField);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(0, 0, 10, 0));
        
        // Main Table (TableView)
        TableView<Shipment> shipmentTable = new TableView<>();
        shipmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Shipment, String> trackingCol = new TableColumn<>("Tracking ID");
        trackingCol.setCellValueFactory(new PropertyValueFactory<>("trackingNumber"));
        trackingCol.setMinWidth(80);

        TableColumn<Shipment, String> senderCol = new TableColumn<>("Sender");
        senderCol.setCellValueFactory(new PropertyValueFactory<>("senderName"));

        TableColumn<Shipment, String> receiverCol = new TableColumn<>("Receiver");
        receiverCol.setCellValueFactory(new PropertyValueFactory<>("receiverName"));

        TableColumn<Shipment, String> senderAddressCol = new TableColumn<>("origin");
        senderAddressCol.setCellValueFactory(new PropertyValueFactory<>("senderAddress"));
        senderAddressCol.setMinWidth(100);
        
        TableColumn<Shipment, String> recieverAddressCol = new TableColumn<>("Destination");
        recieverAddressCol.setCellValueFactory(new PropertyValueFactory<>("deliveryAddress"));
        recieverAddressCol.setMinWidth(100);

        TableColumn<Shipment, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        TableColumn<Shipment, Shipment.Status> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("currentStatus"));

        shipmentTable.getColumns().addAll(trackingCol, senderCol, receiverCol, senderAddressCol, recieverAddressCol, amountCol, statusCol);

        // Load Initial Data
        ShipmentIO shipmentIO = new ShipmentIO();
        ObservableList<Shipment> data = FXCollections.observableArrayList(shipmentIO.getAllShipments());
        shipmentTable.setItems(data);

        statusFilter.setOnAction(e -> {
            Shipment.Status selected = statusFilter.getValue();
            if (selected == null) {
                shipmentTable.setItems(FXCollections.observableArrayList(shipmentIO.getAllShipments()));
            } else {
                shipmentTable.setItems(FXCollections.observableArrayList(shipmentIO.filterByStatus(selected)));
            }
        });
        
        searchField.textProperty().addListener((observable, oldText, newText) -> {

            ArrayList<Shipment> matches = new ArrayList<>();

            for (Shipment s : data) {

                if (s.getTrackingNumber().toLowerCase().contains(newText.toLowerCase())) {
                    matches.add(s);
                }
            }

            shipmentTable.setItems(FXCollections.observableArrayList(matches));
        });
        
        
        HBox actionBtns = new HBox(10);
        actionBtns.setAlignment(Pos.CENTER_RIGHT);
        actionBtns.setPadding(new Insets(10, 0, 0, 0));

        Button editBtn = new Button("✏ Edit Selected");
        editBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        Button deleteBtn = new Button("🗑 Delete Selected");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        // Disable buttons if no choice
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);

        shipmentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            boolean isEmpty = (newSelection == null);
            editBtn.setDisable(isEmpty);
            deleteBtn.setDisable(isEmpty);
        });

        // EDIT ACTION
        editBtn.setOnAction(e -> {
            Shipment selected = shipmentTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                root.setCenter(createEditShipmentView(selected));
            }
        });

        // DELETE ACTION
        deleteBtn.setOnAction(e -> {
            Shipment selected = shipmentTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                ShipmentIO io = new ShipmentIO();
                io.deleteShipment(selected.getTrackingNumber());
                shipmentTable.getItems().remove(selected);
            }
        });

        actionBtns.getChildren().addAll(editBtn, deleteBtn);

        VBox view = new VBox(10, controls, actionBtns, shipmentTable);
        
        return view;
    }

    
    
    private ScrollPane createAddShipmentView() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: white;");

        // Title
        Label header = new Label("Create New Shipment");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // SECTION 1: SENDER DETAILS
        Label senderLabel = new Label("Sender Details");
        senderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField senderName = new TextField(); senderName.setPromptText("Sender Full Name");
        TextField senderPhone = new TextField(); senderPhone.setPromptText("Sender Phone");
        TextField senderAddress = new TextField(); senderAddress.setPromptText("Sender Address");

        GridPane senderGrid = new GridPane();
        senderGrid.setHgap(15); senderGrid.setVgap(10);
        senderGrid.addRow(0, new Label("Name:"), senderName);
        senderGrid.addRow(1, new Label("Phone:"), senderPhone);
        senderGrid.addRow(2, new Label("Address:"), senderAddress);

        // SECTION 2: RECEIVER DETAILS
        Label receiverLabel = new Label("Receiver Details");
        receiverLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField receiverName = new TextField(); receiverName.setPromptText("Receiver Full Name");
        TextField receiverPhone = new TextField(); receiverPhone.setPromptText("Receiver Phone");
        TextField receiverAddress = new TextField(); receiverAddress.setPromptText("Delivery Address");

        GridPane receiverGrid = new GridPane();
        receiverGrid.setHgap(15); receiverGrid.setVgap(10);
        receiverGrid.addRow(0, new Label("Name:"), receiverName);
        receiverGrid.addRow(1, new Label("Phone:"), receiverPhone);
        receiverGrid.addRow(2, new Label("Address:"), receiverAddress);

        // SECTION 3: PACKAGE DETAILS
        Label packageLabel = new Label("Package & Payment");
        packageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField descField = new TextField(); descField.setPromptText("Item Description");
        TextField amountField = new TextField(); amountField.setPromptText("Total Amount");
        TextField remarksField = new TextField(); remarksField.setPromptText("Any special instructions...");

        ComboBox<Shipment.PaymentStatus> paymentBox = new ComboBox<>();
        paymentBox.getItems().addAll(Shipment.PaymentStatus.values());
        paymentBox.setValue(Shipment.PaymentStatus.UNPAID); // Default

        GridPane packageGrid = new GridPane();
        packageGrid.setHgap(15); packageGrid.setVgap(10);
        packageGrid.addRow(0, new Label("Description:"), descField);
        packageGrid.addRow(1, new Label("Amount (PKR):"), amountField);
        packageGrid.addRow(2, new Label("Payment:"), paymentBox);
        packageGrid.addRow(3, new Label("Remarks:"), remarksField);

        Button saveBtn = new Button("Create Shipment");
        saveBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        saveBtn.setPrefWidth(200);
        
        Button returnBtn = new Button("Back");
        
        HBox btns = new HBox(20);
        btns.getChildren().addAll(returnBtn,saveBtn);
        
        Label statusMsg = new Label();

        
        saveBtn.setOnAction(e -> {
            try {
                 if (senderName.getText().isEmpty() || receiverName.getText().isEmpty() || amountField.getText().isEmpty() || receiverAddress.getText().isEmpty() || senderAddress.getText().isEmpty() || remarksField.getText().isEmpty() || descField.getText().isEmpty()) {
                    statusMsg.setText("All fields are required");
                    statusMsg.setTextFill(Color.RED);
                    return;
                }
                 
                double amount = Double.parseDouble(amountField.getText());
                String trackingID = "TRK-" + (1000 + new Random().nextInt(9000));

                // 4. Create Object
                
                controller.CreateShipment (
                    trackingID,
                    senderName.getText(), senderPhone.getText(), senderAddress.getText(),
                    receiverName.getText(), receiverPhone.getText(), receiverAddress.getText(),
                    descField.getText(),
                    amount,
                    paymentBox.getValue(),
                    remarksField.getText()
                );

                statusMsg.setText("Success! Shipment Created: " + trackingID);
                statusMsg.setTextFill(Color.GREEN);

                senderName.clear(); receiverName.clear(); amountField.clear(); descField.clear();

            } catch (NumberFormatException ex) {
                statusMsg.setText("Error: Amount must be a number.");
                statusMsg.setTextFill(Color.RED);
            } catch (Exception ex) {
                statusMsg.setText("Error: " + ex.getMessage());
                statusMsg.setTextFill(Color.RED);
            }
        });
        
        returnBtn.setOnAction(e -> {
            root.setCenter(createShipmentsView());
        });


        content.getChildren().addAll(
            header, 
            new Separator(), senderLabel, senderGrid, 
            new Separator(), receiverLabel, receiverGrid, 
            new Separator(), packageLabel, packageGrid, 
            new Separator(), btns, statusMsg
        );

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        return scroll;
    }
    
    
    private ScrollPane createEditShipmentView(Shipment shipment) {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: white;");

        // Title with Tracking ID
        Label header = new Label("Edit Shipment: " + shipment.getTrackingNumber());
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // --- SENDER ---
        TextField senderName = new TextField(shipment.getSenderName());
        TextField senderPhone = new TextField(shipment.getSenderPhone());
        TextField senderAddress = new TextField(shipment.getSenderAddress());

        GridPane senderGrid = new GridPane();
        senderGrid.setHgap(15); senderGrid.setVgap(10);
        senderGrid.addRow(0, new Label("Sender Name:"), senderName);
        senderGrid.addRow(1, new Label("Sender Phone:"), senderPhone);
        senderGrid.addRow(2, new Label("Sender Addr:"), senderAddress);

        // --- RECEIVER ---
        TextField receiverName = new TextField(shipment.getReceiverName());
        TextField receiverPhone = new TextField(shipment.getReceiverPhone());
        TextField receiverAddress = new TextField(shipment.getDeliveryAddress());

        GridPane receiverGrid = new GridPane();
        receiverGrid.setHgap(15); receiverGrid.setVgap(10);
        receiverGrid.addRow(0, new Label("Receiver Name:"), receiverName);
        receiverGrid.addRow(1, new Label("Receiver Phone:"), receiverPhone);
        receiverGrid.addRow(2, new Label("Delivery Addr:"), receiverAddress);

        // --- STATUS UPDATE ---
        Label statusLabel = new Label("Update Status & History");
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        ComboBox<Shipment.Status> statusBox = new ComboBox<>();
        statusBox.getItems().addAll(Shipment.Status.values());
        statusBox.setValue(shipment.getCurrentStatus());

        TextArea historyLog = new TextArea();
        historyLog.setEditable(false);
        historyLog.setPrefHeight(100);
        
        // Populate History Log
        StringBuilder logText = new StringBuilder();
        for (ShipmentStatus s : shipment.getHistory()) {
            logText.append(s.getUpdatedAt().toString())
                   .append(" - ").append(s.getStatus())
                   .append(": ").append(s.getRemarks()).append("\n");
        }
        historyLog.setText(logText.toString());

        TextField newRemarks = new TextField(); 
        newRemarks.setPromptText("Reason for status change (e.g., 'Arrived at hub')");

        VBox statusBoxContainer = new VBox(10, statusLabel, new Label("Current Status:"), statusBox, newRemarks, new Label("History Log:"), historyLog);
        statusBoxContainer.setStyle("-fx-border-color: #ccc; -fx-padding: 10;");

        // BUTTONS
        Button updateBtn = new Button("💾 Save Changes");
        updateBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> root.setCenter(createShipmentsView())); // Go back

        HBox btnBox = new HBox(15, cancelBtn, updateBtn);
        Label statusMsg = new Label();

        // UPDATE LOGIC
        updateBtn.setOnAction(e -> {
            try {
                // 1. Update Basic Fields
                shipment.setSenderName(senderName.getText());
                shipment.setSenderPhone(senderPhone.getText());
                shipment.setSenderAddress(senderAddress.getText());
                shipment.setReceiverName(receiverName.getText());
                shipment.setReceiverPhone(receiverPhone.getText());
                shipment.setDeliveryAddress(receiverAddress.getText());

                // 2. Handle Status Change
                Shipment.Status newStatus = statusBox.getValue();
                if (newStatus != shipment.getCurrentStatus()) {
                    // If status changed, we MUST add a history entry
                    String remarkText = newRemarks.getText().isEmpty() ? "Status updated." : newRemarks.getText();

                    // Create new history entry
                    ShipmentStatus updateEntry = new ShipmentStatus(newStatus, remarkText);

                    // Update object
                    shipment.appendHistory(updateEntry);
                    shipment.setCurrentStatus(newStatus);
                }

                // 3. Save to File
                ShipmentIO io = new ShipmentIO();
                io.saveOrUpdateShipment(shipment);

                statusMsg.setText("Shipment Updated Successfully!");
                statusMsg.setTextFill(Color.GREEN);

            } catch (Exception ex) {
                statusMsg.setText("Error: " + ex.getMessage());
                statusMsg.setTextFill(Color.RED);
            }
        });

        content.getChildren().addAll(
            header, 
            new Label("Sender Info"), senderGrid, new Separator(),
            new Label("Receiver Info"), receiverGrid, new Separator(),
            statusBoxContainer, 
            new Separator(), btnBox, statusMsg
        );

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        return scroll;
    }
    
}