package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.service.AssociadoService;
import com.cooperativismo.votacao.service.SessaoService;
import com.cooperativismo.votacao.service.VotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/api/v1/voto")
public class VotoController {

    private final VotoService votoService;
    private final SessaoService sessaoService;
    private final AssociadoService associadoService;

    // Endpoint para registrar um voto
    @PostMapping
    public ResponseEntity<Void> registrarVoto(@RequestParam String cpf,
                                              @RequestParam Long pautaId,
                                              @RequestParam String voto) {

        Boolean votoRegistro = Boolean.parseBoolean(voto);

        votoService.registrarVoto(cpf, pautaId, votoRegistro);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/resultado/{pautaId}")
    public ResponseEntity<Map<String, Object>> resultadoDaVotacao(@RequestParam Long pautaId) {
        Map<String, Object> resultado = votoService.ResultadoDaVotacao(pautaId);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }



}
