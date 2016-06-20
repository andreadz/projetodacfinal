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
    private final String stmCadastroExclusao = "UPDATE historicos SET data_exclusao=? WHERE id=? AND idCliente=? ";

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
}
