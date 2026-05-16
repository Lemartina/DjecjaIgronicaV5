/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package novoselac.util;

/**
 *
 * @author Administrator
 */
public class NovoselacException extends Exception {
    
 private String poruka; 

    public NovoselacException(String poruka) {
        super();
        this.poruka= poruka;
    }

    public String getPoruka() {
        return poruka;
    }

   
 
    
}

