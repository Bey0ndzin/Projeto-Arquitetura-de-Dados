package classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;

public class CardLogin {
    private JScrollPane spArea;
    private JButton atualizarButton;
    private JTable tbClientes;
    public ArrayList<Devedor> Devs = new ArrayList<Devedor>();
    private JPanel loginPainel;
    private JButton btnAchar;
    private JTextField txtId;

    public JPanel getPanel(){
        return loginPainel;
    }

    public void createTable() throws Exception {
        tbClientes.setModel(new DefaultTableModel(null, new String[]{"Divida", "Nome", "Sexo", "Idade",
                "CEP", "Estado", "Cidade", "Rua"}));

        var devedores = Devedores.getDevedores();

        while (devedores.next()) {
            Devs.add(new Devedor(devedores.getInt(2), devedores.getString(3), devedores.getString(4), devedores.getString(5), devedores.getFloat(6)));
        }

        DefaultTableModel modelo = (DefaultTableModel) tbClientes.getModel();
        for (Devedor dev : Devs) {
            Logradouro logradouro = (Logradouro) ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", dev.getCep());
            if (logradouro != null) {
                modelo.addRow(new Object[]{dev.getDivida(), dev.getNome(),
                        dev.getSexo(), dev.getIdade(), dev.getCep(),
                        logradouro.getEstado(), logradouro.getCidade(), logradouro.getLogradouro()});
            }
        }
    }
    public CardLogin() throws Exception {

        createTable();

    }

}
