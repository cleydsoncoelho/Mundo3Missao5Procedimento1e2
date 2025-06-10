package cadastroserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import controller.MovimentoJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import controller.PessoaJpaController;
import model.Usuario;
import model.Movimento;
import model.Pessoa;
import model.Produto;
import java.util.List;

public class MovimentoThread extends Thread {
    private ProdutoJpaController ctrlProd;
    private UsuarioJpaController ctrlUsu;
    private MovimentoJpaController ctrlMov;
    private PessoaJpaController ctrlPessoa;
    private Socket s1;

    public MovimentoThread(
            ProdutoJpaController ctrlProd,
            UsuarioJpaController ctrlUsu,
            MovimentoJpaController ctrlMov,
            PessoaJpaController ctrlPessoa,
            Socket s1
    ) {
        this.ctrlProd = ctrlProd;
        this.ctrlUsu = ctrlUsu;
        this.ctrlMov = ctrlMov;
        this.ctrlPessoa = ctrlPessoa;
        this.s1 = s1;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream saida = new ObjectOutputStream(s1.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(s1.getInputStream());

            String login = (String) entrada.readObject();
            String senha = (String) entrada.readObject();

            Usuario usuario = ctrlUsu.findUsuario(login, senha);

            if (usuario == null) {
                saida.writeObject("Login ou senha inválidos!");
                s1.close();
                return;
            }
            saida.writeObject("Login OK");

            while (true) {
                String comando = (String) entrada.readObject();
                if (comando.equals("L")) {
                    List<Produto> produtos = ctrlProd.findProdutoEntities();
                    saida.writeObject(produtos);
                } else if (comando.equals("E") || comando.equals("S")) {
                    int idPessoa = (Integer) entrada.readObject();
                    int idProduto = (Integer) entrada.readObject();
                    int quantidade = (Integer) entrada.readObject();
                    double valorUnit = (Double) entrada.readObject();

                    Movimento mov = new Movimento();
                    mov.setUsuario(usuario);
                    mov.setTipo(comando); // "E" ou "S"
                    mov.setPessoa(ctrlPessoa.findPessoa(idPessoa));
                    mov.setProduto(ctrlProd.findProduto(idProduto));
                    mov.setQuantidade(quantidade);
                    mov.setValorUnitario(valorUnit);
                    ctrlMov.create(mov);

                    Produto produto = ctrlProd.findProduto(idProduto);
                    int novaQuantidade = produto.getQuantidade();
                    if (comando.equals("E")) {
                        novaQuantidade += quantidade;
                    } else {
                        novaQuantidade -= quantidade;
                    }
                    produto.setQuantidade(novaQuantidade);
                    ctrlProd.edit(produto);

                    saida.writeObject("Movimento registrado com sucesso!");
                } else if (comando.equals("X")) {
                    break;
                }
            }
            s1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
