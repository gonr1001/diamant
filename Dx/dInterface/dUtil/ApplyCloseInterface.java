/**
 * Created on Jun 21, 2005
 * 
 * Project Dx
 * Title: ApplyCloseIterface.java 
 * 
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
 * @since JDK1.3
 */

package dInterface.dUtil;

/**
 * @author : Ruben Gonzalez-Rubio
 *
 * Description: ApplyCloseIterface.java is an interface used to: 
 * <p> 
 * Force to implement applyPressed and closePressed by the
 * Dialogs which use the ApplyClosePanel
 *
 */
public interface ApplyCloseInterface {
	public void applyPressed();
	public void closePresed();
}//end  ApplyCloseIterface
