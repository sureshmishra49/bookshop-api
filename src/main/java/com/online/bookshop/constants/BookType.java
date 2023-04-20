package com.online.bookshop.constants;

public enum BookType {
    FICTION(1L, "FICTION"),
    COMIC(2L, "COMIC");

    private final String value;
    private Long id;

    BookType(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Long getId() {
        return id;
    }
}

