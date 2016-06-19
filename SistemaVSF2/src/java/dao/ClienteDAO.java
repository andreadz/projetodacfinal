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
import models.Cliente;
import models.*;
import java.util.ArrayList;

/**
 *
 * @author André
 */
public class ClienteDAO {

    private final String stmInserir = "INSERT INTO clientes (nome,cpf,cnpj,rg,endereco,cep,telefone,email,renda,senha) values (?,?,?,?,?,?,?,?,?,?);";
    private final String stmAbrirContaCorrente = "INSERT INTO contas values (?,?,?,?,?,?,?,?)";

    private final String stmAtualizar = "UPDATE clientes SET endereco = ?, cep=?, telefone = ?, email=?, renda=? WHERE id=?";
    private final String stmAlteraSenha = "UPDATE clientes SET senha = ? WHERE id=?";
    private final String stmInativarCliente = "UPDATE contas SET statusConta = ? where idCliente = ?";

    private final String stmLogin = "SELECT clientes.id, clientes.nome, clientes.cpf, clientes.cnpj, clientes.rg, "
            + " clientes.endereco, clientes.cep, clientes.telefone, clientes.email, clientes.renda, clientes.senha FROM clientes "
            + " INNER JOIN contas ON contas.idCliente = clientes.id  "
            + " WHERE contas.agencia=? AND contas.conta=? AND clientes.senha = ? AND contas.idCliente = clientes.id";
    private final String stmVerificaUsuarioFisico = "SELECT nome,cpf FROM clientes WHERE cpf = ?";
    private final String stmVerificaUsuarioJuridico = "SELECT nome, cnpj FROM clientes WHERE cnpj = ?";
    private final String stmVerificaLoco = "SELECT cli.nome as Nome ,con.cnpj As CNPJ, cli.rg as RG, cli.endereco as Endereco, "
            + "cli.cep as CEP, cli.telefone, cli.email, cli.renda FROM clientes cli JOIN contas con "
            + "ON cli.id = con.idCliente WHERE con.cnpj = ?";
    private final String stmGetClienteFisico = "SELECT cli.id, cli.nome, cli.rg,cli.endereco,cli.cep,cli.telefone,cli.email, cli.renda, cli.senha ,"
            + " con.id as idConta, con.agencia,con.conta,con.saldo,con.limite,con.statusConta, con.tipoConta "
            + "FROM clientes cli JOIN contas con ON con.idCliente = cli.id WHERE clientes.cpf = ? ";
    private final String stmSaldoAtualTotal = "SELECT saldo, limite FROM contas where idCliente=?";
    private final String stmClienteByConta = "SELECT cli.nome,cli.cpf, cli.cnpj FROM clientes cli JOIN contas con ON con.idCliente = cli.id WHERE con.agencia=? AND con.conta=?";

