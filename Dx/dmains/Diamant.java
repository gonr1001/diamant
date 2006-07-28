/**
 *
 * Title: Diamant 
 * Description: DRun is a class used to call the whole
 *              application Which uses the Model View Control pattern
 *
 *              the main method of the whole program is here.
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
 */
package dmains;

import java.io.File;

import lineInterface.DILigne;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eLib.exit.dialog.FatalProblemDlg;

import dConstants.DConst;
import dInterface.DApplication;
import dInternal.DxDeploymentManager;

/**
 *
 * Diamant is used to call the whole application.
 * It makes an instance of DApplication then call doIt methode
 * which runs the application.
 * 
 * The constant GUI (true) allows to have a Graphical User Interface
 * or when (false) a line interface.
 *
 */

public class Diamant {

	private final static boolean GUI = true;

	
	private static Logger _logger = Logger.getLogger(Diamant.class.getName());

	private static DApplication dApplic = new DApplication();

	/**
	 * Just creating an instance of the DApplication
	 * or a textual interface
	 * When working with a DApplication instance
	 * doIt takes in chage the application
	 *
	 * When working with the textual interface
	 * the own method doIt takes in charge
	 * to run the application.
	 * 
	 * XXXX Pascal: ecrit en anglais et grammaticalement incomprehensible.
	 */

	public static void main(String[] args) {
        (new DxDeploymentManager()).checkAndDeploy();
		PropertyConfigurator.configure(System.getProperty("user.home")
				+ File.separator + "trace" + File.separator + "log4j.conf");
		if (GUI) {
			_logger.warn("hi_with_a_GUI");
			_logger.warn("Java version: " + System.getProperty("java.version"));

			if (DConst.JVM.compareToIgnoreCase(System
					.getProperty("java.version")) <= 0) {
				dApplic.doIt(args);
			} else {
				new FatalProblemDlg(
						"You need to download and install a new  \n"
								+ "Java Virtual Machine");
				System.out.println("bye");
				System.exit(1);
			}
			_logger.warn("hi_with_a_GUI");
		} else {
			System.out.println("hello");
			DILigne dil = new DILigne();
			dil.doIt(args);
			System.out.println("bye");
			System.exit(1);
		}

	} // end main

} /* end class DRun */
