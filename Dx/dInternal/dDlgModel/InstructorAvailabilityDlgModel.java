/**
 * Created on Jan 17, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Project Dx
 * Title: InstructorAvailabilityDlgModel.java 
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
 * 
 * @since JDK1.3
 */

package dInternal.dDlgModel;

import java.util.Vector;

import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dInstructors.SetOfInstructors;
import dInternal.dTimeTable.TTStructure;

/**
 * @author : Ruben Gonzalez-Rubio
 *
 * Description: InstructorAvailabilityDlgModel.java is a class used to: 
 * <p>
 *
 */
public class InstructorAvailabilityDlgModel {
	private String [] _hours;
	private String [] _days;
	private int _maxNbOfPeriods;
	private Vector _instructorsNames;
	/**
	 * 
	 */
	public InstructorAvailabilityDlgModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param hourOfPeriodsADay
	 */
	public void setHours(String[] hourOfPeriodsADay) {
		_hours = hourOfPeriodsADay;
	}

	/**
	 * @return
	 */
	public String[] getHours() {
		return _hours;
	}
	/**
	 * @return
	 */
	public String[] getDays() {
		return _days;
	}
	
	/**
	 * @return
	 */
	public int getMaxNbOfPeriods() {
		return _maxNbOfPeriods;
	}

	/**
	 * @return
	 */
	public Vector getInstructorsNames() {
		return _instructorsNames;
	}
	/**
	 * @param structure
	 */
	public void setDays(TTStructure ttStructure) {
		_days = new String[ttStructure.getNumberOfActiveDays()];
		for (int i = 0; i < _days.length; i++)
		_days[i] = ttStructure.getWeekTable()[i];		
	}

	/**
	 * @param structure
	 */
	public void setMaxNumOfPeriods(TTStructure ttStructure) {
		_maxNbOfPeriods = ttStructure.getCurrentCycle().getMaxNumberOfPeriodsADay();
		
	}

	public void setInstructorsNames(SetOfInstructors setOfInstructors) {
		_instructorsNames = setOfInstructors.getNamesVector(1);
	}
	
//	public void setNewInstructorsNames(DxSetOfInstructors setOfInstructors) {
//		_instructorsNames = setOfInstructors.getNamesVector();
//	}
	
	
}
