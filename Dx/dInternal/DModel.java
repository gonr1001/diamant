/**
 *
 * Title: DModel $Revision: 1.10 $  $Date: 2003-04-30 17:04:22 $
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
 * @version $Revision: 1.10 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInternal;

import java.util.Vector;
import java.io.*;
import dInternal.dData.*;

public class DModel{
  private Vector _dmListeners = new Vector();
  private TTParameters _ttParameters;
  private Status _status;
  private SetOfInstructors _instructorsList;
  private SetOfRooms _roomsList;
  private SetOfStudents _studentList;

  public DModel() {
    _status = new Status();
    _ttParameters = new TTParameters();
    importData("hello");
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
    _instructorsList = loadData.extractInstructors(null, false);
    // _roomsList =
    _studentList = loadData.extractStudents(null, false);
  }



  public SetOfInstructors getSetOfInstructors(){
    return _instructorsList;
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
} /* end class DModel */
