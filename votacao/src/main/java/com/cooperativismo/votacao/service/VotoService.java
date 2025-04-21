package com.cooperativismo.votacao.service;

import com.cooperativismo.votacao.model.Associado;
import com.cooperativismo.votacao.model.Pauta;
import com.cooperativismo.votacao.model.Voto;
import com.cooperativismo.votacao.repository.AssociadoRepository;
import com.cooperativismo.votacao.repository.PautaRepository;
import com.cooperativismo.votacao.repository.VotoRepository;

import java.time.LocalDateTime;
import java.util.Map;

public class VotoService {

    private final VotoRepository votoRepository;
    private final AssociadoRepository associadoRepository;
    private final PautaRepository pautaRepository;

    public VotoService(VotoRepository votoRepository, AssociadoRepository associadoRepository, PautaRepository pautaRepository) {
        this.votoRepository = votoRepository;
        this.associadoRepository = associadoRepository;
        this.pautaRepository = pautaRepository;
    }

    public void registrarVoto(String cpf, Long pautaId, Boolean votoValor) {

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));

        Associado associado = associadoRepository.findByCPF(cpf)
                .orElseThrow(() -> new RuntimeException("Associado não encontrado"));

        if (votoRepository.existsByAssociadoAndPauta(associado, pauta)) {
            throw new RuntimeException("Voto já registrado para este associado e pauta");
        }
        if (pauta.getSessao() == null) {
            throw new RuntimeException("Sessão não está aberta para esta pauta");
        }
        if (pauta.getSessao().getFim().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Sessão já está encerrada");
        }

        Voto novoVoto = new Voto();
        novoVoto.setAssociado(associado);
        novoVoto.setPauta(pauta);
        novoVoto.setVoto(votoValor);
        votoRepository.save(novoVoto);
    }

    public Map<String, Long> ResultadoDaVotacao(Long pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));

        if (pauta.getSessao() == null) {
            throw new RuntimeException("Sessão não está aberta para esta pauta");
        }

        if (pauta.getSessao().getFim().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Sessão ainda está aberta");
        }

        Long votosSim = votoRepository.countByPautaAndVoto(pauta, true);
        Long votosNao = votoRepository.countByPautaAndVoto(pauta, false);

        return Map.of("VotosSim", votosSim, "VotosNao", votosNao);
    }
}