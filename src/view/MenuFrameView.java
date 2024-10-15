package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;
import model.MySQLConnectorModel;

public class MenuFrameView extends JFrame {
    private Image backgroundImage;

    public MenuFrameView() {
        super("Perfect Burger🍔");
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\uept02-user\\Documents\\Lucas\\PERFECTBURGER\\logo\\logo.jpg"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar a imagem: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        setLayout(new BorderLayout());
        JPanel panel = new BackgroundPanel();
        panel.setLayout(new FlowLayout());

        JMenu usuarioMenu = new JMenu("Usuários");
        usuarioMenu.setMnemonic('U');

        JMenuItem cadastrarMenu = new JMenuItem("Cadastrar");
        cadastrarMenu.setMnemonic('C');
        usuarioMenu.add(cadastrarMenu);

        JMenuItem pesquisarMenu = new JMenuItem("Pesquisar");
        pesquisarMenu.setMnemonic('P');
        usuarioMenu.add(pesquisarMenu);

        cadastrarMenu.addActionListener(e -> {
            NovoCadastroView appNovoCadastro = new NovoCadastroView();
            appNovoCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        });

        pesquisarMenu.addActionListener(e -> {
            String email = JOptionPane.showInputDialog("Digite o email a ser pesquisado:");
            if (email != null && !email.trim().isEmpty()) {
                try {
                    Connection conexao = MySQLConnectorModel.conectar();
                    String query = "SELECT * FROM `db_teste`.`tbl_teste` WHERE email = ?";
                    PreparedStatement pstmt = conexao.prepareStatement(query);
                    pstmt.setString(1, email);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        ClienteView clienteView = new ClienteView(email);
                        clienteView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao buscar cliente: " + ex.getMessage());
                }
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('x');
        usuarioMenu.add(exitItem);
        exitItem.addActionListener(e -> {
            int confirmDialog = JOptionPane.showConfirmDialog(null, "Deseja mesmo sair do sistema?", "Confirmação", JOptionPane.OK_CANCEL_OPTION);
            if (confirmDialog == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });

        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(usuarioMenu);

        panel.add(new JLabel("Bem-vindo ao Perfect Burger!"));

        add(panel, BorderLayout.CENTER);
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        new MenuFrameView();
    }
}
