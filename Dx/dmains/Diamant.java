/**
 *
 * Title: Diamant $Revision: 1.20 $  $Date: 2004-10-21 13:39:55 $
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
 * @version $Revision: 1.20 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dmains;

import java.io.File;

import lineInterface.DILigne;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eLib.exit.dialog.FatalProblemDlg;

import dConstants.DConst;
import dInterface.DApplication;

/**
 *
 * Diamant is used to call the whole application.
 * It makes an instance of Dview then call doIt methode
 * which runs the application
 * The constant TITLE is the title of the jFrame
 * the constant GUI (true) allows to have a Graphical User Interface
 * or when (false) a line interface
 *
 */

public class Diamant {

    private final static boolean GUI = true;
    private static Logger _logger = Logger.getLogger(Diamant.class.getName());
    /**
     * The constructor is not necessary, so empty.
     * All happens in main
     */
    public Diamant() {}

    /**
      * Just creating an instance of the DApplication
      * or a textual interface
      * When working with a DApplication instance
      * doIt takes in chage the application
      *
      * When working with the textual interface
      * the own method doIt takes in charge
      * to run the application.
      */

    public static void main(String[] args) {
      PropertyConfigurator.configure("trace" + File.separator + "log4j.conf");
        if (GUI) {
          _logger.warn("hello_with_a_GUI");
          _logger.warn("Java version: "+ System.getProperty("java.version"));

          if (0 <= System.getProperty("java.version").compareTo(DConst.JVM)) {
			DApplication dApplic = new DApplication();
			dApplic.doIt();
          }
          else {
            new FatalProblemDlg("You need to download and install a new  \n" +
                                "Java Virtual Machine");
            System.out.println("bye");
            System.exit(1);
          }

        }
        else {
            System.out.println("hello");
            DILigne dil = new DILigne();
            dil.doIt(args);
            System.out.println("bye");
            System.exit(1);
        }
        _logger.warn("bye");
    } // end main

} /* end class DRun */

