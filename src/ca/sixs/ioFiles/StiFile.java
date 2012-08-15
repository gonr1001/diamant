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
import java.util.HashMap;
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
public class StiFile implements InstructorConst {

	/**
	 * @param inputStream
	 * @throws IOException
	 * @throws UnmarshalException
	 * @throws JAXBException
	 * @throws JDOMException
	 */
	@SuppressWarnings("rawtypes")
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
		
		ArrayList<StiInstructor> si = extractInstructors(instructorsList);
		stiD.setInstructors(si);

		// // call method to add all enseignants from current diamant_cours
		// insertInstructor(instructorsList);

		// we have a element "diamant_cours" and we search Element activites
		Element activites = diamantCours.getChild("activites");
		// then we get a List from Element "activites"
		List activitesList = activites.getChildren("activite");
		ArrayList<StiActivity> sa = extractActivities(activitesList);
		stiD.setActivities(sa);
		// call method to add all activites from current diamant_cours
		// importOk = insertActivites(context, databaseName, activitesList,
		// facultykey, sessionKey);

		// we have a element "diamant_cours" and we search Element
		// "etudiants"
		Element etudiants = diamantCours.getChild("etudiants");

		// then we get a List from Element "etudiants"
//    		List etudiantsList = etudiants.getChildren("etudiant");

		// call method to add all etudiants from current diamant_cours
		// importOk = insertEtudiants(context, databaseName, etudiantsList,
		// facultykey, sessionKey);
		return stiD;

	}

	/**
	 * @param activitesList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList<StiActivity> extractActivities(List activitesList) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	private ArrayList<StiInstructor> extractInstructors(List enseignants) {
		ArrayList<StiInstructor> allInstructors = new ArrayList<StiInstructor>();
		Iterator iterator = enseignants.iterator();
		
		while (iterator.hasNext()) {
			// // here, Element is a Enseignant folder
			Element oneEnseignant = (Element) iterator.next();

			HashMap<Integer, String> hm = new HashMap<Integer, String>();
			// Put elements to the map
			hm.put(ID, oneEnseignant.getAttributeValue("id_enseignant"));
			hm.put(FN, oneEnseignant.getAttributeValue("prenom_enseignant"));
			hm.put(LN, oneEnseignant.getAttributeValue("nom_enseignant"));
			hm.put(TY, oneEnseignant.getAttributeValue("statut_enseignant"));
			StiInstructor enseignant = new StiInstructor(hm);

			allInstructors.add(enseignant);
		}
		return allInstructors;
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
