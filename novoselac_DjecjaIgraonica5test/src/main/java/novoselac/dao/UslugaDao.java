package novoselac.dao;

import novoselac.model.Usluga;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa zadužena isključivo za CRUD operacije nad tablicom 'usluga'.
 */
public class UslugaDao {
    private static final Logger LOGGER = Logger.getLogger(UslugaDao.class.getName());
    
    // Konfiguracija baze 
    private static final String URL = "jdbc:mysql://localhost/djecjaigraonicahib";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver nije pronađen", ex);
            throw new SQLException("Driver nije pronađen", ex);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /**
     * Mapira ResultSet (redak iz baze) u objekt Usluga.
     */
    private Usluga mapResultSetToUsluga(ResultSet rs) throws SQLException {
        Usluga usluga = new Usluga();
        
        // Fleksibilno čitanje šifre (podržava 'sifra' ili 'id' iz baze)
        try {
            usluga.setSifra(rs.getInt("sifra"));
        } catch (SQLException e) {
            try {
                usluga.setSifra(rs.getInt("id"));
            } catch (SQLException e2) {
                usluga.setSifra(0);
            }
        }
        
        usluga.setNaziv(rs.getString("naziv"));
        usluga.setJedinicaMjere(rs.getString("jedinicaMjere"));
        
        // Cijena i Količina
        try {
            usluga.setCijena(rs.getBigDecimal("cijena"));
        } catch (SQLException e) {
            usluga.setCijena(BigDecimal.valueOf(rs.getDouble("cijena")));
        }
        try {
            usluga.setKolicina(rs.getBigDecimal("kolicina"));
        } catch (SQLException e) {
            usluga.setKolicina(BigDecimal.valueOf(rs.getDouble("kolicina")));
        }
        
        return usluga;
    }
    
    // --- CRUD metode ---
    
    /**
     * NOVO: Dohvaća jednu specifičnu uslugu preko njezine šifre.
     * Potrebno za Service sloj i BrisiUsluguController (doGet).
     */
    public Usluga getUslugaBySifra(int sifra) throws SQLException {
        Usluga usluga = null;
        // Budući da u azurirajUslugu koristiš 'WHERE sifra = ?', držimo se stupca 'sifra'
        String sql = "SELECT * FROM usluga WHERE sifra = ?";
        
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, sifra);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    usluga = mapResultSetToUsluga(rs);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Greška pri dohvatu usluge po šifri: " + sifra, ex);
            throw ex;
        }
        return usluga;
    }

    public Usluga dohvatiUsluguPoNazivu(String naziv) throws SQLException {
        Usluga usluga = null;
        String sql = "SELECT * FROM usluga WHERE naziv = ?";
        
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, naziv);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    usluga = mapResultSetToUsluga(rs);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Greška pri dohvatu usluge po nazivu: " + naziv, ex);
            throw ex;
        }
        return usluga;
    }
    
    /**
     * Dohvaća sve usluge iz baze.
     */
    public List<Usluga> getAllUsluge() throws SQLException {
        List<Usluga> usluge = new ArrayList<>();
        String sql = "SELECT * FROM usluga ORDER BY naziv";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usluge.add(mapResultSetToUsluga(rs));
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Greška u getAllUsluge: ", e);
            throw e;
        }
        return usluge;
    }
    
    /**
     * Dodaje novu uslugu u bazu.
     */
    public boolean dodajUslugu(Usluga usluga) throws SQLException {
        String sql = "INSERT INTO usluga (naziv, cijena, jedinicaMjere, kolicina) VALUES (?, ?, ?, ?)";
        
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, usluga.getNaziv());
            pst.setBigDecimal(2, usluga.getCijena());
            pst.setString(3, usluga.getJedinicaMjere());
            pst.setBigDecimal(4, usluga.getKolicina());
            
            int row = pst.executeUpdate();
            return row > 0;
        }
    }
    
    /**
     * Ažurira postojeću uslugu.
     */
    public boolean azurirajUslugu(Usluga usluga) throws SQLException {
        String sql = "UPDATE usluga SET naziv = ?, cijena = ?, jedinicaMjere = ?, kolicina = ? WHERE sifra = ?";
        
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, usluga.getNaziv());
            pst.setBigDecimal(2, usluga.getCijena());
            pst.setString(3, usluga.getJedinicaMjere());
            pst.setBigDecimal(4, usluga.getKolicina());
            pst.setInt(5, usluga.getSifra());
            
            int row = pst.executeUpdate();
            return row > 0;
        }
    }
    
    /**
     * POPRAVLJENO: Briše uslugu po šifri (int).
     * Popravljen pst.setInt umjesto pst.setString.
     */
    public boolean obrisiUslugu(int sifra) throws SQLException {
        String sql = "DELETE FROM usluga WHERE sifra = ?";
        
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, sifra);
            int row = pst.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Greška pri brisanju usluge sa šifrom: " + sifra, ex);
            throw ex;
        }
    }
    
    /**
     * POPRAVLJENO: Provjerava ima li usluga zavisne zapise preko primarnog ključa (sifra).
     */
    public boolean hasDependentRecords(int sifra) throws SQLException {
        String sql = "SELECT COUNT(*) FROM uslugaposjeta WHERE usluga = ?";
        
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, sifra);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Greška pri provjeri zavisnih zapisa za šifru: " + sifra, ex);
            throw ex;
        }
        return false;
    }

    /**
     * Pomoćna metoda (opcionalna) ako i dalje negdje u aplikaciji provjeravaš preko naziva.
     */
    public boolean hasDependentRecords(String naziv) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usluga a " +
                     "INNER JOIN uslugaposjeta b ON a.sifra = b.usluga " +
                     "WHERE a.naziv = ?";
        
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, naziv);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}