package dInterface.dTimeTable;

/**
 *
 * Title: OpenTTSDlg $Revision: 1.27 $  $Date: 2007-11-29 15:57:12 $
 * Description: OpenTTSDlg is a class used to
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
 * @version $Revision: 1.27 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import java.awt.Dimension;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgGetFileName;

import dResources.DFileFilter;


public class OpenTTSDlg extends JDialog implements DlgGetFileName {

//	/**
//	 *
//	 * */
//	private void buildDocument(DApplication dApplic) {
//		JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
//		String str1 = DConst.XML;
//		String str2 = DConst.XML_FILE;
//		//String str3 = DConst.NEW_TT_M;
//
//		fc.setFileFilter(new DFileFilter(new String[] { str1 }, str2));
//		// Display the file chooser in a dialog
//		Dimension d = fc.getPreferredSize();
//		fc.setPreferredSize(new Dimension((int) d.getWidth() + 100, (int) d
//				.getHeight()));
//		//int returnVal = fc.showDialog(dApplic.getJFrame(), str3);
//		//int returnVal = DxTools.showDialog(dApplic.getJFrame(), fc, str3);
//		
//		int returnVal=0;
//	      String filename="nothing.txt";
//	      while(!(new File(filename)).exists()&&
//	            (returnVal==JFileChooser.APPROVE_OPTION)){
//	          returnVal = fc.showOpenDialog(dApplic.getJFrame());
//	        if(fc.getSelectedFile()!=null)
//	          filename= fc.getSelectedFile().getAbsolutePath();
//
//	      }
//	     
//		// If the file chooser exited sucessfully,
//		// and a file was selected, continue
//		if (returnVal == JFileChooser.CANCEL_OPTION) {
//			dApplic.closeCurrentDxDoc();
//			dApplic.hideToolBar();
//		}
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			// get the file name
//			String fil = fc.getSelectedFile().getAbsolutePath();
//			dApplic.setCurrentDir(fil);
/////*!!!NIC!!!*/			String error;
//            try {
////            	if (DxFlags.newDoc){
//            		dApplic.getDMediator().addDxTTStructureDoc(fil);
////            	} else {
////            		dApplic.getDMediator().addDoc(fil, 0);
////            	}
//            } catch (Exception e) {
//                /*!!!NIC!!!*/
//            }
/////*!!!NIC!!!*/			if (error.length() != 0) {
/////*!!!NIC!!!*/				new FatalProblemDlg(dApplic.getJFrame(), error);
/////*!!!NIC!!!*/				System.exit(1);
/////*!!!NIC!!!*/			}
//			dispose();
//			
//
//		}
//	}// end loadTTData

	@Override
	public String getFileName(DApplication dApplic) {
		JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
		String str1 = DConst.XML;
		String str2 = DConst.XML_FILE;
		//String str3 = DConst.NEW_TT_M;

		fc.setFileFilter(new DFileFilter(new String[] { str1 }, str2));
		// Display the file chooser in a dialog
		Dimension d = fc.getPreferredSize();
		fc.setPreferredSize(new Dimension((int) d.getWidth() + 100, (int) d
				.getHeight()));
		int returnVal = 0;
		String fileName="";
		while (!(new File(fileName)).exists()
				&& (returnVal == JFileChooser.APPROVE_OPTION)) {
			returnVal = fc.showOpenDialog(dApplic.getJFrame());
			if (fc.getSelectedFile() != null)
				fileName = fc.getSelectedFile().getAbsolutePath();

		}
		// If the file chooser exited sucessfully,
		// and a file was selected, continue
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// get the file name
			return  fc.getSelectedFile().getAbsolutePath();		
		}
		if (returnVal == JFileChooser.CANCEL_OPTION) {
			return "";			
		}
		return "";
	}// end getFileName
	

} /* end class OpenTTSDlg */
