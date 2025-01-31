package com.dbserver.votacao.exception.Conflict;

public class AssociateAlreadyRegisteredException extends ConflictException {
    public AssociateAlreadyRegisteredException(String message) {
        super(message);
    }
}
