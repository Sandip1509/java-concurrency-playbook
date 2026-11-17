package com.technologyzer.scenario1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Correct but slow: one call after another.
public class Step2Sequential {
    static void main() {
        ProductService.resetClock();
        Map<String, String> page = new ConcurrentHashMap<>();

        page.put("stock", ProductService.fetchStock());
        page.put("price", ProductService.fetchPrice());
        page.put("reviews", ProductService.fetchReviews());
        page.put("offers", ProductService.fetchOffers());

        ProductService.log("Sending response: "+ page);

    }
}
