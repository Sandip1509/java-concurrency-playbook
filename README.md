# ⚡ Java Concurrency Playbook

> Real-world concurrency problems solved with clean, idiomatic Java code.

---

## 📖 About

This repository walks through one production-style concurrency problem: a product page that needs to call four independent backend services (price, stock, reviews, offers) and assemble their results into a single response.

The `scenario1` package shows the same problem solved five different ways, in order:

1. 🚨 `Step1NoWait` — starts the four calls on separate threads but never waits for them, so the response is built before the results arrive (the bug).
2. 🐢 `Step2Sequential` — calls the four services one after another; correct, but slow.
3. ✅ `Step3CountDownLatch` — runs the calls concurrently and uses a `CountDownLatch` to wait for all of them.
4. ✅ `Step4InvokeAll` — runs the calls concurrently via `ExecutorService.invokeAll`.
5. ✅ `Step5CompletableFuture` — runs the calls concurrently with `CompletableFuture.allOf`, adding a timeout fallback for the reviews call and exception handling for a failing offers call.

`ProductService` backs all five steps: it simulates the four backend calls with artificial latency and provides a small `resetClock`/`log` utility for timing output.

---

## 📂 Repository Structure

```
java-concurrency-playbook/
├── src/
│   └── com/
│       └── technologyzer/
│           └── scenario1/
│               ├── ProductService.java
│               ├── Step1NoWait.java
│               ├── Step2Sequential.java
│               ├── Step3CountDownLatch.java
│               ├── Step4InvokeAll.java
│               └── Step5CompletableFuture.java
└── README.md
```

---

## 🚀 Running a step

There is no build tool configured — compile and run directly with `javac`/`java`:

```bash
javac -d out src/com/technologyzer/scenario1/*.java
java -cp out com.technologyzer.scenario1.Step5CompletableFuture
```

Swap the class name to run any of the other steps (`Step1NoWait`, `Step2Sequential`, `Step3CountDownLatch`, `Step4InvokeAll`).

---

## 👤 Author

Sandip Kar
