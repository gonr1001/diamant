/**
 *
 * Title: DModel $Revision: 1.39 $  $Date: 2003-07-09 16:26:54 $
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
 * @version $Revision: 1.39 $
 * @author  $Author: alexj $
 * @since JDK1.3
 */
package dInternal;

import java.util.Vector;
import java.io.*;
import javax.swing.JOptionPane;
import dInterface.DApplication;
import dInternal.dData.*;
import dInternal.dData.LoadData;

import dInternal.dTimeTable.TTStructure;



public class DModel {
  private Vector _dmListeners = new Vector();
  //private TTParameters _ttParameters;
  private int _type;
  private boolean _modified = false;
  private String _version;
  private String _error;
  private Status _status;
  private SetOfInstructors _setOfInstructors=null;
  private SetOfRooms _setOfRooms=null;
  private SetOfStudents _setOfStudents=null;
  private SetOfActivities _setOfActivities=null;
  private DApplication _dApplic;
  private TTStructure _ttStruct;
  private int _currentCycle = 1;

  //for new and open Timetable
  //for new TTStructure and open a TTStructure from a file
  public DModel(DApplication dApplic, String fileName, int type) {
    _error = "";
    _status = new Status();
    _dApplic = dApplic;
    if(fileName.endsWith(".dia")){
      _error=loadTimeTable(fileName);
    }else if(fileName.endsWith(".xml")){
      _ttStruct = new TTStructure();
      _error=_ttStruct.loadTTStructure(fileName);
    }else{
      _error="Wrong type of file";
    }
    _type = type;
    _modified = false;
  }

  public String getError(){
    return _error;
  }
  public boolean getModified(){
    return _modified;
  }

  public Status getStatus() {
    return _status;
  }

  public DApplication getDApplication(){
    return _dApplic;
  }
  public void incrementModification() {
    _status.incrModif();
    sendEvent();
  }

  /**
   *
   * */
  public String loadTimeTable(String fileName){
    LoadData loadD = new LoadData();
    Vector project = loadD.loadProject(fileName);
    if (project.size()!=0) {
      setVersion((String)project.get(0));
      _ttStruct= (TTStructure)project.get(1);
      // _dApplic.getDMediator().getCurrentDoc().addTTListener(_ttStruct);
      // addTTStructureListener(this);
      _setOfInstructors = (SetOfInstructors)project.get(2);
      _setOfRooms= (SetOfRooms)project.get(3);
      _setOfActivities=(SetOfActivities)project.get(4);
      _setOfStudents = (SetOfStudents)project.get(5);
      if( _setOfRooms.getError().length()!=0){
        return _setOfRooms.getError();
      }
      if( _setOfInstructors.getError().length()!=0){
        return _setOfInstructors.getError();
      }
      if( _setOfActivities.getError().length()!=0){
        return _setOfActivities.getError();
      }
      if( _setOfStudents.getError().length()!=0){
        return _setOfStudents.getError();
      }
    }
    _ttStruct.sendEvent();// a deplacer
    return"";
  }


  /***/
public String getVersion(){
  return _version;
}

/**
 * */
public void setVersion(String version){
  _version=version;
  }

  public String importData(String str) {
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

  private String rreadTT(String fileName){
    JOptionPane.showMessageDialog(_dApplic.getJFrame(),
                         "rreadTT was here",
                          "trace",
                             JOptionPane.OK_OPTION);
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

  public int getCurrentCycle() {
  return _currentCycle;
  }

//this method must be renamed to saveTT
 /* public String rsaveTT(String filename) {
    JOptionPane.showMessageDialog(_dApplic.getJFrame(),
                             "rsaveTT was here",
                              "trace",
                                 JOptionPane.OK_OPTION);
    return "";
  }*/


  public void saveTimeTable(String filename) {
    SaveData saveD= new SaveData("1.5");
    saveD.saveTimeTable(_ttStruct,_setOfInstructors,_setOfRooms,_setOfActivities,_setOfStudents,filename);
    _modified = false;
  }

  public void sendEvent() {
    _modified = true;
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
