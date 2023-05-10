package classes;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CardLogin {
    private JScrollPane spArea;
    private JButton atualizarButton;
    private JTable tbClientes;
    public ArrayList<Devedor> Devs = new ArrayList<Devedor>();
    private JPanel loginPainel;
    private JButton btnAchar;
    private JTextField txtId;
    private JButton btnExcluir;

    public JPanel getPanel(){
        return loginPainel;
    }

    public void createTable() throws Exception {
        tbClientes.setModel(new DefaultTableModel(null, new String[]{"Id","Divida", "Nome", "Sexo", "Idade",
                "CEP", "Estado", "Cidade", "Rua"}));

        var devedores = Devedores.getDevedores();

        while (devedores.next()) {
            Devs.add(new Devedor(devedores.getInt(2), devedores.getString(3), devedores.getString(4), devedores.getString(5), devedores.getFloat(6)));
        }

        DefaultTableModel modelo = (DefaultTableModel) tbClientes.getModel();
        int id = 0;
        for (Devedor dev : Devs) {
            dev.setIdDevedor(id);
            Logradouro logradouro = (Logradouro) ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", dev.getCep());
            if (logradouro != null) {
                modelo.addRow(new Object[]{dev.getIdDevedor(),dev.getDivida(), dev.getNome(),
                        dev.getSexo(), dev.getIdade(), dev.getCep(),
                        logradouro.getEstado(), logradouro.getCidade(), logradouro.getLogradouro()});
            }
            id++;
        }
        btnExcluir.addActionListener(this::Excluir);
        btnAchar.addActionListener(this::Achar);
        atualizarButton.addActionListener(this::Atualizar);

        txtId.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try{
                    int id = Integer.parseInt(txtId.getText())%10;

                    tbClientes.setModel(new DefaultTableModel(null, new String[]{"Id","Divida", "Nome", "Sexo", "Idade",
                            "CEP", "Estado", "Cidade", "Rua"}));

                    DefaultTableModel modelo = (DefaultTableModel) tbClientes.getModel();


                    Devedor dev = Devedores.getDevedor(id);
                    System.out.println(dev);

                    Logradouro logradouro = (Logradouro) ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", dev.getCep());
                    if (logradouro != null) {
                        modelo.addRow(new Object[]{dev.getIdDevedor(),dev.getDivida(), dev.getNome(),
                                dev.getSexo(), dev.getIdade(), dev.getCep(),
                                logradouro.getEstado(), logradouro.getCidade(), logradouro.getLogradouro()});
                    }

                }catch (Exception ex){
                    ex.getMessage();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tbClientes.setModel(new DefaultTableModel(null, new String[]{"Id","Divida", "Nome", "Sexo", "Idade",
                        "CEP", "Estado", "Cidade", "Rua"}));

                DefaultTableModel modelo = (DefaultTableModel) tbClientes.getModel();
                int id = 0;
                for (Devedor dev : Devs) {
                    try {
                        dev.setIdDevedor(id);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    Logradouro logradouro = (Logradouro) ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", dev.getCep());
                    if (logradouro != null) {
                        modelo.addRow(new Object[]{dev.getIdDevedor(),dev.getDivida(), dev.getNome(),
                                dev.getSexo(), dev.getIdade(), dev.getCep(),
                                logradouro.getEstado(), logradouro.getCidade(), logradouro.getLogradouro()});
                    }
                    id++;
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    public CardLogin() throws Exception {
        createTable();
    }

    private void Achar(ActionEvent actionEvent)
    {
        JOptionPane.showMessageDialog(null, "A");
    }
    private void Excluir(ActionEvent actionEvent)
    {
        JOptionPane.showMessageDialog(null, "ABC");
    }
    private void Atualizar(ActionEvent actionEvent)
    {
        JOptionPane.showMessageDialog(null, "ABC233");
    }
}
