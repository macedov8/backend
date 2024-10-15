package controller;

import model.UserModel;

public class UserController {
    public UserController() {
        new UserModel();
    }

    public boolean cadastrarUsuario(String nome, String email, String senha, byte[] foto) {
        return UserModel.cadastrarUsuario(nome, email, senha, foto);
    }

    public boolean atualizarUsuario(String email, String nome, String senha, byte[] foto) {
        return UserModel.atualizarUsuario(email, nome, senha, foto);
    }

    public boolean removerUsuario(String email) {
        return UserModel.removerUsuario(email);
    }
}
