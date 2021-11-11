package Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author moritz siems
 * @version 14.06.2021
 * 
 *          In dieser Klasse werden die Attribute eines Customers festgelegt
 *
 */
public class Customer implements Serializable {

	/**
	 * relevante Eigenschaften eines Kundens
	 */
	private static final long serialVersionUID = 1L;
	private String vorname;
	private String nachname;
	private String nummer;

	private String begin;
	private String end;
	private String datum;

	String pattern = "dd.MM.yyyy HH:mm";

	/**
	 * 
	 * @param vorname
	 * @param nachname
	 * @param nummer
	 * @param begin
	 * @param end
	 * @param datum
	 * 
	 *                 Im Konstruktor werden die Eigenschaften zugewiesen
	 */
	public Customer(String vorname, String nachname, String nummer, String begin, String end, String datum) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.nummer = nummer;
		this.begin = begin;
		this.end = end;
		this.datum = datum;

	}

	/**
	 * Die toString Methode wird korrekt überschrieben
	 */
	public String toString() {
		return vorname + " " + nachname + ", Tel.Nr: " + nummer + ", am " + datum + " Von: " + begin + " Bis: " + end;
	}
	public String toString2() {
		return vorname + " " + nachname + ", Tel.Nr: " + nummer + "   [" + datum + "]";
	}

	/**
	 * Getter Methoden
	 */
	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public String getNummer() {
		return nummer;
	}

	public String getBegin() {
		return begin;
	}

	public String getEnd() {
		return end;
	}

	public String getDate() {
		return datum;
	}
}
