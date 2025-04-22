package com.cooperativismo.votacao.teste;

import com.cooperativismo.votacao.model.Associado;
import com.cooperativismo.votacao.model.Pauta;
import com.cooperativismo.votacao.model.Sessao;
import com.cooperativismo.votacao.model.Voto;
import com.cooperativismo.votacao.repository.AssociadoRepository;
import com.cooperativismo.votacao.repository.PautaRepository;
import com.cooperativismo.votacao.repository.VotoRepository;
import com.cooperativismo.votacao.service.VotoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VotoServiceTest {

    private final VotoRepository votoRepository = Mockito.mock(VotoRepository.class);
    private final AssociadoRepository associadoRepository = Mockito.mock(AssociadoRepository.class);
    private final PautaRepository pautaRepository = Mockito.mock(PautaRepository.class);
    private final VotoService votoService = new VotoService(votoRepository, associadoRepository, pautaRepository);

    @Test
    void deveRegistrarVotoComSucesso() {
        String cpf = "12345678901";
        Long pautaId = 1L;
        Boolean votoValor = true;

        Associado associado = new Associado();
        associado.setCpf(cpf);

        Pauta pauta = new Pauta();
        Sessao sessao = new Sessao();
        sessao.setInicio(LocalDateTime.now().minusMinutes(5));
        sessao.setFim(LocalDateTime.now().plusMinutes(5));
        pauta.setSessao(sessao);

        when(associadoRepository.findBycpf(cpf)).thenReturn(Optional.of(associado));
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(votoRepository.existsByAssociadoAndPauta(associado, pauta)).thenReturn(false);

        votoService.registrarVoto(cpf, pautaId, votoValor);

        verify(votoRepository, times(1)).save(any(Voto.class));
    }

    @Test
    void deveLancarExcecaoQuandoAssociadoJaVotou() {
        String cpf = "12345678901";
        Long pautaId = 1L;
        Boolean votoValor = true;

        Associado associado = new Associado();
        associado.setCpf(cpf);

        Pauta pauta = new Pauta();
        Sessao sessao = new Sessao();
        sessao.setInicio(LocalDateTime.now().minusMinutes(5));
        sessao.setFim(LocalDateTime.now().plusMinutes(5));
        pauta.setSessao(sessao);

        when(associadoRepository.findBycpf(cpf)).thenReturn(Optional.of(associado));
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(votoRepository.existsByAssociadoAndPauta(associado, pauta)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> votoService.registrarVoto(cpf, pautaId, votoValor));

        assertEquals("Associado já votou nesta pauta.", exception.getMessage());
        verify(votoRepository, never()).save(any(Voto.class));
    }

    @Test
    void deveLancarExcecaoQuandoSessaoNaoEstaAberta() {
        String cpf = "12345678901";
        Long pautaId = 1L;
        Boolean votoValor = true;

        Associado associado = new Associado();
        associado.setCpf(cpf);

        Pauta pauta = new Pauta();

        when(associadoRepository.findBycpf(cpf)).thenReturn(Optional.of(associado));
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> votoService.registrarVoto(cpf, pautaId, votoValor));

        assertEquals("Sessão não está aberta para esta pauta.", exception.getMessage());
        verify(votoRepository, never()).save(any(Voto.class));
    }
}