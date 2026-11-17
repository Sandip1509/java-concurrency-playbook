package com.technologyzer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

// Solution 1: CountDownLatch. The main thread waits until the count reaches zero.
public class Step3CountDownLatch {
    static void main() throws InterruptedException {
        ProductService.resetClock();
        Map<String, String> page= new ConcurrentHashMap<>();
        CountDownLatch latch = new CountDownLatch(4);
        startTask("stock", ProductService::fetchStock, page, latch);
        startTask("price", ProductService::fetchPrice, page, latch);
        startTask("reviews", ProductService::fetchReviews, page, latch);
        startTask("offers", ProductService::fetchOffers, page, latch);
        ProductService.log("waiting for all 4 tasks...");
        latch.await();
        ProductService.log("sending response: " + page);
    }
    private static void startTask(String key, Supplier<String> call,
                                            Map<String, String> page, CountDownLatch latch){
        new Thread(() -> {
            try{
                page.put(key, call.get());
            } finally {
                latch.countDown();
            }
        }, key +"-thread").start();
    }
}
