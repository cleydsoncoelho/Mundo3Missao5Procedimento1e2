package model;

import java.io.Serializable;
import java.util.Date;

public class Movimento implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idMovimento;
    private Usuario usuario;
    private String tipo;
    private Pessoa pessoa;
    private Produto produto;
    private Integer quantidade;
    private Double valorUnitario;
    private Date dataMovimento;

    public Movimento() {}

    public Integer getIdMovimento() { return idMovimento; }
    public void setIdMovimento(Integer idMovimento) { this.idMovimento = idMovimento; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(Double valorUnitario) { this.valorUnitario = valorUnitario; }

    public Date getDataMovimento() { return dataMovimento; }
    public void setDataMovimento(Date dataMovimento) { this.dataMovimento = dataMovimento; }

    @Override
    public String toString() {
        return "Movimento [id=" + idMovimento + ", tipo=" + tipo +
               ", produto=" + (produto != null ? produto.getNome() : "null") +
               ", qtd=" + quantidade + "]";
    }
}
