<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dodaj novu uslugu</title>
    <meta charset="UTF-8">
    <link href='http://fonts.googleapis.com/css?family=Lato&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="mystyle.css">
</head>
<body>
    <div>
        <div>
            <!-- NAVIGACIJA -->
            <div>
                <h2>Dodavanje nove usluge</h2>
            </div>

            <!-- PORUKE -->
            <c:if test="${not empty poruka}">
                <div style="color: green; padding: 10px; border: 1px solid green;">
                    ${poruka}
                </div>
            </c:if>
            <c:if test="${not empty greska}">
                <div style="color: red; padding: 10px; border: 1px solid red;">
                    ${greska}
                </div>
            </c:if>

            <!-- FORMA ZA DODAVANJE -->
            <form action="DodajUsluguController" method="post" onsubmit="return validirajFormu()">
                <div>
                
                    <input type="text" id="naziv" name="naziv" placeholder="Unesite naziv usluge" required>
                </div>
                
                <div>
                   
                    <input type="number" id="cijena" name="cijena" placeholder="Unesite cijenu" 
                           step="0.01" min="0.01" required>
                </div>
                
                <div>
                 
                    <input type="number" id="kolicina" name="kolicina" placeholder="Unesite količinu" 
                           step="0.001" min="0" required>
                </div>
                
                <div>
                 
                    <input type="text" id="jedinicaMjere" name="jedinicaMjere" 
                           placeholder="Unesite jedinicu mjere" required>
                </div>

                <button type="submit">Dodaj</button>
                
                <!-- ISPRAVLJEN LINK ZA ODUSTANI -->
                <a href="DodajUsluguController" style="text-decoration: none;">
                    <button type="button">Odustani</button>
                </a>
            </form>

            <!-- TABLICA ZA PRIKAZ POSTOJEĆIH USLUGA -->
            <c:if test="${not empty usluge}">
                <h3>Postojeće usluge</h3>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Naziv</th>
                            <th>Cijena</th>
                            <th>Količina</th>
                            <th>Jedinica mjere</th>
                            <th>Uredi</th>
                            <th>Briši</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="usluga" items="${usluge}">
                            <tr>
                                <td>${usluga.naziv}</td>
                                <td>${usluga.cijena} €</td>
                                <td>${usluga.kolicina}</td>
                                <td>${usluga.jedinicaMjere}</td>
                                <!-- ISPRAVLJENI LINKOVI -->
                                <td>
                                    <a href="UrediUsluguController?id=${usluga.sifra}">Uredi</a>
                                </td>
                                <td>
                                    <a href="BrisiUsluguController?id=${usluga.sifra}" 
                                       onclick="return confirm('Jeste li sigurni da želite obrisati uslugu?')">Briši</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>

    <!-- LINK ZA POČETNU -->
    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/index.html">Početna stranica</a>
    </div>

    <!-- JAVASCRIPT VALIDACIJA -->
    <script>
        function validirajFormu() {
            const cijena = document.getElementById('cijena').value;
            const kolicina = document.getElementById('kolicina').value;
            
            if (parseFloat(cijena) <= 0) {
                alert('Cijena mora biti veća od 0!');
                return false;
            }
            
            if (parseFloat(kolicina) < 0) {
                alert('Količina ne može biti negativna!');
                return false;
            }
            
            return true;
        }
        
        // Očisti poruke nakon 5 sekundi
        setTimeout(function() {
            const porukaDiv = document.querySelector('[style*="color: green"]');
            const greskaDiv = document.querySelector('[style*="color: red"]');
            
            if (porukaDiv) porukaDiv.style.display = 'none';
            if (greskaDiv) greskaDiv.style.display = 'none';
        }, 5000);
    </script>
</body>


<a href="${pageContext.request.contextPath}/index.html">
                    
                    
                    Početna stranica
                </a>
</html>