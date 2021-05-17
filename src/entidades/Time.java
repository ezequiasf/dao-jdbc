/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author zecaubun
 */
public class Time implements Serializable{
    
    public Time(){     
    }

    public Time(String nome) {
        this.nome = nome;
    }
   
    private Integer id;
    private String nome;

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer in){
        this.id = in;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Time{" + "nome=" + nome + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Time other = (Time) obj;
        return Objects.equals(this.nome, other.nome);
    }
    
}
