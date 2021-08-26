package ru.tfs.concurrency.money_transfer;

public class AccountThreadDeadLock extends Thread {

    private final Account firstAccount;
    private final Account  secondAccount;
    private int money;

    public AccountThreadDeadLock(Account firstAccount, Account secondAccount, int money) {
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
        this.money = money;
    }

    @Override
    public void run() {
        synchronized (firstAccount) {
            try {
                sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (secondAccount) {
                if (firstAccount.takeOffMoney(money)) {
                    secondAccount.addMoney(money);
                }
            }
        }
    }
}

