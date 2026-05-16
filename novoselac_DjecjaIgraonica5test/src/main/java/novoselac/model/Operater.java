 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package novoselac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 *
 * @author Administrator
 */
@Entity
public class Operater  extends Entitet{

    
 @Column(columnDefinition = "char(61)")
    private char[] lozinka;
 
 public String getImePerezime(){
     return getIme() +" "+ getPrezime();
 }
   
  
    //seteri i geteri
 
 public char[] getLozinka(){
     return lozinka;
      }
    public void setLozinka(char[] lozinka) {
        this.lozinka = lozinka;
    }
    
       public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
  
    //privatna svojstva

    private String ime;
    private String prezime;
    private String oib;
    private String email;


     public String getImePrezime(){
        return ime +" "+ prezime;
    }
    

   
  
    
}
