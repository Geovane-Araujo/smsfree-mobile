package com.example.sendsms.models;

public class Usuario {

    public Boolean add = true;
    public Boolean edit = false;
    public Boolean dele = false;
    public int id;
    public String nome = "";
    public String login = "";
    public String senha = "";

    public Boolean getAdd() {
        return add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public Boolean getDele() {
        return dele;
    }

    public void setDele(Boolean dele) {
        this.dele = dele;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
