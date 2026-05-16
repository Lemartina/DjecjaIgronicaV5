/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package novoselac.controller;

import jakarta.persistence.NoResultException;
import java.util.List;
import novoselac.model.Operater;
import novoselac.util.Alati;
import novoselac.util.NovoselacException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Administrator
 */
public class ObradaOperater extends Obrada<Operater> {
    
    //implementacija metoda

    @Override
    public List<Operater> read() {
        return (List<Operater>) session.createQuery("from Operater", Operater.class).list();
                
           }
    
        public void unosAdminOperatera()  {
        Operater o = new Operater();
        o.setIme("Martina");
        o.setPrezime("Novoselac");
        o.setOib("69266610915");
        o.setEmail("novoselac.martina@gmail.com");
        o.setLozinka(BCrypt.hashpw("edunova",
                BCrypt.gensalt()).toCharArray());

        entitet = o;

        try {
             create();
        } catch (NovoselacException ex) {
            System.out.println(ex.getPoruka());
        }
       
    }
        
          public Operater autoriziraj(String email, char[] lozinka){
        Operater o;
        try {
            o = session.createQuery(
                    "from Operater o where o.email=:email", 
                    Operater.class)
                    .setParameter("email",email)
                    .getSingleResult();
            
        } catch (NoResultException e) {
            return null;
        }
                    
        if(BCrypt.checkpw(
                new String(lozinka), 
                new String(o.getLozinka())
                            )
                ){
            return o;
        }
        
        return null;
    }
        // implementacija metoda

    @Override
    protected void kontrolaUnos() throws NovoselacException {
     kontrolaFormatOib();
     kontrolaOibDupliUBazi();
        
   }

    @Override
    protected void kontrolaPromjena() throws NovoselacException {
   }

    @Override
    protected void kontrolaBrisanje() throws NovoselacException {
   }

    private void kontrolaOibDupliUBazi() throws NovoselacException{
         List<Operater> djelatnici=null;
        try {
            djelatnici = session.createQuery("from Djelatnik u "
                    + " where u.oib=:oib", 
                    Operater.class)
                    .setParameter("oib", entitet.getOib())
                    .list();
        } catch (Exception e) {
        }
        if(djelatnici!=null && !djelatnici.isEmpty()){
            throw new NovoselacException("Operater s istim oibom postoji u bazi");
        }
    }
            
            
            
    private void kontrolaFormatOib() throws NovoselacException{
        if(!Alati.kontrolaOIB(entitet.getOib())){
            throw new NovoselacException("OIB nije u dobrom formatu");
        }
    }
    
}
