package com.digiwallet.service.transfer.pojos;

import java.util.UUID;

public class TransferDepositCompletedPayload {

    private UUID transferId;

    public TransferDepositCompletedPayload(UUID transferId) {
        this.transferId = transferId;
    }

    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }
    
}
