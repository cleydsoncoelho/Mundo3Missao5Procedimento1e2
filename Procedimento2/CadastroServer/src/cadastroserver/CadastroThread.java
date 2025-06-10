package cadastroserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.Produto;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import model.Usuario;
import java.util.List;

public class CadastroThread extends Thread {
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
                    List<Produto> produtos = ctrl.findProdutoEntities();
                    saida.writeObject(produtos);
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
