/**
 * Created on Jun 21, 2005
 * 
 * 
 * Project Dx
 * Title: DDialog.java 
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

package dInterface;

import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JDialog;



import dInterface.dUtil.ApplyCloseInterface;
import dInterface.dUtil.RightLeftInterface;

/**
 * @author : Ruben Gonzalez-Rubio
 *
 * Description: DDialog.java is a class used as: 
 * <p>
 * A common ancestor of all classes that uses an
 * ApplyClosePanel
 * 
 * 
 */
public abstract class DDialog  extends JDialog implements ApplyCloseInterface,
														RightLeftInterface {
	
	public DDialog() {
		super();
	}
	
	public DDialog(Dialog owner, String title, boolean mode) {
		super(owner, title, mode);
	}
	
	public DDialog(Frame owner, String title, boolean mode) {
		super(owner, title, mode);
	}
	
	/* implemented in descendant classes
	 * @see dInterface.dUtil.ApplyCloseInterface#applyPressed()
	 */
/*	public void applyPressed(){
		// implemented in descendant classes
	}
*/
	/* implemented in descendant classes
	 * @see dInterface.dUtil.ApplyCloseInterface#closePresed()
	 */
/*	public void closePresed(){
		//implemented in descendant classes
	}
*/
}
