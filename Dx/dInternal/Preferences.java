/**
 *
 * Title: Preferences $Revision: 1.22 $  $Date: 2004-09-29 19:00:39 $
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
 * @version $Revision: 1.22 $
 * @author  $Author: garr2701 $
 * @since JDK1.3
 */
package dInternal;

import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;



import dInterface.DApplication;
import dInternal.dTimeTable.TTStructure;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.exception.IOFileException;
import eLib.exit.txt.ByteOutputFile;
import eLib.exit.txt.FilterFile;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.tictac.mouseTrap.dModel.Trace;

public class Preferences {
  private final String CR_LF = "\r\n";

  public String _lookAndFeel;
  public String _language;
  public String _standardTTC;
  public String _standardTTE;
  public String _defaultDir;
  private String _originalFullFileName;
  public String _acceptedChars;
  public String _selectedOptionsInFullReport;
  public String _selectedOptionsInConflictReport;
  public String _conflictLimits;
  private DApplication _dApplic;

  //+++++++++++++++++++++++++++++
  Logger logger = Logger.getLogger(this.getClass().getName());
  Trace trace=new Trace();
  public Preferences() {
    PropertyConfigurator.configureAndWatch("trace"+File.separator+"log4j.conf");
  }
  public Preferences(boolean flag) {
    PropertyConfigurator.configureAndWatch("trace"+File.separator+"log4jreex.conf");
  }
  //-----------------------------
  
  
  public Preferences(String str) {//throws InputFileException{
  	//+++++++++++++++++++++++++++++
  	logger.info(trace.write(this,str));	
    //-----------------------------

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
      _originalFullFileName = st.nextToken();
      _acceptedChars = st.nextToken();
      _selectedOptionsInFullReport = st.nextToken();
      _selectedOptionsInConflictReport = st.nextToken();
      _conflictLimits = st.nextToken();
      }
    } catch (Exception e) {
      System.out.println("Preferences:"+e);
    }
  }

  public void setLAFName(String lnfName) {
  	//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this,lnfName));	
    //-----------------------------
    _lookAndFeel = lnfName;
  }
  public void  save() {
	//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this));	
    //-----------------------------
    writeFile(_defaultDir, toString());
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
           str += _originalFullFileName;
           str += CR_LF;
           str += _acceptedChars;
           str += CR_LF;
           str += _selectedOptionsInFullReport;
           str += CR_LF;
           str += _selectedOptionsInConflictReport;
           str += CR_LF;
           str += _conflictLimits;
      return str;
  }


  private void writeFile(String name, String data) {
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(name);
  	traceParams.add(data);
  	logger.info(trace.write(this,traceParams));	
    //-----------------------------
    try {
      ByteOutputFile bof = new ByteOutputFile(name);
      bof.writeFileFromBytes(data.getBytes());
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

  public int [] getConflictLimits() {
  StringTokenizer st = new StringTokenizer(_conflictLimits,";");
  String s = "conflictLimits";
  Vector res= new Vector();
  //st.nextToken();
  if (st.nextToken().equals(s)) {
    while (st.countTokens() > 0 ){
       res.add(st.nextToken());
    }
  } else {
    System.out.println("Preferences.getConflictLimits  Help");
  }
  int [] a = new int[res.size()];
  for(int i = 0 ; i < a.length; i++) {
    a[i] = Integer.parseInt((String)res.get(i));
  }

  return a;
} //end getSelectedOptionsInFullReport()


  public void setSelectedOptionsInFullReport(Vector v){
  	//+++++++++++++++++++++++++++++
  	logger.info(trace.write(this, v));	
    //-----------------------------
    _selectedOptionsInFullReport = "selectedOptionsInFullReport"+";";
    for (int i=0; i < v.size(); i++){
      _selectedOptionsInFullReport += (String) v.elementAt(i)+";";
    }
  } //end setSelectedOptionsInFullReport

  public void setSelectedOptionsInConflictReport(Vector v){
  	//+++++++++++++++++++++++++++++
  	logger.info(trace.write(this, v));	
    //-----------------------------

    _selectedOptionsInConflictReport = "selectedOptionsInConflictReport"+";";
    for (int i=0; i < v.size(); i++){
      _selectedOptionsInConflictReport += (String) v.elementAt(i)+";";
    }
  } //end setSelectedOptionsInConflictReport

  public void setConflicLimits(Vector v){
  	//+++++++++++++++++++++++++++++
  	logger.info(trace.write(this, v));	
    //-----------------------------
    _conflictLimits = "conflictLimits"+";";
    for (int i=0; i < v.size(); i++){
      _conflictLimits += (String) v.elementAt(i)+";";
    }
  } //end setSelectedOptionsInFullReport

} /* end Preferences */





