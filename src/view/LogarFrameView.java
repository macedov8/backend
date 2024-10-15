package view;

import javax.swing.*;
import controller.LoginController;
import java.awt.*;

public class LogarFrameView extends JFrame {
    private final JLabel lblLogin = new JLabel("Login");
    private final JLabel lblSenha = new JLabel("Senha");
    public static final JLabel lblNotificacoes = new JLabel("Bem-vindo! Faça login ou cadastre-se.", SwingConstants.CENTER);
    public static final JTextField txtLogin = new JTextField();
    public static final JPasswordField txtSenha = new JPasswordField();
    private final JButton btnLogin = new JButton("Login");
    private final JButton btnCadastrar = new JButton("Cadastrar");
    public static final JCheckBox cbxAceite = new JCheckBox("Aceito os termos");
    public static LogarFrameView appLoginFrame;

    public LogarFrameView() {
        super("Tela de Login🍔");
        setLayout(new GridLayout(7, 1, 5, 5));
        addComponents();
        setSize(400, 300);
        setVisible(true);
    }

    private void addComponents() {
        JPanel pnlLinha2 = new JPanel(new GridLayout(1, 1, 5, 5));
        JPanel pnlLinha3 = new JPanel(new GridLayout(1, 1, 5, 5));
        JPanel pnlLinha4 = new JPanel(new GridLayout(1, 1, 5, 5));
        JPanel pnlLinha5 = new JPanel(new GridLayout(1, 1, 5, 5));
        JPanel pnlLinha6 = new JPanel(new GridLayout(1, 1, 5, 5));
        JPanel pnlLinha7 = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnlLinha8 = new JPanel(new GridLayout(1, 1, 5, 5));

        pnlLinha2.add(lblLogin);
        add(pnlLinha2);
        pnlLinha3.add(txtLogin);
        add(pnlLinha3);
        pnlLinha4.add(lblSenha);
        add(pnlLinha4);
        pnlLinha5.add(txtSenha);
        add(pnlLinha5);
        pnlLinha6.add(cbxAceite);
        add(pnlLinha6);
        pnlLinha7.add(btnLogin);
        pnlLinha7.add(btnCadastrar);
        add(pnlLinha7);
        pnlLinha8.add(lblNotificacoes);
        add(pnlLinha8);

        btnLogin.addActionListener(e -> new LoginController().logar(txtLogin.getText(), new String(txtSenha.getPassword())));
        btnCadastrar.addActionListener(e -> {
            NovoCadastroView appNovoCadastro = new NovoCadastroView();
            appNovoCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        appLoginFrame = new LogarFrameView();
    }
}
