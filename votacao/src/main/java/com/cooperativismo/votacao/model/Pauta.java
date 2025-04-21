package com.cooperativismo.votacao.model;
import jakarta.persistence.*;

@Entity
public class Pauta {


    @Id
    // Gerar o ID automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    // Relacionamento com a entidade Sessao de 1:1
    @OneToOne(mappedBy = "pauta", cascade = CascadeType.ALL)
    private Sessao sessao;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }
}
