package web.controlevacinacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.controlevacinacao.model.Pessoa;
import web.controlevacinacao.repository.queries.pessoa.PessoaQueries;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaQueries{
    
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);

}
