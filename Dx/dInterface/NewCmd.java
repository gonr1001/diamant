/**
 *
 * Title: NewCmd $Revision: 1.4 $  $Date: 2003-01-31 16:43:14 $
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
 * @version $Revision: 1.4 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import javax.swing.JFrame;

public class NewCmd implements Command {
  DMediator _med;

  public NewCmd(DMediator med) {
      _med = med;
  } //end NewCmd

  public void execute() {
    _med.addDoc();
  }
} /* end NewCmd class */
