package com.example.mobileapi.entity.enums;

public enum BookForm {
    HARDCOVER("Bìa cứng"),
    PAPERBACK("Bìa mềm"),
    EBOOK("Sách điện tử"),
    AUDIOBOOK("Sách nói"),
    LEATHER_BOUND("Bìa da"),
    SPIRAL_BOUND("Sách xoắn"),
    BOARD_BOOK("Sách cho trẻ em"),
    COMBO("Bản combo in và điện tử"),
    LIMITED_EDITION("Ấn bản giới hạn");

    private final String displayName;

    BookForm(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
