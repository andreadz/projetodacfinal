/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author André
 */

public class Transacao {
    private int id;
    private int tipoTransacao;
    private double valor;
    private Date dataTransacao;      
    private int idConta1;
    private int idConta2;
    private Conta ContaOperacao;
    private Conta ContaRecepcao;
    private double saldoConta;

//    public Transacao(int id, int tipoTransacao, double valor, Date dataTransacao, int idConta1, int idConta2, double saldoConta) {
//        this.id = id;
//        this.tipoTransacao = tipoTransacao;
//        this.valor = valor;
//        this.dataTransacao = dataTransacao;
//        this.idConta1 = idConta1;
//        this.idConta2 = idConta2;
//        this.saldoConta = saldoConta;
//    }
//    public Transacao(){
//    }
    
    public double getSaldoConta() {
        return saldoConta;
    }

    public void setSaldoConta(double saldoConta) {
        this.saldoConta = saldoConta;
    }

    public Conta getContaOperacao() {
        return ContaOperacao;
    }

    public void setContaOperacao(Conta ContaOperacao) {
        this.ContaOperacao = ContaOperacao;
    }

    public Conta getContaRecepcao() {
        return ContaRecepcao;
    }

    public void setContaRecepcao(Conta ContaRecepcao) {
        this.ContaRecepcao = ContaRecepcao;
    }
    

    public int getIdConta1() {
        return idConta1;
    }

    public void setIdConta1(int idConta1) {
        this.idConta1 = idConta1;
    }

    public int getIdConta2() {
        return idConta2;
    }

    public void setIdConta2(int idConta2) {
        this.idConta2 = idConta2;
    }
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(int tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    
    
}
