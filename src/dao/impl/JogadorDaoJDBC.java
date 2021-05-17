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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author zecaubun
 */
public class JogadorDaoJDBC implements GenericDAO<Jogador> {

    private Connection con;
    
    public JogadorDaoJDBC(Connection con){
        this.con = con;
    }
    
    @Override
    public void insert(Jogador jog) {
       
    }

    @Override
    public void update(Jogador jog) {
       
    }

    @Override
    public void deleteById(Integer id) {
       
    }

    @Override
    public Jogador findById(Integer id) {
       PreparedStatement ps =null;
       ResultSet rs = null;
       try{
          ps = con.prepareStatement("SELECT Jogador.*, Time.nome as NomeTime"
                  + "FROM seller INNER JOIN Time"
                   +"ON Jogador.TimeId = time.id"
                  + "WHERE Jogador.id = ?" );
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Time instanciaTime(ResultSet rs) throws SQLException {
        Time time = new Time();
        time.setId(rs.getInt("TimeId"));      
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
          ps = con.prepareStatement(" SELECT Jogador.*,Time.nome as NomeTime"
                                    + "FROM Jogador INNER JOIN Time" 
                                    + "ON Jogador.TimeId = time.id"
                                    + "WHERE TimeId = ?" 
                                    + "ORDER BY nome" );
         
          ps.setInt(1,tim.getId());
          rs = ps.executeQuery();
          while(rs.next()){
              Time time = map.get(rs.getInt("TimeId"));
              if(time == null){
                  time = instanciaTime(rs);
                  map.put(rs.getInt("TimeId"),time);
              }   
              Jogador jog = instanciaJogador(rs,time);
              lista.add(jog);
              return lista;
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
    
}
