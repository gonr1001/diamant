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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author gonr1001
 * 
 */
public class StiStudent implements StudentConst {

	public StiStudent(HashMap<Integer, String> hm) {
		_student_ID = hm.get(ID_S);
		_studentFirstName = hm.get(FN_S);
		_studentLastName = hm.get(LN_S);

	}

	public String getStudentID() {
		return this._student_ID;
	}

	public String getStudentFirstName() {
		return this._studentFirstName;
	}

	public String getStudentrLastName() {
		return this._studentLastName;
	}
	
	/**
	 * @param loadInstructorsFromActivities
	 */
	public void setActivities(ArrayList<StiActivityInStudent> activites) {
		_activitiesForStudent = activites;
	}

	public ArrayList<StiActivityInStudent> getActivities() {
		return _activitiesForStudent;
	}

	private String _student_ID;
	private String _studentFirstName;
	private String _studentLastName;
	private ArrayList<StiActivityInStudent> _activitiesForStudent;

}
