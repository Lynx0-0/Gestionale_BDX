package FileJava;
public class Funzionalita {
    private int id;
    private String nome;
    private String descrizione;

    public Funzionalita(int id, String nome, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}

    
