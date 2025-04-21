package com.cooperativismo.votacao.controller;
import com.cooperativismo.votacao.model.Pauta;
import com.cooperativismo.votacao.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pautas")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    // Endpoint para criar uma nova pauta
    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@RequestBody Pauta pauta) {
        Pauta novaPauta = pautaService.criarPauta(pauta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPauta);
    }


    // Endpoint para listar todas as pautas
    @GetMapping
    public ResponseEntity<List<Pauta>> listarPautas() {
        List<Pauta> pautas = pautaService.listarPautas();
        return ResponseEntity.ok(pautas);
    }

    // Endpoint para buscar uma pauta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPautaPorId(@PathVariable Long id) {
        Pauta pauta = pautaService.buscarPautaPorId(id);
        return ResponseEntity.ok(pauta);
    }


}
