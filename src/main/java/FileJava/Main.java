package FileJava;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
	private static String azione;

	public static void main(String[] args) {
		/*
		 * args[0] = "carica";
		 * args[1] = "TEMP_RICCO_PF.xml";
		 * args[2] = "TEMP_RICCO_PF";
		 */

		
		if (args.length != 3) {
			System.out.println("Numero di argomenti non valido.");
			System.out.println("Uso corretto: <azione> <nome_file> <nome_tabella>");
			System.out.println(
					"azione: 'carica' per caricamento o 'salva' per salvataggio o 'allinea' per alineare la tabella TEMP_RICCO_PF alla tabella ALL_TEMP_RICCO_PF");
			System.out.println("nome_file: Percorso del file da caricare/salvare");
			System.out.println("nome_tabella: Nome della tabella da cui caricare/salvare i dati");
			return;
		}

		azione = args[0];
		String nomeFile = args[1];
		String nomeTabella = args[2];
		System.out.println(args[1]);
		System.out.println(args[2]);
		// Valida l'azione
		if (!azione.equalsIgnoreCase("carica") && !azione.equalsIgnoreCase("salva")
				&& !azione.equalsIgnoreCase("allinea")) {
			System.out.println("Azione non valida. Sono accettate solo le azioni 'carica' o 'salva'.");
			return;
		}

		
		switch (azione) {
			case "carica":
				caricaDati(nomeFile, nomeTabella);
				break;
			case "salva":
				salvaDati(nomeFile, nomeTabella);
				break;
			case "allinea":
				try {
					Allineamento.allineaTabelle(nomeFile,nomeTabella);
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
				break;
			default:
				
				System.out.println("Azione non riconosciuta.");
				break;
		}
	}

	private static void caricaDati(String nomeFile, String nomeTabella) {
		try {
			System.out.println("Caricamento dati dal file: " + nomeFile);
			ArrayList<Persona> personeDaCaricare = ReadXml.LetturaXml(nomeFile);
			TransferInDB.insertListOfPeople(personeDaCaricare, nomeTabella);
		} catch (Exception e) {
			System.out.println("Errore durante il caricamento dei dati: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void salvaDati(String nomeFile, String nomeTabella) {
		try {
			System.out.println("Salvataggio dati su file: " + nomeFile);
			ReadDB readDB = new ReadDB();
			ArrayList<Persona> personeDaSalvare = readDB.readData(nomeTabella);
			CreateCSV.writeCSV(personeDaSalvare, nomeFile);
		} catch (Exception e) {
			System.out.println("Errore durante il salvataggio dei dati: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static String getAzione() {
		return azione;
	}

}
