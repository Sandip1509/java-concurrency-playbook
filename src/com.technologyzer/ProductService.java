package com.technologyzer;
public class ProductService {
    private static long start = System.currentTimeMillis();

    public static void resetClock(){
        start = System.currentTimeMillis();
    }
    public static void log(String message){
        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("[%3d ms] %s : %s\n", elapsed, Thread.currentThread().getName(), message);
    }

    private static void callTakes(long millis){
        try{
            Thread.sleep(millis);
        } catch(InterruptedException ex){
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        }
    }

    public static String fetchPrice(){
        log("fetching price....");
        callTakes(400);
        log("price fetched");
        return "Rs 1999";
    }

    public static String fetchStock(){
        log("fetching stock....");
        callTakes(300);
        log("Stock fetched.");
        return "In Stock";
    }

    public static String fetchReviews(){
        log("fetching reviews....");
        callTakes(500);
        log("Reviews fetched.");
        return "4.5 stars";
    }

    public static String fetchOffers(){
        log("fetching offers....");
        callTakes(200);
        log("Offer fetched");
        return "10% off";
    }
}
