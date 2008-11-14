/**
*
* Title: SetOfStuCourses $Revision: 1.7 $  $Date: 2005-07-05 12:04:30 $
* Description: SetOfStuCourses is a class used as a data structure container.
*              It contains the student courses and their attributes.
*
*
* Copyright (c) 2001 by rgr.
* All rights reserved.
*
*
* This software is the confidential and proprietary information
* of rgr. ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with rgr.
*
* @version $Revision: 1.7 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/
package dInternal.dData.dStudents;

import dConstants.DConst;
import dInternal.DSetOfResources;
import dInternal.DValue;

/**
 * @author syay1801
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class SetOfStuCourses extends DSetOfResources {

	/**the field to be written before name
	   * and after matricule in toWrite method*/
	private String _auxField;
	
	/**
	 * 
	 */
	public SetOfStuCourses() {
		super();
		_auxField = "";
	}

	/* (non-Javadoc)
	 * @see dInternal.DSetOfResources#getError()
	 */
	public String getError() {
		// TODO Auto-generated method stub
		return "";
	}

	/* (non-Javadoc)
	 * @see dInternal.DObject#toWrite()
	 */
	public String toWrite() {
		// TODO Auto-generated method stub
		return "";
	}

	/* (non-Javadoc)
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	   * return the value of the selected key
	   * INPUT: choice, an integer. choice = 0 return _area; 1 return _stage
	   * 2 return _sex; 3 return _session; 4 return number of courses
	   * OUTPUT: an integer. it return -1 if choice is invalid
	   * */
	  public long getSelectedField(int choice){
	    switch(choice){
	      //case 0: return _area;
	      //case 1: return _stage;
	      //case 2: return _sex;
	      //case 3: return _session;
	      //case 4: return ((SetOfStuCourses)getAttach()).size();
	      case 5: return Long.parseLong(getAuxField());
	    }
	    return -1;
	  }
	  
	  public String getProgram(){
		  return getAuxField().toString();
	  }
	/**
	 * @param id
	 * @return
	 */
	public String toWrite(String site) {
		StringBuffer save= new StringBuffer();
	    DValue choice;
	    for (int i=0; i< size(); i++){
	      save.append(DConst.LINE_DESCRIPTOR_C+" ");
	      save.append(getResourceAt(i).getID());
	      choice = (DValue)getResourceAt(i).getAttach();
	      if (choice.getIntValue()>0){
	        String group= "00"+Integer.toString(choice.getIntValue());
	        save.append(group.substring(group.length()-2,group.length()));
	        if(choice.getBooleanValue())
	        	save.append(";"+1);
	        else
	        	save.append(";"+0);
	      }else
	      	save.append("00");
	      save.append(" "+site);
	      if (i< size()-1)
	      	save.append(DConst.CR_LF);
	    }
	    return save.toString();
	}
	
	 public String getAuxField(){
	    return _auxField;
	  }

	  /**
	   * set the aux field
	   * @param String the resource temporary message
	   * */
	  public void setAuxField(String message){
	    _auxField = message;
	  }


}
