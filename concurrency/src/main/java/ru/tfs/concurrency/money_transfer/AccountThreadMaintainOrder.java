package ru.tfs.concurrency.money_transfer;

public class AccountThreadMaintainOrder extends Thread {

    private final Account firstAccount;
    private final Account secondAccount;
    private int money;

    private final Account recipient;
    private final Account seller;

    public AccountThreadMaintainOrder(Account firstAccount, Account secondAccount, int money) {
        if (firstAccount.getUniqueId() > secondAccount.getUniqueId()) {
            this.firstAccount = firstAccount;
            this.secondAccount = secondAccount;
        } else {
            this.firstAccount = secondAccount;
            this.secondAccount = firstAccount;
        }

        this.seller = firstAccount;
        this.recipient = secondAccount;

        this.money = money;
    }

    @Override
    public void run() {
        //в данном случае спин локи будут хуже, так как предполагаемая блокировка длиннее, чем смена контекста потоков
        synchronized (firstAccount) {
            synchronized (secondAccount) {
                if (seller.takeOffMoney(money)) {
                    recipient.addMoney(money);
                }
            }
        }
    }
}
