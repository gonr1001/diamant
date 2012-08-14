/**
 * #rep
 * This comment must be replaced by
 * a copyright or copy left allowing to
 * distribute the code as open source 
 *
 * the prefix for the packages is
 * ca.sixs.
 *
 * 
 */
package ca.sixs.ioFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.UnmarshalException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * @author gonr1001
 * 
 */
public class StiFile {

	/**
	 * @param inputStream
	 * @throws IOException
	 * @throws UnmarshalException
	 * @throws JAXBException
	 * @throws JDOMException
	 */
	public StiData loadData(String fileName) throws FileNotFoundException,
			IOException, UnmarshalException, JAXBException, JDOMException {
		StiData stiD = new StiData();

		SAXBuilder builder = new SAXBuilder(false);
		Document doc = builder.build(new File(fileName));

		// Get the root element, "diamant_cours"
		Element diamantCours = doc.getRootElement();

		// we have a element "diamant_cours"
		// and we search Element form "enseignants"
		Element instructors = diamantCours.getChild("enseignants");

		// then we get a List from Element "enseignant"
		List instructorsList = instructors.getChildren("enseignant");
		stiD.addInstructors(instructorsList);

		// // call method to add all enseignants from current diamant_cours
		insertInstructor(instructorsList);

		// we have a element "diamant_cours" and we search Element activites
		Element activites = diamantCours.getChild("activites");
		// then we get a List from Element "activites"
		List activitesList = activites.getChildren("activite");

		// call method to add all activites from current diamant_cours
		// importOk = insertActivites(context, databaseName, activitesList,
		// facultykey, sessionKey);

		// we have a element "diamant_cours" and we search Element
		// "etudiants"
		Element etudiants = diamantCours.getChild("etudiants");

		// then we get a List from Element "etudiants"
		List etudiantsList = etudiants.getChildren("etudiant");

		// call method to add all etudiants from current diamant_cours
		// importOk = insertEtudiants(context, databaseName, etudiantsList,
		// facultykey, sessionKey);
		return stiD;

	}
	
	
	/** ***************************************************************** */
	/**
	 * Insert the enseignants to the the database
	 */
	private void insertInstructor(List enseignants) {
				
		boolean importOk = true;
		
		ArrayList <StiInstructor> AllInstructors = new ArrayList<StiInstructor>();

//		SelectSiigEnseignant selectSiigEnseignant = new SelectSiigEnseignant();
//	try {
//			// call one Enseignants folder with Enseignants from diamant_cours
		Iterator iterator = enseignants.iterator();
//
//			// this is Etudian to insert in database
//
		while (iterator.hasNext()) {
//				// here, Element is a Enseignant folder
				Element oneEnseignant = (Element) iterator.next();
//				// we set values of enseignant
				String id = oneEnseignant
						.getAttributeValue("id_enseignant");
				 String fn = oneEnseignant
						.getAttributeValue("prenom_enseignant");
				 String ln =oneEnseignant
						.getAttributeValue("nom_enseignant");
				String ty =oneEnseignant
						.getAttributeValue("statut_enseignant");
						
				StiInstructor enseignant = new StiInstructor(id, fn, ln, ty);
				
				AllInstructors.add(enseignant);
			}// End of While
			// insert all the teachers
//			if (!selectSiigEnseignant.insertAllSiigEnseignant(context,
//					databaseName, allTeachers)) {
//				
//				importOk = false;
//			}

//		} // End try
//		catch (Exception e) {
//			logger.warn("***** Database error at insertEnseignants : " + e);
//		}
//		
		
		
//		SiigEnseignant enseignant = new SiigEnseignant();
//		int i = 0;
//		for (i = 0; i < allTeachers.size(); i++) {
//			enseignant = (SiigEnseignant) allTeachers.get(i);
//
//		}
//		return importOk;
	}// end of insertEnseignants


	/**
	 * @param outputStream
	 * @throws IOException
	 * @throws UnmarshalException
	 * @throws JAXBException
	 */
	public void saveData(String str) throws FileNotFoundException, IOException,
			MarshalException, JAXBException {

	}

}
