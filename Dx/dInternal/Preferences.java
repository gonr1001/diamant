/**
 *
 * Title: Preferences $Revision: 1.2 $  $Date: 2003-02-20 15:13:33 $
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
 * @version $Revision: 1.2 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInternal;


import  com.iLib.rIO.InputFile;
import  com.iLib.rIO.InputFileException;
import  com.iLib.rIO.Writer;

public class Preferences {
  public String _lookAndFeel;
  public String _language;
  private String _fullFileName;
  public Preferences(String str) {//throws InputFileException{
    try {
      InputFile inf = new InputFile(str);
      _lookAndFeel = inf.readLine();
      _language = inf.readLine();
      _fullFileName = str;

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void setLAFName(String lnfName) {
    _lookAndFeel = lnfName;
  }
  public void  save() {
    new Writer(_fullFileName, toString());
  }

  public String toString(){
    String str = _lookAndFeel;
           str += "\n";
           str +=  _language;
           str += "\n";
           str +=_fullFileName;
      return str;
  }
}





