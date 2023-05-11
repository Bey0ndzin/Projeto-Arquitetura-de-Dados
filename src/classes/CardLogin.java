package classes;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class CardLogin {
    private JScrollPane spArea;
    private JButton atualizarButton;
    private JTable tbClientes;
    private ArrayList<Devedor> Devs = new ArrayList<Devedor>();
    private JPanel loginPainel;
    private JButton btnAchar;
    private JButton btnExcluir;
    private JTextField txtId;

    public JPanel getPanel(){
        return loginPainel;
    }

    public void createTable() throws Exception {
        btnExcluir.addActionListener(this::Excluir);
        btnAchar.addActionListener(this::Achar);
        atualizarButton.addActionListener(this::Atualizar);

        var devedores = Devedores.getDevedores();

        while (devedores.next()) {
            Devedor dev = new Devedor(devedores.getInt(2), devedores.getString(3), devedores.getString(4), devedores.getString(5), devedores.getFloat(6));
            dev.setIdDevedor(devedores.getInt(1));
            Devs.add(dev);
        }

        DrawTable();

        txtId.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try{
                    Integer.parseInt(txtId.getText());
                }catch (Exception ezx){
                    try {
                        DrawTable();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        tbClientes.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                atualizarButton.setEnabled(!atualizarButton.isEnabled());
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
    }

    private void DrawTable() throws Exception{
        tbClientes.setModel(new DefaultTableModel(null, new String[]{"Id","Divida", "Nome", "Sexo", "Idade",
                "CEP", "Estado", "Cidade", "Rua"}));

        DefaultTableModel modelo = (DefaultTableModel) tbClientes.getModel();
        for (Devedor dev : Devs) {
            Logradouro logradouro = (Logradouro) ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", dev.getCep());
            if (logradouro != null) {
                modelo.addRow(new Object[]{dev.getIdDevedor(),dev.getDivida(), dev.getNome(),
                        dev.getSexo(), dev.getIdade(), dev.getCep(),
                        logradouro.getEstado(), logradouro.getCidade(), logradouro.getLogradouro()});
            }
        }
    }
    public CardLogin() throws Exception {
        createTable();
    }

    private void Achar(ActionEvent actionEvent)
    {
        try{
            int id = Integer.parseInt(txtId.getText());

            tbClientes.setModel(new DefaultTableModel(null, new String[]{"Id","Divida", "Nome", "Sexo", "Idade",
                    "CEP", "Estado", "Cidade", "Rua"}));

            DefaultTableModel modelo = (DefaultTableModel) tbClientes.getModel();

            Devedor dev = Devedores.getDevedor(id);
            System.out.println(dev);
            if(dev == null)
            {
                DrawTable();
            }

            Logradouro logradouro = (Logradouro) ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", dev.getCep());
            if (logradouro != null) {
                modelo.addRow(new Object[]{id,dev.getDivida(), dev.getNome(),
                        dev.getSexo(), dev.getIdade(), dev.getCep(),
                        logradouro.getEstado(), logradouro.getCidade(), logradouro.getLogradouro()});
            }

        }catch (Exception ex){
            ex.getMessage();
        }
    }
    private void Excluir(ActionEvent actionEvent)
    {
        try{
            int id = Integer.parseInt(txtId.getText());

            Devedores.excluir(id);
            Devs = new ArrayList<Devedor>();
            var devedores = Devedores.getDevedores();

            while (devedores.next()) {
                Devedor dev = new Devedor(devedores.getInt(2), devedores.getString(3), devedores.getString(4), devedores.getString(5), devedores.getFloat(6));
                dev.setIdDevedor(devedores.getInt(1));
                Devs.add(dev);
            }

            DrawTable();

        }catch (Exception ex){
            ex.getMessage();
        }
    }
    private void Atualizar(ActionEvent actionEvent)
    {
        ArrayList<Devedor> novosDevs = new ArrayList<Devedor>();
        String[] dadosDevedor = new String[tbClientes.getColumnCount()-3];
        for(int i = 0; i < tbClientes.getRowCount(); i++)
        {
            for(int j = 1; j < tbClientes.getColumnCount()-3; j++)
            {
                dadosDevedor[j-1] = tbClientes.getModel().getValueAt(i, j).toString();
            }
            try{
                int idade = Integer.parseInt(dadosDevedor[3]);
                String nome = dadosDevedor[1];
                String sexo = dadosDevedor[2];
                String cep = dadosDevedor[4];
                Float divida = Float.parseFloat(dadosDevedor[0]);
                Devedor dev = new Devedor(idade, nome, sexo, cep, divida);
                dev.setIdDevedor(Integer.parseInt(tbClientes.getModel().getValueAt(i, 0).toString()));
                if(!dev.equals(Devs.get(i)))
                    Devedores.alterar(dev);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            dadosDevedor = new String[tbClientes.getColumnCount()-3];
        }

        /*while (devedores.next()) {
            Devedor dev = new Devedor(devedores.getInt(2), devedores.getString(3), devedores.getString(4), devedores.getString(5), devedores.getFloat(6));
            dev.setIdDevedor(devedores.getInt(1));
            novosDevs.add(dev);
        }*/

    }

}
