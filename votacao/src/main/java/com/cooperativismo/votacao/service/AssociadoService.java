package com.cooperativismo.votacao.service;
import com.cooperativismo.votacao.model.Associado;
import com.cooperativismo.votacao.repository.AssociadoRepository;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService {

    private final AssociadoRepository associadoRepository;

    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public Associado criarAssociado(Associado associado) {
        return associadoRepository.save(associado);
    }

    public Associado buscarAssociadoPorCPF(String cpf) {
        return associadoRepository.findBycpf(cpf)
                .orElseThrow(() -> new RuntimeException("Associado não encontrado"));
    }
}
