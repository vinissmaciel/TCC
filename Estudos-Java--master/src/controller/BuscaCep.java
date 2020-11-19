/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Leonardo
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BuscaCep {
    private String Jcep;        
    private boolean valido = true;
    public BuscaCep(){
    //Construtor
    }
    
    //criação de metodos SETters
     public void setJcep(String Jcep) {
        this.Jcep = Jcep;
    }
    
   public String getEndereco(String CEP) throws IOException {

	//***************************************************
	try{
            
	Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/"+CEP)
			  .timeout(120000)
			  .get();
	Elements urlPesquisa = doc.select("span[itemprop=streetAddress]");
	for (Element urlEndereco : urlPesquisa) {
			return urlEndereco.text();
	}
        
	} catch (SocketTimeoutException | HttpStatusException e) {
		
	}
	if(CEP.matches("[0-9]*")){
            return CEP;
        }else{
            valido = false;
            return "-";
        }
       
}

public String getBairro(String CEP) throws IOException {

	//***************************************************
	try{
		
	Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/"+CEP)
			  .timeout(120000)
			  .get();
	Elements urlPesquisa = doc.select("td:gt(1)");
	for (Element urlBairro : urlPesquisa) {
			return urlBairro.text();
	}

	} catch (SocketTimeoutException | HttpStatusException e) {
		
	}
        if(CEP.matches("[0-9]*")){
            return CEP;
        }else{
            valido = false;
            return "-";
        }
        
	
}

public String getCidade(String CEP) throws IOException {

	//***************************************************
	try{
		
	Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/"+CEP)
			  .timeout(120000)
			  .get();
	Elements urlPesquisa = doc.select("span[itemprop=addressLocality]");
	for (Element urlCidade : urlPesquisa) {
			return urlCidade.text();
	}

	} catch (SocketTimeoutException | HttpStatusException e) {
		
	}
	if(CEP.matches("[0-9]*")){
            return CEP;
        }else{
            valido = false;
            return "-";
        }
        
}

public String getUF(String CEP) throws IOException {

	//***************************************************
	try{
		
	Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/"+CEP)
			  .timeout(120000)
			  .get();
	Elements urlPesquisa = doc.select("span[itemprop=addressRegion]");
	for (Element urlUF : urlPesquisa) {
			return urlUF.text();
	}

	} catch (SocketTimeoutException | HttpStatusException e) {
		
	}
	if(CEP.matches("[0-9]*")){
            return CEP;
        }else{
            valido = false;
            return "-";
        }
        
}
    public void valido(){
        if(!this.valido){
            JOptionPane.showMessageDialog(null, "Digite o CEP corretamente");
        }
        
    }
} 
