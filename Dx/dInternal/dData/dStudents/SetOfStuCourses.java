/**
*
* Title: SetOfStuCourses $Revision: 1.4 $  $Date: 2005-01-28 21:46:54 $
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
* @version $Revision: 1.4 $
* @author  $Author: syay1801 $
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

	/**
	 * 
	 */
	public SetOfStuCourses() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dInternal.DSetOfResources#getError()
	 */
	public String getError() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see dInternal.DObject#toWrite()
	 */
	public String toWrite() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {
		// TODO Auto-generated method stub
		return 0;
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
	
	/**
	   *
	   * */
	 /* public DSetOfResources getCoursesList(){
	    return getCoursesList();
	  }*/

}
