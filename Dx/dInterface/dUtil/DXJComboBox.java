/**
*
* Title: DXJComboBox $Revision: 1.4 $  $Date: 2005-03-08 16:00:43 $
* Description: DXJComboBox is a class used to make dynamic updates
*               to the JComboBox
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
* @author  $Author: syay1801 $
* @since JDK1.3
*/
package dInterface.dUtil;

import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;


	/**
	 * 
	 * @author gonr1001
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public class DXJComboBox extends JComboBox{
		ActionListener [] _actionList;
		boolean _disableListener;
	/**
	 * Constructor
	 */
	public DXJComboBox(){
		super();
		_disableListener = false;
	}
	/**
	 * Constructor
	 */
	public DXJComboBox(Vector items){
		super(items);
		_disableListener = false;
	}
	/**
	 * Constructor
	 */
	public DXJComboBox(Object[] items){
		super(items);
		_disableListener = false;
	}
	
	/**
	 *
	 */
	public void disableActionListeners(){
		_disableListener = true;
		_actionList = this.getActionListeners();
		for (int i = 0; i <_actionList.length; i++) {
			this.removeActionListener(_actionList[i]);
		}
	}
	
	/**
	 *
	 */
	public void enableActionListeners(){
		if(_disableListener){
			_disableListener = false;
			for (int i = 0; i <_actionList.length; i++) {
				this.addActionListener(_actionList[i]);
			}
		}
	}
	}// end DXJComboBox