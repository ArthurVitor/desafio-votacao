package com.dbserver.votacao.exception.Conflict;

public class AssociateAlreadyVotedException extends ConflictException {
    public AssociateAlreadyVotedException(String message) {
        super(message);
    }
}
