package ru.tfs.concurrency.robot;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class Leg implements Runnable {

    private static final AtomicBoolean isLeftWasLast = new AtomicBoolean();

    private final String name;
    private final boolean booleanLeg;

    public Leg(String name, boolean booleanLeg) {
        this.name = name;
        this.booleanLeg = booleanLeg;
    }
    @Override
    public void run() {
        while (true) {
            while (booleanLeg != isLeftWasLast.get());
            System.out.println(name);
            isLeftWasLast.set(!booleanLeg);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.allOf(
                CompletableFuture.runAsync(new Leg("left", true)),
                CompletableFuture.runAsync(new Leg("right", false))
        ).join();
    }


}