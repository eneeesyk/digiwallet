package com.digital.wallet.project.pojos;

import java.util.UUID;

public class TransferDepositFailedPayload {
    
    private UUID transferId;

    private String reason;

    public TransferDepositFailedPayload(UUID transferId, String reason) {
        this.transferId = transferId;
        this.reason = reason;
    }

    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
