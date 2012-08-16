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
		stiD.setActivities(loadActivities(activites));
		

		// we have a element "diamant_cours" and we search Element "etudiants"
//		Element etudiants = diamantCours.getChild("etudiants");

		// then we get a List from Element "etudiants"
 //   		List etudiantsList = etudiants.getChildren("etudiant");

		// call method to add all etudiants from current diamant_cours
		// importOk = insertEtudiants(context, databaseName, etudiantsList,
		// facultykey, sessionKey);
		return stiD;

	}

	@SuppressWarnings("rawtypes")
	private ArrayList<StiInstructor> loadInstructors(Element instructors) {		
		List instructorsList = instructors.getChildren("enseignant");		
		return extractInstructors(instructorsList);		
	}
	
	
	@SuppressWarnings("rawtypes")
	private ArrayList<StiActivity> loadActivities(Element activites) {
		List activitesList = activites.getChildren("activite");	
		return extractActivities(activitesList);		
	}

	
	@SuppressWarnings("rawtypes")
	private ArrayList<StiInstructor> extractInstructors(List instructors) {
		ArrayList<StiInstructor> allInstructors = new ArrayList<StiInstructor>();
		Iterator iterator = instructors.iterator();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		
		while (iterator.hasNext()) {
			// here, Element is a enseignant 
			Element oneInstructor = (Element) iterator.next();
			
			// Put elements to the map
			hm.put(ID, oneInstructor.getAttributeValue("id_enseignant"));
			hm.put(FN, oneInstructor.getAttributeValue("prenom_enseignant"));
			hm.put(LN, oneInstructor.getAttributeValue("nom_enseignant"));
			hm.put(TY, oneInstructor.getAttributeValue("statut_enseignant"));
			StiInstructor instructor = new StiInstructor(hm);

			allInstructors.add(instructor);
		}
		return allInstructors;
	}
	

	@SuppressWarnings("rawtypes")
	private ArrayList<StiActivity> extractActivities(List activites) {
		ArrayList<StiActivity> allActivities = new ArrayList<StiActivity>();
		Iterator iterator = activites.iterator();
		Element instructors;
		
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		
		while (iterator.hasNext()) {
			// here, Element is an activity 
			
			Element oneActivity = (Element) iterator.next();

			
			// Put elements to the map
			hm.put(AC, oneActivity.getAttributeValue("code_activite"));
			hm.put(ACT_TYP, oneActivity.getAttributeValue("nature"));
			hm.put(GRP, oneActivity.getAttributeValue("groupe"));
			hm.put(UAA, oneActivity.getAttributeValue("uaa"));
			hm.put(LOC, oneActivity.getAttributeValue("lieu"));
			hm.put(MAX_S, oneActivity.getAttributeValue("max_etudiant"));
			StiActivity activity = new StiActivity(hm);

			
			
			instructors = oneActivity.getChild("enseignants");
			//ArrayList<StiInstructorID> inst = loadInstructorsFromActivities(instructors);
			activity.setInstructors(loadInstructorsFromActivities(instructors));
			
//			stiD.setInstructors(loadInstructors(instructors));
			
			allActivities.add(activity);
		}
		return allActivities;
	}
	
	
	@SuppressWarnings("rawtypes")
	private ArrayList<StiInstructorID> loadInstructorsFromActivities(Element instructors) {		
		List instructorsList = instructors.getChildren("enseignant");		
		return extractInstructorsForActivity(instructorsList);		
	}
	
	
	@SuppressWarnings("rawtypes")
	private ArrayList<StiInstructorID> extractInstructorsForActivity(
			List instructors) {
		ArrayList <StiInstructorID> instructorsForActivity = new ArrayList<StiInstructorID>();
		Iterator iterator = instructors.iterator();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		while (iterator.hasNext()) {
			// here, Element is an Activites folder
			Element oneInstructor =   (Element) iterator.next();
			// Put elements to the map
			hm.put(ID, oneInstructor.getAttributeValue("id_enseignant"));
			StiInstructorID si = new StiInstructorID(hm);
			instructorsForActivity.add(si);
		}// End of While
		return instructorsForActivity;
	}

	
	

//	private boolean insertActivites(List activites) {
//
//		boolean importOk = true;
//		Element blocsHoraires;
//		List blocsHorairesList;
//		Element enseignants;
//		List enseignantsList;
//		String code = null;
//		ArrayList allActivitys = new ArrayList();
//		ArrayList allTeachersByActivity = new ArrayList();
//		ArrayList allHorairesByActivity = new ArrayList();
//		SelectSiigActivite selectSiigActivite = new SelectSiigActivite();
//
//		try {
//
//			// call one Activites folder with Activites from diamant_cours
//			Iterator iterator = activites.iterator();
//
//			int lieuKey = 1;
//
//			String activiteKey = "";
//			int count = 1;
//			while (iterator.hasNext()) {
//				// here, Element is a Activites`folder
//				Element oneActivite = (Element) iterator.next();
//				SiigActivite activite = new SiigActivite();
//				ArrayList activityByTeacher = new ArrayList();
//				ArrayList timeTableByActivity = new ArrayList();

////
////				allActivitys.add(activite);
//

//
//				activityByTeacher = insertEnseignantsByActivity(context,
//						databaseName, enseignantsList, activiteKey);
//				allTeachersByActivity.addAll(activityByTeacher);
//				// element "blocs_horaires"
//				blocsHoraires = oneActivite.getChild("blocs_horaires");
//				// List from Element "blocs_horaires"
//				blocsHorairesList = blocsHoraires.getChildren("bloc_horaire");
//
//				timeTableByActivity = insertHoraires(context, databaseName,
//						blocsHorairesList, activiteKey);
//				allHorairesByActivity.addAll(timeTableByActivity);
//				count = count + 1;
//			}// End of While
//
//			importOk = selectSiigActivite
//					.insertActivitesAndEnseingnantsAndBlocsHoraires(context,
//							databaseName, allActivitys, allTeachersByActivity,
//							allHorairesByActivity);
//		} // End try
//		catch (Exception e) {
//			logger.warn("***** Error in insertActivites" + e.toString());
//		}
//		SiigActivite activite = new SiigActivite();
//		int i = 0;
//		for (i = 0; i < allActivitys.size(); i++) {
//			activite = (SiigActivite) allActivitys.get(i);
//			
//		}
//		SiigActiviteSiigEnseignant siigActiviteSiigEnseignant = new SiigActiviteSiigEnseignant();
//		int j = 0;
//		for (j = 0; j < allTeachersByActivity.size(); j++) {
//			siigActiviteSiigEnseignant = (SiigActiviteSiigEnseignant) allTeachersByActivity
//					.get(j);
//			
//		}
//		SiigBlockHoraire siigBlockHoraire = new SiigBlockHoraire();
//		int k = 0;
//		for (k = 0; k < allHorairesByActivity.size(); k++) {
//			siigBlockHoraire = (SiigBlockHoraire) allHorairesByActivity.get(k);
//			
//		}
//		
//		return importOk;
//	}// end of insertActivites
	

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
