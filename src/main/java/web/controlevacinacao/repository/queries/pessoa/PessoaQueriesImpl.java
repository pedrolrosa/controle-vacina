package web.controlevacinacao.repository.queries.pessoa;

import java.sql.Date;
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
import web.controlevacinacao.model.Pessoa;
import web.controlevacinacao.model.Status;
import web.controlevacinacao.model.filter.PessoaFilter;
import web.controlevacinacao.repository.pagination.PaginacaoUtil;

public class PessoaQueriesImpl implements PessoaQueries {

    @PersistenceContext
    private EntityManager manager;

	@Override
	public Page<Pessoa> buscarComFiltro(PessoaFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);
        Root<Pessoa> v = criteriaQuery.from(Pessoa.class);
        TypedQuery<Pessoa> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(v.<Long>get("codigo"), filtro.getCodigo()));
        }
        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getCpf())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("cpf")),
                    "%" + filtro.getCpf().toLowerCase() + "%"));
        }
		if (filtro.getDataInicio() != null){
			predicateList.add(builder.greaterThan(
					v.<LocalDate>get("dataNascimento"), 
					filtro.getDataInicio()));
		}
        if (filtro.getDataFim() != null){
			predicateList.add(builder.lessThan(
					v.<LocalDate>get("dataNascimento"), 
					filtro.getDataFim()));
		}
		if (StringUtils.hasText(filtro.getProfissao())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("profissao")),
                    "%" + filtro.getProfissao().toLowerCase() + "%"));
        }

        predicateList.add(builder.equal(v.<Status>get("status"), Status.ATIVO));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(v).where(predArray);
        PaginacaoUtil.prepararOrdem(v, criteriaQuery, builder, pageable);

        typedQuery = manager.createQuery(criteriaQuery);
        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);

        List<Pessoa> pessoas = typedQuery.getResultList();

        long totalPessoas = getTotalPessoas(filtro);

        Page<Pessoa> page = new PageImpl<>(pessoas, pageable, totalPessoas);
        return page;
    }

    private Long getTotalPessoas(PessoaFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Pessoa> v = criteriaQuery.from(Pessoa.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(v.<Long>get("codigo"), filtro.getCodigo()));
        }

        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(
                    builder.lower(v.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }
		if (StringUtils.hasText(filtro.getCpf())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("cpf")),
                    "%" + filtro.getCpf().toLowerCase() + "%"));
        }
		if (filtro.getDataInicio() != null && filtro.getDataFim() != null ){
			predicateList.add(builder.between(
					v.<LocalDate>get("dataNascimento"), 
					filtro.getDataInicio(), filtro.getDataFim()));
		}
		if (StringUtils.hasText(filtro.getProfissao())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("profissao")),
                    "%" + filtro.getProfissao().toLowerCase() + "%"));
        }

        predicateList.add(builder.equal(v.<Status>get("status"), Status.ATIVO));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(builder.count(v)).where(predArray);

        return manager.createQuery(criteriaQuery).getSingleResult();

    }
	
	

}
