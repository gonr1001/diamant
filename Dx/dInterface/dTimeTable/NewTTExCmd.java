/**
 *
 * Title: NewTTExCmd $Revision: 1.12 $  $Date: 2005-04-19 20:37:44 $
 * Description: NewTTExCmd is a class used to have a new
 *  			document window
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
 * @version $Revision: 1.12 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dTimeTable;


import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;


public class NewTTExCmd implements Command {
	
	
	public void execute(DApplication dApplic) {
		new NewTTDlg(dApplic, DConst.EXAM);
		// this is  done : in  NewTTDlg if a file is opened
		// dApplic.getMenuBar().postNewTTCyCmd();
		// there is no postNewTTExCmd();
	} // end execute
} /* end NewTTExCmd class */
