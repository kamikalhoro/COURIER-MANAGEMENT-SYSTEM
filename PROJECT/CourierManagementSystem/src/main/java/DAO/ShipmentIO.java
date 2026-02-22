package DAO;

import model.Shipment;
import java.util.ArrayList;
import model.Shipment.Status;

public class ShipmentIO {

    private static final String file_path = "data/shipments.ser";
    private final ObjectFileIO<Shipment> serializer = new ObjectFileIO<>(file_path);
    
    private ArrayList<Shipment> shipments = new ArrayList<>();

public ShipmentIO() {
        // 1. Load existing data
        ArrayList<Shipment> loadedData = serializer.load();
        
        // Safety check for null
        if (loadedData == null) {
            this.shipments = new ArrayList<>();
        } else {
            this.shipments = loadedData;
        }

        if (this.shipments.isEmpty()) {
            System.out.println("No shipments found. Creating 10 dummy entries...");

            // 1. Pending / Paid
            this.shipments.add(new Shipment("TRK-1001", "Alice Smith", "0300-1234567", "123 Main St, New York",
                    "Bob Jones", "0333-9876543", "456 Market Rd, Boston",
                    "Laptop - Dell XPS", 1500.00, Shipment.PaymentStatus.PAID, "Handle with care"));

            // 2. Pending / COD
            this.shipments.add(new Shipment("TRK-1002", "John Doe", "0321-1112223", "789 Oak Ave, Chicago",
                    "Jane Doe", "0345-4445556", "321 Pine Ln, Seattle",
                    "Legal Documents", 25.50, Shipment.PaymentStatus.COD, "Urgent Delivery"));

            // 3. Dispatched / Unpaid
            Shipment s3 = new Shipment("TRK-1003", "Tech Corp", "0300-0000000", "Silicon Valley, CA",
                    "StartUp Inc", "0311-1111111", "Austin, TX",
                    "Server Rack Equipment", 5000.00, Shipment.PaymentStatus.UNPAID, "Heavy Load");
            s3.setCurrentStatus(Shipment.Status.DISPATCHED); // Manually setting status for variety
            this.shipments.add(s3);

            // 4. Delivered / Paid
            Shipment s4 = new Shipment("TRK-1004", "Sarah Connor", "0301-2223334", "Los Angeles, CA",
                    "Kyle Reese", "0312-3334445", "Unknown Location",
                    "Protective Gear", 300.00, Shipment.PaymentStatus.PAID, "Leave at front door");
            s4.setCurrentStatus(Shipment.Status.DELIVERED);
            this.shipments.add(s4);

            // 5. Assigned / COD
            Shipment s5 = new Shipment("TRK-1005", "Mario Rossi", "0340-5556667", "Little Italy, NYC",
                    "Luigi Verde", "0341-7778889", "Brooklyn, NY",
                    "Plumbing Tools", 120.75, Shipment.PaymentStatus.COD, "Call upon arrival");
            s5.setCurrentStatus(Shipment.Status.ASSIGNED);
            this.shipments.add(s5);

            // 6. Cancelled / Unpaid
            Shipment s6 = new Shipment("TRK-1006", "Evil Corp", "0300-6666666", "Metropolis",
                    "Lex Luthor", "0300-7777777", "Gotham City",
                    "Kryptonite Sample", 9999.99, Shipment.PaymentStatus.UNPAID, "Fragile - Do not drop");
            s6.setCurrentStatus(Shipment.Status.CANCELLED);
            this.shipments.add(s6);

            // 7. Returned / COD
            Shipment s7 = new Shipment("TRK-1007", "Grandma Jenkins", "0355-8889990", "Countryside Farm",
                    "Billy Kid", "0355-0001112", "City Center Apts",
                    "Homemade Jam", 15.00, Shipment.PaymentStatus.COD, "Glass Jars");
            s7.setCurrentStatus(Shipment.Status.RETURNED);
            this.shipments.add(s7);

            // 8. Pending / Paid
            this.shipments.add(new Shipment("TRK-1008", "Office Supplies Co.", "0322-4445556", "Warehouse 42",
                    "Dunder Mifflin", "0322-9998887", "Scranton, PA",
                    "A4 Paper (50 Boxes)", 450.00, Shipment.PaymentStatus.PAID, "Deliver to Reception"));

            // 9. Dispatched / COD
            Shipment s9 = new Shipment("TRK-1009", "Gym Shark", "0333-2221110", "Fitness Blvd",
                    "Arnold S.", "0333-5556667", "Gold's Gym",
                    "Weights Set (50kg)", 200.00, Shipment.PaymentStatus.COD, "Heavy Item");
            s9.setCurrentStatus(Shipment.Status.DISPATCHED);
            this.shipments.add(s9);

            // 10. Pending / Unpaid
            this.shipments.add(new Shipment("TRK-1010", "Book Barn", "0311-8884444", "Library Lane",
                    "Hermione G.", "0311-2223333", "Hogwarts",
                    "Spell Books", 85.50, Shipment.PaymentStatus.UNPAID, "Keep dry"));

            // 3. Save this new data to the file immediately
            serializer.save(this.shipments);
        }
    }

    public ArrayList<Shipment> getAllShipments() {
        return shipments;
    }

  
    public Shipment findByTrackingNumber(String trackingNumber) {
        
        for (Shipment shipment : shipments) {
            if (shipment.getTrackingNumber().equals(trackingNumber)) {
                return shipment;
            }
        }  
        return null;
        
    }
    
    
    public void saveOrUpdateShipment(Shipment shipment) {
        // Remove the old version if it exists (Specifically when Updating)
        shipments.removeIf(s -> s.getTrackingNumber().equals(shipment.getTrackingNumber()));
        
        shipments.add(shipment);
        serializer.save(shipments);
    }
    
    
    public void deleteShipment(String trackingNumber) {
        if (shipments.removeIf(s -> s.getTrackingNumber().equals(trackingNumber))) {
            serializer.save(shipments);
        }
    }
    
    
    public ArrayList<Shipment> filterByStatus(Status status) {
       ArrayList<Shipment> filteredShipments = new ArrayList<>();
       for (Shipment shipment : shipments) {
           if (((shipment.getCurrentStatus()).name()).equals(status.name())) {
               filteredShipments.add(shipment);
           }
       }
       return filteredShipments;
    }
    
}
