/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.banco.tratamento;

/**
 *
 * @author harlock
 */
public  class Pessoa {
    private String nome;
    private String CPF;
    private String senha;
    private String agencia;
    private String saldo;
    private String Conjuta;
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getConjuta() {
        return Conjuta;
    }

    public void setConjuta(String Corrente) {
        this.Conjuta = Corrente;
    }
    

    
}
