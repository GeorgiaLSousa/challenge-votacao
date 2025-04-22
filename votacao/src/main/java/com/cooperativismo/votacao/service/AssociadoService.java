package com.cooperativismo.votacao.service;
import com.cooperativismo.votacao.model.Associado;
import com.cooperativismo.votacao.repository.AssociadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssociadoService {

    private final AssociadoRepository associadoRepository;

    public Associado criarAssociado(Associado associado) {
        return associadoRepository.save(associado);
    }

    public Associado buscarAssociadoPorCPF(String cpf) {
        return associadoRepository.findBycpf(cpf)
                .orElseThrow(() -> new RuntimeException("Associado não encontrado"));
    }
}
