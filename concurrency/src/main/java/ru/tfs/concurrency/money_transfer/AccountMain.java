package ru.tfs.concurrency.money_transfer;

import java.util.concurrent.CompletableFuture;

public class AccountMain {

    public static void main(String[] args) {

        //Тестирования просто лока (плохо будет работать с несколькими клинтами, так как лок один)
        Account firstAccount = new Account(100_000, 1);
        Account secondAccount = new Account(100_000, 2);

        AccountThreadLock firstThreadLock = new AccountThreadLock(firstAccount, secondAccount, 100);
        AccountThreadLock secondThreadLock = new AccountThreadLock(secondAccount, firstAccount, 100);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(firstThreadLock),
                CompletableFuture.runAsync(secondThreadLock)
        ).join();

        System.out.println("AccountThreadLock test");
        System.out.println("Cash balance of the first account: " + firstAccount.getCacheBalance());
        System.out.println("Cash balance of the second account: " + secondAccount.getCacheBalance());

        if (!(firstAccount.getCacheBalance() == 100_000 && secondAccount.getCacheBalance() == 100_000)) {
            throw new RuntimeException("Что не то с балансом");
        }

        //Тестирование захвата двух ресурсов в строго определенном порядке
        //Теперь транзакции от Пети к Васи, не будут мешать транзакциям от Марине Коле
        //Так как порядок строго задан, то никогда не возникнет дедлок
        AccountThreadMaintainOrder firstThreadOrdered = new AccountThreadMaintainOrder(firstAccount, secondAccount, 100);
        AccountThreadMaintainOrder secondThreadOrdered = new AccountThreadMaintainOrder(secondAccount, firstAccount, 100);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(secondThreadOrdered),
                CompletableFuture.runAsync(firstThreadOrdered)
        ).join();

        System.out.println("AccountThreadLock test");
        System.out.println("Cash balance of the first account: " + firstAccount.getCacheBalance());
        System.out.println("Cash balance of the second account: " + secondAccount.getCacheBalance());

        if (!(firstAccount.getCacheBalance() == 100_000 && secondAccount.getCacheBalance() == 100_000)) {
            throw new RuntimeException("Что не то с балансом");
        }

        //Создание deadlock
        AccountThreadDeadLock firstThreadDead = new AccountThreadDeadLock(firstAccount, secondAccount, 100);
        AccountThreadDeadLock secondThreadDead = new AccountThreadDeadLock(secondAccount, firstAccount, 100);

        //Если запускать так, дедлок тоже получиться, но будет не такой наглядный, так как у потов нельзя будет
        //посмотреть состояние
//        CompletableFuture.allOf(
//                CompletableFuture.runAsync(firstThreadDead),
//                CompletableFuture.runAsync(secondThreadDead)
//        );

        firstThreadDead.start();
        secondThreadDead.start();

        try {
            //ждем, чтобы убедиться, что потоки в deadlock
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("AccountThreadDeadLock test");

        //Проверяем, что обе нити в состоянии безнадежном BLOCKED
        if (firstThreadDead.getState().equals(Thread.State.BLOCKED) && secondThreadDead.getState().equals(Thread.State.BLOCKED)) {
            System.out.println("Deadlock удался");
            Runtime.getRuntime().exit(0);
        } else {
            System.out.println("Deadlock НЕ удался");
            Runtime.getRuntime().exit(-1);
        }

    }

}