package com.dbserver.votacao.exception.NotFound;

public class VotingSessionNotFoundException extends NotFoundException {
    public VotingSessionNotFoundException(String message) {
        super(message);
    }
}
