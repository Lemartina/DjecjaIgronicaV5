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
    
    public UslugaService(UslugaDao uslugaDao) {
        this.uslugaDAO = uslugaDao;
    }
    
    public List<Usluga> dohvatiSveUsluge() throws SQLException {
        return uslugaDAO.getAllUsluge();
    }
    
    public boolean dodajUslugu(Usluga usluga) throws SQLException {
        if (usluga.getCijena() == null || usluga.getCijena().compareTo(java.math.BigDecimal.ZERO) <= 0 || 
            usluga.getKolicina() == null || usluga.getKolicina().compareTo(java.math.BigDecimal.ZERO) < 0 ||
            usluga.getNaziv() == null || usluga.getNaziv().trim().isEmpty()) {
            return false;
        }
        
        return uslugaDAO.dodajUslugu(usluga);
    }
    
    public boolean obrisiUslugu(String naziv) throws SQLException {
        if (uslugaDAO.hasDependentRecords(naziv)) {
            return false;
        }
        return uslugaDAO.deleteByNaziv(naziv);
    }
}