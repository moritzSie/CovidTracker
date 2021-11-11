package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import Model.Customer;
import Model.Datenstruktur;
import View.CovidTrackerView;

/**
 * 
 * @author moritz
 * @version 14.06.2021
 *
 */
public class CovidTrackerController {

	/**
	 * Klassen Variablen:
	 */
	private static final long serialVersionUID = 1L;
	private CovidTrackerView view; // Referenz auf die graphische Oberfläche

	/**
	 * Mit dieser Methode wird die Referenz auf die GUI gesetzt
	 * 
	 * @param view
	 */
	public void setView(CovidTrackerView view) {
		this.view = view;
	}

	/**
	 * Die Folgenden Methoden liefern jeweils eine neu erzeugte Instanz der
	 * verschiedenen ButtonListener Klassen
	 * 
	 * @return ButtonListener Objekte
	 */
	public ButtonListener_exit getButtonListenerExit() {
		return new ButtonListener_exit();
	}

	public ButtonListener_search getButtonListenerSearch() {
		return new ButtonListener_search();
	}

	public ButtonListener_safe getButtonListenerSafe() {
		return new ButtonListener_safe();
	}
	public ButtonListener_showList getButtonListenerShowList() {
		return new ButtonListener_showList();
	}

	/**
	 * 
	 * Diese Klassen implementieren das ActionListener Interface
	 *
	 */
	public class ButtonListener_exit implements ActionListener {
		/**
		 * in der *actionPerformed()* Methode wird die Methode *System.exit()* mit dem
		 * Parameter 0 aufgerufen Diese bewirkt das sofortige schließen der Klasse
		 */
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	public class ButtonListener_showList implements ActionListener {
		/**
		 * Die Klasse soll die gesamte Kunden Liste auf der GUi anzeigen
		 */
		public void actionPerformed(ActionEvent e) {
			view.setTextAreaList(Datenstruktur.data);
		}
	}

	public class ButtonListener_search implements ActionListener {
		/**
		 * in der *actionPerformed()* werden Parameter aus der GUI ausgelesen und über
		 * die Methode getKontaktPersonen alle Kontaktpersonen aus der LinkesList
		 * herausgelesen. Über If-Bedingungen wird versucht ungültige Eingaben
		 * abzufangen
		 */

