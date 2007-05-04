/**
 * Created on Jul 26, 2006
 * 
 * 
 * Title: DxFlags.java 
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
package dDeveloper;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxFlags is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxFlags {

	
	public final static boolean MASTER = true;
//	//public final static boolean newRooms = false;
//	public final static boolean newRooms = MASTER;

	public final static boolean newAlg = MASTER;
	//public final static boolean newAlg = true;

	//public final static boolean newPartitionAlg = true;
	public final static boolean newPartitionAlg = true;

	// public final static boolean newActivity = true;
	public final static boolean newActivity = false;
	
	//public final static boolean newEvent = false;
	public final static boolean newEvent = MASTER;
	
	//public final static boolean newEditEventDlg = false;
	public final static boolean newEditEventDlg = MASTER;

	public static boolean unitTest = true;

}
