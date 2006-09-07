/**
 * Created on 2006-09-07
 * 
 * Title: DxExceptionDlg.java 
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

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dDeveloper.DxFlags;

public class DxExceptionDlg {

	public DxExceptionDlg(String message) {
		new DxExceptionDlg(null,message,"DxException");
	}
	public DxExceptionDlg(Component comp, String message, String title) {
		if (!DxFlags.unitTest)
			JOptionPane.showMessageDialog(comp,
	                  message,title,  JOptionPane.ERROR_MESSAGE);
	}

	public DxExceptionDlg(String message, Exception e) {
		new DxExceptionDlg(null,message,"DxException",e);
	}

	public DxExceptionDlg(Component comp, String error) {
		new DxExceptionDlg(comp,error,"DxException");
	}
	public DxExceptionDlg(Component comp, String message, String title, Exception e) {
		message=message+DxException.getCurrentMethod(e);
		new DxExceptionDlg(comp,message,title);
	}
	public DxExceptionDlg(Component comp, String message, Exception e) {
		new DxExceptionDlg(comp,message,"DxException",e);
	}

}
