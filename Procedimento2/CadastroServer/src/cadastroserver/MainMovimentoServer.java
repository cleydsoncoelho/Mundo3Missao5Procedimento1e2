package cadastroserver;

import controller.MovimentoJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import controller.PessoaJpaController;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainMovimentoServer {

    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroPU");
            ProdutoJpaController ctrlProd = new ProdutoJpaController(emf);
            UsuarioJpaController ctrlUsu = new UsuarioJpaController(emf);
            MovimentoJpaController ctrlMov = new MovimentoJpaController(emf);
            PessoaJpaController ctrlPessoa = new PessoaJpaController(emf);

            ServerSocket servidor = new ServerSocket(4321);
            while (true) {
                Socket cliente = servidor.accept();
                Thread t = new MovimentoThread(ctrlProd, ctrlUsu, ctrlMov, ctrlPessoa, cliente);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
