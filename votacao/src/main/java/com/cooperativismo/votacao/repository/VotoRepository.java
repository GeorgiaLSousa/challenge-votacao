package com.cooperativismo.votacao.repository;
import com.cooperativismo.votacao.model.Associado;
import com.cooperativismo.votacao.model.Pauta;
import com.cooperativismo.votacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByAssociadoAndPauta(Associado associado, Pauta pauta);
    long countByPautaAndVoto(Pauta pauta, Boolean voto);


}
