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
import models.*;
import vsf.*;

/**
 *
 * @author sdgdsgd
 */
public class UsuarioDAO {

    private final String stmInserirUsuario = "INSERT INTO usuarios (nome,senha,email,perfil,statusUsuario) values (?,?,?,?,?)";
    
    private final String stmEditarUsuario = "UPDATE usuarios SET nome=?,email=?, perfil=? WHERE id=?";
    private final String stmPegarUsuario = "SELECT * FROM usuarios WHERE id=?";
    private final String stmSelUsuariosAtivos = "SELECT * FROM usuarios WHERE statusUsuario=1";
    private final String stmSelTodosUsuarios = "SELECT * FROM usuarios ";
    private final String stmInativarUsuario = "DELETE FROM usuarios WHERE id=?";

    private final String stmLogin = "SELECT * FROM usuarios WHERE email=? AND senha=?";

    public Usuario login(String email, String senha) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Usuario usuario = new Usuario();
        try {
            conexao = DbConexao.getConection();

            pstmt = conexao.prepareStatement(stmLogin, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPerfil(rs.getInt("perfil"));
                usuario.setStatusUsuario(rs.getBoolean("statusUsuario"));
            } else {
                usuario = null;
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
        return usuario;
    }

    public void cadastrarUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmInserirUsuario, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setInt(4, usuario.getPerfil());
            pstmt.setBoolean(5, true);
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int i = rs.getInt(1);
            usuario.setId(i);
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

    public void atualizarUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmEditarUsuario, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setInt(3, usuario.getPerfil());
            pstmt.setInt(4, usuario.getId());
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

    public void inativarUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmInativarUsuario, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, usuario.getId());
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

    public ArrayList<Usuario> todosUsuarios() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Usuario usuario;
        ArrayList<Usuario> usuarios = new ArrayList();
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmSelTodosUsuarios, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                usuario= new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPerfil(rs.getInt("perfil"));
                usuario.setStatusUsuario(rs.getBoolean("statusUsuario"));
                usuarios.add(usuario);
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
        return usuarios;
    }
    
    public ArrayList<Usuario> todosUsuariosAtivos() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Usuario usuario;
        ArrayList<Usuario> usuarios = new ArrayList();
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmSelUsuariosAtivos, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                usuario= new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPerfil(rs.getInt("perfil"));
                usuario.setStatusUsuario(rs.getBoolean("statusUsuario"));
                usuarios.add(usuario);
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
        return usuarios;
    }
    
    public Usuario pegarUsuarioById(int id){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        Usuario usuario = new Usuario();
        try {
            conexao = DbConexao.getConection();
            pstmt = conexao.prepareStatement(stmPegarUsuario, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPerfil(rs.getInt("perfil"));
                usuario.setStatusUsuario(rs.getBoolean("statusUsuario"));
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
        return usuario;
    }
}
