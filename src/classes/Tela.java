package classes;

import sql.MeuResultSet;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.synth.Region;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class Tela extends JFrame {

    private JTextField txtNome, txtIdade, txtCep, txtDivida, txtId, txtEstado, txtCidade, txtRua, txtNumCasa;
    private JComboBox txtSexo;

    public Tela(){
        //classes.Tela principal
        setVisible(true);
        setSize(900,650);
        setTitle("Agiotagem");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        /*TEM QUE MUDAR O ICONE*/
        
        //Painel da tela
        JLabel titulo = new JLabel("Seu app de Agiotagem");
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setBounds(220, 50, 450, 100);
        
        JButton btnCadastro = new JButton("Cadastrar Devedor"); //cadastro (create)
        btnCadastro.setBounds(300,250,250,70);
        btnCadastro.setFont(new Font("Cascadia Code", Font.BOLD, 15));
        btnCadastro.addActionListener(this::cardCadastro);

        JButton btnLogin = new JButton("Encontrar Devedor"); //login (read, update e delete)
        btnLogin.setBounds(300, 350, 250, 70);
        btnLogin.setFont(new Font("Cascadia Code", Font.BOLD, 15));
        btnLogin.addActionListener(this::cardLogin);

        JPanel painel = new JPanel(new ScrollPaneLayout());
        //MUDAR O BG PRA UMA IMG
        painel.add(titulo);
        painel.add(btnCadastro);
        painel.add(btnLogin);

        add(painel);
    }

    public void cardCadastro(ActionEvent actionEvent) {
        //Card de cadastro
        JFrame cardCadastro = new JFrame("Cadastro");
        cardCadastro.setVisible(true);
        cardCadastro.setSize(600,550);
        cardCadastro.setLocationRelativeTo(null);
        cardCadastro.setResizable(false);

        //Campos de dados
        JLabel lbNome = new JLabel("Nome");
        lbNome.setBounds(50, 20,200,50);
        lbNome.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        txtNome = new JTextField();
        txtNome.setBounds(110, 20, 390, 50);
        txtNome.setFont(new Font("Cascadia Code", Font.PLAIN, 20));

        JLabel lbSexo = new JLabel("Sexo");
        lbSexo.setBounds(50, 120,200,50);
        lbSexo.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        String sexo[] = {"M", "F"};
        txtSexo = new JComboBox<>(sexo);
        txtSexo.setBounds(110, 120, 60, 50);
        txtSexo.setFont(new Font("Cascadia Code", Font.PLAIN, 20));

        JLabel lbIdade = new JLabel("Idade");
        lbIdade.setBounds(45, 220,200,50);
        lbIdade.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        txtIdade = new JTextField("0");
        txtIdade.setBounds(110, 220, 80, 50);
        txtIdade.setFont(new Font("Cascadia Code", Font.PLAIN, 20));

        JLabel lbCep = new JLabel("Cep");
        lbCep.setBounds(50, 320,200,50);
        lbCep.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        txtCep = new JTextField("0");
        txtCep.setBounds(110, 320, 120, 50);
        txtCep.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        txtCep.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String cep = txtCep.getText();
                if(cep.length() == 8)
                {
                    try{
                        Logradouro logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", cep);
                        if(logradouro != null)
                        {
                            txtCidade.setText(logradouro.getCidade());
                            txtEstado.setText(logradouro.getEstado());
                            txtRua.setText(logradouro.getLogradouro());
                        }
                        else{
                            txtCep.setText("");
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String cep = txtCep.getText();
                if(cep.length() == 8)
                {
                    try{
                        Logradouro logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", cep);
                        if(logradouro != null)
                        {
                            txtCidade.setText(logradouro.getCidade());
                            txtEstado.setText(logradouro.getEstado());
                            txtRua.setText(logradouro.getLogradouro());
                        }
                        else
                        {
                            txtCep.setText("");
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) { }
        });

        JLabel lbDivida = new JLabel("Divida");
        lbDivida.setBounds(30, 420,200,50);
        lbDivida.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        txtDivida = new JTextField("0");
        txtDivida.setBounds(110, 420, 130, 50);
        txtDivida.setFont(new Font("Cascadia Code", Font.PLAIN, 20));

        JLabel lbId = new JLabel("Id");
        lbId.setBounds(330, 100,200,50);
        lbId.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        txtId = new JTextField("-");
        txtId.setBounds(370, 100, 60, 50);
        txtId.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        txtId.setEditable(false);

        JLabel lbEstado = new JLabel("Estado");
        lbEstado.setBounds(290, 170,200,50);
        lbEstado.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        txtEstado = new JTextField("digite um cep válido");
        txtEstado.setBounds(370, 170, 200, 50);
        txtEstado.setFont(new Font("Cascadia Code", Font.PLAIN, 12));
        txtEstado.setEditable(false);

        JLabel lbCidade = new JLabel("Cidade");
        lbCidade.setBounds(290, 230,200,50);
        lbCidade.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        txtCidade = new JTextField("digite um cep válido");
        txtCidade.setBounds(370, 230, 200, 50);
        txtCidade.setFont(new Font("Cascadia Code", Font.PLAIN, 12));
        txtCidade.setEditable(false);

        JLabel lbRua = new JLabel("Rua");
        lbRua.setBounds(320, 300,200,50);
        lbRua.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        txtRua = new JTextField("digite um cep válido");
        txtRua.setBounds(370, 300, 200, 50);
        txtRua.setFont(new Font("Cascadia Code", Font.PLAIN, 12));
        txtRua.setEditable(false);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(370, 440,170,50);
        btnSalvar.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
        btnSalvar.addActionListener(this::Cadastrar);

        //Painel de cadastro
        JPanel painelCadastro = new JPanel(new ScrollPaneLayout());
        painelCadastro.add(lbNome);
        painelCadastro.add(txtNome);
        painelCadastro.add(lbSexo);
        painelCadastro.add(txtSexo);
        painelCadastro.add(lbIdade);
        painelCadastro.add(txtIdade);
        painelCadastro.add(lbCep);
        painelCadastro.add(txtCep);
        painelCadastro.add(lbDivida);
        painelCadastro.add(txtDivida);

        painelCadastro.add(lbId);
        painelCadastro.add(txtId);
        painelCadastro.add(lbEstado);
        painelCadastro.add(txtEstado);
        painelCadastro.add(lbCidade);
        painelCadastro.add(txtCidade);
        painelCadastro.add(lbRua);
        painelCadastro.add(txtRua);

        painelCadastro.add(btnSalvar);

        cardCadastro.add(painelCadastro);
    }

    private void Cadastrar(ActionEvent actionEvent){

        try{
            Devedor novoDevedor = new Devedor(Integer.parseInt(txtIdade.getText()), txtNome.getText(), txtSexo.getSelectedItem().toString(), txtCep.getText(), Float.parseFloat(txtDivida.getText()));

            Devedores.incluir(novoDevedor);

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private void Alterar(ActionEvent actionEvent){

    }

    //card login
    private static void createGUI() throws Exception {
        CardLogin cl = new CardLogin();
        JPanel root = cl.getPanel();
        JFrame frame = new JFrame();
        frame.add(root);
        frame.pack();
        frame.setSize(1200,600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void cardLogin(ActionEvent actionEvent) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    createGUI();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
