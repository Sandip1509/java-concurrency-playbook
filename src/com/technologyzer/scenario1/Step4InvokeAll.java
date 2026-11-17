package com.technologyzer.scenario1;

import java.util.List;
import java.util.concurrent.*;

// Solution 2: ExecutorService.invokeAll. It submits all tasks and returns only when all are done.
public class Step4InvokeAll {
    static void main() {
        ProductService.resetClock();
        List<Callable<String>> tasks = List.of(
                ProductService::fetchStock,
                ProductService::fetchPrice,
                ProductService::fetchReviews,
                ProductService::fetchOffers
        );
        try(ExecutorService pool = Executors.newFixedThreadPool(4,
                Thread.ofPlatform().name("worker-", 1).factory())){
            List<Future<String>> results = pool.invokeAll(tasks);
            ProductService.log("all done, building response.");
            for(Future<String> result: results){
                ProductService.log(" "+ result.get());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
