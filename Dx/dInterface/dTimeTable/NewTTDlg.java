package dInterface.dTimeTable;

/**
 *
 * Title: NewTTDlg $Revision: 1.28 $  $Date: 2006-03-15 14:25:35 $
 * Description: NewTTDlg is created by NewTTDCmd it is used when
 *              a new document (timetable) will be created,
 *              it is necessary to ask for
 *              a TTStructure in ordrer to start a the timetable.
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
 * @version $Revision: 1.28 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dResources.DFileFilter;
import eLib.exit.dialog.FatalProblemDlg;

import org.apache.log4j.Logger;

/**
 *
 * NewTTDlg is a class used to show a dialog
 *
 */
public class NewTTDlg extends JDialog {
	private static Logger logger = Logger.getLogger(NewTTDlg.class);

	/**
	 * the constructor will displays the dialog
	 *
	 * @param dApplic    the parent of the dialog
	 * @param type       indicating CYCLE or EXAM
	 * 
	 */
	public NewTTDlg(DApplication dApplic, int type) {
		buildDocument(dApplic, type);
	} // end constructor

	/**
	 *
	 * */
	private void buildDocument(DApplication dApplic, int type) {
		JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
		String str1 = DConst.XML;
		String str2 = DConst.XML_FILE;
		String str3 = "";
		if (type == DConst.CYCLE) {
			str3 = DConst.NTT_CY_TD;
		} else if (type == DConst.EXAM) {
			str3 = DConst.NTT_EX_TD;
		} else if (type == DConst.CYCLEANDEXAM) {
			str3 = DConst.NEW_TT_M;
		} else {
			logger.error("buildDocument type=" + type + " est inconnu");
		}

		fc.setFileFilter(new DFileFilter(new String[] { str1 }, str2));
		// Display the file chooser in a dialog
		Dimension d = fc.getPreferredSize();
		fc.setPreferredSize(new Dimension((int) d.getWidth() + 100, (int) d
				.getHeight())); // XXXX Pascal: Magic number
		int returnVal = DXTools.showDialog(dApplic.getJFrame(), fc, str3);

		// If the file chooser exited sucessfully,
		// and a file was selected, continue
		if ((returnVal == JFileChooser.APPROVE_OPTION)) {
			// get the file name
			String fil = fc.getSelectedFile().getAbsolutePath();
			dApplic.setCurrentDir(fil);

			String error = dApplic.getDMediator().addDoc(
					dApplic.getCurrentDir() + DConst.NO_NAME, fil, type);

			// XXXX Pascal: Ce 'if' n'est jamais appele s'il y a une erreur dans 
			//              dApplic.getDMediator().addDoc(), car addDoc() appelle lui-meme 
			//                new FatalProblemDlg(dApplic.getJFrame(),error);
			//                System.exit(1);
			//
			//              De plus, par convention, les valeurs positives de sortie d'une 
			//              application indiquent que tout s'est BIEN passe.  Il faudrait 
			//              retourner une valeur negative quand un probleme majeur survient.
			if (error.length() != 0) {
				new FatalProblemDlg(dApplic.getJFrame(), error);
				System.exit(1);
			}
			dispose();
			if (dApplic.isInDevelopment()) {
				dApplic.afterNewTTable();
			} else {
//				dApplic.getMenuBar().postNewTTCyCmd();
			}

		} // if ((returnVal == JFileChooser.APPROVE_OPTION)) {
		// XXXX Pascal: else?  Si on choisi un mauvais fichier XML on recoit un message d'erreur est l'application ferme en catastrophe.
		//              Si on choisi un fichier avec une mauvaise extension, on nous le fait savoir et, encore une fois, on ferme en catastrophe.
	}// end loadTTData

} /* end class NewTTDlg */