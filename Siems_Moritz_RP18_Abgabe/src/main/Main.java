package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import Controller.CovidTrackerController;
import View.CovidTrackerView;
import Model.Datenstruktur;

public class Main {

	public static void main(String[] args) {

		Datenstruktur datenstruktur = new Datenstruktur();
		CovidTrackerView ctv = new CovidTrackerView();
		CovidTrackerController ctc = new CovidTrackerController();

		ctc.setView(ctv);
		ctv.setController(ctc);
		ctv.setVisible(true);

	}

}
