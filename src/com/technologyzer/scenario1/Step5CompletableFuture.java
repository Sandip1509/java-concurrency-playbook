package com.technologyzer.scenario1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Solution 3: CompletableFuture.allOf, with a timeout and a fallback like real production code.
public class Step5CompletableFuture {
    static void main() {
        ProductService.resetClock();

        try(ExecutorService pool = Executors.newFixedThreadPool(4,
                Thread.ofPlatform().name("worker-", 1).factory())){
            CompletableFuture<String> price = CompletableFuture.supplyAsync(ProductService::fetchPrice, pool);
            CompletableFuture<String> stock = CompletableFuture.supplyAsync(ProductService::fetchStock, pool);
            CompletableFuture<String> reviews = CompletableFuture
                    .supplyAsync(ProductService::fetchReviews, pool)
                    .completeOnTimeout("reviews unavailable", 450, TimeUnit.MILLISECONDS);
            CompletableFuture<String> offers = CompletableFuture
                    .supplyAsync(Step5CompletableFuture::fetchOffersThatFail, pool)
                    .exceptionally(error -> "no offers");

            CompletableFuture.allOf(stock, price, reviews, offers).join();
            ProductService.log("Sending response: [stock: " + stock.join() +
                                    ", price: "+price.join() +
                                    ", reviews: " + reviews.join() +
                                    ", offers: "+ offers.join()+"]");

        }
    }
    private static String fetchOffersThatFail(){
        ProductService.log("fetching offers ...");
        throw new IllegalStateException("Offer service is down.");
    }
}
