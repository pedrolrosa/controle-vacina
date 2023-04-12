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
import web.controlevacinacao.repository.VacinaRepository;

@Controller
@RequestMapping("/vacinas")
public class VacinaController {

    private static final Logger logger = LoggerFactory.getLogger(VacinaController.class);

    @Autowired
    private VacinaRepository vacinaRepository;

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

}
