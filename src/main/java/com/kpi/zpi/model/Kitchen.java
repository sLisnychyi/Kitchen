package com.kpi.zpi.model;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public record Kitchen(AtomicInteger dirtyPlates, AtomicReference<Plate> head, Set<String> washers) {
    public Kitchen() {
        this(new AtomicInteger(), new AtomicReference<>(), ConcurrentHashMap.newKeySet());
    }
}

