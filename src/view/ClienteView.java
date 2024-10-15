package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import model.MySQLConnectorModel;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ClienteView extends JFrame {
    private JLabel nomeLabel;
    private JLabel emailLabel;
    private JLabel senhaLabel;
    private JLabel fotoLabel;
    private JButton editarButton;
    private JButton removerButton;
    private String email;
    private byte[] foto;

    public ClienteView(String email) {
        super("Detalhes do Cliente");
        this.email = email;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nomeLabel = new JLabel();
        emailLabel = new JLabel();
        senhaLabel = new JLabel();
        fotoLabel = new JLabel();
        editarButton = new JButton("Editar");
        removerButton = new JButton("Remover");

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(nomeLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(emailLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(senhaLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Foto:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        fotoLabel.setPreferredSize(new Dimension(100, 100));
        add(fotoLabel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editarButton);
        buttonPanel.add(removerButton);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        loadClienteData();

        editarButton.addActionListener(e -> abrirMenuEditar());
        removerButton.addActionListener(e -> removerCliente());

        setSize(400, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadClienteData() {
        try {
            Connection conexao = MySQLConnectorModel.conectar();
            String query = "SELECT * FROM `db_teste`.`tbl_teste` WHERE email = ?";
            PreparedStatement pstmt = conexao.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                nomeLabel.setText(rs.getString("nome"));
                emailLabel.setText(rs.getString("email"));
                senhaLabel.setText(rs.getString("senha"));

                foto = rs.getBytes("foto");
                if (foto != null) {
                    InputStream is = new ByteArrayInputStream(foto);
                    Image img = ImageIO.read(is);
                    img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    fotoLabel.setIcon(new ImageIcon(img));
                } else {
                    Image img = ImageIO.read(new File("C:\\Users\\uept02-user\\Documents\\Lucas\\PERFECTBURGER\\logo\\zé ninguem.jpg"));
                    img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    fotoLabel.setIcon(new ImageIcon(img));
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
                dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados.");
        }
    }

    private void abrirMenuEditar() {
        JTextField nomeField = new JTextField(nomeLabel.getText());
        JTextField emailField = new JTextField(emailLabel.getText());
        JPasswordField senhaField = new JPasswordField(senhaLabel.getText());
        JButton alterarFotoButton = new JButton("Alterar Foto");
        JButton removerFotoButton = new JButton("Remover Foto");

        alterarFotoButton.addActionListener(e -> editarFoto());
        removerFotoButton.addActionListener(e -> removerFoto());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);
        panel.add(alterarFotoButton);
        panel.add(removerFotoButton);

        int option = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            editarCliente(nomeField.getText(), emailField.getText(), String.valueOf(senhaField.getPassword()));
        }
    }

    private void editarCliente(String nome, String email, String senha) {
        try {
            Connection conexao = MySQLConnectorModel.conectar();
            String query = "UPDATE tbl_teste SET nome = ?, email = ?, senha = ? WHERE email = ?";
            PreparedStatement pstmt = conexao.prepareStatement(query);
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, senha);
            pstmt.setString(4, this.email);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso.");
            loadClienteData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente.");
        }
    }

    private void editarFoto() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(file)) {
                foto = new byte[(int) file.length()];
                fis.read(foto);

                Connection conexao = MySQLConnectorModel.conectar();
                String query = "UPDATE tbl_teste SET foto = ? WHERE email = ?";
                PreparedStatement pstmt = conexao.prepareStatement(query);
                pstmt.setBytes(1, foto);
                pstmt.setString(2, email);
                pstmt.executeUpdate();

                InputStream is = new ByteArrayInputStream(foto);
                Image img = ImageIO.read(is);
                img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                fotoLabel.setIcon(new ImageIcon(img));

                JOptionPane.showMessageDialog(this, "Foto atualizada com sucesso.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar foto.");
            }
        }
    }

    private void removerFoto() {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover a foto?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conexao = MySQLConnectorModel.conectar();
                String query = "UPDATE tbl_teste SET foto = NULL WHERE email = ?";
                PreparedStatement pstmt = conexao.prepareStatement(query);
                pstmt.setString(1, email);
                pstmt.executeUpdate();
                Image img = ImageIO.read(new File("C:\\Users\\uept02-user\\Documents\\Lucas\\PERFECTBURGER\\logo\\zé ninguem.jpg"));
                img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                fotoLabel.setIcon(new ImageIcon(img));
                JOptionPane.showMessageDialog(this, "Foto removida com sucesso.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao remover foto.");
            }
        }
    }

    private void removerCliente() {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover este cliente?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conexao = MySQLConnectorModel.conectar();
                String query = "DELETE FROM tbl_teste WHERE email = ?";
                PreparedStatement pstmt = conexao.prepareStatement(query);
                pstmt.setString(1, email);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cliente removido com sucesso.");
                dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao remover cliente.");
            }
        }
    }
}
