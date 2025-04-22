package com.cooperativismo.votacao.teste;

import com.cooperativismo.votacao.client.ValidacaoCpf;
import com.cooperativismo.votacao.model.Associado;
import com.cooperativismo.votacao.repository.AssociadoRepository;
import com.cooperativismo.votacao.service.AssociadoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssociadoServiceTest {

    private final AssociadoRepository associadoRepository = Mockito.mock(AssociadoRepository.class);
    private final ValidacaoCpf validacaoCpf = Mockito.mock(ValidacaoCpf.class);
    private final AssociadoService associadoService = new AssociadoService(associadoRepository, validacaoCpf);

    @Test
    void deveLancarExcecaoQuandoAssociadoNaoEncontrado() {
        when(associadoRepository.findBycpf("12345678901")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> associadoService.buscarAssociadoPorCPF("12345678901"));

        System.out.println("Exceção lançada: " + exception.getMessage());

        assertEquals("Associado não encontrado", exception.getMessage());
        verify(associadoRepository, times(1)).findBycpf("12345678901");
    }
}