    public Cliente login(String agencia, int numConta, String senha) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Cliente cliente = new Cliente();
        Conta conta = new Conta();
        try {
            conexao = DbConexao.getConection();

            pstmt = conexao.prepareStatement(stmLogin, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, agencia);
            pstmt.setInt(2, numConta);
            pstmt.setString(3, senha);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCnpj(rs.getString("cnpj"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setCep(rs.getString("cep"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setRenda(rs.getDouble("renda"));
                cliente.setSenha(rs.getString("senha"));
            } else {
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

    public Cliente cadastrarCliente(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmInserir, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, cliente.getNome());
            if (!cliente.getCpf().isEmpty()) {
                pstmt.setString(2, cliente.getCpf());
                pstmt.setString(3, null);
            } else {
                pstmt.setString(2, null);
                pstmt.setString(3, cliente.getCnpj());
            }
            pstmt.setString(4, cliente.getRg());
            pstmt.setString(5, cliente.getEndereco());
            pstmt.setString(6, cliente.getCep());
            pstmt.setString(7, cliente.getTelefone());
            pstmt.setString(8, cliente.getEmail());
            pstmt.setDouble(9, cliente.getRenda());
            pstmt.setString(10, cliente.getSenha());
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
        return cliente;
    }

    public void atualizar(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmAtualizar, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cliente.getEndereco());
            pstmt.setString(2, cliente.getCep());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setDouble(5, cliente.getRenda());
            pstmt.setInt(6, cliente.getId());
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

    public void alterarSenha(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmAlteraSenha, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cliente.getSenha());
            pstmt.setInt(2, cliente.getId());
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

    public void abrirContaCorrente(ContaPf contapf, Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmAbrirContaCorrente, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, contapf.getNumAgencia());
            pstmt.setInt(2, contapf.getNumConta());
            pstmt.setDouble(3, contapf.getSaldo());
            pstmt.setDouble(4, contapf.getLimite());
            pstmt.setBoolean(5, contapf.getStatusConta());
            pstmt.setString(6, contapf.getTipoConta());
            pstmt.setInt(7, cliente.getId());
            pstmt.setString(8, null);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int i = rs.getInt(1);
            contapf.setId(i);
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

    public void abrirContaCorrente(ContaPj contaPj, Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmAbrirContaCorrente, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, contaPj.getNumAgencia());
            pstmt.setInt(2, contaPj.getNumConta());
            pstmt.setDouble(3, contaPj.getSaldo());
            pstmt.setDouble(4, contaPj.getLimite());
            pstmt.setBoolean(5, contaPj.getStatusConta());
            pstmt.setString(6, contaPj.getTipoConta());
            pstmt.setInt(7, cliente.getId());
            pstmt.setString(8, contaPj.getCnpj());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int i = rs.getInt(1);
            contaPj.setId(i);
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

    public void inativarCliente(Cliente cliente, ContaPf contapf) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmInativarCliente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setBoolean(1, contapf.getStatusConta());
            pstmt.setInt(2, cliente.getId());
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

    public void inativarCliente(Cliente cliente, ContaPj contapj) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmInativarCliente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setBoolean(1, contapj.getStatusConta());
            pstmt.setInt(2, cliente.getId());
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

    public ArrayList<Conta> saldoAtualTotal(Cliente cliente) {
        ArrayList<Conta> contas = new ArrayList<Conta>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmSaldoAtualTotal, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, cliente.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Conta conta = new Conta();
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
                contas.add(conta);
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
        return contas;
    }

    public Boolean verificaUsuarioExistenteF(String cpf) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Boolean verifica = false;
        try {
            conexao = DbConexao.getConection();

            pstmt = conexao.prepareStatement(stmVerificaUsuarioFisico, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rs.getString("nome");
                rs.getString("cpf");
                verifica = true;
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
        return verifica;
    }

    public Boolean verificaUsuarioExistenteJ(String cnpj) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Boolean verifica = false;
        try {
            conexao = DbConexao.getConection();

            pstmt = conexao.prepareStatement(stmVerificaUsuarioJuridico, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cnpj);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rs.getString("nome");
                rs.getString("cnpj");
                verifica = true;
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
        return verifica;
    }

    public Conta getClienteFisico(String cpf) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Conta conta = new ContaPf();
        Cliente cliente = new Cliente();
        try {
            conexao = DbConexao.getConection();

            pstmt = conexao.prepareStatement(stmGetClienteFisico, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setRg(rs.getString("rg"));
                //cliente.setCpf(cpf);
                cliente.setRenda(rs.getDouble("renda"));
                cliente.setCep(rs.getString("cep"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                conta.setId(rs.getInt("idConta"));
                conta.setCliente(cliente);
                conta.setLimite(rs.getDouble("limite"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setNumAgencia(rs.getString("agencia"));
                conta.setNumConta(rs.getInt("conta"));
                conta.setStatusConta(rs.getBoolean("statusConta"));
                conta.setTipoConta(rs.getString("tipoConta"));
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
        return conta;
    }

    public Cliente clienteByConta(String agencia, int conta) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Cliente cliente = new Cliente();
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmClienteByConta, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, agencia);
            pstmt.setInt(2, conta);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCnpj(rs.getString("cnpj"));
            } else {
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
