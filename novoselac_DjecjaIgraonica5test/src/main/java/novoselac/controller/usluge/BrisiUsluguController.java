package novoselac.controller.usluge;

import novoselac.service.UslugaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/BrisiUsluguController")
public class BrisiUsluguController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BrisiUsluguController.class.getName());
    private UslugaService uslugaService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.uslugaService = new UslugaService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String naziv = request.getParameter("naziv");
        HttpSession session = request.getSession();

        if (naziv == null || naziv.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Naziv usluge nije proslijeđen.");
            response.sendRedirect("DodajUsluguController");
            return;
        }

        try {
            boolean uspjeh = uslugaService.obrisiUslugu(naziv);
            
            if (uspjeh) {
                session.setAttribute("successMessage", "Usluga je uspješno obrisana.");
            } else {
                session.setAttribute("errorMessage", "Brisanje nije uspjelo. Usluga ima zavisne podatke ili ne postoji.");
            }
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Greška u kontroleru pri brisanju usluge", ex);
            session.setAttribute("errorMessage", "Greška u bazi podataka: " + ex.getMessage());
        }
        
        response.sendRedirect("DodajUsluguController");
    }
}