package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import model.MySQLConnectorModel;

public class NovoCadastroView extends JFrame {
    private final JLabel nomeJLabel = new JLabel("Digite um nome:", SwingConstants.RIGHT);
    private final JTextField nomeJTextField = new JTextField();
    private final JLabel emailJLabel = new JLabel("Digite um email:", SwingConstants.RIGHT);
    private final JTextField emailJTextField = new JTextField();
    private final JLabel senhaJLabel = new JLabel("Digite uma senha:", SwingConstants.RIGHT);
    private final JPasswordField senhaJPasswordField = new JPasswordField();
    private final JButton cadastrarJButton = new JButton("Cadastrar");
    private final JLabel notificacaoJLabel = new JLabel("Notificações...", SwingConstants.CENTER);
    private final JButton adicionarFotoJButton = new JButton("Adicionar Foto");
    private byte[] foto;

    public NovoCadastroView() {
        super("Novo Cadastro🍔");
        setLayout(new GridLayout(6, 1, 5, 5));
        addComponents();
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addComponents() {
        JPanel linha1 = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel linha2 = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel linha3 = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel linha4 = new JPanel(new GridLayout(1, 1, 5, 5));
        JPanel linha5 = new JPanel(new GridLayout(1, 1, 5, 5));
        JPanel linha6 = new JPanel(new GridLayout(1, 1, 5, 5));

        cadastrarJButton.addActionListener(e -> cadastrarUsuario());
        adicionarFotoJButton.addActionListener(e -> adicionarFoto());

        linha1.add(nomeJLabel);
        linha1.add(nomeJTextField);
        add(linha1);

        linha2.add(emailJLabel);
        linha2.add(emailJTextField);
        add(linha2);

        linha3.add(senhaJLabel);
        linha3.add(senhaJPasswordField);
        add(linha3);

        linha4.add(adicionarFotoJButton);
        add(linha4);

        linha5.add(cadastrarJButton);
        add(linha5);

        linha6.add(notificacaoJLabel);
        add(linha6);
    }

    private void adicionarFoto() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(file)) {
                foto = new byte[(int) file.length()];
                fis.read(foto);
                notificacaoJLabel.setText("Foto adicionada com sucesso.");
            } catch (Exception e) {
                notificacaoJLabel.setText("Erro ao adicionar foto: " + e.getMessage());
            }
        }
    }

    private void cadastrarUsuario() {
        if (foto == null) {
            notificacaoJLabel.setText("Erro: Nenhuma foto adicionada.");
            return;
        }

        try {
            Connection conexao = MySQLConnectorModel.conectar();
            String strSqlPesquisarEmail = "SELECT * FROM `db_teste`.`tbl_teste` WHERE `email` = ?";
            PreparedStatement pstmt = conexao.prepareStatement(strSqlPesquisarEmail);
            pstmt.setString(1, emailJTextField.getText());
            ResultSet rstPesquisarEmail = pstmt.executeQuery();
            if (rstPesquisarEmail.next()) {
                notificacaoJLabel.setText("Ops! Este email já está cadastrado.");
            } else {
                String strSqlCadastrarRegistro = "INSERT INTO `db_teste`.`tbl_teste` (`nome`,`email`,`senha`,`foto`) VALUES (?,?,?,?)";
                PreparedStatement pstmtCadastrar = conexao.prepareStatement(strSqlCadastrarRegistro);
                pstmtCadastrar.setString(1, nomeJTextField.getText());
                pstmtCadastrar.setString(2, emailJTextField.getText());
                pstmtCadastrar.setString(3, String.valueOf(senhaJPasswordField.getPassword()));
                pstmtCadastrar.setBytes(4, foto);
                pstmtCadastrar.executeUpdate();
                notificacaoJLabel.setText("Cadastro realizado com sucesso!");
                dispose();
            }
        } catch (Exception e) {
            notificacaoJLabel.setText("Erro ao realizar o cadastro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        NovoCadastroView appNovoCadastro = new NovoCadastroView();
        appNovoCadastro.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
