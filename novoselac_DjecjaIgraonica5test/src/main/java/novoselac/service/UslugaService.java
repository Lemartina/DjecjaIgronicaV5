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
    
   public boolean obrisiUslugu(int sifra) throws SQLException {
        if (sifra <= 0) {
            return false;
        }
        
        // Provjera ovisnih zapisa u bazi se sada također radi preko jedinstvene šifre
        if (uslugaDAO.hasDependentRecords(sifra)) {
            return false;
        }
        
        return uslugaDAO.obrisiUslugu(sifra);
    }
    
   public boolean azurirajUslugu(Usluga usluga) throws SQLException {
        if (usluga.getCijena() == null || usluga.getCijena().compareTo(java.math.BigDecimal.ZERO) <= 0 || 
            usluga.getKolicina() == null || usluga.getKolicina().compareTo(java.math.BigDecimal.ZERO) < 0 ||
            usluga.getNaziv() == null || usluga.getNaziv().trim().isEmpty() ||
            usluga.getSifra() <= 0) {
            return false;
        }
        return uslugaDAO.azurirajUslugu(usluga);
    }

    public Usluga dohvatiUsluguPoNazivu(String naziv) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}