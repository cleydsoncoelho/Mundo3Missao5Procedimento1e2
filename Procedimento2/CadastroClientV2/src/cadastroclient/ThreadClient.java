package cadastroclient;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.util.List;

public class ThreadClient extends Thread {
    private ObjectInputStream entrada;
    private JTextArea textArea;

    public ThreadClient(ObjectInputStream entrada, JTextArea textArea) {
        this.entrada = entrada;
        this.textArea = textArea;
    }

    public void run() {
        try {
            while (true) {
                Object obj = entrada.readObject();

                // DEBUG: veja no Output do NetBeans o que está chegando!
                System.out.println("Objeto recebido: " + obj);
                System.out.println("Classe: " + (obj != null ? obj.getClass() : "null"));

                if (obj instanceof String) {
                    String msg = (String) obj;
                    SwingUtilities.invokeLater(() -> textArea.append(msg + "\n"));
                } else if (obj instanceof List) {
                    List<?> lista = (List<?>) obj;
                    StringBuilder sb = new StringBuilder();
                    for (Object item : lista) {
                        sb.append(item.toString()).append("\n");
                    }
                    SwingUtilities.invokeLater(() -> textArea.append(sb.toString()));
                } else {
                    // Para qualquer outro objeto não tratado
                    SwingUtilities.invokeLater(() -> textArea.append("Objeto desconhecido recebido: " + obj + "\n"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // ESSENCIAL: mostra o erro no Output
            SwingUtilities.invokeLater(() -> textArea.append("Conexão encerrada!\n"));
        }
    }
}
