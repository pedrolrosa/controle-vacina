package web.controlevacinacao.model.filter;

import java.sql.Date;

public class PessoaFilter {

	private Long codigo;
	private String nome;
	private String cpf;
	private Date dataInicio;
    private Date dataFim;
	private String profissao;
    
    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public Date getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    public Date getDataFim() {
        return dataFim;
    }
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    public String getProfissao() {
        return profissao;
    }
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    @Override
    public String toString() {
        return "PessoaFilter [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + ", dataInicio=" + dataInicio
                + ", dataFim=" + dataFim + ", profissao=" + profissao + "]";
    }
    
}
