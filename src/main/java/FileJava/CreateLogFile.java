package FileJava;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateLogFile {
    // Creazione di un file vuoto

    public static void newFile() {
        String path = "C:/Users/tudor/eclipse-workspace/Grafica/src/main/log.txt";
        try {
            File file = new File(path);
            if (file.exists())
                System.out.println("Il file " + path + " esiste");
            else if (file.createNewFile())
                System.out.println("Il file " + path + " è stato creato");
            else
                System.out.println("Il file " + path + " non può essere creato");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Scrittura di un file con BufferWriter
    public static void writeFile(String tipologia,int nRecordCaricati, String nome, String cognome, String status, String cFiscale) {
        String path = "C:/Users/tudor/eclipse-workspace/Grafica/src/main/log.txt";

        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {

        	if(tipologia=="carico") {
              	 bw.write("Numero di record caricati: " + nRecordCaricati);
              }
              else if(tipologia=="allinea"){
              	bw.write("Numero di record eliminati: " + nRecordCaricati);

              }
            bw.newLine(); // Vai a nuova riga
            bw.write("Cognome: " + cognome); // Cognome su nuova riga
            bw.newLine(); // Vai a nuova riga
            bw.write("Nome: " + nome); // Nome su nuova riga
            bw.newLine(); // Aggiungi una nuova riga alla fine (opzionale)
            bw.write("Codice Fiscale: " + cFiscale); // Codice Fiscale su nuova riga
            bw.newLine(); // Aggiungi una nuova riga alla fine (opzionale)
            bw.write("Status: " + status); // Status su nuova riga
            bw.newLine(); // Aggiungi una nuova riga alla fine (opzionale)
            // Non è necessario chiamare bw.flush() e bw.close() qui, sarà fatto
            // automaticamente
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void writeLine(String line) {
    	String path = "C:/Users/tudor/eclipse-workspace/Grafica/src/main/log.txt";
    	
    	
    	 try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
    		 bw.newLine();
             bw.write("Errore: " + line); 
             bw.newLine();
    	 } catch (IOException e) {
             e.printStackTrace();
         }
    	 
    }

}
