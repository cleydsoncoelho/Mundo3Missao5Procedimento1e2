package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "PessoaJuridica")
public class PessoaJuridica extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "cnpj")
    private String cnpj;

    public PessoaJuridica() {}

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    @Override
    public String toString() {
        return "PessoaJuridica [id=" + getIdPessoa() + ", nome=" + getNome() + ", cnpj=" + cnpj + "]";
    }
}
