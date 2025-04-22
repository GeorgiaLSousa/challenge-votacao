package com.cooperativismo.votacao.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Boolean voto;

    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    @ManyToOne
    @JoinColumn(name = "associado_id")
    private Associado associado;

}
