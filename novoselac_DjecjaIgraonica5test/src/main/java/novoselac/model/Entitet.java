package novoselac.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Entitet {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)//generiranje od strane baze
	//svojstva
	private int sifra;

	
	//prazan konstruktor
	public Entitet() {
		super();
	}

	//puni konstrukotr
	public Entitet(int sifra) {
		super();
		this.sifra = sifra;
	}

	//geteri i seteri
	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}
	

}
