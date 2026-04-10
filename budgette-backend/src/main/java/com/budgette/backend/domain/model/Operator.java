package com.budgette.backend.domain.model;

public enum Operator {
    MTN("MTN Mobile Money"),
    MOOV("Moov Money");

    private final String displayName;

    Operator(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
