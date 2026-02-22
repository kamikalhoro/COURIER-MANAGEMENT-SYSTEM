package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import static model.Shipment.Status;

public class ShipmentStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private Status status;
    private String remarks;
    private LocalDateTime updatedAt;
    

    public ShipmentStatus (Status status, String remarks) {
        this.status = status;
        this.remarks = remarks;
        this.updatedAt = LocalDateTime.now();
    }
    
// 1. Status
    public Status getStatus() {
        return status;
    }

// 2. Remarks
    public String getRemarks() {
        return remarks;
    }

// 3. Updated At (Timestamp)
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
