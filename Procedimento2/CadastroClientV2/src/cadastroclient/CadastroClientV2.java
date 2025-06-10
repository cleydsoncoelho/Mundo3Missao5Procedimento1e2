package cadastroclient;

import java.io.*;
import java.net.Socket;
import javax.swing.*;

public class CadastroClientV2 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4321);
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            // Envio login/senha
            System.out.print("Login: ");
            String login = teclado.readLine();
            saida.writeObject(login);

            System.out.print("Senha: ");
            String senha = teclado.readLine();
            saida.writeObject(senha);

            // Recebe resposta do servidor
            String resposta = (String) entrada.readObject();
            System.out.println(resposta);
            if (!resposta.equals("Login OK")) {
                socket.close();
                return;
            }

            // Janela para saída
            SaidaFrame janela = new SaidaFrame();
            janela.setVisible(true);

            // Thread para leitura assíncrona
            Thread t = new ThreadClient(entrada, janela.texto);
            t.start();

            // Loop de menu
            while (true) {
                System.out.println("\nMenu: [L]istar, [E]ntrada, [S]aída, [X] Sair");
                String cmd = teclado.readLine().toUpperCase();

                saida.writeObject(cmd);

                if (cmd.equals("E") || cmd.equals("S")) {
                    System.out.print("Id da pessoa: ");
                    int idPessoa = Integer.parseInt(teclado.readLine());
                    saida.writeObject(idPessoa);

                    System.out.print("Id do produto: ");
                    int idProduto = Integer.parseInt(teclado.readLine());
                    saida.writeObject(idProduto);

                    System.out.print("Quantidade: ");
                    int quantidade = Integer.parseInt(teclado.readLine());
                    saida.writeObject(quantidade);

                    System.out.print("Valor unitário: ");
                    double valorUnit = Double.parseDouble(teclado.readLine());
                    saida.writeObject(valorUnit);
                } else if (cmd.equals("X")) {
                    break;
                }
                // Comando "L" só envia, resposta aparece na janela
            }
            socket.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
