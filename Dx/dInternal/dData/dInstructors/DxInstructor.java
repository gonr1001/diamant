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
 * Keep the state of an instructor
 * <p>
 * 
 */
public class DxInstructor {
	
	private long _instructorKey;

	private String _name;

	private DxAvailability _availability;

	/**
	 * Constructor
	 * 
	 * @param sName
	 *            Name of the new instructor
	 * @param a
	 *            Availability of the new instructor
	 * @param lKey
	 *            Instructor key
	 */
	public DxInstructor(String sName, DxAvailability a, long lKey) {
		_instructorKey = lKey;
		_name = sName;
		_availability = a;
	}

	/**
	 * Modify the availability of the instructor
	 * 
	 * @param aNewAvailability
	 *            Availability that has to be assigned to the instructor
	 */
	public void setInstructorAvailability(DxAvailability aNewAvailability) {
		_availability = aNewAvailability;
	}

	/**
	 * Modify instructor's name
	 * 
	 * @param sNewName
	 *            New name for the instructor
	 */
	public void setInstructorName(String sNewName) {
		_name = sNewName;
	}

	/**
	 * 
	 * @return DxAvailability Availability of the instructor
	 */
	public DxAvailability getInstructorAvailability() {
		return _availability;
	}

	/**
	 * 
	 * @return String Name of the instructor
	 */
	public String getInstructorName() {
		return _name;
	}

	/**
	 * 
	 * @return int Unique ID of the instructor
	 */
	public long getInstructorKey() {
		return _instructorKey;
	}

	public static Comparator <DxInstructor> NameComparator = new Comparator <DxInstructor> () {
		public int compare(DxInstructor arg0, DxInstructor arg1) {
			DxInstructor left = arg0;
			DxInstructor right = arg1;
			return left._name.compareTo(right._name);
		}
	};
    
    public static Comparator <DxInstructor> KeyComparator = new Comparator <DxInstructor> () {
        public int compare(DxInstructor arg0, DxInstructor arg1) {
            DxInstructor left = arg0;
            DxInstructor right = arg1;
            long diff=left._instructorKey-right._instructorKey;
            if(diff>0)
                return 1;
            if(diff<0)
                return -1;
            return 0;
        }
    };
}
