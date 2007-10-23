//package dInternal.dData.dRooms;
//
//import java.io.File;
//
//import dConstants.DConst;
//import dInternal.DResource;
//import dInternal.DSetOfResources;
//import dInternal.DValue;
//import eLib.exit.xml.input.XMLReader;
//import eLib.exit.xml.input.XMLInputFile;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
///**
// * @author Yannick S
// *
// * Cette classe permet de lire le fichier xml des functions
// * de locaux et de construire l'ensemble des fonctions
// */
//public class SetOfRoomsFunctions extends DSetOfResources {
//
//	//private final String ROOTTAG = "ROOM_FUNCTIONS";
//
//	private final String FUNCTIONTAG = "ROOM_FUNCTION";
//
//	private final String KEYTAG = "KEY";
//
//	private final String NAMETAG = "NAME";
//
//	private final String ALL_ROOMS_FUNCTION_KEY = "1";
//
//	/**
//	 *Constructeur
//	 *Il initialise la classe mere DSetOfResources 
//	 */
//	public SetOfRoomsFunctions() {
//		super();
//		addFunction(ALL_ROOMS_FUNCTION_KEY, DConst.ALL);
//	}
//
//	/*
//	 * Cette methode permet de lire un fichier xml de fonction
//	 * de locaux et le charge dans la collection
//	 * @param String le nom du fichier
//	 */
//	public void functionReader() {
//		String fileName = System.getProperty("user.home") + File.separator
//				+ "pref" + File.separator + "room_function.xml";
//		XMLInputFile xmlFile;
//		Element root; //, item, ID;
//		try {
//			xmlFile = new XMLInputFile();
//			Document doc = xmlFile.createDocument(fileName);
//			XMLReader list = new XMLReader();
//			root = list.getRootElement(doc);
//			int size = list.getSize(root, FUNCTIONTAG);
//			for (int i = 0; i < size; i++) {
//				Element function = list.getElement(root, FUNCTIONTAG, i);
//				String key = list.getElementValue(function, KEYTAG);
//				String name = list.getElementValue(function, NAMETAG);
//				if (!key.equalsIgnoreCase(ALL_ROOMS_FUNCTION_KEY))
//					addFunction(key, name);
//			}
//		} catch (Exception e) {
//			System.out.println("SetOfRoomsFunctions 1 :" + e);
//		}
//
//	}
//
//	/**
//	 * Cette methode permet d'ajouter une fonction à la liste
//	 * @param key la clé de la resource
//	 * @param name l'identifiant de la resource
//	 */
//	private void addFunction(String key, String name) {
//		int insertType = 1;//insert using name order
//		DResource resc = new DResource(name, new DValue());
//		this.setCurrentKey(Long.parseLong(key));
//		this.addResource(resc, insertType);
//
//	}
//
//	/**
//	 * 
//	 */
//	public String getError() {
//		return null;
//	}
//
//	/*
//	 * 
//	 */
//	public String toWrite() {
//		return null;
//	}
//
//	/*
//	 * 
//	 */
//	public long getSelectedField() {
//		return 0;
//	}
//}
