/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import org.joda.time.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.*;
import models.*;
/**
 *
 * @author André
 */
public class TransacaoDAO {
    private final String stmInserirTransacao = "INSERT INTO transacoes (tipoTransacao,valor,dataTransacao,idContaOperacao,idContaRecepcao,saldoConta) values (?,?,?,?,?,?)";
//    private final String stmTransacoesPeriodo = "SELECT * FROM transacoes WHERE idContaOperacao=? AND dataTransacao BETWEEN '?' AND NOW()";
//    private final String stmTransacoes = "SELECT * FROM transacoes WHERE idContaOperacao=?";
    
    public void salvarTransacao(Transacao transac){
        Connection conexao =  null;PreparedStatement pstmt = null;
        try{
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmInserirTransacao, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setInt(1, transac.getTipoTransacao());
            pstmt.setDouble(2, transac.getValor());
            pstmt.setDate(3, transac.getDataTransacao());            
            pstmt.setInt(4, transac.getIdConta1());   
            pstmt.setInt(5, transac.getIdConta2());   
            pstmt.setDouble(6, transac.getSaldoConta());   
            
            pstmt.executeUpdate();            
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }finally{
            try{pstmt.close();} catch(Exception ex){System.out.println("Erro:" + ex.getMessage());}
            try{conexao.close();} catch(Exception ex){System.out.println("Erro:" + ex.getMessage());}
        }
    }
    
//    public ArrayList<Transacao> pegarTransacoes(int periodo, int idConta) {
//        Connection conexao = null;
//        PreparedStatement pstmt = null;
//        ArrayList<Transacao> transacoes = new ArrayList();
//        Transacao transacao;
//        try {
//            conexao = DbConexao.getConection();
//            pstmt = conexao.prepareStatement(stmTransacoesPeriodo, Statement.RETURN_GENERATED_KEYS);
//            Date dataAtual = new Date();            
//            //LocalDate date = LocalDate.now().minusDays(15);            
//            Calendar c = Calendar.getInstance();
//            c.setTime(dataAtual);
//            c.add(Calendar.DATE, -periodo);
//            dataAtual.setTime(c.getTime().getTime());
//            
//            pstmt.setInt(1, idConta);
//            pstmt.setDate(2, new java.sql.Date(dataAtual.getTime()));
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                transacao = new Transacao();
//                transacao.setId(rs.getInt("id"));
//                transacao.setTipoTransacao(rs.getInt("tipoTransacao"));
//                transacao.setValor(rs.getDouble("valor"));
//                transacao.setDataTransacao(rs.getDate("dataTransacao"));
//                transacao.setIdConta1(rs.getInt("idContaOperacao"));
//                transacao.setIdConta2(rs.getInt("idContaRecepcao"));
//                transacao.setSaldoConta(rs.getDouble("saldoConta"));
//                transacoes.add(transacao);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                pstmt.close();
//            } catch (Exception ex) {
//                System.out.println("Erro:" + ex.getMessage());
//            }
//            try {
//                conexao.close();
//            } catch (Exception ex) {
//                System.out.println("Erro:" + ex.getMessage());
//            }
//        }
//        return transacoes;
//    }
//    
//    public ArrayList<Transacao> pegarTransacoes(int idConta) {
//        Connection conexao = null;
//        PreparedStatement pstmt = null;
//        ArrayList<Transacao> transacoes = new ArrayList();
//        Transacao transacao;
//        try {
//            conexao = DbConexao.getConection();
//            pstmt = conexao.prepareStatement(stmTransacoes, Statement.RETURN_GENERATED_KEYS);            
//            pstmt.setInt(1, idConta);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                transacao = new Transacao();
//                transacao.setId(rs.getInt("id"));
//                transacao.setTipoTransacao(rs.getInt("tipoTransacao"));
//                transacao.setValor(rs.getDouble("valor"));
//                transacao.setDataTransacao(rs.getDate("dataTransacao"));
//                transacao.setIdConta1(rs.getInt("idContaOperacao"));
//                transacao.setIdConta2(rs.getInt("idContaRecepcao"));
//                transacao.setSaldoConta(rs.getDouble("saldoConta"));
//                transacoes.add(transacao);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                pstmt.close();
//            } catch (Exception ex) {
//                System.out.println("Erro:" + ex.getMessage());
//            }
//            try {
//                conexao.close();
//            } catch (Exception ex) {
//                System.out.println("Erro:" + ex.getMessage());
//            }
//        }
//        return transacoes;
//    }
}
