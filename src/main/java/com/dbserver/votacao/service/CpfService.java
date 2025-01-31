package com.dbserver.votacao.service;

import br.com.caelum.stella.validation.CPFValidator;
import com.dbserver.votacao.dto.Cpf.ListCpf;
import com.dbserver.votacao.enums.CpfAbleToVote;
import com.dbserver.votacao.exception.NotFound.CpfNotValidException;
import org.springframework.stereotype.Service;

@Service()
public class CpfService {
    public ListCpf isCpfValid(String cpf) {
        try {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf.replace(".", "").replace("-", ""));

            CpfAbleToVote status = (Math.random() < 0.5) ? CpfAbleToVote.ABLE_TO_VOTE : CpfAbleToVote.UNABLE_TO_VOTE;

            return new ListCpf(status);
        } catch (Exception e) {
            throw new CpfNotValidException("CPF inválido");
        }
    }
}
