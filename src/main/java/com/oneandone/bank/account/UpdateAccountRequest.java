package com.oneandone.bank.account;

public class UpdateAccountRequest {
    public enum Operation {
        DEPOSIT, REDRAW
    }
    private Operation operation;
    private Double amount;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
