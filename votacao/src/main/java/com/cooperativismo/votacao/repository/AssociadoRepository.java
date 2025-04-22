package com.cooperativismo.votacao.repository;
import com.cooperativismo.votacao.model.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {
    @Query("SELECT a FROM Associado a WHERE a.cpf = :cpf")
    Optional<Associado> findBycpf(@Param("cpf") String cpf);
}