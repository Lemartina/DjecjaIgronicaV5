//POZVIA DAO CRUD
package novoselac.service;

import java.sql.SQLException;
import novoselac.dao.UslugaDao;
import novoselac.model.Usluga;
import java.util.List;

public class UslugaService {
    private UslugaDao uslugaDAO;
    
    public UslugaService() {
        this.uslugaDAO = new UslugaDao();
    }
    
    // DODAJ SQLException U METODU
    public List<Usluga> dohvatiSveUsluge() {
        try {
            return uslugaDAO.getAllUsluge();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // vrati praznu listu umjesto null
        }
    }
    
    // KONSTRUKTOR ZA TESTIRANJE
    public UslugaService(UslugaDao uslugaDao) {
        this.uslugaDAO = uslugaDao;
    }
    
    public boolean dodajUslugu(Usluga usluga) {
        try {
            // VALIDACIJA
            if (usluga.getCijena() == null || usluga.getCijena().compareTo(java.math.BigDecimal.ZERO) <= 0 || 
                usluga.getKolicina() == null || usluga.getKolicina().compareTo(java.math.BigDecimal.ZERO) < 0 ||
                usluga.getNaziv() == null || usluga.getNaziv().trim().isEmpty()) {
                return false;
            }
            
            // POZIV INSTANCE METODE NA DAO OBJEKTU
            return uslugaDAO.dodajUslugu(usluga);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        
    }
    
    
    
}