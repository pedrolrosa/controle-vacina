package web.controlevacinacao.model.filter;

import java.time.LocalDate;

public class LoteFilter {
    
    private Long codigo;

	private LocalDate validadeInicial;
    private LocalDate validadeFim;

    private Integer nroDosesDoLoteInicial;
    private Integer nroDosesDoLoteFim;

    private Integer nroDosesAtualInicial;
    private Integer nroDosesAtualFim;

	private String nomeVacina;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public LocalDate getValidadeInicial() {
        return validadeInicial;
    }

    public void setValidadeInicial(LocalDate validadeInicial) {
        this.validadeInicial = validadeInicial;
    }

    public LocalDate getValidadeFim() {
        return validadeFim;
    }

    public void setValidadeFim(LocalDate validadeFim) {
        this.validadeFim = validadeFim;
    }

    public Integer getNroDosesDoLoteInicial() {
        return nroDosesDoLoteInicial;
    }

    public void setNroDosesDoLoteInicial(Integer nroDosesDoLoteInicial) {
        this.nroDosesDoLoteInicial = nroDosesDoLoteInicial;
    }

    public Integer getNroDosesDoLoteFim() {
        return nroDosesDoLoteFim;
    }

    public void setNroDosesDoLoteFim(Integer nroDosesDoLoteFim) {
        this.nroDosesDoLoteFim = nroDosesDoLoteFim;
    }

    public Integer getNroDosesAtualInicial() {
        return nroDosesAtualInicial;
    }

    public void setNroDosesAtualInicial(Integer nroDosesAtualInicial) {
        this.nroDosesAtualInicial = nroDosesAtualInicial;
    }

    public Integer getNroDosesAtualFim() {
        return nroDosesAtualFim;
    }

    public void setNroDosesAtualFim(Integer nroDosesAtualFim) {
        this.nroDosesAtualFim = nroDosesAtualFim;
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina) {
        this.nomeVacina = nomeVacina;
    }

    @Override
    public String toString() {
        return "LoteFilter [codigo=" + codigo + ", validadeInicial=" + validadeInicial + ", validadeFim=" + validadeFim
                + ", nroDosesDoLoteInicial=" + nroDosesDoLoteInicial + ", nroDosesDoLoteFim=" + nroDosesDoLoteFim
                + ", nroDosesAtualInicial=" + nroDosesAtualInicial + ", nroDosesAtualFim=" + nroDosesAtualFim
                + ", nomeVacina=" + nomeVacina + "]";
    }

}
