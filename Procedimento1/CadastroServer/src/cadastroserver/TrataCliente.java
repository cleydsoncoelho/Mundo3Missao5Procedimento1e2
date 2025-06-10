package cadastroserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TrataCliente implements Runnable {

    private Socket socket;

    public TrataCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            // 1. Recebe login
            out.println("Digite o login:");
            String login = in.readLine();

            // 2. Recebe senha
            out.println("Digite a senha:");
            String senha = in.readLine();

            // 3. Valida credenciais (exemplo simples: login=admin, senha=123)
            if (!"admin".equals(login) || !"123".equals(senha)) {
                out.println("Credenciais inválidas. Desconectando...");
                socket.close();
                System.out.println("Cliente desconectado (credenciais inválidas)");
                return;
            }

            out.println("Credenciais válidas. Você está conectado.");

            // 4. Loop de comandos
            String comando;
            while ((comando = in.readLine()) != null) {
                if (comando.equalsIgnoreCase("L")) {
                    // Aqui depois vamos integrar com JPA para pegar produtos
                    out.println("Lista de produtos: Produto 1, Produto 2, Produto 3");
                } else {
                    out.println("Comando desconhecido.");
                }
            }

            socket.close();
            System.out.println("Cliente desconectado.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