		public void actionPerformed(ActionEvent e) {
			LinkedList<Customer> kontaktPersonen;

			if (view.getBestaetigung()) {

				String vorname = view.getVornametxtS();
				String nachname = view.getNachnametxtS();
				String telNr = view.getTelNrtxtS();

				kontaktPersonen = new LinkedList<Customer>();

				kontaktPersonen = Datenstruktur.getKontaktpersonen(vorname, nachname, telNr);
				if (kontaktPersonen != null) {
					view.clearTextArea();
					view.setTextArea(kontaktPersonen);

				} 
				if(kontaktPersonen.isEmpty()) {
					JOptionPane.showMessageDialog(view, "Es wurden keine Kontaktpersonen gefunden", " ",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(view, "Bitte bestätigen Sie den Verdacht einer Corona Infektion", " ",
						JOptionPane.INFORMATION_MESSAGE);

			}

		}

	}

	public class ButtonListener_safe implements ActionListener {
		/**
		 * in der *actionPerformed()* Methode werden die eingegebenen Parameter
		 * ausgelesen und als neuer Customer in der LinkedList gespeichert. Die Liste
		 * wird anschließend persistent gespeichert
		 * 
		 * Über If-Bedingungen wird versucht ungültige Eingaben abzufangen
		 */
		public void actionPerformed(ActionEvent e) {

			String vorname = view.getVornametxt().getText();
			String nachname = view.getNachnametxt().getText();
			String telNr = view.getTelNrtxt().getText();
			String begin = view.getBeginxt().getText();
			String end = view.getEndtxt().getText();
			String datum = view.getDatumtxt().getText();

			view.getVornametxt().setText("");
			view.getNachnametxt().setText("");
			view.getTelNrtxt().setText("");
			view.getBeginxt().setText("");
			view.getEndtxt().setText("");
			view.getDatumtxt().setText("");

			String uhrzeit = "HH:mm";
			String datum2 = "dd.MM.yyyy";
			SimpleDateFormat time = new SimpleDateFormat(uhrzeit);
			SimpleDateFormat date = new SimpleDateFormat(datum2);

			try {
				// Abfangen ungültiger Zeit Formate
				Date checkTimeBegin = time.parse(begin);
				Date checkTimeEnd = time.parse(end);
				Date checkDate = date.parse(datum);
				// Abfangen ungültiger Strings
				if (checkEingabe(vorname, nachname, telNr)) {
					JOptionPane.showMessageDialog(view, "Bitte geben Sie die personenbezogenen Daten korrekt an", " ",
							JOptionPane.ERROR_MESSAGE);
					return; // Abbruch, wenn die if-Bedingung erfüllt ist
				}
				// Abfangen ungültiger Zeitangaben
				if (checkTimeEnd.before(checkTimeBegin)) {
					JOptionPane.showMessageDialog(view, "Bitte geben Sie die Uhrzeiten korrekt an", " ",
							JOptionPane.ERROR_MESSAGE);
					return; // Abbruch, wenn die if-Bedingung erfüllt ist
				}
				// Abfangen leerer Angaben
				if (vorname.equals("") || nachname.equals("") || telNr.equals("")) {
					JOptionPane.showMessageDialog(view, "Bitte geben Sie einen gültigen Namen ein", " ",
							JOptionPane.ERROR_MESSAGE);
					return; // Abbruch, wenn die if-Bedingung erfüllt ist
				}
				// Abfangen leerer Angaben
				if (checkNummer(telNr, vorname, nachname)) {
					JOptionPane.showMessageDialog(view, "Die Telefonnummer existiert bereits", " ",
							JOptionPane.ERROR_MESSAGE);
					return; // Abbruch, wenn die if-Bedingung erfüllt ist
				}

			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(view, "Bitte geben Sie das Datum und die Uhrzeit im korrekten Format an",
						" ", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (view.getDatenschutz()) { // Überprüft, ob der Datenschutz bestätigt wurde

				Customer customer = new Customer(vorname, nachname, telNr, begin, end, datum); // Ein neues Customer
																								// wird erstellt
				Datenstruktur.data.add(customer); // Objekt wird hinzugefügt
				Datenstruktur.saveList(Datenstruktur.data, "data"); // DIe Liste wird gespeichert
				JOptionPane.showMessageDialog(view, "Der Kunde wurde gespeichert !", " ",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(view, "Bitte stimmen Sie der vearbeitung Ihrer Daten zu", " ",
						JOptionPane.INFORMATION_MESSAGE);

			}
		}
	}
	/**
	 * 
	 * @param nummer
	 * @param vorname
	 * @param nachname
	 * @return true, wenn die Telefonnummer berreits einem anderen Gast zugeordnet wurde
	 */
	public boolean checkNummer(String nummer, String vorname, String nachname) {
		Iterator<Customer> it = Datenstruktur.data.iterator();
		while (it.hasNext()) {
			Customer c = it.next();
			if (c.getNummer().equals(nummer) && !c.getVorname().equals(vorname) && !c.getNachname().equals(nachname)) {
				System.out.println(!c.getVorname().equals(vorname));
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param name
	 * @param nachname
	 * @param telNr
	 * @return boolean
	 * 
	 *         Diese Methode überprüft mit Hilfe von Regular Expressions, ob die
	 *         Struktur der jeweilgen Strings stimmt
	 */
	public static boolean checkEingabe(String name, String nachname, String telNr) {
		String regex1 = "([a-zA-Z])*"; // Jeder Kombinantion an groß und klein Buchstaben erfüllt dieses Pattern
		String regex2 = "([0-9])*"; // Jeder Zahl von 0 bis 9 erfüllt dieses Pattern

		Pattern buchstaben = Pattern.compile(regex1);
		Pattern zahlen = Pattern.compile(regex2);

		Matcher mt1 = buchstaben.matcher(name); // Hier wird überprüft, ob der übergebene Parameter mit dem Pattern
												// übereinstimmt
		Matcher mt2 = buchstaben.matcher(nachname);
		Matcher mt3 = zahlen.matcher(telNr);

		boolean result1 = mt1.matches();
		boolean result2 = mt2.matches();
		boolean result3 = mt3.matches();

		if (result1 && result2 && result3) {
			return false;
		}

		return true;
	}
}
