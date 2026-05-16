//ODLUČUJE ŠTO ĆE SE PRIKAZIVATI
package novoselac.controller.usluge;

import novoselac.model.Usluga;
import novoselac.service.UslugaService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/DodajUsluguController")
public class DodajUsluguController extends HttpServlet {
    private UslugaService uslugaService;
    
    @Override
    public void init() {
        this.uslugaService = new UslugaService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // DOHVAĆANJE PODATAKA preko servisa
            List<Usluga> usluge = uslugaService.dohvatiSveUsluge();
            request.setAttribute("usluge", usluge);
            
            // PREBACIVANJE PORUKA IZ SESIJE U REQUEST
            HttpSession session = request.getSession(false);
            if (session != null) {
                String poruka = (String) session.getAttribute("poruka");
                String greska = (String) session.getAttribute("greska");
                
                if (poruka != null) {
                    request.setAttribute("poruka", poruka);
                    session.removeAttribute("poruka");
                }
                if (greska != null) {
                    request.setAttribute("greska", greska);
                    session.removeAttribute("greska");
                }
            }
            
        } catch (Exception e) {
            request.setAttribute("greska", "Došlo je do greške pri učitavanju usluga: " + e.getMessage());
        }
        
        // PROSLJEĐIVANJE NA VIEW
        request.getRequestDispatcher("/WEB-INF/views/DodajUslugaViewWeb.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // DOHVAĆANJE PARAMETARA IZ FORME
        String naziv = request.getParameter("naziv");
        String jedinicaMjere = request.getParameter("jedinicaMjere");
        String cijenaStr = request.getParameter("cijena");
        String kolicinaStr = request.getParameter("kolicina");
        
        HttpSession session = request.getSession();
        
        try {
            // VALIDACIJA OBAVEZNIH POLJA
            if (naziv == null || naziv.trim().isEmpty() ||
                jedinicaMjere == null || jedinicaMjere.trim().isEmpty() ||
                cijenaStr == null || cijenaStr.trim().isEmpty() ||
                kolicinaStr == null || kolicinaStr.trim().isEmpty()) {
                
                session.setAttribute("greska", "Sva polja su obavezna!");
                response.sendRedirect("DodajUsluguController");
                return;
            }
            
            // KONVERZIJA STRINGOVA U BIGDECIMAL
            BigDecimal cijena = new BigDecimal(cijenaStr);
            BigDecimal kolicina = new BigDecimal(kolicinaStr);
            
            // DODATNA VALIDACIJA VRIJEDNOSTI
            if (cijena.compareTo(BigDecimal.ZERO) <= 0) {
                session.setAttribute("greska", "Cijena mora biti veća od 0!");
                response.sendRedirect("DodajUsluguController");
                return;
            }
            
            if (kolicina.compareTo(BigDecimal.ZERO) < 0) {
                session.setAttribute("greska", "Količina ne može biti negativna!");
                response.sendRedirect("DodajUsluguController");
                return;
            }
            
            // KREIRANJE NOVE USLUGE
            Usluga novaUsluga = new Usluga();
            novaUsluga.setNaziv(naziv.trim());
            novaUsluga.setJedinicaMjere(jedinicaMjere.trim());
            novaUsluga.setCijena(cijena);
            novaUsluga.setKolicina(kolicina);
            
            // DODAVANJE USLUGE
            boolean uspjeh = uslugaService.dodajUslugu(novaUsluga);
            
            if (uspjeh) {
                session.setAttribute("poruka", "Usluga je uspješno dodana!");
            } else {
                session.setAttribute("greska", "Došlo je do greške pri dodavanju usluge!");
            }
            
        } catch (NumberFormatException e) {
            session.setAttribute("greska", "Cijena i količina moraju biti valjani brojevi!");
        } catch (Exception e) {
            session.setAttribute("greska", "Došlo je do greške: " + e.getMessage());
        }
        
        // REDIRECT NA GET METODU (PRG PATTERN)
        response.sendRedirect("DodajUsluguController");
    }
}