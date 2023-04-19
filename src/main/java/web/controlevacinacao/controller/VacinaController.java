package web.controlevacinacao.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.model.filter.VacinaFilter;
import web.controlevacinacao.repository.VacinaRepository;
import web.controlevacinacao.service.VacinaService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/vacinas")
public class VacinaController {

    private static final Logger logger = LoggerFactory.getLogger(VacinaController.class);

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private VacinaService vacinaService;

    @GetMapping("/todas")
    public String mostrarTodas(Model model) {

        List<Vacina> vacinas = vacinaRepository.findAll();

        logger.info("Vacinas buscadas no BD: {}", vacinas);

        model.addAttribute("vacinas", vacinas);

        return "vacinas/vacinas";
    }

    @GetMapping("/buscar")
    public String abrirEntradaCodigo() {
        return "vacinas/codigo";
    }

    @PostMapping("/buscar")
    public String buscarPeloCodigo(Long codigo, Model model) {

        if (codigo != null) {
            Optional<Vacina> optVacina = vacinaRepository.findById(codigo);

            if (optVacina.isPresent()) {
                model.addAttribute("vacinas", List.of(optVacina.get()));
                return "vacinas/vacinas";
            } else {
                model.addAttribute("mensagem", "Vacina nao encontrada");
                return "mostrarmensagem";
            }
        } else {
            model.addAttribute("mensagem", "Informe o codigo, corno");
            return "mostrarmensagem";
        }

    }

    @GetMapping("/buscarnome")
    public String abrirEntradaNome() {
        return "vacinas/entradanome";
    }

    @PostMapping("/buscarnome")
    public String buscarPeloNome(String nome, Model model) {

        if (!nome.isEmpty()) {
            List<Vacina> vacinas = vacinaRepository.findByNomeContainingIgnoreCase(nome);
            if(!vacinas.isEmpty()){
                model.addAttribute("vacinas", vacinas);
                return "vacinas/vacinas";
            } else {
                model.addAttribute("mensagem", "Nenhuma vacina com este nome");
                return "mostrarmensagem";
            }
        } else {
            model.addAttribute("mensagem", "Informe o nome");
            return "mostrarmensagem";
        }

    }

    @GetMapping("/abrirpesquisar")
    public String abrirPesquisar(){
        return "vacinas/pesquisar";
    }

    @PostMapping("/pesquisar")
    public String pesquisar(VacinaFilter filtro, Model model){
        
        List<Vacina> vacinas = vacinaRepository.buscarComFiltro(filtro);
        if(!vacinas.isEmpty()){
            model.addAttribute("vacinas", vacinas);
            return "vacinas/vacinas";
        } else {
            model.addAttribute("mensagem", "Nenhuma vacina com estas informações");
            return "mostrarmensagem";
        }
    }

    @GetMapping("/cadastrar")
    public String abrirCadastrar(Vacina vacina, Model model) {
        model.addAttribute("titulo","Cadastrar Vacina");
        model.addAttribute("url", "/vacinas/cadastrar");
        model.addAttribute("textoBotao","Cadastrar");
        return "vacinas/cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Vacina vacina) {
        vacinaService.salvar(vacina);
        return"redirect:/vacinas/cadastrook";
    }

    @GetMapping("/cadastrook")
    public String mostrarMensagemCadastroOK(Model model){
        model.addAttribute("mensagem", "Salvo com sucesso");
        return "mostrarmensagem";
    }

    @PostMapping("/abriralterar")
    public String abrirAlterar(Vacina vacina, Model model) {
        model.addAttribute("titulo", "Alterar Vacina");
        model.addAttribute("url", "/vacinas/alterar");
        model.addAttribute("textoBotao", "Alterar");
        return "vacinas/cadastrar";
    }

    @PostMapping("/alterar")
    public String alterar(Vacina vacina) {
        vacinaService.salvar(vacina);
        return "redirect:/vacinas/alteracaook";
    }

    @GetMapping("/alteracaook")
    public String mostrarMensagemAlteracaoOK(Model model) {
        model.addAttribute("mensagem", "Vacina alterada com sucesso");
        return "mostrarmensagem";
    }

    @PostMapping("/abrirremover")
    public String abrirRemover(Vacina vacina){
        return "vacinas/confirmarremocao";
    }
    
    @PostMapping("/remover")
    public String remover(Long codigo){
        vacinaService.remover(codigo);
        return "redirect:/vacinas/remocaook";
    }

    @GetMapping("/remocaook")
    public String mostrarMensagemRemocaoOK(Model model) {
        model.addAttribute("mensagem", "Vacina removida com sucesso");
        return "mostrarmensagem";
    }

}

    
