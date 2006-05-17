/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxInstructor.java 
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
 * 
 */
package dInternal.dData.dInstructors;

import java.util.Comparator;

import dInternal.dData.DxAvailability;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxInstructor is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxInstructor {
	//private static int UNIQUE_ID = 0;

	private long _instructorKey;

	private String _sName;

	private DxAvailability _aInstrucAvail;

	/**
	 * Constructor
	 * 
	 * @param sName
	 *            Name of the new instructor
	 * @param a
	 *            Availability of the new instructor
	 */
	public DxInstructor(String sName, DxAvailability a, long lKey) {
		_instructorKey = lKey;
		_sName = sName;
		_aInstrucAvail = a;

	}

	/**
	 * Modify the availability of the instructor
	 * 
	 * @param aNewAvailability
	 *            Availability that has to be assigned to the instructor
	 */
	public void setInstructorAvailability(DxAvailability aNewAvailability) {
		_aInstrucAvail = aNewAvailability;
	}

	/**
	 * Modify instructor's name
	 * 
	 * @param sNewName
	 *            New name for the instructor
	 */
	public void setInstructorName(String sNewName) {
		_sName = sNewName;
	}

	/**
	 * 
	 * @return DxAvailability Availability of the instructor
	 */
	public DxAvailability getInstructorAvailability() {
		return _aInstrucAvail;
	}

	/**
	 * 
	 * @return String Name of the instructor
	 */
	public String getInstructorName() {
		return _sName;
	}

	/**
	 * 
	 * @return int Unique ID of the instructor
	 */
	public long getInstructorKey() {
		return _instructorKey;
	}

	public static Comparator<DxInstructor> NameComparator = new Comparator<DxInstructor>() {
		public int compare(DxInstructor arg0, DxInstructor arg1) {
			DxInstructor left = arg0;
			DxInstructor right = arg1;
			return left._sName.compareTo(right._sName);
		}
	};
}
