package dInterface.dTimeTable;

/**
*
* Title: NewTTSCyCmd $Revision: 1.10 $  $Date: 2005-02-01 21:27:16 $
* Description: NewTTSCyCmd is a class used to
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
* @version $Revision: 1.10 $
* @author  $Author: syay1801 $
* @since JDK1.3
*/


import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * NewTTSCmd is class used to call the command
 * which displays the New Time Table Structure dialog
 *
 */

public class NewTTSCyCmd implements Command{
  //private final boolean PARTIAL = true;

  public NewTTSCyCmd() {
  }

  public void execute(DApplication dApplic) {
    dApplic.showToolBar();
    dApplic.getDMediator().addDoc(dApplic.getPreferences()._standardTTC, DConst.NO_TYPE);
    dApplic.getMenuBar().postNewTTSCyCmd();
  } // end execute

}/* end class NewTTSCmd */






