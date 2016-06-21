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

    private final String stmAtivarClienteFis = "UPDATE clientes SET statusDOR=0 WHERE cpf=?";
    private final String stmAtivarClienteJur = "UPDATE clientes SET statusDOR=0 WHERE cnpj=?";
    private final String stmNegativarClienteFis = "UPDATE clientes SET statusDOR=1 WHERE cpf=?";
    private final String stmNegativarClienteJur = "UPDATE clientes SET statusDOR=1 WHERE cnpj=?";

    private final String stmSelClientesAtivos = "SELECT * FROM clientes WHERE statusDOR=1";
    private final String stmSelClientesNegativos = "SELECT * FROM clientes WHERE statusDOR=0";
    private final String stmSelClientes = "SELECT * FROM clientes ";
    private final String stmPegarClienteJur = "SELECT * FROM clientes WHERE cnpj=?";
    private final String stmPegarClienteFis = "SELECT * FROM clientes WHERE cpf=?";
    private final String stmPegarClienteById = "SELECT * FROM clientes WHERE id=?";
    

    public Boolean cadastrarCliente(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Boolean verifica = false;
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
            verifica = true;
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
        return verifica;
    }

    public Cliente verificaRestricaoDOR(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            if (null == cliente.getCpf()) {
                pstmt = conexao.prepareStatement(stmPegarClienteJur, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, cliente.getCnpj());
            } else {
                pstmt = conexao.prepareStatement(stmPegarClienteFis, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, cliente.getCpf());
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCnpj(rs.getString("cnpj"));
                cliente.setStatusDOR(rs.getBoolean("statusDOR"));
            }
            else{
                cliente = null;
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
        return cliente;
    }

    public Boolean ativarCliente(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Boolean verifica = false;
        try {
            conexao = DbConexao.getConection();
             if (null == cliente.getCpf()) {
                pstmt = conexao.prepareStatement(stmAtivarClienteJur, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, cliente.getCnpj());
            } else {
                pstmt = conexao.prepareStatement(stmAtivarClienteFis, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, cliente.getCpf());
            }
            pstmt.executeUpdate();
            verifica = true;
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
        return verifica;
    }

    public Boolean inativarCliente(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Boolean verifica = false;
        try {
            conexao = DbConexao.getConection();
            
            if (null == cliente.getCpf()) {
                pstmt = conexao.prepareStatement(stmNegativarClienteJur, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, cliente.getCnpj());
            } else {
                pstmt = conexao.prepareStatement(stmNegativarClienteFis, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, cliente.getCpf());
            }
            pstmt.executeUpdate();
            verifica = true;
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
        return verifica;
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
    
    public ArrayList<Cliente> buscaByNome(String nome) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Cliente cliente;
        ArrayList<Cliente> clientes = new ArrayList();
        String stmClientesByNome = "SELECT * FROM clientes WHERE nome LIKE '%" + nome + "%'";
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmClientesByNome, Statement.RETURN_GENERATED_KEYS);
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
    
    public Cliente buscarByCpf(String cpf) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Cliente cliente = new Cliente();
        try {
            conexao = DbConexao.getConection();
                pstmt = conexao.prepareStatement(stmPegarClienteFis, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, cpf);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCnpj(rs.getString("cnpj"));
                cliente.setStatusDOR(rs.getBoolean("statusDOR"));
            }
            else{
                cliente = null;
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
        return cliente;
    }
    
    public Cliente pegarClienteById(int id) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Cliente cliente = new Cliente();
        try {
            conexao = DbConexao.getConection();
                pstmt = conexao.prepareStatement(stmPegarClienteById, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCnpj(rs.getString("cnpj"));
                cliente.setStatusDOR(rs.getBoolean("statusDOR"));
            }
            else{
                cliente = null;
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
        return cliente;
    }
    
    
}
