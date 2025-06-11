package com.archiservice.user.enums;

public enum Period {
    HISTORY(1,3),
    CURRENT(1, 1),
    NEXT(0, 1);

    private final int offset;
    private final int limit;

    Period(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }
    public int getLimit() {return limit;}
}
