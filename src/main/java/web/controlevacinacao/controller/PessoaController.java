package web.controlevacinacao.controller;

import java.util.Optional;

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
import web.controlevacinacao.model.Status;
import web.controlevacinacao.model.filter.PessoaFilter;
import web.controlevacinacao.pagination.PageWrapper;
import web.controlevacinacao.repository.PessoaRepository;
import web.controlevacinacao.service.PessoaService;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;


    @GetMapping("/cadastrar")
    public String abrirCadastrar(Pessoa pessoa, Model model) {
        model.addAttribute("titulo","Cadastrar Pessoa");
        model.addAttribute("url", "/pessoas/cadastrar");
        model.addAttribute("textoBotao","Cadastrar");
        return "pessoas/cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Pessoa pessoa) {
        pessoaService.salvar(pessoa);
        return"redirect:/pessoas/cadastrook";
    }

    @GetMapping("/cadastrook")
    public String mostrarMensagemCadastroOK(Model model){
        model.addAttribute("mensagem", "Salvo com sucesso");
        return "mostrarmensagem";
    }

    @GetMapping("/abrirpesquisar")
    public String abrirPesquisar(){
        return "pessoas/pesquisar";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(PessoaFilter filtro, Model model,
            @PageableDefault(size = 5)
            @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request){
        
        Page<Pessoa> pagina = pessoaRepository.buscarComFiltro(filtro, pageable);
        if(!pagina.isEmpty()){
            PageWrapper<Pessoa> paginaWrapper = new PageWrapper<>(pagina, request);
            model.addAttribute("pagina", paginaWrapper);
            
            return "pessoas/pessoas";
        } else {
            model.addAttribute("mensagem", "Nenhuma pessoa com estas informações");
            return "mostrarmensagem";
        }
    }

    @PostMapping("/abriralterar")
    public String abrirAlterar(Pessoa pessoa, Model model) {
        model.addAttribute("titulo", "Alterar Pessoa");
        model.addAttribute("url", "/pessoas/alterar");
        model.addAttribute("textoBotao", "Alterar");
        return "pessoas/cadastrar";
    }

    @PostMapping("/alterar")
    public String alterar(Pessoa pessoa) {
        pessoaService.salvar(pessoa);
        return "redirect:/pessoas/alteracaook";
    }

    @GetMapping("/alteracaook")
    public String mostrarMensagemAlteracaoOK(Model model) {
        model.addAttribute("mensagem", "Pessoa alterada com sucesso");
        return "mostrarmensagem";
    }

    @PostMapping("/abrirremover")
    public String abrirRemover(Pessoa pessoa){
        return "pessoas/confirmarremocao";
    }
    
    @PostMapping("/remover")
    public String remover(Long codigo, Model model){
        Optional<Pessoa> optPessoa = pessoaRepository.findById(codigo);
        if(optPessoa.isPresent()){
            Pessoa pessoa = optPessoa.get();
            pessoa.setStatus(Status.INATIVO);
            pessoaService.salvar(pessoa);

            return "redirect:/pessoas/remocaook";
        } else {
            model.addAttribute("mensagem", "nenhuma pessoa foi encontrada com este codigo");
            return "mostrarmensagem";
        }

        
    }

    @GetMapping("/remocaook")
    public String mostrarMensagemRemocaoOK(Model model) {
        model.addAttribute("mensagem", "Pessoa removida com sucesso");
        return "mostrarmensagem";
    }
}
