package com.kpi.zpi.model;

import java.util.UUID;

public record Plate(UUID uuid, Plate next) {
    public Plate(Plate next) {
        this(UUID.randomUUID(), next);
    }
}
