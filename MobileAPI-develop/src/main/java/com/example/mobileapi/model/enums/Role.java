package com.example.mobileapi.model.enums;

public enum Role {
    ADMIN(true),
    USER(false);

    private final boolean value;

    Role(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public static Role role(boolean value) {
        return value ? ADMIN : USER;
    }
}
