package com.digiwallet.service.transfer.pojos;

import java.util.UUID;

public class TransferWithdrawFailedPayload {
    
    private UUID transferId;

    private String reason;

    public TransferWithdrawFailedPayload(UUID transferId, String reason) {
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
