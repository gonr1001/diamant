/**
*
* Title: ConflictsOfAnEventCmd $Revision: 1.2 $  $Date: 2005-03-08 16:00:43 $
* Description: ConflictsOfAnEventCmd is a class used to
*              
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
* @version $Revision: 1.2 $
* @author  $Author: syay1801 $
* @since JDK1.3
*/

package dInterface.dTimeTable;

/**
 * @author gonr1001
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;



public class ConflictsOfAnEventCmd implements Command {


  public ConflictsOfAnEventCmd() {
  } //end ConflictsOfAnEventCmd

  public void execute(DApplication dApplic) {
    new ConflictsOfAnEventDlg(dApplic,DConst.CONFLICTS_OF_AN_EVENT_DLG_TITLE);
  } // end execute
} /* end ConflictsOfAnEventCmd class */