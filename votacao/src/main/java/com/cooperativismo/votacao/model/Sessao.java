package com.cooperativismo.votacao.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inicio;

    private LocalDateTime fim;

    @OneToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

}
