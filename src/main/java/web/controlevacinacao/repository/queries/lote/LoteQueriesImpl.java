package web.controlevacinacao.repository.queries.lote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import web.controlevacinacao.model.Lote;
import web.controlevacinacao.model.Status;
import web.controlevacinacao.model.filter.LoteFilter;
import web.controlevacinacao.repository.pagination.PaginacaoUtil;

public class LoteQueriesImpl implements LoteQueries{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lote> buscarComFiltro(LoteFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lote> criteriaQuery = builder.createQuery(Lote.class);
        Root<Lote> l = criteriaQuery.from(Lote.class);
        TypedQuery<Lote> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(l.<Long>get("codigo"), filtro.getCodigo()));
        }
        if (filtro.getValidadeInicial() != null){
			predicateList.add(builder.greaterThan(
					l.<LocalDate>get("validade"), 
					filtro.getValidadeInicial()));
		}
        if (filtro.getValidadeInicial() != null){
			predicateList.add(builder.lessThan(
					l.<LocalDate>get("validade"), 
					filtro.getValidadeInicial()));
		}
        if (filtro.getNroDosesDoLoteInicial() != null){
			predicateList.add(builder.greaterThanOrEqualTo(
					l.<Integer>get("nroDosesDoLote"), 
					filtro.getNroDosesDoLoteInicial()));
		}
        if (filtro.getNroDosesDoLoteFim() != null){
			predicateList.add(builder.lessThanOrEqualTo(
					l.<Integer>get("nroDosesDoLote"), 
					filtro.getNroDosesDoLoteFim()));
		}
        if (filtro.getNroDosesAtualInicial() != null){
			predicateList.add(builder.greaterThanOrEqualTo(
					l.<Integer>get("nroDosesAtual"), 
					filtro.getNroDosesAtualInicial()));
		}
        if (filtro.getNroDosesAtualFim() != null){
			predicateList.add(builder.lessThanOrEqualTo(
					l.<Integer>get("nroDosesAtual"), 
					filtro.getNroDosesAtualFim()));
		}
        

        predicateList.add(builder.equal(l.<Status>get("status"), Status.ATIVO));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(l).where(predArray);
        PaginacaoUtil.prepararOrdem(l, criteriaQuery, builder, pageable);

        typedQuery = manager.createQuery(criteriaQuery);
        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);

        List<Lote> lotes = typedQuery.getResultList();

        long totalLotes = getTotalLotes(filtro);

        Page<Lote> page = new PageImpl<>(lotes, pageable, totalLotes);
        return page;
    }
    
    private Long getTotalLotes(LoteFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Lote> l = criteriaQuery.from(Lote.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(l.<Long>get("codigo"), filtro.getCodigo()));
        }
        if (filtro.getValidadeInicial() != null){
			predicateList.add(builder.greaterThan(
					l.<LocalDate>get("validade"), 
					filtro.getValidadeInicial()));
		}
        if (filtro.getValidadeInicial() != null){
			predicateList.add(builder.lessThan(
					l.<LocalDate>get("validade"), 
					filtro.getValidadeInicial()));
		}
        if (filtro.getNroDosesDoLoteInicial() != null){
			predicateList.add(builder.greaterThanOrEqualTo(
					l.<Integer>get("nroDosesDoLote"), 
					filtro.getNroDosesDoLoteInicial()));
		}
        if (filtro.getNroDosesDoLoteFim() != null){
			predicateList.add(builder.lessThanOrEqualTo(
					l.<Integer>get("nroDosesDoLote"), 
					filtro.getNroDosesDoLoteFim()));
		}
        if (filtro.getNroDosesAtualInicial() != null){
			predicateList.add(builder.greaterThanOrEqualTo(
					l.<Integer>get("nroDosesAtual"), 
					filtro.getNroDosesAtualInicial()));
		}
        if (filtro.getNroDosesAtualFim() != null){
			predicateList.add(builder.lessThanOrEqualTo(
					l.<Integer>get("nroDosesAtual"), 
					filtro.getNroDosesAtualFim()));
		}

        predicateList.add(builder.equal(l.<Status>get("status"), Status.ATIVO));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(builder.count(l)).where(predArray);

        return manager.createQuery(criteriaQuery).getSingleResult();

    }

}
