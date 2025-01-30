package com.dbserver.votacao.exception;

public class VotingSessionNotFoundException extends NotFoundException {
    public VotingSessionNotFoundException(String message) {
        super(message);
    }
}
