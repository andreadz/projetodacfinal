/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import models.*;
import vsf.*;

/**
 *
 * @author sdgdsgd
 */
public class HistoricoDAO {
    
    private final String stmCadastroInclusao = "INSERT INTO historicos "
            + "(data_inclusao,data_exclusao,empresaNegativacao,idCliente) values (?,?,?,?)";
    private final String stmHistoricoExistente = "SELECT MAX(id) AS historicoId FROM historicos WHERE idCliente = ?";
    private final String stmHistoricoByCliente = "SELECT * FROM historicos WHERE idCliente=?";
    private final String stmCadastroExclusao = "UPDATE historicos SET data_exclusao=? WHERE id=? AND idCliente=? ";
    private final String stmCadastroAtivacaoByDOR = "UPDATE historicos SET data_exclusao=?,empresaNegativacao=? WHERE id=? AND idCliente=? ";
    private final String stmCadastroInativacaoByDOR = "UPDATE historicos SET data_exclusao=?,empresaNegativacao=? WHERE id=? AND idCliente=? ";

    
    public ArrayList<Historico> historicoByCliente(int id) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Historico historico;
        Cliente cliente = new Cliente();
        ArrayList<Historico> historicos = new ArrayList();
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmHistoricoByCliente, Statement.RETURN_GENERATED_KEYS);            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                historico = new Historico();
                historico.setId(rs.getInt("id"));
                historico.setDataInclusao(rs.getDate("data_inclusao"));
                historico.setDataExclusao(rs.getDate("data_exclusao"));
                historico.setEmpresa(rs.getString("empresaNegativacao"));
                cliente.setId(rs.getInt("id"));
                historico.setCliente(cliente);
                historicos.add(historico);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pstmt.close();
            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
            try {
                conexao.close();
            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
        }
        return historicos;
    }
    
    public void cadastrarHistorico(Cliente cliente, Historico historico) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmCadastroInclusao, Statement.RETURN_GENERATED_KEYS);
            Date dataAtual = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dataAtual);
            dataAtual.setTime(c.getTime().getTime());

            pstmt.setDate(1, new java.sql.Date(dataAtual.getTime()));
            pstmt.setDate(2, null);
            pstmt.setString(3, historico.getEmpresa());
            pstmt.setInt(4, cliente.getId());
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int i = rs.getInt(1);
            historico.setId(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pstmt.close();
            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
            try {
                conexao.close();
            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
        }
    }    

    public void cadastroReativacao(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Historico historico = new Historico();
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmHistoricoExistente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, cliente.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int idhistorico = rs.getInt("historicoId");
                historico.setId(idhistorico);
            }
            pstmt.clearParameters();            
            pstmt = conexao.prepareStatement(stmCadastroExclusao, Statement.RETURN_GENERATED_KEYS);
            
            Date dataAtual = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dataAtual);
            dataAtual.setTime(c.getTime().getTime());

            pstmt.setDate(1, new java.sql.Date(dataAtual.getTime()));
            pstmt.setInt(2, historico.getId());
            pstmt.setInt(3, cliente.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pstmt.close();
            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
            try {
                conexao.close();
            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
        }
    }
    
    public void cadastroReativacaoByDOR(Cliente cliente, Historico historico) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmHistoricoExistente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, cliente.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int idhistorico = rs.getInt("historicoId");
                historico.setId(idhistorico);
            }
            pstmt.clearParameters();  
            Date dataAtual = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dataAtual);
            dataAtual.setTime(c.getTime().getTime());
            pstmt = conexao.prepareStatement(stmCadastroAtivacaoByDOR, Statement.RETURN_GENERATED_KEYS);            
            
            pstmt.setDate(1, new java.sql.Date(dataAtual.getTime()));
            pstmt.setString(2, historico.getEmpresa());
            pstmt.setInt(3, historico.getId());
            pstmt.setInt(4, cliente.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pstmt.close();
            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
            try {
                conexao.close();
            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
        }
    }
    
    public void cadastroInativacaoByDOR(Cliente cliente, Historico historico) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmHistoricoExistente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, cliente.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int idhistorico = rs.getInt("historicoId");
                historico.setId(idhistorico);
            }
            pstmt.clearParameters();  
            Date dataAtual = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dataAtual);
            dataAtual.setTime(c.getTime().getTime());
            pstmt = conexao.prepareStatement(stmCadastroInativacaoByDOR, Statement.RETURN_GENERATED_KEYS);            
            
            pstmt.setDate(1, new java.sql.Date(dataAtual.getTime()));
            pstmt.setString(2, historico.getEmpresa());
            pstmt.setInt(3, historico.getId());
            pstmt.setInt(4, cliente.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pstmt.close();
            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
            try {
                conexao.close();
            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
        }
    }
}
