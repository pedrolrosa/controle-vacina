package web.controlevacinacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.controlevacinacao.model.Vacina;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {

    List<Vacina> findByNomeContainingIgnoreCase(String nome);
    
}
