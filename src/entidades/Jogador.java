/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author zecaubun
 */
public class Jogador implements Serializable {

    public Jogador(){
    }

    public Jogador(String nome, Date dataNascimento, int numeroCamisa, Time time) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.numeroCamisa = numeroCamisa;
        this.time = time;
    }
    
    private Integer id;
    private String nome;
    private Date dataNascimento;
    private int numeroCamisa;
    private Time time;

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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date nasc) {
        dataNascimento = nasc;
    }

    public int getNumeroCamisa() {
        return numeroCamisa;
    }

    public void setNumeroCamisa(int numeroCamisa) {
        this.numeroCamisa = numeroCamisa;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    
    @Override
    public String toString() {
        return "Jogador{" + "nome=" + nome + ", numeroCamisa=" + numeroCamisa + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.nome);
        hash = 89 * hash + this.numeroCamisa;
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
        final Jogador other = (Jogador) obj;
        if (this.numeroCamisa != other.numeroCamisa) {
            return false;
        }
        return Objects.equals(this.nome, other.nome);
    }
   
}
