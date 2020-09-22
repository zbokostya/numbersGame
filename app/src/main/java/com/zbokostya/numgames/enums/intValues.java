package com.zbokostya.numgames.enums;

public enum intValues {
    NUMBER_NUMS(25),
    spanCount(9)
    ;

    private int value;

    intValues(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
