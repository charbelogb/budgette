package com.budgette.backend.domain.model;

public enum Country {
    BJ("Bénin", "XOF");

    private final String displayName;
    private final String currency;

    Country(String displayName, String currency) {
        this.displayName = displayName;
        this.currency = currency;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCurrency() {
        return currency;
    }
}
