/**
*
* Title: SetOfSites $Revision: 1.3 $  $Date: 2004-12-16 19:21:00 $
* Description: SetOfSites is a class used as a data structure container.
*              It contains the rooms and their attributes.
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
* @version $Revision: 1.3 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/
package dInternal.dData.dStudents;

import java.awt.Component;
import java.util.EventListener;

public interface SetOfStudentsListener extends EventListener{
  void changeInSetOfStudents(SetOfStudentsEvent e, Component c);
}


