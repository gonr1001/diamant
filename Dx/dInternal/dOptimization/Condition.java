/**
*
* Title: Condition $Revision: 1.4 $  $Date: 2005-03-08 16:00:44 $
* Description: Condition is a class used as 
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

package dInternal.dOptimization;

import dInternal.dTimeTable.Period;

public interface Condition {

  /**
   *
   * @param per
   * @param eventKey
   * @param int operation -1= remove event, 0= do nothing, 1= add event
   * @return
   */
  public int executeTest(int[] perKey, Period per, String eventKey, int operation);

}