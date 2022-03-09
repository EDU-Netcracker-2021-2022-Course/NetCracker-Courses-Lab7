package service;

import enums.OperationType;
import model.BankClient;

public abstract class ClientGenerator {
    private static final double minCashValue = 10;
    private static final double maxCashValue = 1000000;
    private static final double minDurationValue = 1;
    private static final double maxDurationValue = 20;

    public static BankClient generate() {
        BankClient client = new BankClient();
        client.setOpCashValue(getRandomCashValue(minCashValue, maxCashValue));
        client.setOpDuration(getRandomOperationDuration(minDurationValue, maxDurationValue));
        client.setOpType(getRandomOperationType());

        return client;
    }

    private static OperationType getRandomOperationType() {
        return Randomizer.generate();
    }

    private static double getRandomCashValue(double minValue, double maxValue) {
        return Randomizer.generate(minValue, maxValue);
    }

    private static double getRandomOperationDuration(double minValue, double maxValue) {
        return Randomizer.generate(minValue, maxValue);
    }
}
