package com.example.demo;


import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompleteFutureTest {

    public static final ExecutorService executor = Executors.newFixedThreadPool(10);


    private static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }


    public Future<String> calculateAsyncWithCancellation() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.cancel(false);
            return null;
        });

        return completableFuture;
    }

    @Test
    public void testRunAsync() {

        CompletableFuture.runAsync(() -> {

        });
        CompletableFuture.runAsync(() -> {

        }, executor);


    }

    @Test
    public void testSupplyAsync() {
        CompletableFuture.supplyAsync(() -> {
            return null;
        });
        CompletableFuture.supplyAsync(() -> {
            return null;
        }, executor); 
    }

}
