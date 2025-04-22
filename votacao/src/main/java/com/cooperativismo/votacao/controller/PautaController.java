package com.cooperativismo.votacao.controller;
import com.cooperativismo.votacao.model.Pauta;
import com.cooperativismo.votacao.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pautas", description = "Gerenciamento de pautas")
@AllArgsConstructor
@Controller
@RequestMapping("/api/v1/pautas")
public class PautaController {

    private final PautaService pautaService;


    // Endpoint para criar uma nova pauta
    @Operation(summary = "Criar uma nova pauta", description = "Endpoint para criar uma nova pauta.")
    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@RequestBody Pauta pauta) {
        Pauta novaPauta = pautaService.criarPauta(pauta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPauta);
    }


    // Endpoint para listar todas as pautas
    @Operation(summary = "Listar todas as pautas", description = "Endpoint para listar todas as pautas cadastradas.")
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
