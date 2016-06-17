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
import models.Usuario;
import vsf.Cliente;

/**
 *
 * @author sdgdsgd
 */
public class ClienteDAO {
    private final String stmInserirCliente = "INSERT INTO clientes (nome,cpf,cnpj,statusDOR) values (?,?,?,1)";
    
    private final String stmNegativarCliente = "UPDATE clientes SET statusDOR=0 WHERE id=?";
    private final String stmAtivarCliente = "UPDATE clientes SET statusDOR=1 WHERE id=?";
    
    private final String stmSelClientesAtivos = "SELECT * FROM clientes WHERE statusDOR=1";
    private final String stmSelClientesNegativos = "SELECT * FROM clientes WHERE statusDOR=0";
    private final String stmSelClientes = "SELECT * FROM clientes ";
    
    public void cadastrarCliente(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmInserirCliente, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf());
            pstmt.setString(3, cliente.getCnpj());
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int i = rs.getInt(1);
            cliente.setId(i);
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

    public void ativarCliente(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmAtivarCliente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, cliente.getId());
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

    public void inativarCliente(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmNegativarCliente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, cliente.getId());
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
    
    public ArrayList<Cliente> todosClientes() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Cliente cliente;
        ArrayList<Cliente> clientes = new ArrayList();
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmSelClientes, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCnpj(rs.getString("cnpj"));
                cliente.setStatusDOR(rs.getBoolean("statusDOR"));
                clientes.add(cliente);
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
        return clientes;
    }
    
    public ArrayList<Cliente> todosClientesNegativados() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Cliente cliente;
        ArrayList<Cliente> clientes = new ArrayList();
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmSelClientesNegativos, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCnpj(rs.getString("cnpj"));
                cliente.setStatusDOR(rs.getBoolean("statusDOR"));
                clientes.add(cliente);
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
        return clientes;
    }
}
