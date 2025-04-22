package com.cooperativismo.votacao.repository;
import com.cooperativismo.votacao.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    @Query("SELECT p.titulo FROM Pauta p WHERE p.id = :id")
    Optional<String> findTituloById(@Param("id") Long id);
}
