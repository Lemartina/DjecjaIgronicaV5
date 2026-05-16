package novoselac.model;

//nasljeđuje entitet

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Dijete extends Entitet {
	//svojstva
	
        
	private String ime;
	private String prezime;
	private String imeRoditelja;
	private String telefonRoditelja;
        private String oib;

    public Dijete(String oib, int sifra) {
        super(sifra);
        this.oib = oib;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }
        
        @ManyToMany(mappedBy="djeca")
        private List<Posjeta> posjete= new ArrayList<>();	

   
	
	//prazni konstruktor
	public Dijete() {
		super();//poziv konstrukora nadklase
	}
	//puni konstruktor
	public Dijete(int sifra, String ime, String prezime, String imeRoditelja, String telefonRoditelja) {
		super(sifra);
		this.ime = ime;
		this.prezime = prezime;
		this.imeRoditelja = imeRoditelja;
		this.telefonRoditelja = telefonRoditelja;
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
	public String getImeRoditelja() {
		return imeRoditelja;
	}
	public void setImeRoditelja(String imeRoditelja) {
		this.imeRoditelja = imeRoditelja;
	}
	public String getTelefonRoditelja() {
		return telefonRoditelja;
	}
	public void setTelefonRoditelja(String telefonRoditelja) {
		this.telefonRoditelja = telefonRoditelja;
	}
	
         public List<Posjeta> getPosjete() {
        return posjete;
    }

    public void setPosjete(List<Posjeta> posjete) {
        this.posjete = posjete;
    }
	@Override
	public String toString() {
				return ime +" "+ prezime + ", oib: "+ oib;
	}
	
	
	

}
