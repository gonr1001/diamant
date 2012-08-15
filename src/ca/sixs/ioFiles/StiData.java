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
	
	ArrayList <StiInstructor> _instructors;
	ArrayList <StiActivity> _activities;
	
	
	
	public StiData() {
		_instructors = new ArrayList<StiInstructor>();
		_activities = new ArrayList<StiActivity>();
	}
	void setInstructors(ArrayList <StiInstructor> si){
		_instructors = si;
	}
	
	ArrayList <StiInstructor> getInstructors(){
		return _instructors;
	}

	public void setActivities(ArrayList<StiActivity> sa) {
		_activities =sa;	
	}
	
	

}
