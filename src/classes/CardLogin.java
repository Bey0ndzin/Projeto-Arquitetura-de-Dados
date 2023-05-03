package classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class CardLogin {
    private JScrollPane spArea;
    private JLabel lbIdCliente;
    private JTextField txtIdCliente;
    private JButton atualizarButton;
    private JButton btnAcharCliente;
    private JTable tbClientes;

    public JScrollPane getRootScroolPane(){
        return spArea;
    }

    public void createTable(){
        tbClientes.setModel(new DefaultTableModel(null, new String[] {"Id", "Divida", "Nome", "Sexo", "Idade",
                "CEP", "Estado", "Cidade", "Bairro", "Rua", "Num Casa" }));
    }

    public CardLogin(){
        createTable();
    }

}
