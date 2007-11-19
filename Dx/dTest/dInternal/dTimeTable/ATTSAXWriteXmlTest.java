//package dTest.dInternal.dTimeTable;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//import org.xml.sax.SAXException;
//
//import dInternal.dTimeTable.ACycle;
//import dInternal.dTimeTable.ATTSAXParser;
//import dInternal.dTimeTable.ATTSAXWriteXml;
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//
//public class ATTSAXWriteXmlTest extends TestCase {
//	static String _path = "." + File.separator + "dataTest" + File.separator
//			+ "ASAXTTxmlFiles" + File.separator;
//
//	ATTSAXWriteXml testWriteVide;
//
//	ATTSAXWriteXml testWriteComplet;
//
//	public ATTSAXWriteXmlTest(String name) {
//		super(name);
//	}
//
//	public static Test suite() {
//		// the type safe way is in SimpleTest
//		// the dynamic way :
//		return new TestSuite(ATTSAXWriteXmlTest.class);
//	} // end suite
//
//	/**
//	 * Test pour verifier la validité syntaxique d'un fichier XML avec un shema
//	 * xsd
//	 * 
//	 * @throws FileNotFoundException
//	 */
//	public void testEcritureVide() throws FileNotFoundException {
//		System.out.println("-- Begin Test ATTSAXWriteXml ----");
//		System.out
//				.println("Test d'ecriture d'un Time Table Vide dans un fichier Texte");
//		testWriteVide = new ATTSAXWriteXml(_path + "TesteWriteVide",
//				new ACycle());
//		System.out.println("-- Fichier avec un TimeTable vide crée ----");
//	}
//
//	public void testEcritureTimeTable() throws SAXException, IOException {
//		ATTSAXParser saxParserForWrite = new ATTSAXParser(_path
//				+ "ASAXDXTimeTableTest.xml", "ASAXSchemaTest.xsd");
//		System.out
//				.println("Test d'ecriture d'un Time Table lu apartir d'un fichier XML dans un fichier Texte");
//		testWriteComplet = new ATTSAXWriteXml(_path + "TesteWriteComplet",
//				saxParserForWrite.theContentHandler().getCycleLu());
//		System.out
//				.println("-- Fichier avec un TimeTable lu apartir d'un fichier XML est  crée ----");
//		System.out.println("-- End Test ATTSAXWriteXml ----");
//	}
//
//}
