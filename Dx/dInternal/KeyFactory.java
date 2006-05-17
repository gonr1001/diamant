/**
 * Created on May 16, 2006
 * 
 * 
 * Title: KeyFactory.java 
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
package dInternal;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: KeyFactory is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class KeyFactory {	
	private static long key = 1;
	
	public static long getKey(){
		return key++;
	}
}
