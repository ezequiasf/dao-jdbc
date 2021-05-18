/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.GenericDAO;
import db.DBConnection;
import db.DBException;
import entidades.Time;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author zecaubun
 */
public class TimeDaoJDBC implements GenericDAO<Time>{

    public TimeDaoJDBC(Connection con){
        this.con = con;
    }
    private final Connection con;
    
    @Override
    public void insert(Time t){
        PreparedStatement ps = null;

        try{
            ps = con.prepareStatement("INSERT INTO Time "
                    + "nome = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,t.getNome());
            int linhasAfetadas = ps.executeUpdate();
            if(linhasAfetadas>0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    t.setId(rs.getInt(1));
                }
                DBConnection.closeResultSet(rs);
            }
            else{
                throw new DBException("Nenhum dado foi inserido!");
            }
        }catch(SQLException ex){
            throw new DBException(ex.getMessage());
        }
        finally{
            DBConnection.closeStatement(ps);
        }
    }

    @Override
    public void update(Time t) {
      PreparedStatement ps = null;
      try{
          ps = con.prepareStatement("UPDATE Time "
                  + "SET nome = ? WHERE id = ?");
          ps.setString(1, t.getNome());
          ps.setInt(2, t.getId());
          ps.executeUpdate();
      }catch(SQLException ex){
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
            ps = con.prepareStatement("DELETE FROM Time "
                    + "WHERE id = ?");
            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            if(linhasAfetadas == 0){
                throw new DBException("Id não consta no banco de dados");
            }
        }catch(SQLException ex){
            throw new DBException(ex.getMessage());
        }
        finally{
            DBConnection.closeStatement(ps);
        }
    }

    private Time instanciaTime(ResultSet rs) throws SQLException{
        Time tim = new Time();
        tim.setId(rs.getInt("id"));
        tim.setNome(rs.getString("nome"));
        return tim;
    }
    @Override
    public Time findById(Integer id) {
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("SELECT Time.*, "
                    + "WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Time time = instanciaTime(rs);
                DBConnection.closeResultSet(rs);
                return time;
            }
            return null;
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            DBConnection.closeStatement(ps);
        }
    }

    @Override
    public List<Time> findAll() {
        PreparedStatement ps = null;
        List<Time> lista = new ArrayList<>();
        try{
            ps = con.prepareStatement("SELECT Time.*");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Time tim = instanciaTime(rs);
                lista.add(tim);
            }
            DBConnection.closeResultSet(rs);
            return lista;
        }catch(SQLException ex){
            throw new DBException(ex.getMessage());
        }
        finally{
            DBConnection.closeStatement(ps);
        }
    }

    @Override
    public List<Time> findByRelation(Object obj) {
        PreparedStatement ps = null;
        List<Time> lista = new ArrayList<>();
        try{
            ps = con.prepareStatement("SELECT Time.*");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Time tim = instanciaTime(rs);
                lista.add(tim);
            }
            DBConnection.closeResultSet(rs);
            return lista;
        }catch(SQLException ex){
            throw new DBException(ex.getMessage());
        }
        finally{
            DBConnection.closeStatement(ps);
        }
    }
    
}
