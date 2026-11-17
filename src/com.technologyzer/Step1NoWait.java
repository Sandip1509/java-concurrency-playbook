package com.technologyzer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// The bug: we start the threads but never wait for them.
public class Step1NoWait {
    static void main() {
        ProductService.resetClock();
        Map<String, String> page = new ConcurrentHashMap<>();
        new Thread(() -> page.put("price", ProductService.fetchPrice()), "price-thread").start();
        new Thread(() -> page.put("stock", ProductService.fetchStock()), "stock-thread").start();
        new Thread(() -> page.put("reviews", ProductService.fetchReviews()), "reviews-thread").start();
        new Thread(() -> page.put("offers", ProductService.fetchOffers()), "offers-thread").start();
        ProductService.log("Sending response "+ page);
    }
}
