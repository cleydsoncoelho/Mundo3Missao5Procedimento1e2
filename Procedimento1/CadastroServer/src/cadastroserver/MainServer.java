package cadastroserver;

import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    public static void main(String[] args) {
        final int PORTA = 4321;

        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor aguardando conexões na porta " + PORTA);

            // Instanciar EntityManagerFactory
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");

            // Instanciar ProdutoJpaController
            ProdutoJpaController ctrl = new ProdutoJpaController(emf);

            // Instanciar UsuarioJpaController
            UsuarioJpaController ctrlUsu = new UsuarioJpaController(emf);

            // Loop infinito para aceitar conexões
            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + socketCliente.getInetAddress());

                // Criar e iniciar a thread passando ctrl, ctrlUsu e o Socket
                Thread t = new Thread(new CadastroThread(ctrl, ctrlUsu, socketCliente));
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
