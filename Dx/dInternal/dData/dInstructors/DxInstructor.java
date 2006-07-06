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

import dInternal.dData.DxAvailability;
import dInternal.dData.DxResource;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxInstructor is a class used to:
 * <p>
 * Keep the state of an instructor
 * <p>
 * 
 */
public class DxInstructor extends DxResource{
    private static long lKey=1;
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
    public DxInstructor(String sName, DxAvailability a) {
        super(lKey++,sName);
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
        return this.getResourceName();
    }

    /**
     * 
     * @return int Unique ID of the instructor
     */
    public long getInstructorKey() {
        return this.getResourceKey();
    }

	public void setInstructorAvailability(int[][] nAvailbilities) {
		_availability.setAvailability(nAvailbilities);
	}
	
	public boolean isEqual(DxResource dxrOther){
		DxInstructor dxiOther = (DxInstructor)dxrOther;
		if(!this.getResourceName().equalsIgnoreCase(dxiOther.getResourceName())){
			return false;
		}
		
		if(!this.getInstructorAvailability().isEqual(dxiOther.getInstructorAvailability())){
			return false;
		}
		
		return true;
	}
}
