/**
 *
 * Title: ClassName $Revision: 1.8 $  $Date: 2004-02-24 15:19:40 $
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
 * @version $Revision: 1.8 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
// Bernard

import dInterface.DApplication;
import lineInterface.DILigne;

/**
 *
 * DRun is used to call the whole application.
 * It makes an instance of Dview then call doIt methode
 * which runs the application
 * The constant TITLE is the title of the jFrame
 * the constant GUI (true) allows to have a Graphical User Interface
 * or when (false) a line interface
 *
 */
public class DRun {

    private final static boolean GUI = true;
    /**
     * The constructor is not necessary, so empty.
     * All happens in main
     */
    public DRun() {}

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
        if (GUI) {
          System.out.println("hello_with_a_GUI");
          System.out.println("Java version: "+System.getProperty("java.version"));
          DApplication _dApplic = new DApplication();
        }
        else {
            System.out.println("hello");
            DILigne dil = new DILigne();
            dil.doIt(args);
            System.out.println("bye");
            System.exit(1);
        }

    } // end main

} /* end class DRun */

