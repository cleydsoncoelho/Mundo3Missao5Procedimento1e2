package cadastroserver;

import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import model.Produto;
import model.Usuario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class CadastroThread implements Runnable {

    private ProdutoJpaController ctrl;
    private UsuarioJpaController ctrlUsu;
    private Socket s1;

    public CadastroThread(ProdutoJpaController ctrl, UsuarioJpaController ctrlUsu, Socket s1) {
        this.ctrl = ctrl;
        this.ctrlUsu = ctrlUsu;
        this.s1 = s1;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(s1.getInputStream())) {

            // Login
            out.writeObject("Digite o login:");
            out.flush();

            String login = (String) in.readObject();

            // Senha
            out.writeObject("Digite a senha:");
            out.flush();

            String senha = (String) in.readObject();

            // Validação
            Usuario u = ctrlUsu.findUsuario(login, senha);

            if (u == null) {
                out.writeObject("Credenciais inválidas. Conexão encerrada.");
                out.flush();
                return;
            }

            out.writeObject("Credenciais válidas. Você está conectado.");
            out.flush();

            // Loop de comandos
            while (true) {
                out.writeObject("Digite um comando (L para lista de produtos, outro para sair):");
                out.flush();

                String comando = (String) in.readObject();

                if (comando.equalsIgnoreCase("L")) {
                    // Pegar produtos do banco
                    List<Produto> produtos = ctrl.findProdutoEntities();

                    // Enviar lista de produtos para o cliente
                    out.writeObject(produtos);
                    out.flush();
                } else {
                    // Outro comando → sair
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                s1.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
