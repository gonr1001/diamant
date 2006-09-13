/**
 * Created on 2006-09-07
 * 
 * Title: DxException.java 
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

package eLib.exit.exception;

import dConstants.DConst;
import eLib.exit.dialog.DxExceptionDlg;

public class DxException extends Exception {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	public DxException() {
		super();
	}

	public DxException(String message) {
		super(message);
		message+=DConst.CR_LF+getCurrentMethod(this);
		new DxExceptionDlg(message);
	}

	 public static String getCurrentMethod(Exception e){
		 return e.getStackTrace()[0].getClassName() + 
                 "." + e.getStackTrace()[0].getMethodName() + 
                 "()!";
 }

}
