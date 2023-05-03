package web.controlevacinacao.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import web.controlevacinacao.model.Lote;
import web.controlevacinacao.model.Status;
import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.model.filter.LoteFilter;
import web.controlevacinacao.pagination.PageWrapper;
import web.controlevacinacao.repository.LoteRepository;
import web.controlevacinacao.repository.VacinaRepository;
import web.controlevacinacao.service.LoteService;

@Controller
@RequestMapping("/lotes")
public class LoteController {

    private static final Logger logger = LoggerFactory.getLogger(VacinaController.class);


    @Autowired
    private VacinaRepository vacinaRepository;
    
    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private LoteService loteService;


    @GetMapping("/cadastrar")
    public String abrirCadastrar(Lote lote, Model model) {

        List<Vacina> vacinas = vacinaRepository.findByStatus(Status.ATIVO);

        model.addAttribute("titulo","Cadastrar Lote");
        model.addAttribute("url", "/lotes/cadastrar");
        model.addAttribute("textoBotao","Cadastrar");
        model.addAttribute("vacinas", vacinas);
        return "lotes/cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Lote lote) {
        loteService.salvar(lote);
        return"redirect:/lotes/cadastrook";
    }

    @GetMapping("/cadastrook")
    public String mostrarMensagemCadastroOK(Model model){
        model.addAttribute("mensagem", "Salvo com sucesso");
        model.addAttribute("opcao", "lotes");
        return "mostrarmensagem";
    }

    @GetMapping("/abrirpesquisar")
    public String abrirPesquisar(Model model){
        List<Vacina> vacinas = vacinaRepository.findByStatus(Status.ATIVO);
        model.addAttribute("vacinas", vacinas);
        return "lotes/pesquisar";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(LoteFilter filtro, Model model,
            @PageableDefault(size = 5)
            @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request){
        
        Page<Lote> pagina = loteRepository.buscarComFiltro(filtro, pageable);
        if(!pagina.isEmpty()){
            PageWrapper<Lote> paginaWrapper = new PageWrapper<>(pagina, request);
            model.addAttribute("pagina", paginaWrapper);
            
            return "lotes/lotes";
        } else {
            model.addAttribute("mensagem", "Nenhum lote com estas informações");
            return "mostrarmensagem";
        }
    }

    @PostMapping("/abriralterar")
    public String abrirAlterar(Lote lote, Model model) {
        model.addAttribute("titulo", "Alterar Lote");
        model.addAttribute("url", "/lotes/alterar");
        model.addAttribute("textoBotao", "Alterar");
        return "lotes/cadastrar";
    }

    @PostMapping("/alterar")
    public String alterar(Lote lote) {
        loteService.salvar(lote);
        return "redirect:/lotes/alteracaook";
    }

    @GetMapping("/alteracaook")
    public String mostrarMensagemAlteracaoOK(Model model) {
        model.addAttribute("mensagem", "Lote alterada com sucesso");
        return "mostrarmensagem";
    }

    @PostMapping("/abrirremover")
    public String abrirRemover(Lote lote){
        return "lotes/confirmarremocao";
    }
    
    @PostMapping("/remover")
    public String remover(Long codigo, Model model){
        Optional<Lote> optPessoa = loteRepository.findById(codigo);
        if(optPessoa.isPresent()){
            Lote lote = optPessoa.get();
            lote.setStatus(Status.INATIVO);
            loteService.salvar(lote);

            return "redirect:/lotes/remocaook";
        } else {
            model.addAttribute("mensagem", "nenhuma lote foi encontrada com este codigo");
            return "mostrarmensagem";
        }

        
    }

    @GetMapping("/remocaook")
    public String mostrarMensagemRemocaoOK(Model model) {
        model.addAttribute("mensagem", "Lote removida com sucesso");
        return "mostrarmensagem";
    }

}
