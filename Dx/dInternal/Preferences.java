/**
 *
 * Title: Preferences $Revision: 1.17 $  $Date: 2003-11-25 21:50:12 $
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
 * @version $Revision: 1.17 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInternal;

import dInterface.DApplication;
import  com.iLib.gIO.FilterFile;
import java.util.StringTokenizer;
import  com.iLib.gException.IOFileException;
import com.iLib.gIO.ByteOutputFile;
import com.iLib.gDialog.FatalProblemDlg;
import java.util.Vector;
public class Preferences {
  private final String CR_LF = "\r\n";

  public String _lookAndFeel;
  public String _language;
  public String _standardTTC;
  public String _standardTTE;
  public String _defaultDir;
  private String _fullFileName;
  public String _acceptedChars;
  public String _selectedOptionsInFullReport;
  public String _selectedOptionsInConflictReport;
  private DApplication _dApplic;

  public Preferences(String str) {//throws InputFileException{
    //_dApplic = dApplic;
    try {
      FilterFile filter = new FilterFile();
      if (filter.adjustingFile(str)) {
      StringTokenizer st = new StringTokenizer(new String (filter.getByteArray()), CR_LF );
      //System.out.println(st.toString());
      _lookAndFeel = st.nextToken();
      _language = st.nextToken();
      _standardTTC = st.nextToken();
      _standardTTE = st.nextToken();
      _defaultDir = st.nextToken();
      _fullFileName = st.nextToken();
      _acceptedChars = st.nextToken();
      _selectedOptionsInFullReport = st.nextToken();
      _selectedOptionsInConflictReport = st.nextToken();
      }
    } catch (Exception e) {
      System.out.println("Preferences:"+e);
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
           str += CR_LF;
           str +=  _language;
           str += CR_LF;
           str += _standardTTC;
           str += CR_LF;
           str += _standardTTE ;
           str += CR_LF;
           str += _defaultDir ;
           str += CR_LF;
           str += _fullFileName;
           str += CR_LF;
           str += _acceptedChars;
           str += CR_LF;
           str += _selectedOptionsInFullReport;
           str += CR_LF;
           str += _selectedOptionsInConflictReport;
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


  public Vector getSelectedOptionsInFullReport() {
    StringTokenizer st = new StringTokenizer(_selectedOptionsInFullReport,";");
    String s = "selectedOptionsInFullReport";
    Vector res= new Vector();
    //st.nextToken();
    if (st.nextToken().equals(s)) {
      while (st.countTokens() > 0 ){
         res.add(st.nextToken());
      }
    } else {
      System.out.println("Preferences.getSelectedOptionsFullReport  Help");
    }
    return res;
  } //end getSelectedOptionsInFullReport()

  public Vector getSelectedOptionsInConflictReport() {
    StringTokenizer st = new StringTokenizer(_selectedOptionsInConflictReport,";");
    String s = "selectedOptionsInConflictReport";
    Vector res= new Vector();
    //st.nextToken();
    if (st.nextToken().equals(s)) {
      while (st.countTokens() > 0 ){
         res.add(st.nextToken());
      }
    } else {
      System.out.println("Preferences.getSelectedOptionsInConflictReport  Help");
    }
    return res;
  } //end getSelectedOptionsInConflictReport()

  public void setSelectedOptionsInFullReport(Vector v){
    _selectedOptionsInFullReport = "selectedOptionsInFullReport"+";";
    for (int i=0; i < v.size(); i++){
      _selectedOptionsInFullReport += (String) v.elementAt(i)+";";
    }
  } //end setSelectedOptionsInFullReport

  public void setSelectedOptionsInConflictReport(Vector v){
    _selectedOptionsInConflictReport = "selectedOptionsInConflictReport"+";";
    for (int i=0; i < v.size(); i++){
      _selectedOptionsInConflictReport += (String) v.elementAt(i)+";";
    }
  } //end setSelectedOptionsInConflictReport


} /* end Preferences */





