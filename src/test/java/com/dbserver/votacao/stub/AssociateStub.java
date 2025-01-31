package com.dbserver.votacao.stub;

import com.dbserver.votacao.dto.Associate.CreateAssociateDto;
import com.dbserver.votacao.dto.Associate.ListAssociateDto;
import com.dbserver.votacao.model.Associate;

public class AssociateStub {

    public static CreateAssociateDto generateValidCreateAssociateDto() {
        return new CreateAssociateDto("Arthur", "123.456.789-00");
    }

    public static Associate generateValidAssociate() {
        Associate associate = new Associate();
        associate.setId(1L);
        associate.setName("Arthur");
        associate.setCpf("123.456.789-00");
        return associate;
    }

    public static ListAssociateDto generateValidListAssociateDto() {
        return new ListAssociateDto(1L, "Arthur", "123.456.789-00");
    }
}
