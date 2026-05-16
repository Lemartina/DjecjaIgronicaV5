package novoselac.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import novoselac.util.Alati;
@Entity
public class Posjeta extends Entitet {
        //svojstva
    
        
	private Date datumVrijemeDolaska;
	private Date datumVrijemeOdlaska;
	private String napomena;
        @ManyToOne
	private Djelatnik djelatnik;
        
         @ManyToMany
          @JoinTable(
            name = "dijetePosjeta",
            joinColumns = {
                @JoinColumn(name = "posjeta")},
            inverseJoinColumns = {
                @JoinColumn(name = "dijete")}
    
          )
         private List<Dijete> djeca;
         
           @ManyToMany
          @JoinTable(
            name = "uslugaPosjeta",
            joinColumns = {
                @JoinColumn(name = "posjeta")},
            inverseJoinColumns = {
                @JoinColumn(name = "usluga")}
    
          )
           private List<Usluga>usluge;


	
//prazni konstruktor
    	public Posjeta() {
		super();
		djeca= new ArrayList<>();
		
		usluge= new ArrayList<>();
	}

    //puni konstruktor
        public Posjeta(Date datumVrijemeDolaska, Date datumVrijemeOdlaska, String napomena, Djelatnik djelatnik, List<Dijete> djeca, List<Usluga> usluge, int sifra) {
        super(sifra);
        this.datumVrijemeDolaska = datumVrijemeDolaska;
        this.datumVrijemeOdlaska = datumVrijemeOdlaska;
        this.napomena = napomena;
        this.djelatnik = djelatnik;
        this.djeca = djeca;
        this.usluge = usluge;
    }

    //geteri i seteri
        
        public Date getDatumVrijemeDolaska() {
        return datumVrijemeDolaska;
    }

    public void setDatumVrijemeDolaska(Date datumVrijemeDolaska) {
        this.datumVrijemeDolaska = datumVrijemeDolaska;
    }

    public Date getDatumVrijemeOdlaska() {
        return datumVrijemeOdlaska;
    }

    public void setDatumVrijemeOdlaska(Date datumVrijemeOdlaska) {
        this.datumVrijemeOdlaska = datumVrijemeOdlaska;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Djelatnik getDjelatnik() {
        return djelatnik;
    }

    public void setDjelatnik(Djelatnik djelatnik) {
        this.djelatnik = djelatnik;
    }

    public List<Dijete> getDjeca() {
        return djeca;
    }

    public void setDjeca(List<Dijete> djeca) {
        this.djeca = djeca;
    }

    public List<Usluga> getUsluge() {
        return usluge;
    }

    public void setUsluge(List<Usluga> usluge) {
        this.usluge = usluge;
    }

    
    //ispis
    @Override
	public String toString() {
            //stavljeno ovako da može stavljati vanjski ključ šifru djelatnika u tablicu posjeta
            try {
            return Alati.sdf.format(datumVrijemeDolaska) + "  ( dijete: " + djeca.get(0)+ " , " + "usluga: " + usluge.get(0)+ ", djelatnik: " + djelatnik.getSifra()+ ")";
        } catch (Exception e) {
        }
		return datumVrijemeDolaska.toString();
	}
}
