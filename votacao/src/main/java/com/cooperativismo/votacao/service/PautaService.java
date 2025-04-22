package com.cooperativismo.votacao.service;
import com.cooperativismo.votacao.model.Pauta;
import com.cooperativismo.votacao.repository.PautaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;

    // Metodo para criar uma nova pauta
    public Pauta criarPauta(Pauta pauta){
        return pautaRepository.save(pauta);

    }

    // Metodo para listar todas as pautas
    public List<Pauta> listarPautas() {
        return pautaRepository.findAll();
    }

    // Metodo para buscar uma pauta por ID
    public Pauta buscarPautaPorId(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));
    }


}
