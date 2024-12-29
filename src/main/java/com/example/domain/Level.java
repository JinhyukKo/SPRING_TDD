package com.example.domain;

public enum Level {
    GOLD(3,null) ,
    SILVER(2,GOLD),
    BASIC(1,SILVER);
    final private int value;
    final private Level next;
    Level(int value,Level next) {
        this.value = value;
        this.next = next;
    }
    public int getValue() {
        return value;
    }
    public static Level valueOf(int value) {
        switch (value) {
            case 1 : return BASIC;
            case 2 : return SILVER;
            case 3 : return GOLD;
            default : throw new AssertionError("Invalid Value");
        }
    }
    public Level nextLevel() {
        return this.next;
    }

}
