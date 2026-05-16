package novoselac.controller.usluge;

import novoselac.dao.UslugaDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/BrisiUsluguController")
public class BrisiUsluguController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BrisiUsluguController.class.getName());
    private UslugaDao uslugaDao;
    
    @Override
    public void init() throws ServletException {
        super.init();
        uslugaDao = new UslugaDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BrisiUsluguController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BrisiUsluguController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        
        String naziv = request.getParameter("naziv");

        // Provjeri je li naziv proslijeđen
        if (naziv == null || naziv.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Naziv usluge nije proslijeđen.");
            forwardToView(request, response);
            return;
        }

        try {
            // Provjeri postoje li zavisni podaci
            if (uslugaDao.hasDependentRecords(naziv)) {
                request.setAttribute("errorMessage", 
                    "Brisanje nije uspjelo jer postoje zavisni podaci. Usluga je već dodana na posjetu.");
            } else {
                // Izvrši brisanje
                boolean deleted = uslugaDao.deleteByNaziv(naziv);
                if (deleted) {
                    request.setAttribute("successMessage", "Usluga je uspješno obrisana.");
                } else {
                    request.setAttribute("errorMessage", "Nema usluge s tim nazivom.");
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Greška pri brisanju usluge", ex);
            request.setAttribute("errorMessage", "Greška u bazi podataka: " + ex.getMessage());
        }
        
        forwardToView(request, response);
    }
    
    private void forwardToView(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        
        // Dohvati sve usluge za prikaz
        java.util.List<novoselac.model.Usluga> usluge = uslugaDao.getAllUsluge();
        request.setAttribute("usluge", usluge);
        
      request.getRequestDispatcher("UslugaService").forward(request, response);
    }
}