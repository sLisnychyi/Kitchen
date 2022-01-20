package com.kpi.zpi;

import com.kpi.zpi.model.Kitchen;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Runner {

    public static void main(String[] args) throws InterruptedException {
        Kitchen kitchen = new Kitchen();

        int numberOfDishWashers = 3;
        int numberOfWaiters = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfDishWashers);

        for (int i = 0; i < numberOfDishWashers; i++) {
            executorService.execute(new PlateWasher(i + "", kitchen));
        }

        List<Waiter> waiters = Stream.iterate(1, e -> e + 1)
                .limit(numberOfWaiters)
                .map(e -> new Waiter(String.valueOf(e), kitchen))
                .collect(Collectors.toList());

        ExecutorService waitersThreads = Executors.newFixedThreadPool(waiters.size());

        while (true) {
            Thread.sleep(ThreadLocalRandom.current().nextLong(1000, 2000));
            for (int i = 0; i < ThreadLocalRandom.current().nextLong(1, numberOfWaiters); i++) {
                waitersThreads.execute(waiters.get(ThreadLocalRandom.current().nextInt(waiters.size())));
            }
        }
    }

}


