package dInternal.dTimeTable;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.*;

public class ASAXParser extends DefaultHandler {

	public static void main(String args[]) {
		if (args.length != 1) {
			System.err.println("Erreur, il manque un argument");
			System.exit(1);
		}
		DefaultHandler handler = new ASAXParser();
		SAXParserFactory factory;
		factory = SAXParserFactory.newInstance();

		try {

			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File(args[0]), handler);

		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}

	/*
	 * private class Validator extends DefaultHandler { public boolean
	 * validationError = false; public SAXParseException saxParseException =
	 * null; public void error(SAXParseException exception) throws SAXException {
	 * validationError = true; saxParseException = exception; }
	 * 
	 * public void fatalError(SAXParseException exception) throws SAXException {
	 * validationError = true; saxParseException = exception; }
	 */

	public void startDocument() throws SAXException {
		System.out.println("debut du document");
	}

	public void endDocument() throws SAXException {
		System.out.println("fin du document");
	}

	public void startElement(String namespaceURI, String sName, String qName,
			Attributes attrs) throws SAXException {
		String eName = sName;
		if ("".equals(eName)) {
			eName = qName;
		}
		System.out.println("balise ouverte ; " + eName);

		if (attrs != null) {
			if (attrs.getLength() != 0) {
				System.out
						.println("voici la liste de balise pour le fichier xml"
								+ eName + ":");
			}
			for (int i = 0; i < attrs.getLength(); i++) {
				String aName = attrs.getLocalName(i);
				if ("".equals(aName)) {
					aName = attrs.getQName(i);

				}
				System.out.println("  " + aName + " =<" + attrs.getValue(i)
						+ " >");
			}
		}
	}

	public void endElemet(String namespaceURI, String simpleName,
			String qualifiedName){// throws SAXException {
		String nomElement = simpleName;
		//nomElement.equals("")
		if ("".equals(nomElement)) {
			nomElement = qualifiedName;
		}
		System.out.println("endElement :" + nomElement);

	}

	public void characters(char[] ch, int start, int end) throws SAXException {
		String chaine = new String(ch, start, end);
		chaine = chaine.trim();
		if (!chaine.equals("")) {
			System.out.println("donnees : " + chaine);
		}
	}
}
