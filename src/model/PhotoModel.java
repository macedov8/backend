package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PhotoModel {
    public static boolean cadastrarFoto(String nome, String caminho) {
        try (Connection conn = MySQLConnectorModel.conectar()) {
            String sql = "INSERT INTO fotos (nome, caminho) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, caminho);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet buscarFoto(String nome) {
        try {
            Connection conn = MySQLConnectorModel.conectar();
            String sql = "SELECT * FROM fotos WHERE nome = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            return pstmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean atualizarFoto(int id, String nome, String caminho) {
        try (Connection conn = MySQLConnectorModel.conectar()) {
            String sql = "UPDATE fotos SET nome = ?, caminho = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, caminho);
            pstmt.setInt(3, id);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removerFoto(int id) {
        try (Connection conn = MySQLConnectorModel.conectar()) {
            String sql = "DELETE FROM fotos WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
