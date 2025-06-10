package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "PessoaFisica")
public class PessoaFisica extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "cpf")
    private String cpf;

    public PessoaFisica() {}

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    public String toString() {
        return "PessoaFisica [id=" + getIdPessoa() + ", nome=" + getNome() + ", cpf=" + cpf + "]";
    }
}
