/**
 *
 * Title: NewCmd $Revision: 1.2 $  $Date: 2003-01-24 18:24:03 $
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
 * @version $Revision: 1.2 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import javax.swing.JFrame;

public class NewCmd implements Command {

  //JFrame _jFrame;
  DMediator _med;

  public NewCmd( DMediator med) {
     //_jFrame = jFrame;
      _med = med;
  } //end NewCmd
//------------------------------
  public void execute() {
      //MouseApp map = _appFrame._map;
      //_appFrame.addMouseListener(map);
      //MouseMoveApp mvap = _appFrame._mvap;
      _med.addDoc();//_jFrame._map, _appFrame._mvap );
	} //end execute
} /* end NewCmd class */
