/**
 *
 * Title: Preferences $Revision: 1.6 $  $Date: 2003-06-25 08:36:11 $
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
 * @version $Revision: 1.6 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInternal;

import dInterface.DApplication;
import  com.iLib.gIO.FilterFile;
import java.util.StringTokenizer;
import  com.iLib.gException.IOFileException;
import com.iLib.gIO.ByteOutputFile;
import com.iLib.gDialog.FatalProblemDlg;


public class Preferences {
  private final String CR_LF = "\r\n";

  public String _lookAndFeel;
  public String _language;
  public String _defaultDir;
  private String _fullFileName;
  private DApplication _dApplic;
  public Preferences(String str, DApplication dApplic) {//throws InputFileException{
    _dApplic = dApplic;
    try {
      FilterFile filter = new FilterFile();
      if (filter.validFile(str)) {
      StringTokenizer st = new StringTokenizer(new String (filter.getByteArray()), CR_LF );
      System.out.println(st.toString());
      _lookAndFeel = st.nextToken();
      _language = st.nextToken();
      _defaultDir = st.nextToken();
      _fullFileName = str;
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void setLAFName(String lnfName) {
    _lookAndFeel = lnfName;
  }
  public void  save() {
    writeFile(_fullFileName, toString());
  }

  public String toString(){
    String str = _lookAndFeel;
           str += "\n";
           str +=  _language;
           str += "\n";
           str +=_fullFileName;
      return str;
  }


  private void writeFile(String name, String data) {
    try {
      ByteOutputFile bof = new ByteOutputFile(name);
      bof.writeFile(data.getBytes());
      bof.close();
    } catch(IOFileException iofe) {
      new FatalProblemDlg(_dApplic.getJFrame(),
                          iofe + "\n I was in Preferences.writeFile");
      System.out.println(iofe);
      iofe.printStackTrace();
      System.exit(31);
    } // end catch

  }// end writeFile
}





