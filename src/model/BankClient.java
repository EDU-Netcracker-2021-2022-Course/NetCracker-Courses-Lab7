package model;

import enums.OperationType;

import java.util.Objects;

public class BankClient {

    private OperationType opType;
    private double opCashValue;
    private double opDuration;

    public BankClient(OperationType opType, double opCashValue, double opTimeDuration) {
        this.opType = opType;
        this.opCashValue = opCashValue;
        this.opDuration = opTimeDuration;
    }

    public BankClient() {
    }

    public OperationType getOpType() {
        return opType;
    }

    public void setOpType(OperationType opType) {
        this.opType = opType;
    }

    public double getOpCashValue() {
        return opCashValue;
    }

    public void setOpCashValue(double opCashValue) {
        this.opCashValue = opCashValue;
    }

    public double getOpDuration() {
        return opDuration;
    }

    public void setOpDuration(double opDuration) {
        this.opDuration = opDuration;
    }

    @Override
    public String toString() {
        return "BankClient{" +
                "opType=" + opType +
                ", opCashValue=" + opCashValue +
                ", opTimeDuration=" + opDuration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankClient)) return false;
        BankClient that = (BankClient) o;
        return Double.compare(that.getOpCashValue(), getOpCashValue()) == 0 && Double.compare(that.getOpDuration(), getOpDuration()) == 0 && getOpType() == that.getOpType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOpType(), getOpCashValue(), getOpDuration());
    }
}
