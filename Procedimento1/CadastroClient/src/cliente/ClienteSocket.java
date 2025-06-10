package cliente;

import model.Produto;  // Import CORRETO — se o pacote model está DIRETO no Source Packages
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClienteSocket {

    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PORTA = 4321;

        try (Socket socket = new Socket(SERVIDOR, PORTA);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // 1️⃣ Recebe "Digite o login:"
            String msg = (String) in.readObject();
            System.out.println(msg);

            // 2️⃣ Envia login (exemplo: op1)
            out.writeObject("op1");
            out.flush();

            // 3️⃣ Recebe "Digite a senha:"
            msg = (String) in.readObject();
            System.out.println(msg);

            // 4️⃣ Envia senha (exemplo: op1)
            out.writeObject("op1");
            out.flush();

            // 5️⃣ Recebe confirmação
            msg = (String) in.readObject();
            System.out.println(msg);

            if (msg.contains("inválidas")) {
                // Se login inválido → encerra
                return;
            }

            // 6️⃣ Recebe "Digite um comando"
            msg = (String) in.readObject();
            System.out.println(msg);

            // 7️⃣ Envia comando "L"
            out.writeObject("L");
            out.flush();

            // 8️⃣ Recebe a List<Produto>
            List<Produto> lista = (List<Produto>) in.readObject();

            System.out.println("Produtos recebidos:");
            for (Produto p : lista) {
                System.out.println(p.getNome() + " - R$ " + p.getPrecoVenda());
            }

            // 9️⃣ Fecha conexão
            System.out.println("Conexão encerrada.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
