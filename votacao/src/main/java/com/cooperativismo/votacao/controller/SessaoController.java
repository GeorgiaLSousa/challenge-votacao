package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.model.Sessao;
import com.cooperativismo.votacao.service.SessaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/api/v1/sessoes")
public class SessaoController {

    private final SessaoService sessaoService;


    @PostMapping("/abrir")
    public ResponseEntity<Sessao> abrirSessao(@RequestParam Long pautaId,
                                              @RequestParam(required = false) Integer minutos){

        Sessao sessao = sessaoService.abrirSessao(pautaId, minutos);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessao);
    }
}
