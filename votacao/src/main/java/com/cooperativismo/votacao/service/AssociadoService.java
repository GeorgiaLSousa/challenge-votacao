package com.cooperativismo.votacao.service;
import com.cooperativismo.votacao.client.ValidacaoCpf;
import com.cooperativismo.votacao.model.Associado;
import com.cooperativismo.votacao.repository.AssociadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final ValidacaoCpf validacaoCpf;

    // Metodo para criar um novo associado
    public Associado criarAssociado(Associado associado) {
        validacaoCpf.validarCpf(associado.getCpf());
        return associadoRepository.save(associado);
    }

    // Metodo para listar todos os associados
    public Associado buscarAssociadoPorCPF(String cpf) {
        return associadoRepository.findBycpf(cpf)
                .orElseThrow(() -> new RuntimeException("Associado não encontrado"));
    }
}
