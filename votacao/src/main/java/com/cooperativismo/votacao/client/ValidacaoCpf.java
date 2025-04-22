package com.cooperativismo.votacao.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Random;

@Component
public class ValidacaoCpf {

    private final Random random = new Random();

    public Map<String, String> validarCpf(String cpf) {

        // validação do CPF com uma chamada boolean
        boolean cpfValido = random.nextBoolean();

        // Simula a validação do CPF como false, e retorna 404
        if (!cpfValido) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CPF inválido");
        }

        // Simula se o usuário pode ou não votar
        boolean podeVotar = random.nextBoolean();
        String status = podeVotar ? "ABLE_TO_VOTE" : "UNABLE_TO_VOTE";

        return Map.of("status", status);
    }
}