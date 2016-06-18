package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import models.*;

/**
 *
 * @author André
 */
public class ContaDAO {

    private final String stmInserirConta = "INSERT INTO contas "
            + " (agencia,conta,saldo,limite,statusConta,tipoConta,idCliente) values (?,?,?,?,?,?,?)";

    private final String stmEncerrarConta = "UPDATE contas SET statusConta = ? where agencia= ? AND conta = ?";
    private final String stmDepositar = "UPDATE contas SET saldo = ?, dataNegativacao=? WHERE agencia = ? AND conta=?";
    private final String stmSacar = "UPDATE contas SET saldo = ? WHERE agencia = ? AND conta=?";
    private final String stmTransferir = "UPDATE contas SET saldo = ? WHERE agencia = ? AND conta=?";
    private final String stmClienteCPF = "SELECT id, nome FROM clientes where cpf=?";
    private final String stmTransferirTerceiros = "UPDATE contas SET saldo = ? WHERE agencia = ? AND conta=? AND idCliente = (SELECT id FROM clientes where cpf=?)";
    
    private final String stmGetContaByCliente = "SELECT * FROM contas WHERE agencia = ? AND conta = ?";
    private final String stmTodasContas = "SELECT * FROM contas WHERE idCliente=?";
    private final String stmVerificaContaExistente = "SELECT MAX(conta) AS conta FROM contas WHERE agencia = ?";
    private final String stmSaldoAtual = "SELECT saldo, limite FROM contas where agencia=? AND conta=? ";
    //private final String stmExtratoCompleto = "SELECT * FROM transacoes WHERE idConta=? AND idClienteConta=? ";
    //private final String stmExtratoIntervalo = "SELECT * FROM transacoes WHERE idConta=? AND idClienteConta=? AND (dataTransacao BETWEEN '?' AND Date(now()))";
    private final String stmExtratoIntervalo = "SELECT * FROM transacoes WHERE idContaOperacao=? AND dataTransacao BETWEEN ? AND NOW()";
    private final String stmExtratoCompleto = "SELECT * FROM transacoes WHERE idContaOperacao=?";
    
    
    public void inserir(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmInserirConta, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf());
            pstmt.setString(3, cliente.getRg());
            pstmt.setString(4, cliente.getEndereco());
            pstmt.setString(5, cliente.getCep());
            pstmt.setString(6, cliente.getTelefone());
            pstmt.setString(7, cliente.getEmail());
            pstmt.setDouble(8, cliente.getRenda());
            pstmt.setString(9, cliente.getSenha());
            pstmt.executeUpdate();

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

