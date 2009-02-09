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

package eLib.exit.dialog;

import java.awt.Component;

import javax.swing.JOptionPane;

import dConstants.DConst;
import dExceptions.DiaException;

public class DxExceptionDlg {

	private final String DX_EXCEPTION = "Diamant a detecté erreur : ";
	
	public DxExceptionDlg(String message) {
		new DxExceptionDlg(null,message,DX_EXCEPTION);
	}
	public DxExceptionDlg(Component comp, String message, String title) {
			JOptionPane.showMessageDialog(comp,
	                  message,title,  JOptionPane.ERROR_MESSAGE);
	}

	public DxExceptionDlg(String message, Exception e) {
		new DxExceptionDlg(null,message,DX_EXCEPTION,e);
	}

	public DxExceptionDlg(Component comp, String error) {
		new DxExceptionDlg(comp,error,DX_EXCEPTION);
	}
	public DxExceptionDlg(Component comp, String message, String title, Exception e) {
		String nmessage = message + DConst.CR_LF + DiaException.getCurrentMethod(e);
		new DxExceptionDlg(comp, nmessage, title);
	}
	public DxExceptionDlg(Component comp, String message, Exception e) {
		new DxExceptionDlg(comp,message,DX_EXCEPTION,e);
	}

}
