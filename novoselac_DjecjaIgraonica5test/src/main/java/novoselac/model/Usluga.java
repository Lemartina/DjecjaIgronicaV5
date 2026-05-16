package novoselac.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
    @Entity
public class Usluga extends Entitet {
        //svojstva

	private String naziv;
	private BigDecimal cijena;
	private String jedinicaMjere;
	private BigDecimal kolicina;
        
        
       @ManyToMany(mappedBy="usluge")
        private List<Posjeta> posjete= new ArrayList<>();

    public List<Posjeta> getPosjete() {
        return posjete;
    }

    public void setPosjete(List<Posjeta> posjete) {
        this.posjete = posjete;
    }
        
	// prazni konstruktor
	public Usluga() {
		super();
	}

	// puni konstruktor

	public Usluga(int sifra, String naziv, BigDecimal cijena, String jedinicaMjere, BigDecimal kolicina) {
		super(sifra);
		this.naziv = naziv;
		this.cijena = cijena;
		this.jedinicaMjere = jedinicaMjere;
		this.kolicina = kolicina;
	}

	// geteri i seteri

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public BigDecimal getCijena() {
		return cijena;
	}

	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}

	public String getJedinicaMjere() {
		return jedinicaMjere;
	}

	public void setJedinicaMjere(String jedinicaMjere) {
		this.jedinicaMjere = jedinicaMjere;
	}

	public BigDecimal getKolicina() {
		return kolicina;
	}

	public void setKolicina(BigDecimal kolicina) {
		this.kolicina = kolicina;
	}

	@Override
	public String toString() {
		return naziv + " (cijena: "+ cijena + " EUR)";
	}

}
