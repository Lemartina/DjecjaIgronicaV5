<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Potvrda brisanja usluge</title>
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css'>
    <link rel="stylesheet" href="../css/stili.css">
</head>
<body>
    <div class="usluga-container">
        <div class="usluga-wrapper">
            <!-- NAVIGACIJA -->
            <div class="usluga-nav">
                <h2><i class="fas fa-trash"></i> Potvrda brisanja usluge</h2>
                <a href="PregledUslugaController" class="usluga-link btn-view">
                    <i class="fas fa-list"></i> Pregled svih usluga
                </a>
                <a href="../index.html" class="usluga-link">
                    <i class="fas fa-home"></i> Početna stranica
                </a>
            </div>

            <!-- PORUKE -->
            <c:if test="${not empty errorMessage}">
                <div class="usluga-error">
                    <i class="fas fa-exclamation-triangle"></i> ${errorMessage}
                </div>
            </c:if>

            <!-- KARTICA ILI DETALJI ZA POTVRDU BRISANJA -->
            <c:choose>
                <c:when test="${empty uslugaZaBrisanje}">
                    <div class="usluga-error">
                        <i class="fas fa-info-circle"></i> Podaci o usluzi nisu učitani ili usluga ne postoji.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="usluga-error" style="background-color: #fff3cd; color: #856404; border-color: #ffeeba;">
                        <i class="fas fa-exclamation-triangle"></i> 
                        <strong>UPOZORENJE:</strong> Jeste li sigurni da želite trajno obrisati ovu uslugu? Ova operacija se ne može poništiti! Ukoliko usluga ima zavisne podatke neće biti obrisana!
                    </div>
                    
                    <table class="usluga-table">
                        <thead>
                            <tr>
                                <th>Šifra</th>
                                <th>Naziv</th>
                                <th>Cijena (EUR)</th>
                                <th>Količina</th>
                                <th>Jedinica mjere</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${uslugaZaBrisanje.sifra}</td>
                                <td><strong>${uslugaZaBrisanje.naziv}</strong></td>
                                <td>${uslugaZaBrisanje.cijena} €</td>
                                <td>${uslugaZaBrisanje.kolicina}</td>
                                <td>${uslugaZaBrisanje.jedinicaMjere}</td>
                            </tr>
                        </tbody>
                    </table>

                    <!-- FORMA KOJA ŠALJE ZAHTJEV ZA STVARNO BRISANJE -->
                    <div style="text-align: center; margin-top: 20px;">
                        <form action="BrisiUsluguController" method="post" style="display:inline;">
                            <input type="hidden" name="sifra" value="${uslugaZaBrisanje.sifra}">
                            
                            <button type="submit" class="btn-delete" style="padding: 10px 20px; font-size: 16px;">
                                <i class="fas fa-trash"></i> Da, trajno obriši
                            </button>
                            
                            <a href="PregledUslugaController" class="usluga-link" style="margin-left: 15px; background: #6c757d; color: white; padding: 10px 20px; border-radius: 4px; text-decoration: none;">
                                Odustani
                            </a>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>