package model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BankOperator extends Thread {

    private List<BankClient> clients;
    private CashDesk cashDesk;
    private Logger logger;

    public BankOperator() {
        clients = new ArrayList<>();
        cashDesk = CashDesk.getInstance();
        logger = Logger.getLogger("Logger: BankOperator");
    }

    @Override
    public void run() {
        logger.info("Оператор " + this.getName() + " начал работу.");

        while (true) {
            if (!clientListIsEmpty()) {
                try {
                    serveClient();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pushClient(BankClient bankClient) {
        clients.add(bankClient);
        System.out.println("Клиент был добавлен в очередь к оператору " + this.getName());
    }

    public BankClient popClient() {
        BankClient client = null;

        if (!clientListIsEmpty()) {
            client = clients.get(0);
            clients.remove(0);
        }

        return client;
    }

    public void popClient(BankClient client) {
        if (!clientListIsEmpty()) {
            clients.remove(client);
        }
    }

    public int getRowSize() {
        return clients.size();
    }

    public synchronized boolean clientListIsEmpty() {
        if (clients.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            this.notify();
            return false;
        }
    }

    public void serveClient() throws InterruptedException {
        synchronized (cashDesk) {
            if (!clientListIsEmpty()) {
                System.out.println("Обслуживание нового клиента. \nДенег в кассе перед началом операции: $" + cashDesk.getCashValue());

                BankClient client = popClient();

                switch (client.getOpType()) {
                    case PUSH_CASH:
                        cashDesk.pushCash(client.getOpCashValue());
                        System.out.println("Клиент внес $" + client.getOpCashValue());
                        System.out.println("Денег в кассе после операции пополнения: $" + cashDesk.getCashValue());
                        this.sleep((long) client.getOpDuration());
                        popClient(client);
                        break;
                    case PULL_CASH:
                        if (cashDesk.getCashValue() - client.getOpCashValue() >= 0) {
                            cashDesk.pullCash(client.getOpCashValue());
                            System.out.println("Клиент снял $" + client.getOpCashValue());
                            System.out.println("Денег в кассе после операции снятия: $" + cashDesk.getCashValue());
                            this.sleep((long) client.getOpDuration());
                            popClient(client);
                        } else {
                            popClient(client);
                            System.out.println("Для снятия $" + client.getOpCashValue() + " в кассе недостаточно наличных. В операции клиенту отказано.");
                        }
                        break;
                }
            }
        }
    }
}
