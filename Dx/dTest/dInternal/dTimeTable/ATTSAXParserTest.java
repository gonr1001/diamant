package dTest.dInternal.dTimeTable;

import java.io.File;
import org.xml.sax.SAXException;
import dInternal.dTimeTable.ATTSAXParser;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ATTSAXParserTest extends TestCase {
	static String _path = "." + File.separator + "dataTest" + File.separator
			+ "ASAXTTxmlFiles" + File.separator;

	public ATTSAXParserTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ATTSAXParserTest.class);
	} // end suite

	/** Test pour verifier la Confirmite syntaxique d'un fichier XML */
	public void testConfirmite() throws SAXException {
		System.out.println("-- Begin Test ATTSAXParser ----");

		final ATTSAXParser simpleSaxParser = new ATTSAXParser(_path
				+ "ASAXDXTimeTableTest.xml", "ASAXSchemaTest.xsd");
		assertTrue(simpleSaxParser.Confirmite());
		System.out.println("-- End Test ATTSAXParser ----");
	}

}
