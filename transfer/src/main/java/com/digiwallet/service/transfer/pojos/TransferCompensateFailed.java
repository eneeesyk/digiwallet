package com.digiwallet.service.transfer.pojos;

import java.util.UUID;

public class TransferCompensateFailed {
    private UUID transferId;

    public TransferCompensateFailed(UUID transferId) {
        this.transferId = transferId;
    }

    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

}
