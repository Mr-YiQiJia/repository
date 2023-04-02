package com.sata.yqj.cqdxer.exception;

import java.util.function.Supplier;

public class InValiDataException extends RuntimeException implements Supplier<InValiDataException> {
    private static final long serialVersionUID = -377590820727161168L;

    public InValiDataException(String str, Exception e) {
        super(str, e);
    }

    public InValiDataException(String str) {
        super(str);
    }

    @Override
    public InValiDataException get() {
        return this;
    }
}
