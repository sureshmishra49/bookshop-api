package com.online.bookshop.constants;

public enum BookType {
    FICTION ("FICTION"),
    COMIC("COMIC");

    private final String value;


    BookType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

