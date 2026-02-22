package controller;

import DAO.ShipmentIO;
import model.Shipment;
import model.Shipment.PaymentStatus;

public class ShipmentController {
    public void CreateShipment
        (String trackingNumber, String senderName, String senderPhone, String senderAddress, 
        String receiverName, String receiverPhone, String deliveryAddress, 
        String packageDescription, double totalAmount, PaymentStatus paymentStatus, String remarks) {
                
        Shipment newShipment = new Shipment(trackingNumber, senderName, senderPhone, senderAddress, receiverName, receiverPhone, deliveryAddress, packageDescription, totalAmount, paymentStatus, remarks);

        ShipmentIO shipmentIO = new ShipmentIO();
        shipmentIO.saveOrUpdateShipment(newShipment);                
    }
}
