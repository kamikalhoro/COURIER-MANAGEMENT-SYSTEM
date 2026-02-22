package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Shipment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum Status { PENDING, ASSIGNED, DISPATCHED, DELIVERED, RETURNED, CANCELLED }
    public enum PaymentStatus { PAID, UNPAID, COD }

    private String trackingNumber;
     
    private String senderName;
    private String senderPhone;
    private String senderAddress;
    
    private String receiverName;
    private String receiverPhone;
    private String deliveryAddress;

    private String packageDescription;
    private double totalAmount;
    private PaymentStatus paymentStatus;
    private String remarks;
    
    private Status currentStatus = Status.PENDING;
    private LocalDateTime createdAt = LocalDateTime.now();
    private ArrayList<ShipmentStatus> history = new ArrayList<>();
    
    public Shipment(String trackingNumber, String senderName, String senderPhone, String senderAddress, 
                    String receiverName, String receiverPhone, String deliveryAddress, 
                    String packageDescription, double totalAmount, PaymentStatus paymentStatus, String remarks) {
        
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.senderAddress = senderAddress;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.deliveryAddress = deliveryAddress;
        this.packageDescription = packageDescription;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.remarks = remarks;
        this.history.add(new ShipmentStatus(Status.PENDING, "Shipment created."));
    }
    
// 1. Tracking Number
public String getTrackingNumber() {
    return trackingNumber;
}

// 2. Sender Name
public String getSenderName() {
    return senderName;
    }
public void setSenderName(String senderName) {
    this.senderName = senderName;
    }

// 3. Sender Phone
public String getSenderPhone() {
    return senderPhone;
    }
public void setSenderPhone(String senderPhone) {
    this.senderPhone = senderPhone;
    }

// 4. Sender Address
public String getSenderAddress() {
    return senderAddress;
    }
public void setSenderAddress(String senderAddress) {
    this.senderAddress = senderAddress;
    }

// 5. Receiver Name
public String getReceiverName() {
    return receiverName;
    }
public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
    }

// 6. Receiver Phone
public String getReceiverPhone() {
    return receiverPhone;
    }
public void setReceiverPhone(String receiverPhone) {
    this.receiverPhone = receiverPhone;
    }

// 7. Delivery Address
public String getDeliveryAddress() {
    return deliveryAddress;
    }
public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
    }

// 8. Package Description
public String getPackageDescription() {
    return packageDescription;
    }
public void setPackageDescription(String packageDescription) {
    this.packageDescription = packageDescription;
    }

// 9. Total Amount
public double getTotalAmount() {
    return totalAmount;
    }
public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
    }

// 10. Payment Status
public PaymentStatus getPaymentStatus() {
    return paymentStatus;
    }
public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
    }

// 11. Remarks
public String getRemarks() {
    return remarks;
    }
public void setRemarks(String remarks) {
    this.remarks = remarks;
    }

// 12. Current Status
public Status getCurrentStatus() {
    return currentStatus;
    }
public void setCurrentStatus (Status status) {
    this.currentStatus = status;
    }

// 13. Creation Timestamp
public LocalDateTime getCreatedAt() {
    return createdAt;
    }

// 14. History List
public ArrayList<ShipmentStatus> getHistory() {
    return history;
    }
public void appendHistory(ShipmentStatus updatedStatus) {
    this.history.add(updatedStatus);
    }

}