/**
 * Created on 2006-09-29
 * 
 * Title: DxStudent.java 
 * 
 *
 * Copyright (c) 2006 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * @author HAROUNA Abdoul-Kader
 * @since JDK1.3
 */

package dInternal.dData.dStudents;

import dInternal.dData.DxAvailability;
import dInternal.dData.DxResource;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxStudent is a class used to:
 * <p>
 * Holds a student informations 
 * <p>
 * 
 */
public class DxStudent extends DxResource{
	 /** Student key **/ 
	private static long lKey = 1;
		
	 /**
     * Constructor
     * 
     * @param sName
     *            Name of the new student
     * @param lKey
     *            Student key
     */
	 public DxStudent(String sName) {
		 super(lKey++, sName);
		}
	 
}
