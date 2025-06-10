package cadastroserver;

import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainServer {

    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroPU");
            ProdutoJpaController ctrlProd = new ProdutoJpaController(emf);
            UsuarioJpaController ctrlUsu = new UsuarioJpaController(emf);

            ServerSocket servidor = new ServerSocket(4321);
            while (true) {
                Socket cliente = servidor.accept();
                Thread t = new CadastroThread(ctrlProd, ctrlUsu, cliente);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
