package novoselac.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;
  @Entity
public class Djelatnik extends Entitet {

	
	//svojstva
  
	private String ime;
	private String prezime;
	private String oib;
	private String iban;
	private String radnoMjesto;
        
        @OneToMany(mappedBy="djelatnik")
        private List<Posjeta>posjete;
        
        // dodani geteri i seteri za posjete

    public List<Posjeta> getPosjete() {
        return posjete;
    }

    public void setPosjete(List<Posjeta> posjete) {
        this.posjete = posjete;
    }

  
	
	//prazni konstruktor
	public Djelatnik() {
		super();
	}
	//puni konstruktor
	public Djelatnik(int sifra, String ime, String prezime, String oib, String iban, String radnoMjesto) {
		super(sifra);
		this.ime = ime;
		this.prezime = prezime;
		this.oib = oib;
		this.iban = iban;
		this.radnoMjesto = radnoMjesto;
	}
	//geteri i seteri
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
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getRadnoMjesto() {
		return radnoMjesto;
	}
	public void setRadnoMjesto(String radnoMjesto) {
		this.radnoMjesto = radnoMjesto;
	}
	
	@Override
	public String toString() {
		return ime+ " "+ prezime+", "+ radnoMjesto;
	}
	
}
