package com.kpi.zpi;

import com.kpi.zpi.model.Kitchen;
import com.kpi.zpi.model.Plate;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

record Waiter(String name, Kitchen kitchen) implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PlateWasher.class);

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Plate currentPlate = kitchen.head().get();
            Plate dirtyPlate = new Plate(currentPlate);
            if (kitchen.head().compareAndSet(currentPlate, dirtyPlate)) {
                logger.info("Waiter {} put dirty plate {}. Number of dirty plates = {} (thread = {})\n",
                        name, dirtyPlate.uuid(), kitchen.dirtyPlates().incrementAndGet(), Thread.currentThread());
                synchronized (kitchen) {
                    kitchen.notifyAll();
                }
                return;
            }
        }
    }
}
