package com.thoughtworks.lean.sonar.testpyramid.exception;

public class AnalyseException extends RuntimeException {
    public AnalyseException() {
        super();
    }

    public AnalyseException(Throwable cause) {
        super(cause);
    }

    public AnalyseException(String message) {
        super(message);
    }

    public AnalyseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AnalyseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
