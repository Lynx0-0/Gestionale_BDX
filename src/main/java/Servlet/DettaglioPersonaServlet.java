package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "DettaglioPersonaServlet", urlPatterns = { "/dettaglioPersona" })
public class DettaglioPersonaServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");

        HttpSession session = request.getSession();
        ArrayList<FileJava.Persona> persone = (ArrayList<FileJava.Persona>) session.getAttribute("persone");
        // ArrayList<Persona> persone = (ArrayList<Persona>) getServletContext().getAttribute("persone");

        FileJava.Persona personaSelezionata = persone.stream()
            .filter(persona -> id.equals(persona.getId()))
            .findFirst()
            .orElse(null);

        if (personaSelezionata != null) {
            out.println("<html><head><title>Dettaglio Persona</title></head><body>");
            out.println("<h1>Dettaglio Persona</h1>");
            out.println("<p>ID: " + personaSelezionata.getId() + "</p>");
            out.println("<p>Nome: " + personaSelezionata.getNome() + "</p>");
            out.println("<p>Cognome: " + personaSelezionata.getCognome() + "</p>");
            out.println("</body></html>");
        } else {
            out.println("<html><head><title>Errore</title></head><body>");
            out.println("<h1>Persona non trovata!</h1>");
            out.println("</body></html>");
        }
    }
}