    public void saldoAtual(Conta conta) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmSaldoAtual, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, conta.getNumAgencia());
            pstmt.setInt(2, conta.getNumConta());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
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
    }

    public Conta pegarContaByCliente(String agencia, int numConta, Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Conta conta;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmGetContaByCliente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, agencia);
            pstmt.setInt(2, numConta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("tipoConta").equals("J")) {
                    conta = new ContaPj();
                } else {
                    conta = new ContaPf();
                }
                conta.setId(rs.getInt("id"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
                conta.setNumConta(rs.getInt("conta"));
                conta.setNumAgencia(rs.getString("agencia"));
                conta.setTipoConta(rs.getString("tipoConta"));
                conta.setStatusConta(rs.getBoolean("statusConta"));
                conta.setCliente(cliente);
            } else {
                conta = null;
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

    public Conta pegarContaByConta(String agencia, int numConta) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Conta conta = new Conta();
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmGetContaByCliente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, agencia);
            pstmt.setInt(2, numConta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                conta.setId(rs.getInt("id"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
                conta.setNumConta(rs.getInt("conta"));
                conta.setNumAgencia(rs.getString("agencia"));
                conta.setTipoConta(rs.getString("tipoConta"));
                conta.setStatusConta(rs.getBoolean("statusConta"));
            } else {
                conta = null;
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

    public ArrayList<Conta> pegarTodasContasByCliente(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ArrayList<Conta> contas = new ArrayList<Conta>();
        Conta conta;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmTodasContas, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, cliente.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (cliente.getCpf() == null) {
                    conta = new ContaPj();
                } else {
                    conta = new ContaPf();
                }
                conta.setId(rs.getInt("id"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
                conta.setNumConta(rs.getInt("conta"));
                conta.setNumAgencia(rs.getString("agencia"));
                conta.setTipoConta(rs.getString("tipoConta"));
                conta.setStatusConta(rs.getBoolean("statusConta"));
                conta.setCliente(cliente);
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

    public Conta criarConta(String agencia, Cliente cliente, double limite) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        int valorconta = 0;
        Conta conta;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmVerificaContaExistente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, agencia);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                valorconta = rs.getInt("conta");
            }
            pstmt.clearParameters();
            valorconta = valorconta + 1;
            if (cliente.getCpf().isEmpty()) {
                conta = new ContaPj();
                conta.setTipoConta("J");
            } else {
                conta = new ContaPf();
                conta.setTipoConta("F");
            }
            conta.setNumConta(valorconta);
            conta.setNumAgencia(agencia);
            conta.setSaldo(0.00);
            conta.setLimite(limite);
            conta.setStatusConta(true);
            conta.setCliente(cliente);

            pstmt = conexao.prepareStatement(stmInserirConta, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, conta.getNumAgencia());
            pstmt.setInt(2, conta.getNumConta());
            pstmt.setDouble(3, conta.getSaldo());
            pstmt.setDouble(4, conta.getLimite());
            pstmt.setBoolean(5, conta.getStatusConta());
            pstmt.setString(6, conta.getTipoConta());
            pstmt.setInt(7, cliente.getId());
            pstmt.execute();

            rs = pstmt.getGeneratedKeys();
            rs.next();
            int i = rs.getInt(1);
            conta.setId(i);
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

    public void encerrarConta(Conta conta) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmEncerrarConta, Statement.RETURN_GENERATED_KEYS);
            pstmt.setBoolean(1, conta.getStatusConta());
            pstmt.setString(2, conta.getNumAgencia());
            pstmt.setInt(3, conta.getNumConta());
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

    public void depositar(Conta contaRetirada, Conta contaDeposito) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            java.util.Date dataAtual = new java.util.Date();            
            //LocalDate date = LocalDate.now().minusDays(15);            
            Calendar c = Calendar.getInstance();
            c.setTime(dataAtual);
            dataAtual.setTime(c.getTime().getTime());
            
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmDepositar, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1, contaRetirada.getSaldo());
            pstmt.setDate(2, new java.sql.Date(contaRetirada.getDataNegativacao().getTime()));
            pstmt.setString(2, contaRetirada.getNumAgencia());
            pstmt.setInt(3, contaRetirada.getNumConta());
            pstmt.executeUpdate();
            if (contaDeposito != null) {
                pstmt.clearParameters();
                pstmt.setDouble(1, contaDeposito.getSaldo());
                pstmt.setString(2, contaDeposito.getNumAgencia());
                pstmt.setInt(3, contaDeposito.getNumConta());
                pstmt.executeUpdate();
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
    }
    
    public void sacar(Conta contaRetirada) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmSacar, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1, contaRetirada.getSaldo());
            pstmt.setString(2, contaRetirada.getNumAgencia());
            pstmt.setInt(3, contaRetirada.getNumConta());
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
    
    //do próprio cliente
    public void transferir(Conta contaRetirada, Conta contaRecebeTransf) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmTransferir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1, contaRetirada.getSaldo());
            pstmt.setString(2, contaRetirada.getNumAgencia());
            pstmt.setInt(3, contaRetirada.getNumConta());
            pstmt.executeUpdate();
            pstmt.clearParameters();
            //pstmt = conexao.prepareStatement(stmTransferir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1, contaRecebeTransf.getSaldo());
            pstmt.setString(2, contaRecebeTransf.getNumAgencia());
            pstmt.setInt(3, contaRecebeTransf.getNumConta());
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

    //para terceiros
    public void transferir(Cliente cliente, Conta contaRetirada, Conta contaDeposito) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmTransferirTerceiros, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1, contaRetirada.getSaldo());
            pstmt.setString(2, contaRetirada.getNumAgencia());
            pstmt.setInt(3, contaRetirada.getNumConta());
            //pstmt.setInt(4, contaRetirada.getCliente());           
            pstmt.executeUpdate();
            pstmt.setDouble(1, contaDeposito.getSaldo());
            pstmt.setString(2, contaDeposito.getNumAgencia());
            pstmt.setInt(3, contaDeposito.getNumConta());
            // pstmt.setInt(4, contaDeposito.getCliente());
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

    //public ArrayList<Transacao> extratoCompleto(Conta conta) {
