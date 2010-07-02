/**
 * Created on 2006-09-07
 * 
 * Title: DiaException.java 
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

package dExceptions;

@SuppressWarnings("serial")
public class DiaException extends Exception {

	public DiaException() {
		super();
	}

	public DiaException(String message) {
		super(message);
	}

	public static String getCurrentMethod(Exception e) {
		return e.getStackTrace()[0].getClassName() + "."
				+ e.getStackTrace()[0].getMethodName() + "()!";
	}

}
