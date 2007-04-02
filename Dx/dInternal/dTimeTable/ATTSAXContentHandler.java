package dInternal.dTimeTable;
/** Created on 6 mar. 07 par M-A.A
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
// import java.util.StringTokenizer;
import java.text.NumberFormat;
import java.util.StringTokenizer;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.LocatorImpl;

/**
 * @author Amine
 * 
 * Exemple d'implementation extremement simplifiee d'un SAX XML ContentHandler.
 * Le but de cet exemple est purement pedagogique. Very simple implementation
 * sample for XML SAX ContentHandler.
 */
public class ATTSAXContentHandler implements ContentHandler {

	public final static NumberFormat HourFormat = NumberFormat.getIntegerInstance();
	static String _path = "." + File.separator + "dataTest" + File.separator + "ASAXTTxmlFiles" + File.separator;
	FileWriter fw;
	private String grilleTree = "";
	private StringBuffer currentText;
	private int i = 0;
	//private int j = 0;
	private ACycle cycle = new ACycle();
	private ADay day = new ADay();
	private ASequence sequence = new ASequence();
	private APeriod period = new APeriod();
	private StringTokenizer stBeginTime;
	private StringTokenizer stEndTime;
	private String hourBegin ;
	private String minuteBegin ;
	private String hourEnd ;
	private String minuteEnd ;
	//private Vector<ACycle> ttCycles;
	private Vector<ADay> ttDays;
	private Vector<ASequence> ttSequences;
	private Vector<APeriod> ttPeriods;

	/**
	 * Constructeur par defaut.
	 */
	public ATTSAXContentHandler() {
		super();
		// On definit le locator par defaut.
		locator = new LocatorImpl();

	}

	/**
	 * Definition du locator qui permet a tout moment pendant l'analyse, de
	 * localiser le traitement dans le flux. Le locator par defaut indique, par
	 * exemple, le numero de ligne et le numero de caractere sur la ligne.
	 * 
	 * @author Amine
	 * @param value
	 *            le locator a utiliser.
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	public void setDocumentLocator(Locator value) {
		locator = value;
	}

	/**
	 * Evenement envoye au demarrage du parse du flux xml.
	 * 
	 * @throws SAXException
	 *             en cas de probleme quelquonque ne permettant pas de se lancer
	 *             dans l'analyse du document.
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
		// Traitement pour la console
		System.out.println("Debut de l'analyse du document");
				
		// Traitement pour un fichier de test
		try {
			fw = new FileWriter(_path + "unFichier.txt");
			fw.write("debut du document");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Evenement envoye a la fin de l'analyse du flux xml.
	 * 
	 * @throws SAXException
	 *             en cas de probleme quelquonque ne permettant pas de
	 *             considerer l'analyse du document comme etant complete.
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
		// System.out.println("Fin de l'analyse du document" );

		try {
			fw.write("fin du document");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Debut de traitement dans un espace de nommage.
	 * 
	 * @param prefixe
	 *            utilise pour cet espace de nommage dans cette partie de
	 *            l'arborescence.
	 * @param URI
	 *            de l'espace de nommage.
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String,
	 *      java.lang.String)
	 */
	public void startPrefixMapping(String prefix, String URI)
			throws SAXException {
		System.out.println("Traitement de l'espace de nommage : " + URI
				+ ", prefixe choisi : " + prefix);
	}

