package novoselac.controller.usluge;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import novoselac.dao.UslugaDao;
import novoselac.model.Usluga;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/UrediUsluguController")
public class UrediUsluguController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UrediUsluguController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {
        String naziv = req.getParameter("naziv");
        UslugaDao dao = new UslugaDao(); // Stvaranje DAO instance

        try {
            // KONTROLER POZIVA DAO (Data Access Object)
            Usluga usluga = dao.dohvatiUsluguPoNazivu(naziv);

            if (usluga != null) {
                // Podaci su pronađeni. Stavljamo ih u Request.
                req.setAttribute("uslugaZaUredivanje", usluga);
                
                // DISPEČIRANJE na VIEW (JSP stranicu)
                RequestDispatcher rd = req.getRequestDispatcher("/UrediUsluguIzTablice.jsp");
                rd.forward(req, rsp);
            } else {
                // Usluga nije pronađena.
                req.setAttribute("poruka", "Usluga s nazivom '" + naziv + "' nije pronađena.");
                RequestDispatcher rd = req.getRequestDispatcher("/greska.jsp"); 
                rd.forward(req, rsp);
            }

        } catch (SQLException ex) {
            // Greška iz DAO sloja. Postavljanje poruke i dispečiranje na greška.jsp
            LOGGER.log(Level.SEVERE, "Greška u Controlleru prilikom dohvata usluge.", ex);
            req.setAttribute("poruka", "Greška pri dohvatu podataka: " + ex.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("/greska.jsp"); 
            rd.forward(req, rsp);
        }
    }
}