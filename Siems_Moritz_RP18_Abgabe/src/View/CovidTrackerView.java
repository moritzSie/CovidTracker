package View;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import Controller.CovidTrackerController;
import Model.Customer;
import Model.Datenstruktur;

/**
 * 
 * @author moritz
 * @version 14.06.2021
 *
 */
public class CovidTrackerView extends JFrame {
	/**
	 * Deklaration aller Komponenten
	 */

	/**
	 * Referenz auf die Controller Klasse
	 */
	CovidTrackerController controller;
	/**
	 * Deklaration der Textfelder
	 */
	JTextField vornamePa_TF; // Pa := Personenaufnahme
	JTextField nachnamePa_TF; // TF := TextField Komponente
	JTextField telNrPa_TF;
	JTextField besuchDatumPa_TF;
	JTextField besuchVonUhrzeitPa_TF;
	JTextField besuchBisUhrzeitPa_TF;
	JTextField vornamePv_TF; // Pv := Personenverfolgung
	JTextField nachnamePv_TF;
	JTextField telNrPv_TF;
	/**
	 * Deklaration der Label
	 */
	JLabel vornamePa_L; // L := Label Komponente
	JLabel nachnamePa_L;
	JLabel telNrPa_L;
	JLabel besuchPa_L;
	JLabel besuchDatumPa_L;
	JLabel besuchVonPa_L;
	JLabel besuchBisPa_L;
	JLabel vornamePv_L;
	JLabel nachnamePv_L;
	JLabel telNrPv_L;
	JLabel personenaufnahme_L;
	JLabel kontaktpersonen_L;
	JLabel personenverfolgung_L;
	JLabel monat_L;
	JLabel jahr_L;
	JLabel covidTracker_L;
	JLabel kontaktverfolgung;
	/**
	 * Deklaration des Textfeldes
	 */
	JTextArea textArea = new JTextArea();
	JTextArea textAreaList = new JTextArea();
	/**
	 * Deklaration der Buttons
	 */
	JButton bestaetigenPa_B; // B := Button Komponente
	JButton bestaetigenPv_B;
	JButton verlassen_B;
	JButton showList_B;
	/**
	 * Deklaration der Checkboxen
	 */
	JCheckBox datenschutzPa_CB; // CB := Checkbox Komponente
	JCheckBox covidPositivPv_CB;
	/*
	 * Farben
	 */
	Color colorVordergrund = new Color(239, 239, 239);
	Color colorHintergrund = new Color(76, 68, 57);
	Color colorBorder = new Color(111, 193, 229);
	Color colorHeadline = new Color(238, 117, 130);
	Color box = new Color(111, 193, 229);
	/*
	 * Schriftarten
	 */
	Font art = new Font("", Font.BOLD, 15);
	Font titel = new Font("Helvetica", Font.BOLD, 25);
	/**
	 * Deklaration Container
	 */
	Container container;

	/**
	 * Im Konstruktor wird die graphische Oberfläche gebaut
	 */

	public CovidTrackerView() {

		container = getContentPane();
		container.setBackground(colorHintergrund);
		container.setLayout(new BorderLayout());

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("CovidTracker");

		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);

		initComponents(); // In dieser Methode werden die Komponenten initialisiert

		/**
		 * Erstellung des NorthPanels
		 */
		JPanel northPanel = new JPanel();
		northPanel.setBorder(new LineBorder(colorBorder, 3));
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
		northPanel.setBackground(colorVordergrund);

		northPanel.add(covidTracker_L);
		covidTracker_L.setFont(titel);
		covidTracker_L.setForeground(box);
		/**
		 * Erstellung des WestPanels
		 */
		JPanel westPanel = new JPanel();
		westPanel.setPreferredSize(new Dimension(400, 20));
		westPanel.setBorder(new LineBorder(colorBorder, 3));
		westPanel.setLayout(new GridLayout(3, 1));
		westPanel.setBackground(colorVordergrund);
		// Unterpanels des Westpanels
		JPanel gridPanelWest1 = new JPanel(new GridLayout(4, 1));
		JPanel gridPanelWest2 = new JPanel(new GridLayout(2, 1));
		// Unterpanels werden dem Westpanel hinzugefügt
		westPanel.add(gridPanelWest1);
		westPanel.add(gridPanelWest2);
		// Unterpanels der gridPanelWest
		JPanel gridPanelTitle = new JPanel(new GridLayout(1, 1));
		JPanel gridPanelEingabe = new JPanel(new GridLayout(3, 2));
		gridPanelEingabe.setPreferredSize(new Dimension(100, 70));
		JPanel gridPanelTitleBesuch = new JPanel(new GridLayout(1, 1));
		JPanel gridPanelDatum = new JPanel(new GridLayout(2, 3));
		gridPanelDatum.setPreferredSize(new Dimension(10, 10));
		JPanel gridPanelDatenschutz = new JPanel(new GridLayout(1, 1));
		JPanel gridPanelButton = new JPanel(new GridLayout(3, 1));

