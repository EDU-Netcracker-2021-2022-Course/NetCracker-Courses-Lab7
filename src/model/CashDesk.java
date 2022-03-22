package model;

import interfaces.ICashDeskOperator;
import service.Randomizer;

public class CashDesk implements ICashDeskOperator {

    private static volatile CashDesk instance;
    private static final double minCashValue = 1000;
    private static final double maxCashValue = 100000000;
    private double cashValue;

    public static CashDesk getInstance() {
        CashDesk localInstance = instance;

        if (localInstance == null) {
            synchronized (CashDesk.class) {
                localInstance = instance;

                if (localInstance == null) {
                    instance = localInstance = new CashDesk();
                    instance.cashValue = Randomizer.generate(minCashValue, maxCashValue);
                }
            }
        }
        return  localInstance;
    }

    public double getCashValue() {
        return cashValue;
    }

    @Override
    public void pushCash(double cashValue) {
        this.cashValue += cashValue;
    }

    @Override
    public void pullCash(double cashValue) {
        if (this.cashValue >= cashValue) {
            this.cashValue -= cashValue;
        }
    }
}
