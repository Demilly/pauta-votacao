package br.com.votacao.repository;

import br.com.votacao.model.entity.VotoSessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VotoSessaoRepository extends JpaRepository<VotoSessao, Long> {
    @Query("select u from VotoSessao u WHERE u.pauta.id = ?1")
    Optional<VotoSessao> findByPauta(Long idPauta);
}
