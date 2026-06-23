package com.digital.wallet.project.pojos;

import java.util.UUID;

public class TransferCompensateCompleted {

    private UUID transferId;

    public TransferCompensateCompleted(UUID transferId) {
        this.transferId = transferId;
    }

    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }
}
