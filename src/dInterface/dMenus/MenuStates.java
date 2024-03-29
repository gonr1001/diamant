/**
 * Created on Feb 19, 2006
 * 
 * 
 * Title: MenuStates.java 
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
package dInterface.dMenus;

/**
 * @author : Ruben Gonzalez-Rubio
 * 
 * Description: MenuStates.java is a interface used to:
 * <p>
 * Interface implemented by each Menu Class
 * 
 */
public interface MenuStates {
	
	public void initialState();
	
	public void afterNewTTable();
	
	public void afterNewTTStruc();

	public void afterOpenTTSruc();

	public void afterImport(); // ready for initial Assignment
	
	public void afterInitialAssignment();

	public void showAllMenus();

} // end MenuStates
