package br.com.votacao.repository;

import br.com.votacao.model.entity.VotoSessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoSessaoRepository extends JpaRepository<VotoSessao, Long> {
    Optional<VotoSessao> findByPauta(Long idPauta);
}
