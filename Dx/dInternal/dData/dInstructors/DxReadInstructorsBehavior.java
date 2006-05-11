/**
 * Created on May 11, 2006
 * 
 * 
 * Title: DxReadBehavior.java 
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

import dInternal.DataExchange;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxReadBehavior is an interface used to:
 * <p>
 * Define the methods a ressource importer should always implement. Might also be applied more generally to all SetOfRessources. 
 * <p> 
 * 
 */
public interface DxReadInstructorsBehavior {
	boolean analyseTokens(DataExchange de);
	DxSetOfInstructors buildSetOfRessources(DataExchange de);
}
