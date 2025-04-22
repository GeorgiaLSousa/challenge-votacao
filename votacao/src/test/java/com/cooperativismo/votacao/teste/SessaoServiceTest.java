package com.cooperativismo.votacao.teste;

import com.cooperativismo.votacao.model.Pauta;
import com.cooperativismo.votacao.model.Sessao;
import com.cooperativismo.votacao.repository.PautaRepository;
import com.cooperativismo.votacao.service.SessaoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessaoServiceTest {

    private final PautaRepository pautaRepository = Mockito.mock(PautaRepository.class);
    private final SessaoService sessaoService = new SessaoService(null, pautaRepository);

    @Test
    void deveAbrirSessaoComSucesso() {
        Pauta pauta = new Pauta();

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        Sessao sessao = sessaoService.abrirSessao(1L, 10);

        System.out.println("Sessão aberta: Início = " + sessao.getInicio() + ", Fim = " + sessao.getFim());

        assertNotNull(sessao);
        assertEquals(pauta, sessao.getPauta());
        verify(pautaRepository, times(1)).save(pauta);
    }

    @Test
    void deveLancarExcecaoQuandoSessaoJaAberta() {
        Pauta pauta = new Pauta();
        pauta.setSessao(new Sessao());

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> sessaoService.abrirSessao(1L, 10));

        System.out.println("Exceção lançada: " + exception.getMessage());

        assertEquals("Sessão já está aberta", exception.getMessage());
        verify(pautaRepository, never()).save(pauta);
    }

    @Test
    void deveLancarExcecaoQuandoPautaNaoEncontrada() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> sessaoService.abrirSessao(1L, 10));

        System.out.println("Exceção lançada: " + exception.getMessage());

        assertEquals("Pauta não encontrada", exception.getMessage());
        verify(pautaRepository, never()).save(any());
    }
}