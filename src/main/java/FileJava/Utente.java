package FileJava;

import java.util.ArrayList;
import java.util.List;

public class Utente {
    private String email;
    private List<Funzionalita> funzionalita;

    // Constructor for creating an empty list of functionalities
    public Utente(String email) {
        this.email = email;
        this.funzionalita = new ArrayList<>();
    }
    
    // Constructor for creating a user with a predefined list of functionalities
    public Utente(String email, List<Funzionalita> funzionalita) {
        this.email = email;
        // Create a new list from the provided list to avoid aliasing issues
        this.funzionalita = new ArrayList<>(funzionalita);
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Add a functionality to the user
    public void addFunzionalita(Funzionalita funzione) {
        funzionalita.add(funzione);
    }

    // Get the list of functionalities
    public List<Funzionalita> getFunzionalita() {
        return funzionalita;
    }
}
