package controller;
import model.*;
import view.*;

import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class LogarController extends JFrame {
    public static void logar() {
        try {
            if (LoginFrameView.txtLogin.getText().trim().length() == 0) {
                LoginFrameView.lblNotificacoes.setText(HelperController.setHtmlFormat("É necessário digitar um login para acessar! Por favor, verifique e tente novamente."));
                LoginFrameView.txtLogin.requestFocus();
            } else if (String.valueOf(LoginFrameView.txtSenha.getPassword()).trim().length() == 0) {
                LoginFrameView.lblNotificacoes.setText(HelperController.setHtmlFormat("É necessário digitar uma senha para acessar! Por favor, verifique e tente novamente."));
                LoginFrameView.txtSenha.requestFocus();
            } else {
                boolean achouUsuario = LoginModel.loginUsuario(LoginFrameView.txtLogin.getText(), String.valueOf(LoginFrameView.txtSenha.getPassword()).trim());
                if (achouUsuario == true) {
                    MenuFrameView appMenuFrame = new MenuFrameView();
                    appMenuFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    LoginFrameView.appLoginFrame.dispose();
                } else {
                    LoginFrameView.lblNotificacoes.setText(HelperController.setHtmlFormat("Não foi encontrado o login e/ou senha digitados! Por favor, verifique e tente novamente."));
                }
            }
        } catch (Exception e) {
            LoginFrameView.lblNotificacoes.setText(HelperController.setHtmlFormat("Ops! Deu ruim no banco de dados, veja o erro: " + e));
        }        
    }
}
