package io.vdubovsky.fxexercise.exception;

public class InstrumentNotAvailableException extends RuntimeException{
    public InstrumentNotAvailableException(String message) {
        super(message);
    }
}
