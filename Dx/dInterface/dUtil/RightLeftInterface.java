/**
 * Created on Jun 21, 2005
 * 
 * TODO To change the class description for this generated file
 * 
 * Project Dx
 * Title: RightLeftInterface.java 
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
 * 
 * @since JDK1.3
 */

package dInterface.dUtil;

/**
 * @author : Ruben Gonzalez-Rubio
 *
 * Description: RightLeftInterface.java is a class used to: 
 * <p>
 * Force to implement rigthPressed and leftPressed by the
 * Dialogs which use the RigthLeftPanel
 */
public interface RightLeftInterface {
		public void rightPressed();
		public void leftPressed();
} // end RightLeftInterface
