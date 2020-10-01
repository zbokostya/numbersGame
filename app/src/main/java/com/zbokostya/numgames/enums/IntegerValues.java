package com.zbokostya.numgames.enums;

public enum IntegerValues {
    NUMBER_NUMS(25),
    spanCount(9)
    ;

    private int value;

    IntegerValues(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
