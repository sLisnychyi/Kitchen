package com.kpi.zpi;

import com.kpi.zpi.model.Kitchen;
import com.kpi.zpi.model.Plate;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public record PlateWasher(String name, Kitchen kitchen) implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PlateWasher.class);

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            if (kitchen.dirtyPlates().get() == 0) {
                synchronized (kitchen) {
                    kitchen.wait();
                }
            } else {
                Plate plate = kitchen.head().get();
                if (plate != null && kitchen.head().compareAndSet(plate, plate.next())) {
                    logger.info("Plate washer {} is washing plate {} Number of dirty plates = {} (thead = {})\n",
                            name, plate.uuid(), kitchen.dirtyPlates().decrementAndGet(), Thread.currentThread());
                    Thread.sleep(ThreadLocalRandom.current().nextLong(500, 2000));
                }
            }
        }
    }
}
