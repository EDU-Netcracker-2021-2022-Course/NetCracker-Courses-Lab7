package service;

import enums.OperationType;
import model.BankClient;

public class ClientGenerator extends Thread {
    private static final double minCashValue = 10;
    private static final double maxCashValue = 1000000;
    private static final double minDurationValue = 1;
    private static final double maxDurationValue = 20;
    private static final int CLIENTS_PER_MINUTE = 5;

    public BankClient generate() {
        BankClient client = new BankClient();
        client.setOpCashValue(getRandomCashValue(minCashValue, maxCashValue));
        client.setOpDuration(getRandomOperationDuration(minDurationValue, maxDurationValue));
        client.setOpType(getRandomOperationType());
        System.out.println("\nНовый клиент. Операция: " + client.getOpType() + ", размер кеша: " + client.getOpCashValue() + ", время обслуживания: " + client.getOpDuration() + "\n");
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

    public void pauseClientGenerating(){
        try {
            Thread.sleep((long) (60000/CLIENTS_PER_MINUTE * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {

        }
    }
}
