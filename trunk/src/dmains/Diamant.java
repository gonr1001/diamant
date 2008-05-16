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

import lineInterface.DILine;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import dConstants.DConst;
import dInterface.DApplication;
import dInternal.dDeployment.DxDeploymentManager;
//import dInternal.dDeployment.DxDeploymentManager;
import eLib.exit.dialog.FatalProblemDlg;

/**
 *
 * Diamant is used to call the whole application.
 * It makes an instance of DApplication then call doIt method
 * 
 * which runs the application.
 * 
 * The constant GUI (true) allows to have a Graphical User Interface
 * or when (false) a line interface.
 *
 */

public class Diamant {

	private final static boolean GUI = true;
	
	private static Logger _logger = Logger.getLogger(Diamant.class.getName());

	//private static DApplication dApplic = new DApplication();

	/**
	 *  main has a constant GUI used to decide
	 *  if the application runs with windows or with
	 *  a line interface
	 *  <p>
	 *  main creates an instance of the DApplication when GUI is true
	 *  so the gui has windows
	 *  else creates a DILine 
	 *  so the gui is a command line
	 *  <p>
	 * 
	 */
	public static void main(String[] args) {
//      TODO delete comment
//		// Check that all files required by Diamant exist
//		// if not create them
		DxDeploymentManager deploymentManager = new DxDeploymentManager();
		deploymentManager.checkAndDeploy();

		PropertyConfigurator.configure(System.getProperty("user.home")
				+ File.separator + "trace" + File.separator + "log4j.conf");
		if (GUI) {
			_logger.warn("hi_with_a_GUI");
			_logger.warn("Java version: " + System.getProperty("java.version"));

			if (DConst.JVM.compareToIgnoreCase(System
					.getProperty("java.version")) <= 0) {
				DApplication dApplic = DApplication.getInstance();
				dApplic.doIt(args);
			} else {
				_logger.error(DConst.INCORRECT_JVM);
				new FatalProblemDlg(DConst.INCORRECT_JVM);
				System.out.println("bye");
				System.exit(1);

			}
			_logger.warn("hi_with_a_GUI");
		} else {
			System.out.println("hello");
			DILine dil = new DILine();
			dil.doIt(args);
			System.out.println("bye");
			System.exit(0);
		}

	} // end main

} /* end class DRun */
