package com.cooperativismo.votacao.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pauta {

    // Gerar o ID automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título da pauta é obrigatório")
    private String titulo;

    private String descricao;

    // Relacionamento com a entidade Sessao de 1:1
    @OneToOne(mappedBy = "pauta", cascade = CascadeType.ALL)
    private Sessao sessao;


}
