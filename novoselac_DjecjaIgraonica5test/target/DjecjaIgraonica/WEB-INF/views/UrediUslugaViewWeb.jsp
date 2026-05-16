<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Uredi uslugu</title>
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css'>
    <link rel="stylesheet" href="../css/stili.css">
</head>
<body>
    <div class="usluga-container">
        <div class="usluga-wrapper">
            <!-- NAVIGACIJA -->
            <div class="usluga-nav">
                <h2><i class="fas fa-edit"></i> Uređivanje usluge</h2>
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

            <!-- FORMA ZA UREĐIVANJE -->
            <c:if test="${not empty usluga}">
                <form action="../UslugaController?action=uredi&id=${usluga.sifra}" method="post" class="usluga-form">
                    <div class="usluga-form-group">
                        <label for="naziv"><i class="fas fa-tag"></i> Naziv usluge:</label>
                        <input type="text" id="naziv" name="naziv" value="${usluga.naziv}" required>
                    </div>
                    
                    <div class="usluga-form-group">
                        <label for="cijena"><i class="fas fa-euro-sign"></i> Cijena (EUR):</label>
                        <input type="text" id="cijena" name="cijena" value="${usluga.cijena}" required>
                    </div>
                    
                    <div class="usluga-form-group">
                        <label for="kolicina"><i class="fas fa-balance-scale"></i> Količina:</label>
                        <input type="text" id="kolicina" name="kolicina" value="${usluga.kolicina}" required>
                    </div>
                    
                    <div class="usluga-form-group">
                        <label for="jedinicaMjere"><i class="fas fa-ruler"></i> Jedinica mjere:</label>
                        <input type="text" id="jedinicaMjere" name="jedinicaMjere" value="${usluga.jedinicaMjere}" required>
                    </div>

                    <button type="submit" class="usluga-btn usluga-btn-success">
                        <i class="fas fa-save"></i> Spremi promjene
                    </button>
                    
                    <a href="pregledUsluga.jsp" class="usluga-link">
                        <i class="fas fa-times"></i> Odustani
                    </a>
                </form>
            </c:if>
        </div>
    </div>
</body>
</html>