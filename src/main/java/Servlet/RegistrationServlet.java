package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "RegistrationServlet", urlPatterns = { "/RegistrationServlet" })
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		 String azione = "simple";
		 String gmail = request.getParameter("email");
         String password1 = request.getParameter("password");
         String password2 = request.getParameter("verifyPassword");
         System.out.println("Email: " + gmail);
         System.out.println("Password: " + password1);
         System.out.println("Passwor2: " + password2);

         if(password1.equals(password2)){
        	 System.out.println("si");
        	 FileJava.Registrazione.registra(azione,gmail,password1);
        	 response.getWriter().write("La registrazione ha avuto succeso.");
         }else {
        	 response.sendRedirect("/GestionaleBDX/Home.html");
         }
         
         
	}

}
