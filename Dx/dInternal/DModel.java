/**
 *
 * Title: DModel $Revision: 1.14 $  $Date: 2003-05-09 14:28:46 $
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
 * @version $Revision: 1.14 $
 * @author  $Author: ysyam $
 * @since JDK1.3
 */
package dInternal;

import java.util.Vector;
import java.io.*;
import javax.swing.JFrame;
import dInternal.dData.*;
import com.iLib.gDialog.FatalProblemDlg;

public class DModel{
  private Vector _dmListeners = new Vector();
  private TTParameters _ttParameters;
  private Status _status;
  private SetOfInstructors _setOfInstructors;
  private SetOfRooms _setOfRooms;
  private SetOfStudents _setOfStudents;
  private JFrame _jFrame;

  public DModel(JFrame jFrame) {
    _status = new Status();
    _ttParameters = new TTParameters();
    _jFrame = jFrame;
    importData("hello");
    //test1_setAvailability();
  }

  public Status getStatus() {
    return _status;
  }
  public void incrementModification() {
    _status.incrModif();
    sendEvent();
  }

  public void importData(String str) {
    String path =System.getProperty("user.dir")+ File.separator+"data"+File.separator+"filedata.sig";
    str = path;
    LoadData loadData = new LoadData(str);
    // import set of instructors
      _setOfInstructors = loadData.extractInstructors(null, false);
     if( _setOfInstructors.getError().length()!=0){
       new FatalProblemDlg(_jFrame,_setOfInstructors.getError());
      System.exit(1);
     }

     // import set of students
     _setOfStudents = loadData.extractStudents(null, false);
     if(_setOfStudents.getError().length()!=0){
       new FatalProblemDlg(_jFrame,_setOfStudents.getError());
      System.exit(1);
     }

     // import set of rooms
      _setOfRooms = loadData.extractRooms(null, false);
     if( _setOfRooms.getError().length()!=0){
       new FatalProblemDlg(_jFrame,_setOfRooms.getError());
      System.exit(1);
     }

    // _roomsList =
     // _studentList = loadData.extractStudents(null, false);
    //}
      /*else {
      new FatalProblemDlg(loadData.getError());
      System.exit(1);
    }*/
  }



  public SetOfInstructors getSetOfInstructors(){
    return _setOfInstructors;
  }

  public TTParameters getTTParameters() {
    return _ttParameters;
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

  public void setParameters(int [] a) {
    _ttParameters.setValues(a);
    sendEvent();
  }


  public void test1_setAvailability(){
 Vector v = new Vector();
 v.add("1 1 1 1 5");
 v.add("1 1 1 5 5");
 int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};

  }
} /* end class DModel */
