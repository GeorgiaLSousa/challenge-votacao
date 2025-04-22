package com.cooperativismo.votacao.service;
import com.cooperativismo.votacao.model.Pauta;
import com.cooperativismo.votacao.model.Sessao;
import com.cooperativismo.votacao.repository.PautaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SessaoService {

    private final AssociadoService associadoService;
    private final PautaRepository pautaRepository;

    // Metodo para abrir uma nova sessão
    public Sessao abrirSessao(Long PautaId, Integer minutos){
        Pauta pauta = pautaRepository.findById(PautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));

        if(pauta.getSessao() != null){
            throw new RuntimeException("Sessão já está aberta");
        }

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusMinutes(minutos  != null ? minutos : 1);

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setInicio(inicio);
        sessao.setFim(fim);

        pauta.setSessao(sessao);
        pautaRepository.save(pauta);

        return sessao;


    }



}
