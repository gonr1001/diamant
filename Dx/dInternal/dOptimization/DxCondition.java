/**
 * Created on May 24, 2006
 * 
 * 
 * Title: DxCondition.java 
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
package dInternal.dOptimization;

import dInternal.dTimeTable.Period;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxCondition is a class used to:
 * <p>
 * ConditionsToTest Pattern
 * <p>
 * 
 */
public interface DxCondition {

	public int getInfo(int[] perKey, Period per, String eventKey);
	
	public int addTest(int[] perKey, Period per, String eventKey);
	
	public int removeTest(int[] perKey, Period per, String eventKey);

}