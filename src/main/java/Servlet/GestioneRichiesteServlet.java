package Servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "GestioneRichiesteServlet", urlPatterns = { "/GestioneRichiesteServlet" })
public class GestioneRichiesteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
       
    	String azione = request.getParameter("azione");
        String nomeFile = request.getParameter("nomeFile");
        String nomeTabella = request.getParameter("nomeTabella");
        System.out.println(azione);
        String[] args = new String[]{azione, nomeFile, nomeTabella};
        
        FileJava.Main.main(args);
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Azione eseguita correttamente.");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
       
        String azione = request.getParameter("azione");
        String nomeFile = request.getParameter("nomeFile");
        String nomeTabella = request.getParameter("nomeTabella");
        
     
        
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        
        response.getWriter().write("GET request handled successfully.");
    }
}
