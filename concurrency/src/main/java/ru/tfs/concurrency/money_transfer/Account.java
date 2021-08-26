package ru.tfs.concurrency.money_transfer;

public class Account {

    private int cacheBalance;
    private int uniqueId;

    public Account(int cacheBalance) {
        this.cacheBalance = cacheBalance;
    }

    public Account(int cacheBalance, int uniqueId) {
        this.cacheBalance = cacheBalance;
        this.uniqueId = uniqueId;
    }

    public void addMoney(int money) {
        this.cacheBalance += money;
    }

    public boolean takeOffMoney(int money) {
        if (this.cacheBalance < money) {
            return false;
        }

        this.cacheBalance -= money;
        return true;
    }

    public int getCacheBalance() {
        return cacheBalance;
    }

    public int getUniqueId() {
        return uniqueId;
    }
}
