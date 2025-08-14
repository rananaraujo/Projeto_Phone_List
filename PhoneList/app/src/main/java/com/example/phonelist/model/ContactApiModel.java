package com.example.phonelist.model;

public class ContactApiModel {
    private String name;
    private String apelido;
    private String number;
    private String cpf;
    private String email;

    public ContactApiModel(String name, String apelido, String number, String cpf, String email) {
        this.name = name;
        this.apelido = apelido;
        this.number = number;
        this.cpf = cpf;
        this.email = email;
    }

    public ContactApiModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}