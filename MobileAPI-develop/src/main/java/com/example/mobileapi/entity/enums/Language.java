package com.example.mobileapi.entity.enums;

public enum Language {
    ENGLISH("Tiếng Anh"),
    VIETNAMESE("Tiếng Việt"),
    FRENCH("Tiếng Pháp"),
    SPANISH("Tiếng Tây Ban Nha"),
    GERMAN("Tiếng Đức"),
    CHINESE("Tiếng Trung"),
    JAPANESE("Tiếng Nhật"),
    KOREAN("Tiếng Hàn"),
    RUSSIAN("Tiếng Nga"),
    ARABIC("Tiếng Ả Rập"),
    PORTUGUESE("Tiếng Bồ Đào Nha"),
    ITALIAN("Tiếng Ý"),
    HINDI("Tiếng Hindi");

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
