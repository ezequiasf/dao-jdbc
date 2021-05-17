/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author zecaubun
 * 
 */
public interface GenericDAO<T> {
   
    void insert(T t);
    void update(T t);
    void deleteById(Integer id);
    T findById(Integer id);
    List<T> findAll();
    /*
       Método que tem a intenção de retornar uma lista de um tipo T, a partir
       de alguma relação na tabela, que não seja o ID;
    */
    List<T> findByRelation(Object obj);
}
