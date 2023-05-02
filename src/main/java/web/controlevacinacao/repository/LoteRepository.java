package web.controlevacinacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.controlevacinacao.model.Lote;

public interface LoteRepository extends JpaRepository<Lote, Long> {
    
}
