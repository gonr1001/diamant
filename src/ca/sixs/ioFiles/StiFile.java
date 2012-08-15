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
public class StiFile implements InstructorConst, ActivityConst {

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

		// we have a element "diamant_cours" and we search Element "enseignants"
		Element instructors = diamantCours.getChild("enseignants");
		// then loadInstructors
		stiD.setInstructors(loadInstructors(instructors));

		// we have a element "diamant_cours" and we search Element "activites"
		Element activites = diamantCours.getChild("activites");
		// thenloadActivites
//		stiD.setInstructors(loadActivites(activites));
//		List activitesList = activites.getChildren("activite");
//		ArrayList<StiActivity> sa = extractActivities(activitesList);
		stiD.setActivities(loadActivities(activites));
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


	private ArrayList<StiInstructor> loadInstructors(Element instructors) {
		@SuppressWarnings("rawtypes")
		List instructorsList = instructors.getChildren("enseignant");		
		return extractInstructors(instructorsList);		
	}
	
	
	private ArrayList<StiActivity> loadActivities(Element instructors) {
		@SuppressWarnings("rawtypes")
		List activitiesList = instructors.getChildren("enseignant");		
		return extractActivities(activitiesList);		
	}

	
	@SuppressWarnings("rawtypes")
	private ArrayList<StiInstructor> extractInstructors(List intructors) {
		ArrayList<StiInstructor> allInstructors = new ArrayList<StiInstructor>();
		Iterator iterator = intructors.iterator();
		
		while (iterator.hasNext()) {
			// here, Element is a enseignant 
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
	}
	

	@SuppressWarnings("rawtypes")
	private ArrayList<StiActivity> extractActivities(List activites) {
		ArrayList<StiActivity> allActivities = new ArrayList<StiActivity>();
		Iterator iterator = activites.iterator();
		
		while (iterator.hasNext()) {
			// here, Element is a enseignant 
			Element oneActivity = (Element) iterator.next();

			HashMap<Integer, String> hm = new HashMap<Integer, String>();
			// Put elements to the map
			hm.put(AC, oneActivity.getAttributeValue("code_activite"));
			hm.put(NAT, oneActivity.getAttributeValue("nature"));
			hm.put(GRP, oneActivity.getAttributeValue("groupe"));
			hm.put(UAA, oneActivity.getAttributeValue("uaa"));
			StiActivity activity = new StiActivity(hm);

			allActivities.add(activity);
		}
		return allActivities;
	}
	
	
	

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