		// Zuweisung der Farben
		verlassen_B.setBackground(box);
		bestaetigenPa_B.setBackground(box);
		personenaufnahme_L.setForeground(colorHeadline);
		besuchPa_L.setForeground(colorHeadline);
		showList_B.setBackground(box);

		// Zuweisung der Schriftarten
		besuchPa_L.setFont(art);
		personenaufnahme_L.setFont(titel);

		// Die Komponenten werden der Pane hinzugefügt
		gridPanelTitle.add(personenaufnahme_L);
		gridPanelEingabe.add(vornamePa_L);
		gridPanelEingabe.add(vornamePa_TF);
		gridPanelEingabe.add(nachnamePa_L);
		gridPanelEingabe.add(nachnamePa_TF);
		gridPanelEingabe.add(telNrPa_L);
		gridPanelEingabe.add(telNrPa_TF);
		gridPanelTitleBesuch.add(besuchPa_L);
		gridPanelDatum.add(besuchDatumPa_L);
		gridPanelDatum.add(besuchVonPa_L);
		gridPanelDatum.add(besuchBisPa_L);
		gridPanelDatum.add(besuchDatumPa_TF);
		gridPanelDatum.add(besuchVonUhrzeitPa_TF);
		gridPanelDatum.add(besuchBisUhrzeitPa_TF);
		gridPanelDatenschutz.add(datenschutzPa_CB);
		gridPanelButton.add(bestaetigenPa_B);
		gridPanelButton.add(showList_B);
		gridPanelButton.add(verlassen_B);
		

		// ToolTipTexte werden hinzugefügt
		besuchDatumPa_TF.setToolTipText("Bitte geben Sie das Datum im Format: 'dd.MM.yyyy' an");
		besuchVonUhrzeitPa_TF.setToolTipText("Bitte geben Sie die Uhrzeit im Format: 'HH:mm' an");
		besuchBisUhrzeitPa_TF.setToolTipText("Bitte geben Sie die Uhrzeit im Format: 'HH:mm' an");

