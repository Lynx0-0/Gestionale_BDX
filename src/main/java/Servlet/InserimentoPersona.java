package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import FileJava.Persona;
import FileJava.TransferInDB;

@WebServlet(name = "InserimentoPersona", urlPatterns = { "/InserimentoPersona" })
public class InserimentoPersona extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(InserimentoPersona.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
    	
    	HttpSession session = request.getSession();
    	String permesi =(String) session.getAttribute("permissions");
    	
    	if (permesi.equals("writing")||permesi.equals("administrator")) {

    	ArrayList<Persona> persone = (ArrayList<Persona>) session.getAttribute("persone");
    	int nmaggiore = 0;

    	for (Persona persona : persone) {
    	    try {
    	        int idCorrente = Integer.parseInt(persona.getId());
    	        if (idCorrente > nmaggiore) {
    	            nmaggiore = idCorrente;
    	        }
    	    } catch (NumberFormatException e) {
    	        logger.log(Level.WARNING, "Formato ID non valido per la persona: " + persona.getId(), e);
    	    }
    	}

        try {
            Persona persona = new Persona();
            String nomeTabella = request.getParameter("nomeTabella");

            
            String cognome = request.getParameter("PF_COGNOME");
            String nome = request.getParameter("PF_NOME");
            String codiceFiscale = request.getParameter("PF_CDFISC");
            String comuneCodice = request.getParameter("PF_CDCOMU");
            String dataNascitaStr = request.getParameter("PF_DTNASC");

            if (cognome == null || nome == null || codiceFiscale == null || comuneCodice == null || dataNascitaStr == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dati mancanti o non validi");
                return;
            }

            String idAsString = String.valueOf(nmaggiore+1);
            persona.setId(idAsString);
            persona.setCognome(cognome);
            persona.setNome(nome);
            persona.setCodiceFiscale(codiceFiscale);
            persona.setComuneCodice(comuneCodice);
            persona.setDataNascita(dataNascitaStr);

            String sesso = request.getParameter("PF_FSESSO");
            if (sesso != null && !sesso.isEmpty()) {
                persona.setSesso(sesso.charAt(0));
            }

            persona.setDataInserimento(LocalDate.now().toString());
            ArrayList<Persona> listaPersone = new ArrayList<>();
            listaPersone.add(persona);

            TransferInDB.insertListOfPeople(listaPersone, nomeTabella);

            response.setContentType("text/html");
            response.getWriter().write("<p>Azione eseguita correttamente.</p>");
             
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore generico durante l'inserimento della persona", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore generico durante l'elaborazione della richiesta");
        }
    	}else {
    		response.getWriter().write("Non hai il permeso.");
    	}
    }
}
