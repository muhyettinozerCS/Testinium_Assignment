package com.trello.utils;

public class CustomList {
    private final String id;
    private final String name;

    public CustomList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}