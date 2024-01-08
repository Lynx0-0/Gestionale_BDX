package Servlet;

import java.io.*;
import java.util.*;

import FileJava.Funzionalita;
import FileJava.GestioneUtenti;
import FileJava.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ListaAccountSevlet", urlPatterns = { "/ListaAccountSevlet" })
public class ListaAccountSevlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        GestioneUtenti gestioneUtenti = new GestioneUtenti();

        try (PrintWriter out = response.getWriter()) {
           
            List<Utente> listaUtenti = gestioneUtenti.getAllUsersWithTheirFunctionalities();
            List<Funzionalita> funzionalitaDisponibili = GestioneUtenti.getFunzionalitaDisponibili();

            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Elenco Persone</title>");
            out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/homecss.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Lista Utenti e Funzionalità</h2>");

            for (Utente utente : listaUtenti) {
                out.println("<p>" + utente.getEmail() + ": ");

                out.println("<div style='display: flex; align-items: center;'>");

                for (Funzionalita funz : utente.getFunzionalita()) {
                    out.println("<div style='margin-right: 5px;'>"); 
                    out.println(funz.getNome());
                    out.println("<form action='" + request.getContextPath() + "/AggiornamentoUtentiFunzionalitaServlet' method='POST' style='display: inline;'>");
                    out.println("<input type='hidden' name='azione' value='rimuovi' />");
                    out.println("<input type='hidden' name='email' value='" + utente.getEmail() + "' />");
                    out.println("<button type='submit' name='rimuoviFunzionalita' value='" + funz.getId() + "'>X</button>");
                    out.println("</form>");
                    out.println("</div>");
                }

                out.println("<form action='" + request.getContextPath() + "/AggiornamentoUtentiFunzionalitaServlet' method='POST'>");
                out.println("<input type='hidden' name='azione' value='aggiungi' />");
                out.println("<input type='hidden' name='email' value='" + utente.getEmail() + "' />");
                out.println("<select name='nuovaFunzionalita'>");
                for (Funzionalita funz : funzionalitaDisponibili) {
                    out.println("<option value='" + funz.getId() + "'>" + funz.getNome() + "</option>");
                }
                out.println("</select>");
                out.println("<input type='submit' value='Aggiungi Funzionalità' />");
                out.println("</form>");
                out.println("</p>");
                
                
                out.println("</div>"); 
                out.println("</p>");
            
            }

            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Si è verificato un errore");
        }
    }
}