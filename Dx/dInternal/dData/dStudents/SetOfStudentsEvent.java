/**
*
* Title: SetOfStudentsEvent $Revision: 1.2 $  $Date: 2004-12-01 17:17:09 $
* Description: SetOfStudentsEvent is a class used as a .
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
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package dInternal.dData.dStudents;


import java.util.EventObject;

public class SetOfStudentsEvent extends EventObject{

  public SetOfStudentsEvent(SetOfStuSites source) {
    super (source);
  }
}