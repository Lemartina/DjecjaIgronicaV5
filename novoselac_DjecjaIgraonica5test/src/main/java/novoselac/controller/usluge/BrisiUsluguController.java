package novoselac.controller.usluge;

import novoselac.service.UslugaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import novoselac.model.Usluga;

@WebServlet("/BrisiUsluguController")
public class BrisiUsluguController extends HttpServlet {
    private UslugaService uslugaService;
    
    @Override
    public void init() {
        this.uslugaService = new UslugaService();
    }
    
    /**
     * KORAK 1: DOHVAĆANJE JEDNE USLUGE PREKO ŠIFRE ZA POTVRDU BRISANJA
     * Poziva se preko linka, npr: BrisiUsluguController?sifra=5
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("sifra");
        if (idStr == null) {
            idStr = request.getParameter("id"); // Za svaki slučaj ako je parametar 'id'
        }
        
        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                int sifra = Integer.parseInt(idStr.trim());

                // Tražimo isključivo tu jednu specifičnu uslugu
                Usluga usluga = uslugaService.dohvatiSveUsluge().stream()
                        .filter(u -> u.getSifra() == sifra)
                        .findFirst()
                        .orElse(null);

                if (usluga != null) {
                    // Šaljemo samo tu jednu uslugu na JSP pod ključem "uslugaZaBrisanje"
                    request.setAttribute("uslugaZaBrisanje", usluga);
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("errorMessage", "Usluga sa šifrom " + sifra + " ne postoji.");
                    response.sendRedirect("pregledUsluga.jsp"); // Ili vaš kontroler za pregled
                    return;
                }
            } else {
                request.setAttribute("errorMessage", "Nije proslijeđena šifra usluge.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Proslijeđena šifra nije ispravnog formata.");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Došlo je do pogreške: " + e.getMessage());
        }
        
        // Prikazujemo ekran za potvrdu brisanja te jedne usluge
        request.getRequestDispatcher("/WEB-INF/views/BrisiUslugaViewWeb.jsp").forward(request, response);
    }
    
    /**
     * KORAK 2: STVARNO BRISANJE NAKON POTVRDE (POST)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String idStr = request.getParameter("sifra");
        
        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                int sifra = Integer.parseInt(idStr.trim());
                
                boolean obrisano = uslugaService.obrisiUslugu(sifra);
                
                if (obrisano) {
                    session.setAttribute("successMessage", "Usluga je uspješno obrisana.");
                } else {
                    session.setAttribute("errorMessage", "Uspijelo brisanje usluge sa šifrom " + sifra);
                }
            }
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Greška pri brisanju: " + e.getMessage());
        }
        
        // Nakon brisanja, logično je vratiti korisnika na pregled svih usluga
        response.sendRedirect(request.getContextPath() + "/DodajUsluguController"); 
    }
}