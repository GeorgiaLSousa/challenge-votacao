package com.cooperativismo.votacao.teste;

import com.cooperativismo.votacao.model.Pauta;
import com.cooperativismo.votacao.repository.PautaRepository;
import com.cooperativismo.votacao.service.PautaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PautaServiceTest {

    private final PautaRepository pautaRepository = Mockito.mock(PautaRepository.class);
    private final PautaService pautaService = new PautaService(pautaRepository);

    @Test
    void deveCriarPautaComSucesso() {
        Pauta pauta = new Pauta();
        pauta.setTitulo("Nova Pauta");

        when(pautaRepository.save(pauta)).thenReturn(pauta);

        Pauta resultado = pautaService.criarPauta(pauta);

        System.out.println("Pauta criada: " + resultado.getTitulo());

        assertNotNull(resultado);
        assertEquals("Nova Pauta", resultado.getTitulo());
        verify(pautaRepository, times(1)).save(pauta);
    }

    @Test
    void deveLancarExcecaoQuandoPautaNaoEncontrada() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pautaService.buscarPautaPorId(1L));

        System.out.println("Exceção lançada: " + exception.getMessage());

        assertEquals("Pauta não encontrada", exception.getMessage());
        verify(pautaRepository, times(1)).findById(1L);
    }

    @Test
    void deveListarTodasAsPautas() {
        Pauta pauta1 = new Pauta();
        pauta1.setTitulo("Pauta 1");

        Pauta pauta2 = new Pauta();
        pauta2.setTitulo("Pauta 2");

        when(pautaRepository.findAll()).thenReturn(Arrays.asList(pauta1, pauta2));

        List<Pauta> pautas = pautaService.listarPautas();

        System.out.println("Pautas listadas: ");
        pautas.forEach(pauta -> System.out.println("- " + pauta.getTitulo()));

        assertNotNull(pautas);
        assertEquals(2, pautas.size());
        assertEquals("Pauta 1", pautas.get(0).getTitulo());
        assertEquals("Pauta 2", pautas.get(1).getTitulo());
        verify(pautaRepository, times(1)).findAll();
    }
}