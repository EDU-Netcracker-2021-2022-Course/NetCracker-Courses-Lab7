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
            if (!clients.isEmpty()) {
                try {
                    serveClient();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            } else {
                synchronized (clients) {
                    try {
                        clients.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                continue;
            }
        }
    }

    public void pushClient(BankClient bankClient) {
        synchronized (clients) {
            clients.add(bankClient);
            clients.notify();
            System.out.println("Клиент был добавлен в очередь к оператору " + this.getName());
        }
    }

    public BankClient popClient() {
        BankClient client = null;

        synchronized (clients) {
            if (!clients.isEmpty()) {
                client = clients.get(0);
                clients.remove(0);
            }
        }

        return client;
    }

    public void popClient(BankClient client) {
        synchronized (clients) {
            if (!clients.isEmpty()) {
                clients.remove(client);
            }
        }
    }

    public int getRowSize() {
        return clients.size();
    }

    public void serveClient() throws InterruptedException {
        synchronized (cashDesk) {
            if (!clients.isEmpty()) {
                System.out.println("Обслуживание нового клиента. \nДенег в кассе перед началом операции: $" + cashDesk.getCashValue());

                BankClient client = popClient();

                switch (client.getOpType()) {
                    case PUSH_CASH:
                        cashDesk.pushCash(client.getOpCashValue());
                        System.out.println("\nКлиент внес $" + client.getOpCashValue() + " операционисту " + this.getName());
                        System.out.println("\nДенег в кассе после операции пополнения: $" + cashDesk.getCashValue());
                        this.sleep((long) client.getOpDuration());
                        popClient(client);
                        break;
                    case PULL_CASH:
                        if (cashDesk.getCashValue() - client.getOpCashValue() >= 0) {
                            cashDesk.pullCash(client.getOpCashValue());
                            System.out.println("\nКлиент снял $" + client.getOpCashValue() + " c помощью операциониста " + this.getName());
                            System.out.println("\nДенег в кассе после операции снятия: $" + cashDesk.getCashValue());
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
