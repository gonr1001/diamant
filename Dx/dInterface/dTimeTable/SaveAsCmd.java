/**
 *
 * Title: SaveAsCmd $Revision: 1.1 $  $Date: 2003-06-04 16:26:13 $
 * Description: SaveAsCmd is a class used to save a
 *  			document
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
 * @version $Revision: 1.1 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface.dTimeTable;

import javax.swing.JFrame;
import dInterface.Command;
import dInterface.DApplication;


public class SaveAsCmd implements Command {

  public SaveAsCmd() {
  } //end NewCmd

  public void execute(DApplication dApplic) {
    new SaveAsDlg(dApplic);
  } // end execute
} /* end SaveAsCmd class */

