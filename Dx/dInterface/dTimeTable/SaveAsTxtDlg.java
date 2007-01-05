/**
 *
 * Title: SaveAsTxtDlg $Revision: 1.13 $  $Date: 2007-01-05 20:14:26 $
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
 * @version $Revision: 1.13 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: SaveAsTxtDlg is a class used to
 *
 */
package dInterface.dTimeTable;



import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.ObjectStreamException;

import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dResources.DFileFilter;

import eLib.exit.dialog.DxExceptionDlg;

public class SaveAsTxtDlg extends SaveAsDlg{
	
	public SaveAsTxtDlg(DApplication dApplic) {
		super(dApplic);
		saveAs(null,false); //no data, no report
	} // end constructor*/
	/**
	 * Constructor
	 * @param dApplic The application
	 * @param data contains a String the string will be saved
	 *           as in reports
	 */
	public SaveAsTxtDlg(DApplication dApplic, String data) {
		super(dApplic);
		saveAs(data, true); //data, report
	} // end constructor
	
	
	public void addInNewFile(String currentFile, String data) {
		try{
			FileWriter fw = new FileWriter(currentFile);
			fw.write(data);
			fw.close();
		} catch(FileNotFoundException fnfe){
			new DxExceptionDlg("In SaveAsTxtDlg.inNewFile: "+fnfe.getMessage(),fnfe);
		} catch(ObjectStreamException ose){
			new DxExceptionDlg("In SaveAsTxtDlg.inNewFile: "+ose.getMessage(),ose);
		} catch(Exception e){
			new DxExceptionDlg("In SaveAsTxtDlg.inNewFile: "+e.getMessage(),e);
		}
	}
	
	/**
	 * Saves data in a file by using a FileWriter
	 * @param currentFile the file pathname
	 * @param data the data to be stored
	 * @return
	 */
	/* private String saveReport(String currentFile, String data){
	 String error = "";
	 try{
	 FileWriter fw = new FileWriter(currentFile);
	 fw.write(data);
	 fw.close();
	 return error;
	 } catch(Exception e){
	 error = "Problem with the file";
	 new FatalProblemDlg(error);
	 return error;
	 }
	 }// saveReport*/
	
	public String setFilters(JFileChooser fc){
		fc.setFileFilter( new DFileFilter ( new String[] {DConst.TXT},
				DConst.TXT_FILE ) );
		return DConst.DOT_TXT;
		
	} // setFilters
}