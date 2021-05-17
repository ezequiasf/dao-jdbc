package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBConnection {
    
    private static Connection con = null;
    
    public static Connection getConnection(){
        try{
            if(con == null){
                Properties props = loadProperties();
                String urlBanco = props.getProperty("dburl");
                con = DriverManager.getConnection(urlBanco, props);
            }
        }
        catch(SQLException e){
                throw new DBException("Erro ao fazer a conexão com o banco");
        }
        return con;
    }
    
    public static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties prop = new Properties();
            prop.load(fs);
            return prop;
        }    
        catch(IOException e){
            throw new DBException("Erro ao carregar arquivo de config.");
        }
    }
    
    public static void closeConnection(){
        if(con!=null){
            try{
                con.close();
            }catch(SQLException e){
                throw new DBException("Erro ao fechar a conexão com o banco.");
            }
        }
    }
    
    public static void closeStatement(Statement st){
        if(st!=null){
            try{
                st.close();
            }catch(SQLException e){
                throw new DBException("Erro ao fechar o consultor");
            }
        }
    }
    
    public static void closeResultSet(ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            }catch(SQLException e){
                throw new DBException("Erro ao fechar o result set");
            }
        }
    }
}
