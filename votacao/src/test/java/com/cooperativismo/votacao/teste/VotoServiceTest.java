package com.cooperativismo.votacao.teste;

    import com.cooperativismo.votacao.client.ValidacaoCpf;
    import com.cooperativismo.votacao.model.Associado;
    import com.cooperativismo.votacao.model.Pauta;
    import com.cooperativismo.votacao.model.Sessao;
    import com.cooperativismo.votacao.model.Voto;
    import com.cooperativismo.votacao.repository.AssociadoRepository;
    import com.cooperativismo.votacao.repository.PautaRepository;
    import com.cooperativismo.votacao.repository.VotoRepository;
    import com.cooperativismo.votacao.service.VotoService;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.junit.jupiter.MockitoExtension;

    import java.time.LocalDateTime;
    import java.util.Map;
    import java.util.Optional;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    class VotoServiceTest {

        @InjectMocks
        private VotoService votoService;

        @Mock
        private VotoRepository votoRepository;

        @Mock
        private AssociadoRepository associadoRepository;

        @Mock
        private PautaRepository pautaRepository;

        @Mock
        private ValidacaoCpf validacaoCpf;

        private Pauta pauta;
        private Associado associado;

        @BeforeEach
        void setUp() {
            pauta = new Pauta();
            pauta.setId(1L);
            pauta.setTitulo("Pauta Teste");
            pauta.setSessao(new Sessao());
            pauta.getSessao().setInicio(LocalDateTime.now().minusMinutes(10));
            pauta.getSessao().setFim(LocalDateTime.now().plusMinutes(10));

            associado = new Associado();
            associado.setId(1L);
            associado.setCpf("12345678900");
        }

        @Test
        void deveRegistrarVotoComCpfValido() {
            lenient().when(validacaoCpf.validarCpf("12345678900")).thenReturn(Map.of("status", "ABLE_TO_VOTE"));
            when(associadoRepository.findBycpf("12345678900")).thenReturn(Optional.of(associado));
            when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
            when(votoRepository.existsByAssociadoAndPauta(associado, pauta)).thenReturn(false);

            votoService.registrarVoto("12345678900", 1L, true);

            verify(votoRepository, times(1)).save(any(Voto.class));
        }

        @Test
        void deveLancarExcecaoQuandoCpfInvalido() {
            when(validacaoCpf.validarCpf("12345678900")).thenThrow(new RuntimeException("CPF inválido"));

            RuntimeException exception = assertThrows(RuntimeException.class, () ->
                    votoService.registrarVoto("12345678900", 1L, true));

            assertEquals("CPF inválido", exception.getMessage());
            verify(votoRepository, never()).save(any());
        }

        @Test
        void deveLancarExcecaoQuandoAssociadoJaVotou() {
            lenient().when(validacaoCpf.validarCpf("12345678900")).thenReturn(Map.of("status", "ABLE_TO_VOTE"));
            when(associadoRepository.findBycpf("12345678900")).thenReturn(Optional.of(associado));
            when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
            when(votoRepository.existsByAssociadoAndPauta(associado, pauta)).thenReturn(true);

            RuntimeException exception = assertThrows(RuntimeException.class, () ->
                    votoService.registrarVoto("12345678900", 1L, true));

            assertEquals("Associado já votou nesta pauta.", exception.getMessage());
            verify(votoRepository, never()).save(any());
        }

        @Test
        void deveLancarExcecaoQuandoSessaoEncerrada() {
            pauta.getSessao().setFim(LocalDateTime.now().minusMinutes(1));
            lenient().when(validacaoCpf.validarCpf("12345678900")).thenReturn(Map.of("status", "ABLE_TO_VOTE"));
            when(associadoRepository.findBycpf("12345678900")).thenReturn(Optional.of(associado));
            when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

            RuntimeException exception = assertThrows(RuntimeException.class, () ->
                    votoService.registrarVoto("12345678900", 1L, true));

            assertEquals("Sessão já foi encerrada.", exception.getMessage());
            verify(votoRepository, never()).save(any());
        }
    }