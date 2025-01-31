package com.dbserver.votacao.exception.NotFound;

public class CpfNotValidException extends NotFoundException {
    public CpfNotValidException(String message) {
        super(message);
    }
}
