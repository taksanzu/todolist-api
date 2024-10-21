package org.tak.todolistapi.exception;

public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }
}
