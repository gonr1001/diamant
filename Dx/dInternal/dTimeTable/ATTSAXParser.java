package dInternal.dTimeTable;

/**
 * Created on 6  mar. 07 with Eclipse for Java
 */

import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * SimpleSaxParser class main.
 * 
 * @author amine
 * 
 */
public class ATTSAXParser {
	static String _path = "." + File.separator + "dataTest" + File.separator
			+ "ASAXTTxmlFiles" + File.separator;

	private static ATTSAXContentHandler simpleContentHandler;

	boolean conforme = true;

	/**
	 * Constructeur.
	 */
	public ATTSAXParser(String uri, String uriXsd) throws SAXException {
		XMLReader saxReader = XMLReaderFactory
				.createXMLReader("org.apache.xerces.parsers.SAXParser");
		simpleContentHandler = new ATTSAXContentHandler();
		saxReader.setContentHandler(simpleContentHandler);
		try {
			saxReader.parse(uri);
		} catch (IOException t) {
			System.out.println("le fichier xml est manquant ou eronné");
			conforme = false;
		} catch (SAXException ex) {
			ex.printStackTrace();
		}
		if (conforme) {
			System.out
					.println("Résultat de la conformité du fichier XML au schéma XSD est :  ");
			System.out.println(ATTSAXValidation.validXMLWithSAX(uri, uriXsd));

		} else {
			System.out
					.println("le systeme ne traite pas la conformité au schema XSD tant que le fichier XML est eronné");
		}

	}

	public boolean Confirmite() {
		return conforme;
	}

	public ATTSAXContentHandler theContentHandler() {
		return simpleContentHandler;
	}

}