//        Connection conexao = null;
//        PreparedStatement pstmt = null;
//        ArrayList<Transacao> trans = new ArrayList<Transacao>();
//        try {
//            conexao = DbConexao.getConection();
//            pstmt = conexao.prepareStatement(stmExtratoCompleto, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setInt(1, conta.getId());
//            // pstmt.setInt(2, conta.getCliente());  
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Transacao transacao = new Transacao();
//                transacao.setTipoTransacao(rs.getInt("tipoTransacao"));
//                transacao.setValor(rs.getDouble("valor"));
//                transacao.setDataTransacao(rs.getDate("dataTransacao"));
//                transacao.setIdConta1(rs.getInt("idConta"));
//                transacao.setIdConta2(rs.getInt("idConta2"));
//
//                trans.add(transacao);
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
//        return trans;
//    }
//
//    public ArrayList<Transacao> extratoIntervalo(Conta conta, Date dataTrans) {
//        Connection conexao = null;
//        PreparedStatement pstmt = null;
//        ArrayList<Transacao> trans = new ArrayList<Transacao>();
//        try {
//            conexao = DbConexao.getConection();
//            pstmt = conexao.prepareStatement(stmExtratoCompleto, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setInt(1, conta.getId());
//            // pstmt.setInt(2, conta.getCliente());  
//            pstmt.setDate(3, dataTrans);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Transacao transacao = new Transacao();
//                transacao.setTipoTransacao(rs.getInt("tipoTransacao"));
//                transacao.setValor(rs.getDouble("valor"));
//                transacao.setDataTransacao(rs.getDate("dataTransacao"));
//                transacao.setIdConta1(rs.getInt("idConta"));
//                transacao.setIdConta2(rs.getInt("idConta2"));
//
//                trans.add(transacao);
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
//        return trans;
    //}
    
    public ArrayList<Transacao> extrato(int periodo, int idConta) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ArrayList<Transacao> transacoes = new ArrayList();
        Transacao transacao;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmExtratoIntervalo, Statement.RETURN_GENERATED_KEYS);
            java.util.Date dataAtual = new java.util.Date();            
            //LocalDate date = LocalDate.now().minusDays(15);            
            Calendar c = Calendar.getInstance();
            c.setTime(dataAtual);
            c.add(Calendar.DATE, -periodo);
            dataAtual.setTime(c.getTime().getTime());
            
            pstmt.setInt(1, idConta);
            pstmt.setDate(2, new java.sql.Date(dataAtual.getTime()));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                transacao = new Transacao();
                transacao.setId(rs.getInt("id"));
                transacao.setTipoTransacao(rs.getInt("tipoTransacao"));
                transacao.setValor(rs.getDouble("valor"));
                transacao.setDataTransacao(rs.getDate("dataTransacao"));
                transacao.setIdConta1(rs.getInt("idContaOperacao"));
                transacao.setIdConta2(rs.getInt("idContaRecepcao"));
                transacao.setSaldoConta(rs.getDouble("saldoConta"));
                transacoes.add(transacao);
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
        return transacoes;
    }
    
    public ArrayList<Transacao> extrato(int idConta) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ArrayList<Transacao> transacoes = new ArrayList();
        Transacao transacao;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmExtratoCompleto, Statement.RETURN_GENERATED_KEYS);            
            pstmt.setInt(1, idConta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                transacao = new Transacao();
                transacao.setId(rs.getInt("id"));
                transacao.setTipoTransacao(rs.getInt("tipoTransacao"));
                transacao.setValor(rs.getDouble("valor"));
                transacao.setDataTransacao(rs.getDate("dataTransacao"));
                transacao.setIdConta1(rs.getInt("idContaOperacao"));
                transacao.setIdConta2(rs.getInt("idContaRecepcao"));
                transacao.setSaldoConta(rs.getDouble("saldoConta"));
                transacoes.add(transacao);
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
        return transacoes;
    }
}
