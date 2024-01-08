package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import FileJava.Login;

@WebServlet(name = "LoginServlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            String userPermissions = Login.getPermissions(email, password);

            if (userPermissions != null) {
                HttpSession session = request.getSession();
                session.setAttribute("permissions", userPermissions);
                session.setAttribute("email", email);

                if(userPermissions.equals("administrator")) {
                    response.sendRedirect("Home.html"); // Aggiornato per utilizzare un percorso relativo
                } else {
                    response.sendRedirect("ListaRecord.html"); // Aggiornato per utilizzare un percorso relativo
                }
            } else {
                request.setAttribute("loginError", "Credenziali non valide.");
                request.getRequestDispatcher("loginPage.jsp").forward(request, response);
                // sostituisci "loginPage.jsp" con il percorso effettivo della tua pagina di login JSP o HTML
            }
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Si è verificato un errore durante l'elaborazione della tua richiesta.");
        }
    }
}