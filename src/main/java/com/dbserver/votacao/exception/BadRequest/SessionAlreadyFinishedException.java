package com.dbserver.votacao.exception.BadRequest;

public class SessionAlreadyFinishedException extends BadRequestException {
    public SessionAlreadyFinishedException(String message) {
        super(message);
    }
}
