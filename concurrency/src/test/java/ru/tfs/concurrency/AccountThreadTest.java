package ru.tfs.concurrency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.tfs.concurrency.money_transfer.Account;
import ru.tfs.concurrency.money_transfer.AccountThreadDeadLock;
import ru.tfs.concurrency.money_transfer.AccountThreadLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountThreadTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    @Test
    public void AccountThreadDeadlockTest() {
        Account german_greff = new Account(100_000);
        Account kolyan_oboro = new Account(100);

        AccountThreadDeadLock zarplata = new AccountThreadDeadLock(german_greff, kolyan_oboro, 100);
        AccountThreadDeadLock ipoteka = new AccountThreadDeadLock(kolyan_oboro, german_greff, 100);

        zarplata.setDaemon(false);
        ipoteka.setDaemon(false);

        zarplata.start();
        ipoteka.start();

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(Thread.State.BLOCKED, zarplata.getState());
        Assertions.assertEquals(Thread.State.BLOCKED, ipoteka.getState());
    }

    @Test
    public void accountThreadLockTest() {

        Account german_greff = new Account(100_000);
        Account oleg_tinkoff = new Account(100_000);
        Account igor_sechin = new Account(100_000);
        Account arkady_rotenberg = new Account(100_000);
        Account dmitry_rogozin = new Account(100_000);
        Account elon_musk = new Account(100_000);
        Account kolyan_oboro = new Account(100);
        Account Vladimir_Vladimirovich = new Account(100_000);

        List<Thread> plotiNalogi = new ArrayList<>(600);

        for(int i = 0; i < 100; i++) {
            plotiNalogi.add(new AccountThreadLock(german_greff, Vladimir_Vladimirovich, 100));
            plotiNalogi.add(new AccountThreadLock(oleg_tinkoff, Vladimir_Vladimirovich, 100));
            plotiNalogi.add(new AccountThreadLock(igor_sechin, Vladimir_Vladimirovich, 100));
            plotiNalogi.add(new AccountThreadLock(arkady_rotenberg, Vladimir_Vladimirovich, 100));
            plotiNalogi.add(new AccountThreadLock(dmitry_rogozin, Vladimir_Vladimirovich, 100));
            plotiNalogi.add(new AccountThreadLock(elon_musk, Vladimir_Vladimirovich, 100));
            plotiNalogi.add(new AccountThreadLock(kolyan_oboro, Vladimir_Vladimirovich, 100));
        }

//        CompletableFuture.allOf(
//                CompletableFuture.runAsync(firstThreadDead),
//                CompletableFuture.runAsync(secondThreadDead)
//        );

        plotiNalogi.forEach(Thread::start);
        try {
            for (Thread thread : plotiNalogi) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(90000, german_greff.getCacheBalance());
        Assertions.assertEquals(90000, oleg_tinkoff.getCacheBalance());
        Assertions.assertEquals(90000, igor_sechin.getCacheBalance());
        Assertions.assertEquals(90000, arkady_rotenberg.getCacheBalance());
        Assertions.assertEquals(90000, dmitry_rogozin.getCacheBalance());
        Assertions.assertEquals(90000, elon_musk.getCacheBalance());

        Assertions.assertEquals(0, kolyan_oboro.getCacheBalance());

        Assertions.assertEquals(160100, Vladimir_Vladimirovich.getCacheBalance());

    }


}
