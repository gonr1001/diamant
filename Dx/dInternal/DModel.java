/**
 *
 * Title: DModel $Revision: 1.4 $  $Date: 2003-03-13 15:21:01 $
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
 * @version $Revision: 1.4 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInternal;

import java.util.Vector;
import java.io.*;

public class DModel{
  private Vector _dmListeners = new Vector();
  private TTParameters _ttParameters;
  //private LoadData _loadData;
  private InstructorsList _instructorsList;
  private RoomsList _roomsList;
  private StudentsList _studentList;

  public DModel() {
    _ttParameters = new TTParameters();
  }

  public void importData(String str) {
    LoadData loadData = new LoadData(str);
    _instructorsList = loadData.extractInstructors();
    // _roomsList =
    _studentList = loadData.extractStudents();
  }

  public InstructorsList getInstructorsList(){
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
