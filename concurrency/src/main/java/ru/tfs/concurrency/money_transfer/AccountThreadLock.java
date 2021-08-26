package ru.tfs.concurrency.money_transfer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//плохо, если будет больще 2ух аккаунтов
public class AccountThreadLock extends Thread {

    private static Lock lock = new ReentrantLock();

    private final Account firstAccount;
    private final Account  secondAccount;
    private int money;

    public AccountThreadLock(Account firstAccount, Account secondAccount, int money) {
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
        this.money = money;
    }

    @Override
    public void run() {
        lock.lock();
        if (firstAccount.takeOffMoney(money)) {
            secondAccount.addMoney(money);
        }
        lock.unlock();
    }
}

