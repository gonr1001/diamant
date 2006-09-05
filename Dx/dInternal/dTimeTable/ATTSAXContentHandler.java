package dInternal.dTimeTable;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.io.*;
//import java.sql.Time;
import java.util.*;

public class ATTSAXContentHandler extends DefaultHandler {
	// résultats de notre parsing
	private List<ACycle> AllDXTimeTable;

	private List<ADay> AllDays;

	private List<ASequence> AllSequences;

	private List<APeriod> AllPeriods;

	private ACycle acycle;

	private ADay aday;

	private ASequence asequence;

	private APeriod aperiod;

	// flags nous indiquant la position du parseur
	private boolean inDXTimeTable, inTTcycle, incycleID, inpLength, inTTdays,
			inTTday, indayRef, indayID, inTTsequences, inTTsequence,
			insequenceID, inTTperiods, inTTperiod, inperiodID, inBeginTime,
			inEndTime, inpriority;
	
	private final String[] AITEM_subTag = { "DXTimeTable", "TTcycle", "TTdays",
			"TTday", "TTsequences", "TTsequence", "TTperiods", "TTperiod" };

	private final String[] AITEM_subConst = { "cycleID", "pLength", "dayRef",
			"sequenceID", "priority", "BeginTime", "EndTime", "periodID",
			"dayID" };

	public static final String[] _weekTable = { "Lu", "Ma", "Me", "Je", "Ve",
			"Sa", "Di" };

	public static final String[] _priorityTable = { "Normale", "Basse", "Nulle" };

	// private String _str;
	// private String _error = "";

	// simple constructeur
	public ATTSAXContentHandler() {
		super();

	}

	// détection d'ouverture de balise
	public void startElement(String uri, String localName, String aName,
			Attributes attributes) throws SAXException {
		System.out.println(uri + " " + localName + " " + aName);
		if (aName.equals("DXTimeTable")) {
			AllDXTimeTable = new LinkedList<ACycle>();
			inDXTimeTable = true;
		} else if (aName.equals("TTcycle")) {
			acycle = new ACycle();
			inTTcycle = true;
		} else if (aName.equals("cycleID")) {

			incycleID = true;
		} else if (aName.equals("pLength")) {
			inpLength = true;
		} else if (aName.equals("TTdays")) {
			AllDays = new LinkedList<ADay>();
			inTTdays = true;
		} else if (aName.equals("TTday")) {
			aday = new ADay();
			inTTday = true;
		} else if (aName.equals("dayRef")) {
			indayRef = true;
		} else if (aName.equals("dayID")) {
			indayID = true;
		} else if (aName.equals("TTsequences")) {
			AllSequences = new LinkedList<ASequence>();
			inTTsequences = true;
		} else if (aName.equals("TTsequence")) {
			asequence = new ASequence();
			inTTsequence = true;
		} else if (aName.equals("sequenceID")) {
			insequenceID = true;
		} else if (aName.equals("TTperiods")) {
			AllPeriods = new LinkedList<APeriod>();
			inTTperiods = true;
		} else if (aName.equals("TTperiod")) {
			aperiod = new APeriod();
			inTTperiod = true;
		} else if (aName.equals("periodID")) {
			inperiodID = true;
		} else if (aName.equals("BeginTime")) {
			inBeginTime = true;
		} else if (aName.equals("EndTime")) {
			inEndTime = true;
		} else if (aName.equals("priority")) {
			inpriority = true;
		} else {
			// erreur, on peut lever une exception
			throw new SAXException("Balise " + aName + " inconnue.");
		}
	}

	// détection de caractères

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String lire = new String(ch, start, length);
		if (incycleID) {
			acycle.setttcycleid(lire);
		} else if (inpLength) {
			acycle.setttcycleperiodlength(lire);
			/*
			 * } else if (indayRef = true) { aday.setttdayname(lire);
			 * acycle.setDayAtcycle(aday); } else if (indayID) {
			 * aday.setttdayid(lire); } else if (insequenceID) {
			 * asequence.setttsequenceid(lire); } else if (inperiodID) {
			 * aperiod.setttperiodid(lire); } else if (inBeginTime) {
			 * aperiod.setttperiodbegintime(lire); } else if (inpriority) {
			 * aperiod.setttperiodpriority(lire);
			 */

		} else {
			System.out.println("bonjour");
		}
	} // détection fin de balise

	public void endElement(String uri, String localName, String aName)
			throws SAXException {
		if (aName.equals("DXTimeTable")) {
			inDXTimeTable = false;
		} else if (aName.equals("TTcycle")) {
			AllDXTimeTable.add(acycle);
			acycle = null;
			inTTcycle = false;
		} else if (aName.equals("cycleID")) {
			incycleID = false;
		} else if (aName.equals("pLength")) {
			inpLength = false;
		} else if (aName.equals("TTdays")) {
			inTTdays = false;
		} else if (aName.equals("TTday")) {
			AllDays.add(aday);
			aday = null;
			inTTday = false;
		} else if (aName.equals("dayID")) {
			indayID = false;
		} else if (aName.equals("dayRef")) {
			indayRef = false;
		} else if (aName.equals("TTsequences")) {
			inTTsequences = false;
		} else if (aName.equals("TTsequence")) {
			AllSequences.add(asequence);
			asequence = null;
			inTTsequence = false;
		} else if (aName.equals("TTperiods")) {
			inTTperiods = false;
		} else if (aName.equals("TTperiod")) {
			AllPeriods.add(aperiod);
			aperiod = null;
			inTTperiod = false;
		} else if (aName.equals("priority")) {
			inpriority = false;
		} else if (aName.equals("EndTime")) {
			inEndTime = false;
		} else if (aName.equals("BeginTime")) {
			inBeginTime = false;
		} else if (aName.equals("periodID")) {
			inperiodID = false;
		} else if (aName.equals("sequenceID")) {
			insequenceID = false;
		} else {
			// erreur, on peut lever une exception
			throw new SAXException("Balise " + aName + " inconnue.");
		}
	}

	// début du parsing
	public void startDocument() throws SAXException {
		System.out.println("Début du parsing");
	}

	// fin du parsing
	public void endDocument() throws SAXException {
		System.out.println("Fin du parsing");
		System.out.println("Resultats du parsing");
		for (ACycle ac : AllDXTimeTable) {
			// ac.toString();
			System.out.println(ac);
		}
		for (ADay ad : AllDays) {
			// ad.toString();
			System.out.println(ad);
		}
		for (ASequence as : AllSequences) {
			// as.toString();
			System.out.println(as);
		}
		for (APeriod ap : AllPeriods) {
			// ap.toString();
			System.out.println(ap);
		}
	}

	// test
	public static void main(String[] args) {
		try {
			// création d'une fabrique de parseurs SAX
			SAXParserFactory fabrique = SAXParserFactory.newInstance();

			// création d'un parseur SAX
			SAXParser parseur = fabrique.newSAXParser();

			// lecture d'un fichier XML avec un DefaultHandler
			File afichier = new File("./dataTest/AmineData/GrilleAmineTest.xml");
			DefaultHandler agestionnaire = new ATTSAXContentHandler();
			parseur.parse(afichier, agestionnaire);

		} catch (ParserConfigurationException pce) {
			System.out.println("Erreur de configuration du parseur");
			System.out.println("Lors de l'appel à SAXParser.newSAXParser()");
		} catch (SAXException se) {
			System.out.println("Erreur de parsing");
			System.out.println("Lors de l'appel à parse()");
			se.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("Erreur d'entrée/sortie");
			System.out.println("Lors de l'appel à parse()");
		}
	}
}
