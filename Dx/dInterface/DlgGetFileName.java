/**
 * Created on November 28, 2007
 * 
 * 
 * Title: IndentificationOfDlg.java 
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
package dInterface;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DlgDoIt is a interface used when:
 * <p>
 * A Dialog process a file, this is done to send the exceptions
 * to DApplication
 * <p> 
 * 
 */

public interface DlgGetFileName {
	public String getFileName(DApplication dApplic);
}
