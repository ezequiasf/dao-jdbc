/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author zecaubun
 */
public class DBIntegrityException extends RuntimeException{
    
    public DBIntegrityException(String msg){
        super (msg);
    }
    
}