	/**
	 * Fin de traitement de l'espace de nommage.
	 * 
	 * @param prefixe
	 *            le prefixe choisi a l'ouverture du traitement de l'espace
	 *            nommage.
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	public void endPrefixMapping(String prefix) throws SAXException {
		System.out.println("Fin de traitement de l'espace de nommage : "
				+ prefix);
	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre une balise xml
	 * ouvrante.
	 * 
	 * @param nameSpaceURI
	 *            l'url de l'espace de nommage.
	 * @param localName
	 *            le nom local de la balise.
	 * @param rawName
	 *            nom de la balise en version 1.0
	 *            <code>nameSpaceURI + ":" + localName</code>
	 * @throws SAXException
	 *             si la balise ne correspond pas a ce qui est attendu, comme
	 *             par exemple non respect d'une dtd.
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String nameSpaceURI, String localName,
			String rawName, Attributes attributs) throws SAXException {
		System.out.println("Ouverture de la balise : " + localName);
		try {
			fw.write(localName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!"".equals(nameSpaceURI)) { // espace de nommage particulier
			System.out.println("  appartenant a l'espace de nom : "
					+ nameSpaceURI);
		}
		System.out.println("  Attributs de la balise : ");

		for (int index = 0; index < attributs.getLength(); index++) { // on
			// parcourt la liste des attributs
			System.out.println("     - " + attributs.getLocalName(index)
					+ " = " + attributs.getValue(index));
		}

		// Traitement pour création des objets Début
		grilleTree += "." + localName;
		System.out.println("ICI ON OBSERVE LES DÉBUT DE GRILLE TREE  "+ i + "  ICI" + grilleTree);
		i++;
		if(localName == "TTcycle"){
			cycle = new ACycle();
		}else if (localName == "TTdays"){
			 ttDays = new Vector<ADay>();
		}else if (localName == "TTday"){
			 day = new ADay();
		 }else if (localName == "TTsequences"){
			 ttSequences = new Vector<ASequence>();
		 }else if (localName == "TTsequence"){
			 sequence = new ASequence();
		 }else if (localName == "TTperiods"){
			 ttPeriods = new Vector<APeriod>();
		 }else if (localName == "TTperiod"){
			 period = new APeriod();
		 }
		currentText = new StringBuffer();
		//		

	}

	/**
	 * Evenement recu a chaque fermeture de balise.
	 * 
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void endElement(String nameSpaceURI, String localName, String rawName)
			throws SAXException {
		System.out.print("Fermeture de la balise : " + localName);
		try {
			fw.write(localName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!"".equals(nameSpaceURI)) { // name space non null
			System.out
					.print("appartenant a l'espace de nommage : " + localName);
		}

		System.out.println();
		

		if (grilleTree.startsWith(".DXTimeTable.TTcycle.cycleID")) {
			this.cycle.setCycleID(Integer.parseInt(currentText.toString()));
		} else if (grilleTree.startsWith(".DXTimeTable.TTcycle.pLength")) {
			this.cycle.setpLength(Integer.parseInt(currentText.toString()));
		} else if (grilleTree
				.startsWith(".DXTimeTable.TTcycle.TTdays.TTday.dayRef")) {
			this.day.setDayRef(Integer.parseInt(currentText.toString()));
		} else if (grilleTree
				.startsWith(".DXTimeTable.TTcycle.TTdays.TTday.dayID")) {
			this.day.setDayId(currentText.toString());
		} else if(grilleTree
				.startsWith(".DXTimeTable.TTcycle.TTdays.TTday.TTsequences.TTsequence.sequenceID")) {
			this.sequence.setSequenceId(currentText.toString());
		} else if (grilleTree
				.startsWith(".DXTimeTable.TTcycle.TTdays.TTday.TTsequences.TTsequence.TTperiods.TTperiod.periodID")) {
			period.setPeriodId(Integer.parseInt(currentText.toString()));
		} else if (grilleTree.startsWith(".DXTimeTable.TTcycle.TTdays.TTday.TTsequences.TTsequence.TTperiods.TTperiod.BeginTime")) {
			 String hourMinuteBegin = currentText.toString();
			 stBeginTime = new StringTokenizer(hourMinuteBegin,":");
			hourBegin = stBeginTime.nextToken();
			minuteBegin = stBeginTime.nextToken();
			period.setBeginTime(Integer.parseInt(hourBegin),Integer.parseInt(minuteBegin));// (hour,minute)(Integer.parseInt(currentText.toString()));
		} else if (grilleTree.startsWith(".DXTimeTable.TTcycle.TTdays.TTday.TTsequences.TTsequence.TTperiods.TTperiod.EndTime")) {
			String hourMinuteEnd = currentText.toString();
			 stEndTime = new StringTokenizer(hourMinuteEnd,":");
			hourEnd = stEndTime.nextToken();
			minuteEnd = stEndTime.nextToken();
			period.setEndTime(Integer.parseInt(hourEnd),Integer.parseInt(minuteEnd));
		} else if (grilleTree.startsWith(".DXTimeTable.TTcycle.TTdays.TTday.TTsequences.TTsequence.TTperiods.TTperiod.priority")) {
			period.setPriority(Integer.parseInt(currentText.toString()));
		}
		
		if(localName == "TTperiod"){
			ttPeriods.add(period);
		}else if (localName == "TTperiods"){
			 sequence.setTTperiods(ttPeriods);
		}else if (localName == "TTsequence"){
			 ttSequences.add(sequence);
		 }else if (localName == "TTsequences"){
			 day.setTTsequences(ttSequences);
		 }else if (localName == "TTday"){
			 ttDays.add(day);
		 }else if (localName == "TTdays"){
			 cycle.setTTdays(ttDays);
		 }

		grilleTree = grilleTree.substring(0, grilleTree.length()
				- localName.length() - 1); // delete element name from element
		
		currentText.setLength(0); // clean currentText StringBuffer
		

		
		

	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre des caracteres
	 * (entre deux balises).
	 * 
	 * @param ch
	 *            les caracteres proprement dits.
	 * @param start
	 *            le rang du premier caractere a traiter effectivement.
	 * @param end
	 *            le rang du dernier caractere a traiter effectivement
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(char[] ch, int start, int end) throws SAXException {
		System.out.println("#PCDATA : " + new String(ch, start, end));
		try {
			fw.write("   ");
			fw.write(new String(ch, start, end));
			fw.write("   ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Traitement pour creation des objets
		currentText.append(ch, start, end);
	}

	/**
	 * Recu chaque fois que des caracteres d'espacement peuvent etre ignores au
	 * sens de XML. C'est a dire que cet evenement est envoye pour plusieurs
	 * espaces se succedant, les tabulations, et les retours chariot se
	 * succedants ainsi que toute combinaison de ces trois types d'occurrence.
	 * 
	 * @param ch
	 *            les caracteres proprement dits.
	 * @param start
	 *            le rang du premier caractere a traiter effectivement.
	 * @param end
	 *            le rang du dernier caractere a traiter effectivement
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] ch, int start, int end)
			throws SAXException {
		System.out.println("espaces inutiles rencontres : ..."
				+ new String(ch, start, end) + "...");
	}

	/**
	 * Rencontre une instruction de fonctionnement.
	 * 
	 * @param target
	 *            la cible de l'instruction de fonctionnement.
	 * @param data
	 *            les valeurs associees a cette cible. En general, elle se
	 *            presente sous la forme d'une serie de paires nom/valeur.
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
	 *      java.lang.String)
	 */
	public void processingInstruction(String target, String data)
			throws SAXException {
		System.out.println("Instruction de fonctionnement : " + target);
		System.out.println("  dont les arguments sont : " + data);
	}

	/**
	 * Recu a chaque fois qu'une balise est evitee dans le traitement a cause
	 * d'un probleme non bloque par le parser. Pour ma part je ne pense pas que
	 * vous en ayez besoin dans vos traitements.
	 * 
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	public void skippedEntity(String arg0) throws SAXException {
		// TODO aucun code necessaire pour ce cas
	}

	 public ACycle getCycleLu(){
	 return this.cycle;
	 }

	private Locator locator;

}