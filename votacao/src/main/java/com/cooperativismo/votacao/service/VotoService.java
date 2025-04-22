package com.cooperativismo.votacao.service;

import com.cooperativismo.votacao.model.Associado;
import com.cooperativismo.votacao.model.Pauta;
import com.cooperativismo.votacao.model.Voto;
import com.cooperativismo.votacao.repository.AssociadoRepository;
import com.cooperativismo.votacao.repository.PautaRepository;
import com.cooperativismo.votacao.repository.VotoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class VotoService {

    private final VotoRepository votoRepository;
    private final AssociadoRepository associadoRepository;
    private final PautaRepository pautaRepository;

    public void registrarVoto(String cpf, Long pautaId, Boolean votoValor) {
        log.info("Registrando voto para CPF: {}, Pauta ID: {}", cpf, pautaId);

        Pauta pauta = buscarPautaPorId(pautaId);
        Associado associado = buscarAssociadoPorCpf(cpf);

        if (votoRepository.existsByAssociadoAndPauta(associado, pauta)) {
            throw new RuntimeException("Associado já votou nesta pauta.");
        }
        if (pauta.getSessao() == null) {
            throw new RuntimeException("Sessão não está aberta para esta pauta.");
        }
        if (pauta.getSessao().getFim().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Sessão já foi encerrada.");
        }

        Voto novoVoto = new Voto();
        novoVoto.setAssociado(associado);
        novoVoto.setPauta(pauta);
        novoVoto.setVoto(votoValor);
        votoRepository.save(novoVoto);

        log.info("Voto registrado com sucesso para CPF: {}, Pauta ID: {}", cpf, pautaId);
    }

    public Map<String, Object> ResultadoDaVotacao(Long pautaId) {
        log.info("Calculando resultado da votação para Pauta ID: {}", pautaId);

        Pauta pauta = buscarPautaPorId(pautaId);

        if (pauta.getSessao() == null) {
            throw new RuntimeException("Sessão não está aberta para esta pauta.");
        }
        if (pauta.getSessao().getFim().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Sessão ainda está aberta.");
        }

        Long votosSim = votoRepository.countByPautaAndVoto(pauta, true);
        Long votosNao = votoRepository.countByPautaAndVoto(pauta, false);
        Long totalVotos = votosSim + votosNao;

        double porcentagemSim = (totalVotos > 0) ? (votosSim * 100.0) / totalVotos : 0.0;
        double porcentagemNao = (totalVotos > 0) ? (votosNao * 100.0) / totalVotos : 0.0;

        String vencedor;
        if (votosSim > votosNao) {
            vencedor = "Sim";
        } else if (votosNao > votosSim) {
            vencedor = "Não";
        } else {
            vencedor = "Empate";
        }

        log.info("Resultado calculado: VotosSim={}, VotosNao={}, Vencedor={}", votosSim, votosNao, vencedor);

        return Map.of(
                "VotosSim", votosSim,
                "VotosNao", votosNao,
                "PorcentagemSim", porcentagemSim,
                "PorcentagemNao", porcentagemNao,
                "Vencedor", vencedor
        );
    }

    private Pauta buscarPautaPorId(Long pautaId) {
        return pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta com ID " + pautaId + " não encontrada."));
    }

    private Associado buscarAssociadoPorCpf(String cpf) {
        return associadoRepository.findBycpf(cpf)
                .orElseThrow(() -> new RuntimeException("Associado com CPF " + cpf + " não encontrado."));
    }
}