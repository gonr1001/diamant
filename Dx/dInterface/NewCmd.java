/**
 *
 * Title: NewCmd $Revision: 1.10 $  $Date: 2003-05-15 08:40:34 $
 * Description: NewCmd is a class used to have a new
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
 * @version $Revision: 1.10 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import javax.swing.JFrame;

public class NewCmd implements Command {

  public NewCmd() {
  } //end NewCmd

  public void execute(DApplication dApplic) {
    dApplic.getDMediator().addDoc("Sans titre");
  } // end execute
} /* end NewCmd class */
