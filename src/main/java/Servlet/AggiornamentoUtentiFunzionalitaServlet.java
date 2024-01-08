package Servlet;

import java.io.IOException;

import FileJava.GestioneUtenti;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AggiornamentoUtentiFunzionalitaServlet", urlPatterns = {"/AggiornamentoUtentiFunzionalitaServlet"})
public class AggiornamentoUtentiFunzionalitaServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("email");
        String nuovaFunzionalitaId = request.getParameter("nuovaFunzionalita");
        String rimuoviFunzionalitaId = request.getParameter("rimuoviFunzionalita");

        GestioneUtenti gestioneUtenti = new GestioneUtenti();

        boolean success = false;
        if (nuovaFunzionalitaId != null && !nuovaFunzionalitaId.trim().isEmpty()) {
            success = gestioneUtenti.aggiungiFunzionalitaUtente(userEmail, nuovaFunzionalitaId);
        } else if (rimuoviFunzionalitaId != null && !rimuoviFunzionalitaId.trim().isEmpty()) {
            success = gestioneUtenti.rimuoviFunzionalitaDaUtente(userEmail, rimuoviFunzionalitaId);
        }

        if (success) {
            request.getSession().setAttribute("successMessage", "Modifica effettuata con successo.");
        } else {
            request.getSession().setAttribute("errorMessage", "Errore nella modifica delle funzionalità.");
        }

        response.sendRedirect(request.getContextPath() + "/ListaAccountSevlet");
    }
}
