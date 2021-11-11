package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Iterator;

import javax.swing.JOptionPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author moritz siems
 * @version 14.06.2021
 *
 */
public class Datenstruktur implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Deklaration: LinkedList data -> Liste mit der gearbeitet wird. Typsicherheit
	 * wird durch das Generic "Customer" gesichert variable datei: Dateiname
	 * 
	 */
	public static LinkedList<Customer> data;
	String datei = "data";
	

	/**
	 * Konstruktor loadList(): lädt die Liste aus dem Speicher new File(): Es wird
	 * eine neue File erstellt und der Klassenvariablen zugewiesen
	 */
	public Datenstruktur() {
		Datenstruktur.data = (LinkedList<Customer>) loadList(datei);
		
	}

	/**
	 * 
	 * @param String vornameC, String nachnameC, String telNrC
	 * @return Customer []
	 * 
	 *         Die Methode durchsucht die gesamte Liste nach Customer deren
	 *         Attribute mit den übergebenen Parametern übereinstimmen und gibt ein
	 *         Array zurück
	 * 
	 *         Mit dem Iterator kann über die Liste iteriert werden, solange
	 *         "it.hasNext()" = true ist, läuft der Iterator durch
	 * 
	 *         Wenn der übergebene Name mit einem Namen in der Liste übereinstimmt,
	 *         wird das Objekt zurückgegeben
	 * 
	 *         Ist die Liste leer, wird null zurückgegeben
	 */
	public static Customer[] getCustomer(String vornameC, String nachnameC, String telNrC) {
		Customer[] customer = new Customer[5];
		int i = 0;
		Iterator<Customer> it = Datenstruktur.data.iterator();
		while (it.hasNext()) {
			Customer c = it.next();
			if (c.getVorname().equals(vornameC) && c.getNachname().equals(nachnameC) && c.getNummer().equals(telNrC)) {
				customer[i] = c;

				i = i + 1;
			}
		}
		return customer;

	}

	/**
	 * 
	 * @param vorname
	 * @param nachname
	 * @param telNr
	 * @return LinkedList
	 * 
	 *         Die Methode nimmt die Customer aus der getCustomer() Methode und
	 *         vergleicht jeweils das Datum und jeden Kombination der Ankunfts- und
	 *         Abgangszeiten
	 * 
	 */
	public static LinkedList<Customer> getKontaktpersonen(String vorname, String nachname, String telNr) {

		String uhrzeit = "HH:mm";
		String datum = "dd.MM.yyyy";
		SimpleDateFormat time = new SimpleDateFormat(uhrzeit);
		SimpleDateFormat date = new SimpleDateFormat(datum);

		Date submissionBegin = null;
		Date submissionEnd = null;
		Date submissionDate = null;

		Customer[] positivCustomer = getCustomer(vorname, nachname, telNr);

	
		LinkedList<Customer> liste = new LinkedList<Customer>();

		for (int i = 0; i < positivCustomer.length; i++) { // Die for Schleife stellt sicher, dass ein Kunde auch an
															// verschieden Tagen
															// gekommen, und Menschen infiziert haben kann
			if (positivCustomer[i] != null) {
				Date coronaPositivBegin;
				Date coronaPositivEnd;
				Date coronaPositivDate;

				try {
					coronaPositivBegin = time.parse(positivCustomer[i].getBegin()); // In einem Date Objekt wird die
																					// Ankunftszeit des infizierten
																					// gespeichert
					coronaPositivEnd = time.parse(positivCustomer[i].getEnd()); // In einem Date Objekt wird der
																				// Zeitpunkt des
																				// Verlassens des infizierten
																				// gespeichert
					coronaPositivDate = date.parse(positivCustomer[i].getDate());

					Iterator<Customer> it = Datenstruktur.data.iterator();
					
					while (it.hasNext()) {
						
						Customer c = it.next();
						
						if(positivCustomer[i].getNummer() == c.getNummer()) {
							if(it.hasNext()) {
								c = it.next();
							} else {
								return liste;
							}
						}

						try {
							submissionBegin = time.parse(c.getBegin());
							submissionEnd = time.parse(c.getEnd());
							submissionDate = date.parse(c.getDate());
						} catch (ParseException e) {
							e.printStackTrace();
						}

						if (submissionDate.equals(coronaPositivDate)) {

							if (coronaPositivBegin.equals(submissionBegin) && coronaPositivEnd.equals(submissionEnd)) {
								liste.add(c);
							} else if (submissionBegin.before(coronaPositivBegin)
									&& submissionEnd.before(coronaPositivEnd)
									&& submissionEnd.after(coronaPositivBegin)) {
								liste.add(c);
							} else if (submissionBegin.before(coronaPositivBegin)
									&& submissionEnd.after(coronaPositivEnd)) {
								liste.add(c);
							} else if (coronaPositivBegin.before(submissionBegin)
									&& coronaPositivEnd.after(submissionEnd)) {
								liste.add(c);
							} else if (coronaPositivBegin.before(submissionBegin)
									&& submissionEnd.after(coronaPositivEnd)
									&& submissionBegin.before(coronaPositivEnd)) {
								liste.add(c);
							} else if (coronaPositivBegin.before(submissionBegin)
									&& coronaPositivEnd.equals(submissionEnd)) {
								liste.add(c);
							} else if (coronaPositivBegin.after(submissionBegin)
									&& coronaPositivEnd.equals(submissionEnd)) {
								liste.add(c);
							} else if (coronaPositivBegin.equals(submissionBegin)
									&& coronaPositivEnd.before(submissionEnd)) {
								liste.add(c);
							} else if (coronaPositivBegin.equals(submissionBegin)
									&& coronaPositivEnd.after(submissionEnd)) {
								liste.add(c);
							}
						}
					}

				} catch (ParseException e1) {
					e1.printStackTrace();
				}

			}
			
		}
		
		return (LinkedList<Customer>) liste; // Eine Liste mit allen Kontaktpersonen wird zurückgegeben

	}

	/**
	 * 
	 * @param String dateiName
	 * @return boolean: true, wenn das File bereits existiert; false, sonst
	 * 
	 */
	public boolean checkData(String dateiName) {
		File f = new File(dateiName + ".dat");
		return (f.exists());
	}

	/**
	 * Diese Methode speichert die Liste ab
	 * 
	 * @param LinkedList data
	 * @param String     dateiName
	 * 
	 *                   Die Methode bekommt die zu speichernde Liste und den
	 *                   zugehörigen Datein Namen übergeben
	 * 
	 *                   In der Methode wird ein FileOutputstream erzeugt, welcher
	 *                   den Dateinname übergeben bekommt mit Hilfe diesem können
	 *                   die Daten in ein File geschrieben werden Der neu erzeugte
	 *                   ObjectOutputstream bekommt nun den Fileoutputstream als
	 *                   Parameter übergeben, dadurch wird das persistente Speichern
	 *                   der Informationen möglich
	 * 
	 *                   über *writeObject()* kann nun die Liste in das File
	 *                   geschrieben werden die Methode *flush()* stellt sicher das
	 *                   alles in die Datei geschrieben wurde, bevor der Stream mit
	 *                   *close()* beendet wird
	 * 
	 * @exception IOException: wird abgefangen
	 */
	public static void saveList(LinkedList<Customer> data, String dateiName) {
		try {
			FileOutputStream fos = new FileOutputStream(dateiName + ".dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(data);
			oos.flush();
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Diese Methode soll die Arbeits Liste aus dem Speicher laden
	 * 
	 * @param String dateiName
	 * @return LinkedList
	 * 
	 *         Diese Methode lädt die Liste mit Hilfe der InputStreams aus dem
	 *         Speicher *readObject()*: liest die Informationen der Liste über den
	 *         ObjectInputStream aus dem Speicher
	 * 
	 * @exception IOException:
	 * @exception ClassNotFoundException
	 * 
	 *                                   mit der Methode *checkData()* wird
	 *                                   überprüft, ob die Datei bereits existiert,
	 *                                   falls nicht wird eine neue LinkedList
	 *                                   erstellt, gespeichert und zurück gegeben
	 * 
	 */
	public LinkedList<Customer> loadList(String dateiName) {
		// setze leere Liste auf Null
		LinkedList<Customer> data = null;

		if (checkData(dateiName)) {
			try {
				FileInputStream fis = new FileInputStream(dateiName + ".dat");
				ObjectInputStream ois = new ObjectInputStream(fis);
				data = (LinkedList<Customer>) ois.readObject();
				ois.close();
				fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("Class not found");
				c.printStackTrace();
			}
			return data;
		} else {
			LinkedList<Customer> other = new LinkedList<Customer>();
			saveList(other, dateiName);
			return other;
		}
	}

}
