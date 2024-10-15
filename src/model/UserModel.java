package model;

import java.sql.*;

public class UserModel {
    public static boolean cadastrarUsuario(String nome, String email, String senha, byte[] foto) {
        try (Connection conexao = MySQLConnectorModel.conectar()) {
            String sql = "INSERT INTO tbl_teste (nome, email, senha, foto) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, senha);
            pstmt.setBytes(4, foto);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet buscarUsuario(String email) {
        try {
            Connection conexao = MySQLConnectorModel.conectar();
            String sql = "SELECT * FROM tbl_teste WHERE email = ?";
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, email);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean atualizarUsuario(String email, String nome, String senha, byte[] foto) {
        try (Connection conexao = MySQLConnectorModel.conectar()) {
            String sql = "UPDATE tbl_teste SET nome = ?, senha = ?, foto = ? WHERE email = ?";
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, senha);
            pstmt.setBytes(3, foto);
            pstmt.setString(4, email);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removerUsuario(String email) {
        try (Connection conexao = MySQLConnectorModel.conectar()) {
            String sql = "DELETE FROM tbl_teste WHERE email = ?";
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, email);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
