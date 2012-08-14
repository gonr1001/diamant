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
import java.util.List;

public class StiData {
	
	//@SuppressWarnings("rawtypes")
	ArrayList <List> _stiData = new ArrayList<List>() ;
	
	void addInstructors(List list){
		_stiData.add(list);
	}
	
	List getInstructors(){
		return _stiData.get(0);
	}
	
	

}
