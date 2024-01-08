package Servlet;

import java.io.*;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "GestioneRichiesteServlet", urlPatterns = { "/listaRecord" })
public class listaRecord extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession();
            String permesi = (String) session.getAttribute("permissions");

            if (permesi.equals("writing") || permesi.equals("administrator")) {
            	FileJava.ReadDB readDB = new FileJava.ReadDB();
            	String nomeTabella = request.getParameter("nomeTabella");
                out.println(nomeTabella);
                ArrayList<FileJava.Persona> persone = readDB.readData(nomeTabella);

                session.setAttribute("persone", persone);
                getServletContext().setAttribute("persone", persone);

                // String test =(String) session.getAttribute("permissions");
                // out.println("ciaoooooo "+test);

                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"en\">");
                out.println("<head>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<title>Elenco Persone</title>");
                out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/homecss.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<form action='NewRecord.html' method='get'>");
                out.println("<input type='submit' value='Nuova Persona' />");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");

                out.println("<table border='1'>");
                out.println("<tr>");
                out.println("<th>Id</th>");
                out.println("<th>Nome</th>");
                out.println("<th>Cognome</th>");
                out.println("<th>Azioni</th>");
                out.println("</tr>");

                for (FileJava.Persona persona : persone) {
                    out.println("<tr>");
                    out.println("<td>" + persona.getId() + "</td>");
                    out.println("<td>" + persona.getNome() + "</td>");
                    out.println("<td>" + persona.getCognome() + "</td>");
                    out.println("<td>");

                    out.println(
                            "<form action='" + request.getContextPath() + "/DettaglioPersonaServlet' method='GET'>");
                    out.println("<input type='hidden' name='id' value='" + persona.getId() + "'/>");
                    out.println("<input type='submit' value='Dettagli'/>");
                    out.println("</form>");

                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
                out.println("</body></html>");
            } else {
                response.getWriter().write("Non hai il permeso.");
            }
        } catch (Exception e) {
            out.println("Errore durante la lettura dal database: " + e.getMessage());
        } finally {
            out.close();
        }
    }
}
