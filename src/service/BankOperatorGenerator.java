package service;

import model.BankOperator;

import java.util.ArrayList;
import java.util.List;

public class BankOperatorGenerator {

    public List<BankOperator> generate(int count) {
        List<BankOperator> operatorList = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            BankOperator newOperator = new BankOperator();
            operatorList.add(newOperator);
            newOperator.start();
        }

        System.out.println("");
        return operatorList;
    }
}
