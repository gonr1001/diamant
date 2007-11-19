//package dTest.dInternal.dTimeTable;
//
//import java.io.File;
//
//import dInternal.dTimeTable.ATTSAXValidation;
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//
//public class ATTSAXValidationTest extends TestCase {
//	static String _path = "." + File.separator + "dataTest" + File.separator
//			+ "ASAXTTxmlFiles" + File.separator;
//
//	public ATTSAXValidationTest(String name) {
//		super(name);
//	}
//
//	public static Test suite() {
//		// the type safe way is in SimpleTest
//		// the dynamic way :
//		return new TestSuite(ATTSAXValidationTest.class);
//	} // end suite
//
//	/**
//	 * Test pour verifier la validité syntaxique d'un fichier XML avec un shema
//	 * xsd
//	 */
//	public void testValidation() {
//		System.out.println("-- Begin Test ATTSAXValidation ----");
//		assertTrue(ATTSAXValidation.validXMLWithSAX(_path
//				+ "ASAXDXTimeTableTest.xml", "ASAXSchemaTest.xsd"));
//		System.out
//				.println("Test de validation d'un fichier XML avec un fichier de schema XSD");
//		System.out.println(ATTSAXValidation.validXMLWithSAX(_path
//				+ "ASAXDXTimeTableTest.xml", "ASAXSchemaTest.xsd"));
//		System.out.println("-- End Test ATTSAXValidation ----");
//	}
//}
