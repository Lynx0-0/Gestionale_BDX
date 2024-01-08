package FileJava;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CreateCSV {

    private static final String CSV_SEPARATOR = ",";
    private static final String DIRECTORY_PATH = "C:\\Users\\tudor\\eclipse-workspace\\GestionaleBDX\\General File\\";

    public static void writeCSV(ArrayList<Persona> persone, String nomeFile) {
        String fullPath = DIRECTORY_PATH + nomeFile;
        try (PrintWriter pw = new PrintWriter(new FileWriter(fullPath))) {
            pw.println("ID,COGNOME,NOME,CODICE FISCALE,COMUNE CODICE,DATA NASCITA,SESSO,DATA INSERIMENTO");

            for (Persona persona : persone) {
                String line = String.join(CSV_SEPARATOR,
                        persona.getId(),
                        persona.getCognome(),
                        persona.getNome(),
                        persona.getCodiceFiscale(),
                        persona.getComuneCodice(),
                        persona.getDataNascita(),
                        String.valueOf(persona.getSesso()),
                        persona.getDataInserimento());
                pw.println(line);
            }

            System.out.println("CSV file creato con successo: " + fullPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Si è verificato un errore nella scrittura del file CSV.");
        }
    }
}
