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

import dInternal.dData.AvailableResource;
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
public class DxInstructor extends DxResource implements AvailableResource {
    private static long lKey = 1;

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
        super(lKey++, sName);
        _availability = a;
    }

    /**
     * 
     * @return DxAvailability Availability of the instructor
     */
    public DxAvailability getAvailability() {
        return _availability;
    }

    /**
     * Modify the availability of the instructor
     * 
     * @param aNewAvailability
     *            Availability that has to be assigned to the instructor
     */
    public void setAvailability(DxAvailability aNewAvailability) {
        _availability = aNewAvailability;
    }
    
    public void setAvailability(int[][] nAvailbilities) {
        _availability.setAvailability(nAvailbilities);
    }

    public boolean isEqual(DxResource dxrOther) {
        DxInstructor dxiOther = (DxInstructor) dxrOther;
        if (!this.getName()
                .equalsIgnoreCase(dxiOther.getName())) {
            return false;
        }

        if (!this.getAvailability().isEqual(
                dxiOther.getAvailability())) {
            return false;
        }

        return true;
    }
}
