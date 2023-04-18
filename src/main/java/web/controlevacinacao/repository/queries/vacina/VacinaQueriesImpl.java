package web.controlevacinacao.repository.queries.vacina;

import java.util.List;

import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.model.filter.VacinaFilter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.model.filter.VacinaFilter;

public class VacinaQueriesImpl implements VacinaQueries{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Vacina> buscarComFiltro(VacinaFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Vacina> criteriaQuery = builder.createQuery(Vacina.class);
        Root<Vacina> v = criteriaQuery.from(Vacina.class);
        TypedQuery<Vacina> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(v.<String>get("codigo"),
                    filtro.getCodigo()));
        }
        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(
                    builder.lower(v.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getDescricao())) {
            predicateList.add(builder.like(
                    builder.lower(v.<String>get("descricao")),
                    "%" + filtro.getDescricao().toLowerCase() + "%"));
        }

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(v).where(predArray);
        typedQuery = manager.createQuery(criteriaQuery);

        List<Vacina> vacinas = typedQuery.getResultList();

        return vacinas;
    }
    
}
