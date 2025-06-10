package cadastroclient;

import javax.swing.*;

public class SaidaFrame extends JDialog {
    public JTextArea texto;

    public SaidaFrame() {
        setTitle("Saída do Servidor");
        setBounds(100, 100, 500, 400);
        setModal(false);
        texto = new JTextArea();
        texto.setEditable(false);
        add(new JScrollPane(texto));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
