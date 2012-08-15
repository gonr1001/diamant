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

/**
 * @author gonr1001
 *
 */

import java.util.ArrayList;

public class StiData {

	
	public StiData() {
		_instructors = new ArrayList<StiInstructor>();
		_activities = new ArrayList<StiActivity>();
	}

	public void setInstructors(ArrayList<StiInstructor> si) {
		_instructors = si;
	}

	public ArrayList<StiInstructor> getInstructors() {
		return _instructors;
	}

	public void setActivities(ArrayList<StiActivity> sa) {
		_activities = sa;
	}
	
	public ArrayList<StiActivity> getActivities() {
		return _activities;
	}
	
	private ArrayList<StiInstructor> _instructors;
	private ArrayList<StiActivity> _activities;

}
