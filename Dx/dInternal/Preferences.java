/**
 *
 * Title: Preferences $Revision: 1.1.1.1 $  $Date: 2003-01-23 17:51:40 $
 * Description: Preferences is a class used to save the
 *              user preferences
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
 * @version $Revision: 1.1.1.1 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInternal;

import  com.iLib.rIO.InputFile;
import  com.iLib.rIO.InputFileException;

public class Preferences {
  public String _language;
  public Preferences(String str) {//throws InputFileException{
    try {
      InputFile inf = new InputFile(str);
      _language = inf.readLine();
      System.out.println(_language);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}