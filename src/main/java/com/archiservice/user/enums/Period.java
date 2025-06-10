package com.archiservice.user.enums;

public enum Period {
    CURRENT(1),
    NEXT(0);

    private final int offset;

    Period(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}
