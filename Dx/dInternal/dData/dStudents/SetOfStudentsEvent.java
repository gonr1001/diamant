/**
*
* Title: SetOfStudentsEvent $Revision: 1.3 $  $Date: 2004-12-16 19:21:00 $
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
* @version $Revision: 1.3 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package dInternal.dData.dStudents;


import java.util.EventObject;

public class SetOfStudentsEvent extends EventObject{

  public SetOfStudentsEvent(SetOfStudents source) {
    super (source);
  }
}