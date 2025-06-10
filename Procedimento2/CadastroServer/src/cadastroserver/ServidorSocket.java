package cadastroserver;

import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket {

    public static void main(String[] args) {
        final int PORTA = 12345;

        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor aguardando conexões na porta " + PORTA);

            // Criar controllers uma vez
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");
            ProdutoJpaController ctrlProduto = new ProdutoJpaController(emf);
            UsuarioJpaController ctrlUsuario = new UsuarioJpaController(emf);

            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + socketCliente.getInetAddress());

                // Criar a CadastroThread
                Thread t = new Thread(new CadastroThread(ctrlProduto, ctrlUsuario, socketCliente));
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
