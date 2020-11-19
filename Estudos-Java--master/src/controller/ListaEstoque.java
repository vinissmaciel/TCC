/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;

/**
 *
 * @author Leonardo
 */
public class ListaEstoque {
    public ArrayList<Estoque> listaestoque = new ArrayList<>();
    
    public void adicionar(Estoque e){
        listaestoque.add(e);
    }
}
