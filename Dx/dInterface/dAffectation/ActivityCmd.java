/**
*
* Title: ActivityCmd $Revision: 1.7 $  $Date: 2005-03-08 16:00:43 $
* Description: ActivityCmd is a class used to
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
* @version $Revision: 1.7 $
* @author  $Author: syay1801 $
* @since JDK1.3
*/
package dInterface.dAffectation;



import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;




public class ActivityCmd implements Command{

  public ActivityCmd() {

  }

  public void execute(DApplication dApplic) {
    new ActivityDlg(dApplic, DConst.ACT_LIST);
  }
}