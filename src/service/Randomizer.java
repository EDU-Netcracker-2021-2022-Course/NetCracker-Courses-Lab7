package service;

import enums.OperationType;

import java.util.List;
import java.util.Random;

public abstract class Randomizer {

    public static double generate(double minValue, double maxValue) {
        Random r = new Random();
        return minValue + (maxValue - minValue) * r.nextDouble();
    }

    public static OperationType generate() {
        List<OperationType> VALUES = OperationType.getValues();
        int SIZE = OperationType.getSize();
        Random RANDOM = new Random();

        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
