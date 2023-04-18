package web.controlevacinacao.repository.queries.vacina;

import java.util.List;

import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.model.filter.VacinaFilter;

public interface VacinaQueries {

    List<Vacina> buscarComFiltro(VacinaFilter filtro);
    
}
