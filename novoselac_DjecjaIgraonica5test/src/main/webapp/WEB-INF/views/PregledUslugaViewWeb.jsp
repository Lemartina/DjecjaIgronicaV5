<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pregled svih usluga</title>
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css'>
    <link rel="stylesheet" href="../css/stili.css">
</head>
<body>
    <div class="usluga-container">
        <div class="usluga-wrapper">
            <!-- NAVIGACIJA -->
            <div class="usluga-nav">
                <h2><i class="fas fa-list"></i> Pregled svih usluga</h2>
                <a href="dodajUslugu.jsp" class="usluga-link usluga-link-primary">
                    <i class="fas fa-plus"></i> Dodaj novu uslugu
                </a>
                <a href="brisiUslugu.jsp" class="usluga-link btn-delete">
                    <i class="fas fa-trash"></i> Stranica za brisanje
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

            <!-- TABLICA USLUGA -->
            <c:choose>
                <c:when test="${empty usluge}">
                    <div class="usluga-error">
                        <i class="fas fa-info-circle"></i> Nema unesenih usluga.
                    </div>
                </c:when>
                <c:otherwise>
                    <table class="usluga-table">
                        <thead>
                            <tr>
                                <th>Šifra</th>
                                <th>Naziv</th>
                                <th>Cijena (EUR)</th>
                                <th>Količina</th>
                                <th>Jedinica mjere</th>
                                <th>Akcije</th>
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
                                        <a href="urediUslugu.jsp?id=${usluga.sifra}" class="btn-edit">
                                            <i class="fas fa-edit"></i> Uredi
                                        </a>
                                        <a href="brisiUslugu.jsp?id=${usluga.sifra}" class="btn-delete">
                                            <i class="fas fa-trash"></i> Obriši
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    
                    <div class="usluga-success">
                        <i class="fas fa-info-circle"></i> Pronađeno ukupno usluga: ${usluge.size()}
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>