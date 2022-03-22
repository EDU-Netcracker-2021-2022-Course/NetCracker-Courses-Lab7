package model;

import service.BankOperatorGenerator;
import service.ClientGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class Bank {
    private CashDesk cashDesk = CashDesk.getInstance();

    private List<BankOperator> operatorList;

    private ClientGenerator clientGenerator;

    private BankOperatorGenerator bankOperatorGenerator;

    private static int bankOperatorsCount = 10;

    private Logger logger;

    private boolean bankIsWorking;

    public Bank() {
        bankIsWorking = true;
        logger = Logger.getLogger("Logger: Bank class");
        clientGenerator = new ClientGenerator();
        bankOperatorGenerator = new BankOperatorGenerator();
        operatorList = new ArrayList<>(bankOperatorsCount);
        operatorList.addAll(bankOperatorGenerator.generate(bankOperatorsCount));
        clientGenerator.start();
    }

    public void open() {
        while(bankIsWorking) {
            BankClient client = clientGenerator.generate();

            if (client != null) {
                clientGenerator.pauseClientGenerating();
                operatorList.sort(Comparator.comparingInt(BankOperator::getRowSize));
                operatorList.get(0).pushClient(client);
            }

            for (BankOperator operator: operatorList) {
                System.out.println("Оператор " + operator.getName() + " ожидает в очереди " + operator.getRowSize() + " клиентов.");
            }
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.open();
    }
}