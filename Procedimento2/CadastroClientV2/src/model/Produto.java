package model;

import java.io.Serializable;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idProduto;
    private String nome;
    private Integer quantidade;
    private Double precoVenda;

    public Produto() {}

    public Integer getIdProduto() { return idProduto; }
    public void setIdProduto(Integer idProduto) { this.idProduto = idProduto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(Double precoVenda) { this.precoVenda = precoVenda; }

    @Override
    public String toString() {
        return "Produto [id=" + idProduto + ", nome=" + nome + ", quantidade=" + quantidade + ", preco=" + precoVenda + "]";
    }
}
