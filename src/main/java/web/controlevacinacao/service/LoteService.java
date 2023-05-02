package web.controlevacinacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.controlevacinacao.model.Lote;
import web.controlevacinacao.repository.LoteRepository;

@Service
public class LoteService {
    
    @Autowired
    private LoteRepository repository;

    @Transactional
    public void salvar(Lote lote){
        repository.save(lote);
    }

    @Transactional
    public void remover(Long id){
        repository.deleteById(id);
    }

}
