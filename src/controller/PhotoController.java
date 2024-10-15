package controller;

import model.PhotoModel;
import javax.swing.*;
import java.sql.ResultSet;

public class PhotoController {
    public static String cadastrarFoto(String nome, String caminho) {
        if (PhotoModel.cadastrarFoto(nome, caminho)) {
            return "Foto cadastrada com sucesso.";
        } else {
            return "Erro ao cadastrar foto.";
        }
    }

    public static ResultSet buscarFoto(String nome) {
        try {
            return PhotoModel.buscarFoto(nome);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar foto.");
            return null;
        }
    }

    public static String atualizarFoto(int id, String nome, String caminho) {
        if (PhotoModel.atualizarFoto(id, nome, caminho)) {
            return "Foto atualizada com sucesso.";
        } else {
            return "Erro ao atualizar foto.";
        }
    }

    public static String removerFoto(int id) {
        if (PhotoModel.removerFoto(id)) {
            return "Foto removida com sucesso.";
        } else {
            return "Erro ao remover foto.";
        }
    }
}
