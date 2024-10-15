package controller;

import javax.swing.*;
import model.UserModel;
import view.MenuFrameView;

import java.sql.ResultSet;

public class LoginController {
    public void logar(String email, String senha) {
        try (ResultSet rs = UserModel.buscarUsuario(email)) {
            if (rs != null && rs.next()) {
                if (rs.getString("senha").equals(senha)) {
                    new MenuFrameView().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciais inválidas.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao logar: " + e.getMessage());
        }
    }
}
