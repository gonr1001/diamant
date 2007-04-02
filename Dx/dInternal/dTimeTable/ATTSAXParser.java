package dInternal.dTimeTable;

/*
 * Created on 6  mar. 07 with Eclipse for Java
 */

//import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
//import java.io.PrintStream;

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
	//public final static String SCHEMAGRILLEXSD = "DXTimeTableAmine.xsd";
	//public final static String SCHEMAGRILLEXSD = "ASAXDXTimeTable.xsd";
	static String _path = "." + File.separator + "dataTest" + File.separator + "ASAXTTxmlFiles" + File.separator;
	private static ATTSAXContentHandler simpleContentHandler;
//	private static ATTSAXWriteXml writeFileXml;
	boolean conforme = true;
	/**
	 * Constructeur.
	 */
	public ATTSAXParser(String uri, String uriXsd) throws SAXException, IOException {
		XMLReader saxReader = XMLReaderFactory
				.createXMLReader("org.apache.xerces.parsers.SAXParser");
		simpleContentHandler = new ATTSAXContentHandler();
		saxReader.setContentHandler(simpleContentHandler);
		try {
		saxReader.parse(uri);
		} catch (Throwable t) {
			// t.printStackTrace();
			System.out.println("le fichier xml est manquant ou eronn�");
			conforme = false;
		}
		if (conforme) {
			System.out
					.println("R�sultat de la conformit� du fichier XML au sch�ma XSD est :  ");
			System.out.println(ATTSAXValidation.validXMLWithSAX(uri , uriXsd)); 
//			writeFileXml = new ATTSAXWriteXml(_path +"test.xml",
//					simpleContentHandler.getCycleLu());
		} else {
			System.out
					.println("le systeme ne traite pas la conformit� au schema XSD tant que le fichier XML est eronn�");
		}
		
	}
	
	public boolean Confirmite (){return conforme;}
	public ATTSAXContentHandler theContentHandler(){return simpleContentHandler;}

	
}