		// Die Unterpanels werden dem GridPanel hinzugefügt
		gridPanelWest1.add(gridPanelTitle);
		gridPanelWest1.add(gridPanelEingabe);
		gridPanelWest1.add(gridPanelTitleBesuch);
		gridPanelWest1.add(gridPanelDatum);
		gridPanelWest2.add(gridPanelDatenschutz);
		gridPanelWest2.add(gridPanelButton);
		/**
		 * Erstellung des EastPanels
		 */
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(1, 1));
		eastPanel.setBackground(colorVordergrund);
		eastPanel.setBorder(new LineBorder(colorBorder, 3));
		eastPanel.setPreferredSize(new Dimension(400, 100));
		
		eastPanel.add(textAreaList);
		textAreaList.setBackground(colorVordergrund);
		textAreaList.setFont(art);
		/**
		 * Erstellung des CenterPanels
		 */

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1));
		centerPanel.setBackground(colorVordergrund);

		JPanel gridPanelCenter1 = new JPanel();
		JPanel gridPanelCenter2 = new JPanel();

		gridPanelCenter1.add(kontaktverfolgung);
		gridPanelCenter2.add(textArea);

		textArea.setBackground(colorVordergrund);
		kontaktverfolgung.setForeground(colorHeadline);

		textArea.setFont(art);
		kontaktverfolgung.setFont(titel);

		centerPanel.add(gridPanelCenter1);
		centerPanel.add(gridPanelCenter2);
		/**
		 * Erstellung des southPanels
		 */
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(100, 100));
		southPanel.setBorder(new LineBorder(colorBorder, 2));
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 240, 10));
		southPanel.setBackground(colorVordergrund);

		JPanel gridPanelTitle_South = new JPanel(new GridLayout(1, 1));
		JPanel gridPanelEingabe_South = new JPanel(new GridLayout(3, 3));
		gridPanelEingabe_South.setPreferredSize(new Dimension(500, 70));
		JPanel gridPanelCheck_South = new JPanel(new GridLayout(1, 1));
		JPanel gridPanelButton_South = new JPanel(new GridLayout(1, 1));

		gridPanelTitle_South.add(personenverfolgung_L);
		gridPanelEingabe_South.add(vornamePv_L);
		gridPanelEingabe_South.add(vornamePv_TF);
		gridPanelEingabe_South.add(nachnamePv_L);
		gridPanelEingabe_South.add(nachnamePv_TF);
		gridPanelEingabe_South.add(telNrPv_L);
		gridPanelEingabe_South.add(telNrPv_TF);
		gridPanelCheck_South.add(covidPositivPv_CB);
		gridPanelButton_South.add(bestaetigenPv_B);

		bestaetigenPv_B.setBackground(box);
		personenverfolgung_L.setForeground(colorHeadline);
		personenverfolgung_L.setBackground(box);

		personenverfolgung_L.setFont(titel);
		nachnamePv_L.setFont(art);
		vornamePv_L.setFont(art);
		telNrPv_L.setFont(art);

		southPanel.add(gridPanelTitle_South);
		southPanel.add(gridPanelEingabe_South);
		southPanel.add(gridPanelCheck_South);
		southPanel.add(gridPanelButton_South);

		// Die MainPanels werden dem Controller hinzugefügt
		container.add(northPanel, BorderLayout.NORTH);
		container.add(westPanel, BorderLayout.WEST);
		container.add(centerPanel, BorderLayout.CENTER);
		container.add(eastPanel, BorderLayout.EAST);
		container.add(southPanel, BorderLayout.SOUTH);
	}

	public void initComponents() {
		/*
		 * initialisierung der Komponenten für das northPanel
		 */
		covidTracker_L = new JLabel("Covid Tracker");
		verlassen_B = new JButton("verlassen");
		kontaktverfolgung = new JLabel("Kontaktverfolgung");

		/*
		 * initinalisiert die Komponenten für das southPanel
		 */
		personenverfolgung_L = new JLabel("Personenverfolgung");
		vornamePv_L = new JLabel("Name");
		nachnamePv_L = new JLabel("Nachname");
		vornamePv_TF = new JTextField(20);
		nachnamePv_TF = new JTextField(20);
		covidPositivPv_CB = new JCheckBox("Corona positiv");
		bestaetigenPv_B = new JButton("bestätigen");
		telNrPv_L = new JLabel("Tel.Nr.:");
		telNrPv_TF = new JTextField();
		/*
		 * initialisierung der Komponenten für das westPanel
		 */
		personenaufnahme_L = new JLabel("Personenaufnahme");
		vornamePa_L = new JLabel("Name");
		nachnamePa_L = new JLabel("Nachname");
		telNrPa_L = new JLabel("Telefonnummer");
		telNrPa_TF = new JTextField(20);
		vornamePa_TF = new JTextField(20);
		nachnamePa_TF = new JTextField(20);
		besuchPa_L = new JLabel("Besuch");
		besuchVonPa_L = new JLabel("Von:");
		besuchBisPa_L = new JLabel("Bis:");
		besuchDatumPa_L = new JLabel("Datum");
		besuchDatumPa_TF = new JTextField(20);
		besuchVonUhrzeitPa_TF = new JTextField(10);
		besuchBisUhrzeitPa_TF = new JTextField(10);
		datenschutzPa_CB = new JCheckBox("Datenschutz bestätigen");
		bestaetigenPa_B = new JButton("speichern");
		showList_B = new JButton("gespeicherte Kunden");

	}

	/**
	 * Die Buttons werden bei den verschiedenen ActionListenern registriert.
	 * 
	 * @param CovidTrackerController controller Der übergebene Controller wird in
	 *                               der Klasse gespeichert
	 * 
	 */
	public void setController(CovidTrackerController controller) {
		this.controller = controller;

		verlassen_B.addActionListener(this.controller.getButtonListenerExit());
		bestaetigenPv_B.addActionListener(this.controller.getButtonListenerSearch());
		bestaetigenPa_B.addActionListener(this.controller.getButtonListenerSafe());
		showList_B.addActionListener(this.controller.getButtonListenerShowList());

	}

	/**
	 * Getter Methoden
	 */
	public JTextField getVornametxt() {
		return vornamePa_TF;
	}

	public JTextField getNachnametxt() {
		return nachnamePa_TF;
	}

	public JTextField getTelNrtxt() {
		return telNrPa_TF;
	}

	public JTextField getBeginxt() {
		return besuchVonUhrzeitPa_TF;
	}

	public JTextField getEndtxt() {
		return besuchBisUhrzeitPa_TF;
	}

	public JTextField getDatumtxt() {
		return besuchDatumPa_TF;
	}

	public boolean getDatenschutz() {
		return datenschutzPa_CB.isSelected();
	}

	public boolean getBestaetigung() {
		return covidPositivPv_CB.isSelected();
	}

	public String getVornametxtS() {
		return vornamePv_TF.getText();
	}

	public String getNachnametxtS() {
		return nachnamePv_TF.getText();
	}

	public String getTelNrtxtS() {
		return telNrPv_TF.getText();
	}

	/**
	 * Die Methode stellt die Kontaktpersonen aus der LinkedList in einer JTextArea
	 * da
	 * 
	 * @param kontaktPersonen
	 */

	public void setTextArea(LinkedList<Customer> kontaktPersonen) {

		String text = "";

		Iterator<Customer> it = kontaktPersonen.iterator();
		while (it.hasNext()) {
			Customer c = it.next();
			text = text + c.toString() + "\n";
		}

		textArea.setText(text);
	}
	/**
	 * Die Methode stellt die Kontaktpersonen aus der LinkedList in einer JTextArea
	 * da
	 * 
	 * @param kontaktPersonen
	 */

	public void setTextAreaList(LinkedList<Customer> data) {

		String text = "";

		Iterator<Customer> it = data.iterator();
		while (it.hasNext()) {
			Customer c = it.next();
			text = text + c.toString2() + "\n";
		}

		textAreaList.setText(text);
	}

	/**
	 * Diese Methode löscht den Text aus der TextArea, wenn diese aktualisiert wird
	 */

	public void clearTextArea() {
		textArea.removeAll();
		invalidate();
	}

}
