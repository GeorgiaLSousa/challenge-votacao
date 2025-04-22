package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.model.Associado;
import com.cooperativismo.votacao.service.AssociadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/api/v1/associados")
public class AssociadoController {

    private final AssociadoService associadoService;

    @PostMapping
    public ResponseEntity<Associado> criarAssociado(@RequestBody Associado associado) {
        Associado novoAssociado = associadoService.criarAssociado(associado);
        return ResponseEntity.status(201).body(novoAssociado);
    }

}
