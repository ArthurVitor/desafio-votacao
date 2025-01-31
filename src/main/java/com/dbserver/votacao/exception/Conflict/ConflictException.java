package com.dbserver.votacao.exception.Conflict;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
