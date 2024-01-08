package FileJava;


public class Persona {
    private String id;
    private String cognome;
    private String nome;
    private String codiceFiscale;
    private String comuneCodice;
    private String dataNascita;
    private char sesso;
    private String dataInserimento;
    public char[] getCognome;

    
    public Persona() {
    }

    
    public Persona(String id, String cognome, String nome, String codiceFiscale, String comuneCodice,
            String dataNascita, char sesso, String dataInserimento) {
        this.id = id;
        this.cognome = cognome;
        this.nome = nome;
        this.codiceFiscale = codiceFiscale;
        this.comuneCodice = comuneCodice;
        this.dataNascita = dataNascita;
        this.sesso = sesso;
        this.dataInserimento = dataInserimento;
    }

    public String getId() {
        return id;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getComuneCodice() {
        return comuneCodice;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public char getSesso() {
        return sesso;
    }

    public String getDataInserimento() {
        return dataInserimento;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setComuneCodice(String comuneCodice) {
        this.comuneCodice = comuneCodice;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public void setSesso(char sesso) {
        this.sesso = sesso;
    }

    public void setDataInserimento(String dataInserimento) {
        this.dataInserimento = dataInserimento;
    }
}
