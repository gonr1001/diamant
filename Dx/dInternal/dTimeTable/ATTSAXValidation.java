package dInternal.dTimeTable;


import java.io.IOException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * Created on 6 mars 2007
 */

/**
 * Exemple rapde de validation d'un fichier XML avec SAX en utilisant un XSD
 * (XMLSchema)
 * 
 * @author M-A.ABID
 * @since 6-3-07
 */
public class ATTSAXValidation {

	//static final String JAXP_SCHEMA_LANGUAGE = "org.apache.xerces.jaxp.SAXParserImpl";
	static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	
	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	static boolean isValid;

	static {
		isValid = false;
	}

	public static boolean validXMLWithSAX(String xmlFile, String xsdFile) {
		isValid = true;
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setNamespaceAware(true);
			spf.setValidating(true);
			SAXParser sp = spf.newSAXParser();
			sp.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
			sp.setProperty(JAXP_SCHEMA_SOURCE, xsdFile);
			sp.parse(xmlFile, new DefaultHandler(){
				public void fatalError(SAXParseException e) {
					System.out
							.println("Erreur de validation XSD - Erreur fatal");
					isValid = false;
				}

				public void error(SAXParseException e) {
					System.out.println("Erreur de validation XSD - Erreur");
					System.out.println("l'erreur est a la ligne : ");
					System.out.println(e.getLineNumber());
					System.out.println("l'erreur est a la colonne : ");
					System.out.println(e.getColumnNumber());
					System.out.println(e.getLocalizedMessage());
					isValid = false;
				}

				public void warning(SAXParseException e) {
					System.out.println("Erreur de validation XSD - Warning");
					isValid = false;
				}
			});
		} catch (SAXException se) {
			System.out.println(se);
			return false;
		} catch (ParserConfigurationException pce){
			System.out.println(pce);
			return false;
		} catch (IOException ioe){
			System.out.println(ioe);
			return false;
		}
		return isValid;
	}
	
//	public static void main(String[] args) {
//		
//		System.out.println(ATTSAXValidation.validXMLWithSAX("ASAXDXTimeTableTest.xml", "DXTimeTableAmine.xsd"));
//		System.out.println("Fin de traitement");
//	}
}
