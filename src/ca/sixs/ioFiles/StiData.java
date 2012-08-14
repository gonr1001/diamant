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
	
	//@SuppressWarnings("rawtypes")
	ArrayList <StiInstructor> _instructors = new ArrayList<StiInstructor>() ;
	
	void setInstructors(ArrayList <StiInstructor> instructors){
		_instructors = instructors;
	}
	
	ArrayList <StiInstructor> getInstructors(){
		return _instructors;
	}
	
	

}
