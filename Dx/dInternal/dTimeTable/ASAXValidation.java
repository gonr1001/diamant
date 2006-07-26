//package dInternal.dTimeTable;
//
////import org.apache.log4j.Logger;
//import org.xml.sax.*;
//import java.io.IOException;
//
////	import javax.xml.validation.Validator;
////	import org.w3c.dom.Document;
//import org.xml.sax.ErrorHandler;
//import org.xml.sax.SAXException;
//import org.xml.sax.SAXNotRecognizedException;
//import org.xml.sax.SAXNotSupportedException;
//import org.xml.sax.SAXParseException;
//import org.xml.sax.helpers.DefaultHandler;
//
////	import javax.xml.validation.*;
////	import org.xml.sax.helpers.DefaultHandler;
//public class ASAXValidation {
//
//	// private static Logger logger = Logger.getLogger(XmlValidParser.class);
//
//	final static String schemaUrl = " ./dataTest/AmineData/DXTimeTableAmine.xsd";
//
//	final static String xmlDocumentUrl = " ./dataTest/AmineData/GrilleAmineTest.xml";
//
//	public boolean validateSchema() {
//		/* Sending the message to the trace */
//		// logger.info("***** BEGIN validateSchema( )");
//		boolean validSchema = true;
//
////		SAXParser parser = new SAXParser();
//		try {
//			parser.setFeature("http://xml.org/sax/features/validation", true);
//			parser.setFeature("http://xml.org/sax/features/namespaces", true);
//			parser.setFeature(
//					"http://apache.org/xml/features/validation/schema", true);
//			parser
//					.setFeature(
//							"http://apache.org/xml/features/validation/schema-full-checking",
//							true);
//			parser
//					.setProperty(
//							"http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
//							schemaUrl);
//			Validator handler = new Validator();
//			parser.setErrorHandler(handler);
//			parser.parse(xmlDocumentUrl);
//			if (handler.validationError == true) {
//				System.out
//						.println("******validateSchema() XML Document has Error:"
//								+ "\n handler.validationError = "
//								+ handler.validationError
//								+ "\n handler.saxParseException.getMessage()= "
//								+ handler.saxParseException.getMessage());
//				validSchema = false;
//			} else {
//				System.out.println("******validateSchema() file:"
//						+ xmlDocumentUrl + " is valid!");
//			}
//		} catch (Exception e) {
//			System.out.println("FileNotFoundException: " + e.getMessage());
//			validSchema = false;
//			System.out.println("*************************validSchema"
//					+ validSchema);
//			return validSchema;
//		}
//		System.out.println("***** END validateSchema( )");
//		return validSchema;
//	}
//
//	private class Validator extends DefaultHandler {
//		public boolean validationError = false;
//
//		public SAXParseException saxParseException = null;
//
//		public void error(SAXParseException exception) throws SAXException {
//			validationError = true;
//			saxParseException = exception;
//		}
//
//		public void fatalError(SAXParseException exception) throws SAXException {
//			validationError = true;
//			saxParseException = exception;
//		}
//
//		public void warning(SAXParseException exception) throws SAXException {
//		}
//	}// End DefaultHandler
//
//	public static void main(String[] args) {
//		ASAXValidation xdc = new ASAXValidation();
//		xdc.validateSchema();
//	}
//}
