/**
 *
 * Title: DModel $Revision: 1.19 $  $Date: 2003-06-02 15:05:36 $
 * Description: DModel is a class used to
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
 * @version $Revision: 1.19 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInternal;

import java.util.Vector;
import java.io.*;
import javax.swing.JFrame;
import dInterface.DApplication;
import dInternal.dData.*;

import dInternal.dTimeTable.TTStructure;
//import com.iLib.gDialog.FatalProblemDlg;

public class DModel{
  private Vector _dmListeners = new Vector();
  //private TTParameters _ttParameters;
  private Status _status;
  private SetOfInstructors _setOfInstructors;
  private SetOfRooms _setOfRooms;
  private SetOfStudents _setOfStudents;
  private SetOfActivities _setOfActivities;
  private DApplication _dApplic;
  private TTStructure _ttStruct;


  public DModel(DApplication dApplic, TTStructure ttStruct) {
    _status = new Status();
    //_ttParameters = new TTParameters();
    _dApplic = dApplic;
    _ttStruct = ttStruct;
    //importData("hello");
    //test1_setAvailability();
  }
  public DModel(JFrame jFrame, TTStructure ttStruct) {
    _status = new Status();
    //_ttParameters = new TTParameters();
    //_dApplic = dApplic;
    _ttStruct = ttStruct;
    //importData("hello");
    //test1_setAvailability();
  }
  public Status getStatus() {
    return _status;
  }
  public void incrementModification() {
    _status.incrModif();
    sendEvent();
  }

  public String importData(String str) {
    //String path =System.getProperty("user.dir")+ File.separator+"data"+File.separator+"filedata.sig";
    //str = path;
    LoadData loadData = new LoadData(str);
    // import set of instructors
      _setOfInstructors = loadData.extractInstructors(null, false);
     if( _setOfInstructors.getError().length()!=0){
       return _setOfInstructors.getError();
     }

     // import set of rooms
      _setOfRooms = loadData.extractRooms(null, false);
     if( _setOfRooms.getError().length()!=0){
       return _setOfRooms.getError();
     }

     // import set of activities
     _setOfActivities = loadData.extractActivities(null, false);
     if( _setOfActivities.getError().length()!=0){
       return _setOfActivities.getError();
     }

     // import set of students
     _setOfStudents = loadData.extractStudents(null, false);
     if(_setOfStudents.getError().length()!=0){
       return _setOfStudents.getError();
     }

     return "";
  }

  public String loadTTStruct(String str) {
    LoadData loadData = new LoadData(str);
    // import set of instructors
      _ttStruct = loadData.extractTTStruct();
     if( _ttStruct.getError().length()!=0){
       return _ttStruct.getError();
     }
     return "";
  }

  public SetOfInstructors getSetOfInstructors(){
    return _setOfInstructors;
  }

  public TTStructure getTTStructure() {
    return _ttStruct;
  }

  public void sendEvent() {
    DModelEvent event = new DModelEvent(this);
    for (int i=0; i< _dmListeners.size(); i++) {
      DModelListener dml = (DModelListener) _dmListeners.elementAt(i);
      dml.changeInDModel(event);
    }
  }

  public synchronized void addDModelListener(DModelListener dml) {
    if (_dmListeners.contains(dml)){
      return;
    }
    _dmListeners.addElement(dml);
  }

  public synchronized void removeTTParametersListener(DModelListener dml) {
    _dmListeners.removeElement(dml);
  }

  public void setTTStructure(int [] a) {
    //_ttStruct.setValues(a);
    sendEvent();
  }


  public void test1_setAvailability(){
 Vector v = new Vector();
 v.add("1 1 1 1 5");
 v.add("1 1 1 5 5");
 int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};

  }
} /* end class DModel */
