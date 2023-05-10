package classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CardLogin {
    private JScrollPane spArea;
    private JButton atualizarButton;
    private JTable tbClientes;
    private JPanel loginPainel;
    private JButton btnAchar;
    private JTextField txtId;

    public JPanel getPanel(){
        return loginPainel;
    }

    public void createTable(){
        tbClientes.setModel(new DefaultTableModel(null, new String[] {"Divida", "Nome", "Sexo", "Idade",
                "CEP", "Estado", "Cidade", "Rua"}));
    }

    public CardLogin(){
        createTable();
    }

}
