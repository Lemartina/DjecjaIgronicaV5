<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Brisanje usluga</title>
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css'>
    <link rel="stylesheet" href="../css/stili.css">
</head>
<body>
    <div class="usluga-container">
        <div class="usluga-wrapper">
            <!-- NAVIGACIJA -->
            <div class="usluga-nav">
                <h2><i class="fas fa-trash"></i> Brisanje usluga</h2>
                <a href="pregledUsluga.jsp" class="usluga-link btn-view">
                    <i class="fas fa-list"></i> Pregled svih usluga
                </a>
                <a href="dodajUslugu.jsp" class="usluga-link usluga-link-primary">
                    <i class="fas fa-plus"></i> Dodaj novu uslugu
                </a>
                <a href="../index.html" class="usluga-link">
                    <i class="fas fa-home"></i> Početna stranica
                </a>
            </div>

            <!-- PORUKE -->
            <c:if test="${not empty successMessage}">
                <div class="usluga-success">
                    <i class="fas fa-check-circle"></i> ${successMessage}
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="usluga-error">
                    <i class="fas fa-exclamation-triangle"></i> ${errorMessage}
                </div>
            </c:if>

            <!-- TABLICA ZA BRISANJE -->
            <c:choose>
                <c:when test="${empty usluge}">
                    <div class="usluga-error">
                        <i class="fas fa-info-circle"></i> Nema usluga za brisanje.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="usluga-error">
                        <i class="fas fa-exclamation-triangle"></i> 
                        <strong>UPOZORENJE:</strong> Brisanje je trajna operacija!
                    </div>
                    
                    <table class="usluga-table">
                        <thead>
                            <tr>
                                <th>Šifra</th>
                                <th>Naziv</th>
                                <th>Cijena (EUR)</th>
                                <th>Količina</th>
                                <th>Jedinica mjere</th>
                                <th>Brisanje</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="usluga" items="${usluge}">
                                <tr>
                                    <td>${usluga.sifra}</td>
                                    <td><strong>${usluga.naziv}</strong></td>
                                    <td>${usluga.cijena} €</td>
                                    <td>${usluga.kolicina}</td>
                                    <td>${usluga.jedinicaMjere}</td>
                                    <td class="usluga-actions">
                                        <form action="../BrisiUsluguController" method="post" style="display:inline;">
                                            <input type="hidden" name="id" value="${usluga.sifra}">
                                            <button type="submit" class="btn-delete"
                                                    onclick="return confirm('Jeste li SIGURNI da želite trajno obrisati uslugu: ${usluga.naziv}?')">
                                                <i class="fas fa-trash"></i> Trajno obriši
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>