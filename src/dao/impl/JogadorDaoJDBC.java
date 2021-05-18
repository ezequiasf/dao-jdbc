/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.GenericDAO;
import db.DBConnection;
import db.DBException;
import entidades.Jogador;
import entidades.Time;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author zecaubun
 */
public class JogadorDaoJDBC implements GenericDAO<Jogador> {

    private final Connection con;
    
    public JogadorDaoJDBC(Connection con){
        this.con = con;
    }
    
    @Override
    public void insert(Jogador jog) {
       PreparedStatement ps = null;
       try{
           ps = con.prepareStatement("INSERT into Jogador "
                   + "(nome, dataNascimento, numeroCamisa, timeid)"
                   + " VALUES"
                   + " (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
           ps.setString(1, jog.getNome());
           ps.setDate(2,new Date(jog.getDataNascimento().getTime()));
           ps.setInt(3, jog.getNumeroCamisa());
           ps.setInt(4,jog.getTime().getId());
           
           int linhasAfetadas = ps.executeUpdate();
           if(linhasAfetadas>0){
               ResultSet rs = ps.getGeneratedKeys();
               if(rs.next()){
                   int id = rs.getInt(1);
                   jog.setId(id);
               }
               DBConnection.closeResultSet(rs);
           }
           else{
               throw new DBException("Nenhum dado foi inserido!");
           }
       }catch(SQLException e){
           throw new DBException(e.getMessage());
       }
       finally{
           DBConnection.closeStatement(ps);
       }
    }

    @Override
    public void update(Jogador jog) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE Jogador "
                    + "SET nome = ?,dataNascimento = ?, numeroCamisa = ?, timeid = ?"
                    + " WHERE id = ? ");
            ps.setString(1,jog.getNome());
            ps.setDate(1,new Date(jog.getDataNascimento().getTime()));
            ps.setInt(1,jog.getNumeroCamisa());
            ps.setInt(1,jog.getTime().getId());
            ps.setInt(1,jog.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
        finally{
            DBConnection.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
       PreparedStatement ps = null;
       try{
           ps = con.prepareStatement("DELETE FROM Jogador"
                   + " WHERE id = ?");
           ps.setInt(1, id);
           ps.executeUpdate();
       }catch(SQLException ex){
           throw new DBException(ex.getMessage());
       }
       finally{
           DBConnection.closeStatement(ps);
       }
    }

    @Override
    public Jogador findById(Integer id) {
       PreparedStatement ps =null;
       ResultSet rs = null;
       try{
          ps = con.prepareStatement("SELECT Jogador.*, Time.nome as NomeTime"
                  + " FROM Jogador INNER JOIN Time"
                   +" ON Jogador.timeid = Time.id"
                  + " WHERE Jogador.id = ?" );
          ps.setInt(1,id);
          rs = ps.executeQuery();
          if(rs.next()){
              Time time = instanciaTime(rs);
              Jogador jog = instanciaJogador(rs,time);
              return jog;
          }
        return null;
       }catch(SQLException e){
            throw new DBException(e.getMessage());
       }
       finally{
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(ps);
       }
    }

    @Override
    public List<Jogador> findAll() {
        
       PreparedStatement ps =null;
       ResultSet rs = null;
       List<Jogador> lista = new ArrayList<>();
       Map<Integer, Time> map = new HashMap<>();
       try{
          ps = con.prepareStatement("SELECT Jogador.*,Time.nome as NomeTime"
                                    + " FROM Jogador INNER JOIN Time" 
                                    + " ON Jogador.timeid = Time.id" 
                                    + " ORDER BY nome" );
         
          rs = ps.executeQuery();
          
          while(rs.next()){
              Time time = map.get(rs.getInt("timeid"));
              if(time == null){
                  time = instanciaTime(rs);
                  map.put(rs.getInt("timeid"),time);
              }   
              Jogador jog = instanciaJogador(rs,time);
              lista.add(jog);
             
          }
          return lista;
       }catch(SQLException e){
            throw new DBException(e.getMessage());
       }
       finally{
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(ps);
       }
    }

    private Time instanciaTime(ResultSet rs) throws SQLException {
        Time time = new Time();
        time.setId(rs.getInt("timeid"));      
        time.setNome(rs.getString("NomeTime"));
        return time;
    }

    private Jogador instanciaJogador(ResultSet rs, Time tim) throws SQLException {
        Jogador jog = new Jogador();
        jog.setId(rs.getInt("id"));
        jog.setNome(rs.getString("nome"));
        jog.setNumeroCamisa(rs.getInt("numeroCamisa"));
        jog.setDataNascimento(rs.getDate("dataNascimento"));
        jog.setTime(tim);
        return jog;
    }

    @Override
    public List<Jogador> findByRelation(Object relation) {
       if(!(relation instanceof Time))
           throw new DBException("Não foi encontrada nenhuma relação com este "
                   + "objeto na tabela em questão.");
       PreparedStatement ps =null;
       ResultSet rs = null;
       Time tim = (Time) relation;
       List<Jogador> lista = new ArrayList<>();
       Map<Integer, Time> map = new HashMap<>();
       try{
          ps = con.prepareStatement("SELECT Jogador.*,Time.nome as NomeTime"
                                    + " FROM Jogador INNER JOIN Time" 
                                    + " ON Jogador.timeid = Time.id"
                                    + " WHERE timeid = ?" 
                                    + " ORDER BY nome" );
         
          ps.setInt(1,tim.getId());
          rs = ps.executeQuery();
          while(rs.next()){
              Time time = map.get(rs.getInt("timeid"));
              if(time == null){
                  time = instanciaTime(rs);
                  map.put(rs.getInt("timeid"),time);
              }   
              Jogador jog = instanciaJogador(rs,time);
              lista.add(jog);
          }
          
        return lista;
       }catch(SQLException e){
            throw new DBException(e.getMessage());
       }
       finally{
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(ps);
       }
    }
    
}
