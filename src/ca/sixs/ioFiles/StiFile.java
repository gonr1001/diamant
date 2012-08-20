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
public class StiFile implements InstructorConst, ActivityConst, SlotConst, StudentConst {

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
		Element students = diamantCours.getChild("etudiants");
		// thenloadActivites
		stiD.setStudents(loadStudents(students));

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
	private ArrayList<StiStudent> loadStudents(Element students) {
		List studentList = students.getChildren("etudiant");
		return extractStudents(studentList);
	}

	@SuppressWarnings("rawtypes")
	private ArrayList<StiInstructorID> loadInstructorsFromActivities(
			Element instructors) {
		List instructorsList = instructors.getChildren("enseignant");
		return extractInstructorsForActivity(instructorsList);
	}

	@SuppressWarnings("rawtypes")
	private ArrayList<StiSlot> loadSlotsFromActivities(Element slots) {
		List slotsList = slots.getChildren("bloc_horaire");
		return extractSlotsForActivity(slotsList);
	}

	@SuppressWarnings("rawtypes")
	private ArrayList<StiActivityInStudent> loadActivitiesFromStudents(Element activities) {
		List activitiesList = activities.getChildren("activite");
		return extractActivitiesForStudents(activitiesList);
	}
	
	@SuppressWarnings("rawtypes")
	private ArrayList<StiInstructor> extractInstructors(List instructors) {
		ArrayList<StiInstructor> allInstructors = new ArrayList<StiInstructor>();
		Iterator iterator = instructors.iterator();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();

		while (iterator.hasNext()) {
			Element oneInstructor = (Element) iterator.next();

			// Put elements to the map
			hm.put(ID_I, oneInstructor.getAttributeValue("id_enseignant"));
			hm.put(FN_I, oneInstructor.getAttributeValue("prenom_enseignant"));
			hm.put(LN_I, oneInstructor.getAttributeValue("nom_enseignant"));
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
		Element slots;

		HashMap<Integer, String> hm = new HashMap<Integer, String>();

		while (iterator.hasNext()) {
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
			activity.setInstructors(loadInstructorsFromActivities(instructors));

			slots = oneActivity.getChild("blocs_horaires");
			activity.setSlots(loadSlotsFromActivities(slots));

			allActivities.add(activity);
		}
		return allActivities;
	}
	
	
	@SuppressWarnings("rawtypes")
	private ArrayList<StiStudent> extractStudents(List students) {
		ArrayList<StiStudent> allStudents = new ArrayList<StiStudent>();
		Iterator iterator = students.iterator();
		Element activities;
		HashMap<Integer, String> hm = new HashMap<Integer, String>();

		while (iterator.hasNext()) {
			Element oneStudent = (Element) iterator.next();

			// Put elements to the map
			hm.put(ID_S, oneStudent.getAttributeValue("id_etudiant"));
			hm.put(FN_S, oneStudent.getAttributeValue("prenom_etudiant"));
			hm.put(LN_S, oneStudent.getAttributeValue("nom_enseignant"));
			StiStudent student = new StiStudent(hm);
			
			activities = oneStudent.getChild("activites");
			student.setActivities(loadActivitiesFromStudents(activities));
			

			allStudents.add(student);
		}
		return allStudents;
	}
	
	@SuppressWarnings("rawtypes")
	private ArrayList<StiInstructorID> extractInstructorsForActivity(
			List instructors) {
		ArrayList<StiInstructorID> instructorsForActivity = new ArrayList<StiInstructorID>();
		Iterator iterator = instructors.iterator();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		while (iterator.hasNext()) {
			// here, Element is an Activites folder
			Element oneInstructor = (Element) iterator.next();
			// Put elements to the map
			hm.put(ID_I, oneInstructor.getAttributeValue("id_enseignant"));
			StiInstructorID si = new StiInstructorID(hm);
			instructorsForActivity.add(si);
		}// End of While
		return instructorsForActivity;
	}

	@SuppressWarnings("rawtypes")
	private ArrayList<StiSlot> extractSlotsForActivity(List slots) {
		ArrayList<StiSlot> slotsForActivity = new ArrayList<StiSlot>();
		Iterator iterator = slots.iterator();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		while (iterator.hasNext()) {
			Element oneSlot = (Element) iterator.next();
			// Put elements to the map
			hm.put(DAY, oneSlot.getAttributeValue("jour"));
			hm.put(BEGIN, oneSlot.getAttributeValue("heure_debut"));
			hm.put(END, oneSlot.getAttributeValue("heure_fin"));
			hm.put(FIXED, oneSlot.getAttributeValue("horaire_fixe"));
			hm.put(ROOM, oneSlot.getAttributeValue("no_local"));
			hm.put(ROOM_FIXED, oneSlot.getAttributeValue("local_fixe"));
			StiSlot ss = new StiSlot(hm);
			slotsForActivity.add(ss);
		}// End of While
		return slotsForActivity;
	}
	
	@SuppressWarnings("rawtypes")
	private ArrayList<StiActivityInStudent> extractActivitiesForStudents(
			List activities) {
		ArrayList<StiActivityInStudent> activitiesForStudent = new ArrayList<StiActivityInStudent>();
		Iterator iterator = activities.iterator();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		while (iterator.hasNext()) {
			// here, Element is an Activites folder
			Element oneActivity = (Element) iterator.next();
			// Put elements to the map
			hm.put(AC, oneActivity.getAttributeValue("code_activite"));
			hm.put(ACT_TYP, oneActivity.getAttributeValue("nature"));
			hm.put(GRP, oneActivity.getAttributeValue("groupe"));
			hm.put(LOC, oneActivity.getAttributeValue("lieu"));
			hm.put(FIX_GRP, oneActivity.getAttributeValue("groupe_fixe"));
			
			StiActivityInStudent sais = new StiActivityInStudent(hm);
			activitiesForStudent.add(sais);
		}// End of While
		return activitiesForStudent;
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
