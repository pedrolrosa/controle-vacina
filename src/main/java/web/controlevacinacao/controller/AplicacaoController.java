package web.controlevacinacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import web.controlevacinacao.model.Aplicacao;
import web.controlevacinacao.model.Lote;
import web.controlevacinacao.model.Status;
import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.model.filter.LoteFilter;
import web.controlevacinacao.pagination.PageWrapper;
import web.controlevacinacao.repository.LoteRepository;
import web.controlevacinacao.repository.VacinaRepository;

@Controller
@RequestMapping("/aplicacoes")
public class AplicacaoController {

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private LoteRepository loteRepository;
    
    @GetMapping("/cadastrar")
    public String abrirCadastrar(HttpSession sessao) {
        sessao.setAttribute("aplicacao", new Aplicacao());

        return "aplicacoes/cadastrar";
    }

    @PostMapping("/escolherlote")
    public String abrirPesquisar(Model model) {
        colocarVacinasNoModel(model);
        model.addAttribute("uso","aplicacoes");
        model.addAttribute("url", "/aplicacoes/pesquisarlote");
        return "lotes/pesquisar";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(LoteFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Lote> pagina = loteRepository.buscarComFiltro(filtro, pageable);
        if (!pagina.isEmpty()) {
            PageWrapper<Lote> paginaWrapper = new PageWrapper<>(pagina, request);
            model.addAttribute("pagina", paginaWrapper);
            model.addAttribute("uso", "aplicacoes");

            return "lotes/lotes";
        } else {
            model.addAttribute("mensagem", "Não foram encontrados lotes com esse filtro");
            model.addAttribute("opcao", "aplicacoes");
            return "mostrarmensagem";
        }
    }

    @PostMapping("/definirlote")
    public String definirLote(Long codigoLote, HttpSession sessao){
        Aplicacao aplicacao = (Aplicacao) sessao.getAttribute("aplicacao");
        aplicacao.setLote(loteRepository.buscarComVacina(codigoLote));
        sessao.setAttribute("aplicacao", aplicacao);

        return "";
    }

    private void colocarVacinasNoModel(Model model) {
        List<Vacina> vacinas = vacinaRepository.findByStatus(Status.ATIVO);
        model.addAttribute("vacinas", vacinas);
    }


}
