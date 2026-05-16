<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="novoselac.model.Usluga"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Uređivanje Usluge</title>
</head>
<body>
    <% 
        // Dohvati objekt Usluga koji je Controller postavio
        Usluga usluga = (Usluga) request.getAttribute("uslugaZaUredivanje");
        
        // Osiguravamo se da objekt nije null, iako bi Controller to trebao garantirati
        if (usluga != null) { 
    %>
    
    <h1>Uređivanje Usluge: <%= usluga.getNaziv() %></h1>
    
    <form action="UrediUsluguIzTablice" method="POST">
        <table>
            <tr> 
                <td>Šifra:</td>    
                <td> <input type='text' name='sifra' id='sifra' value='<%= usluga.getSifra() %>' readonly/> </td> 
            </tr>
            <tr> 
                <td>Naziv:</td>    
                <td> <input type='text' name='naziv' id='naziv' value='<%= usluga.getNaziv() %>'/> </td> 
            </tr>
            <tr> 
                <td>Cijena:</td>    
                <td> <input type='text' name='cijena' id='cijena' value='<%= usluga.getCijena() %>'/> </td> 
            </tr>
            <tr> 
                <td>Jedinica Mjere:</td>    
                <td> <input type='text' name='jedinicaMjere' id='jedinicaMjere' value='<%= usluga.getJedinicaMjere() %>'/> </td> 
            </tr>
            <tr> 
                <td>Količina:</td>    
                <td> <input type='text' name='kolicina' id='kolicina' value='<%= usluga.getKolicina() %>'/> </td> 
            </tr>
            <tr>  
                <td colspan='2'> <input type='submit' value='Uredi'/> </td> 
            </tr>
        </table>
    </form>
    
    <% } else { %>
        <p>Došlo je do pogreške ili usluga nije pronađena.</p>
        <p><%= request.getAttribute("poruka") != null ? request.getAttribute("poruka") : "" %></p>
    <% } %>
</body>
</html>