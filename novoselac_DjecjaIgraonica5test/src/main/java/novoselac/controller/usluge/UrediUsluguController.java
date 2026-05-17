package novoselac.controller.usluge;

import novoselac.model.Usluga;
import novoselac.service.UslugaService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/UrediUsluguController")
public class UrediUsluguController extends HttpServlet {
    private UslugaService uslugaService;
    
    @Override
    public void init() {
        this.uslugaService = new UslugaService();
    }
    
    /**
     * KORAK 1: DOHVAĆANJE PODATAKA PREKO ŠIFRE (ID-a)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Prima broj/šifru iz linka (npr. UrediUsluguController?id=5)
        String idStr = request.getParameter("id");
        if (idStr == null) {
            idStr = request.getParameter("sifra"); // Za svaki slučaj ako gumb šalje "sifra"
        }
        
        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                int sifra = Integer.parseInt(idStr.trim());

                // NAJBOLJI PUTEVI: Pretražujemo bazu preko jedinstvenog broja šifre
                Usluga usluga = uslugaService.dohvatiSveUsluge().stream()
                        .filter(u -> u.getSifra() == sifra)
                        .findFirst()
                        .orElse(null);

                if (usluga != null) {
                    request.setAttribute("uslugaZaUredivanje", usluga);
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("greska", "Usluga sa šifrom " + sifra + " ne postoji u bazi.");
                    response.sendRedirect("DodajUsluguController");
                    return;
                }
            }
            
            // Prikazivanje grešaka iz sesije ako postoje
            HttpSession session = request.getSession(false);
            if (session != null) {
                String greska = (String) session.getAttribute("greska");
                if (greska != null) {
                    request.setAttribute("greska", greska);
                    session.removeAttribute("greska");
                }
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("greska", "Prosljeđena šifra nije ispravan broj!");
        } catch (Exception e) {
            request.setAttribute("greska", "Došlo je do greške pri učitavanju: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/WEB-INF/views/UrediUslugaViewWeb.jsp").forward(request, response);
    }
    
    /**
     * KORAK 2: SPREMANJE IZMJENA
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String sifraStr = request.getParameter("sifra");
        String naziv = request.getParameter("naziv");
        String jedinicaMjere = request.getParameter("jedinicaMjere");
        String cijenaStr = request.getParameter("cijena");
        String kolicinaStr = request.getParameter("kolicina");
        
        try {
            if (sifraStr == null || sifraStr.trim().isEmpty() ||
                naziv == null || EmptyOrNull(naziv) ||
                jedinicaMjere == null || EmptyOrNull(jedinicaMjere) ||
                cijenaStr == null || cijenaStr.trim().isEmpty() ||
                kolicinaStr == null || kolicinaStr.trim().isEmpty()) {
                
                request.setAttribute("greska", "Sva polja su obavezna za izmjenu!");
                doGet(request, response); 
                return;
            }
            
            int id = Integer.parseInt(sifraStr.trim());
            BigDecimal cijena = new BigDecimal(cijenaStr.trim());
            BigDecimal kolicina = new BigDecimal(kolicinaStr.trim());
            
            if (cijena.compareTo(BigDecimal.ZERO) <= 0) {
                request.setAttribute("greska", "Cijena mora biti veća od 0!");
                doGet(request, response);
                return;
            }
            
            if (kolicina.compareTo(BigDecimal.ZERO) < 0) {
                request.setAttribute("greska", "Količina ne može biti negativna!");
                doGet(request, response);
                return;
            }
            
            Usluga izmijenjenaUsluga = new Usluga(id, naziv.trim(), cijena, jedinicaMjere.trim(), kolicina);
            boolean uspjeh = uslugaService.azurirajUslugu(izmijenjenaUsluga); 
            
            HttpSession session = request.getSession();
            if (uspjeh) {
                session.setAttribute("poruka", "Usluga '" + naziv + "' je uspješno izmijenjena!");
                response.sendRedirect("DodajUsluguController");
                return;
            } else {
                request.setAttribute("greska", "Došlo je do greške u bazi podataka pri spremanju!");
                doGet(request, response);
                return;
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("greska", "Cijena i količina moraju biti valjani brojevi!");
            doGet(request, response);
            return;
        } catch (Exception e) {
            HttpSession session = request.getSession();
            session.setAttribute("greska", "Došlo je do neočekivane greške: " + e.getMessage());
            response.sendRedirect("DodajUsluguController");
        }
    }

    // Pomoćna metoda za provjeru teksta
    private boolean EmptyOrNull(String tekst) {
        return tekst.trim().isEmpty();
    }
}