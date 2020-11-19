/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author IanLu
 */
public class Estoque {
    public int id;
    public String nome;
    public double preco;
    public double qtd_comprada;

    public double getQtd_comprada() {
        return qtd_comprada;
    }

    public void setQtd_comprada(double qtd_comprada) {
        this.qtd_comprada = qtd_comprada;
    }
    
   
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
