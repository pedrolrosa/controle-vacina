package web.controlevacinacao.controller;

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
import web.controlevacinacao.model.Pessoa;
import web.controlevacinacao.model.filter.PessoaFilter;
import web.controlevacinacao.pagination.PageWrapper;
import web.controlevacinacao.repository.PessoaRepository;
import web.controlevacinacao.service.PessoaService;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository vacinaRepository;

    @Autowired
    private PessoaService vacinaService;


    @GetMapping("/cadastrar")
    public String abrirCadastrar(Pessoa pessoa, Model model) {
        model.addAttribute("titulo","Cadastrar Pessoa");
        model.addAttribute("url", "/pessoas/cadastrar");
        model.addAttribute("textoBotao","Cadastrar");
        return "pessoas/cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Pessoa pessoa) {
        vacinaService.salvar(pessoa);
        return"redirect:/pessoas/cadastrook";
    }

    @GetMapping("/cadastrook")
    public String mostrarMensagemCadastroOK(Model model){
        model.addAttribute("mensagem", "Salvo com sucesso");
        return "mostrarmensagem";
    }

    @GetMapping("/abrirpesquisar")
    public String abrirPesquisar(){
        return "vacinas/pesquisar";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(PessoaFilter filtro, Model model,
            @PageableDefault(size = 5)
            @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request){
        
        Page<Pessoa> pagina = vacinaRepository.buscarComFiltro(filtro, pageable);
        if(!pagina.isEmpty()){
            PageWrapper<Pessoa> paginaWrapper = new PageWrapper<>(pagina, request);
            model.addAttribute("pagina", paginaWrapper);
            
            return "vacinas/vacinas";
        } else {
            model.addAttribute("mensagem", "Nenhuma pessoa com estas informações");
            return "mostrarmensagem";
        }
    }
}
