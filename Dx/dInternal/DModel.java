/**
 *
 * Title: DModel $Revision: 1.55 $  $Date: 2003-08-22 11:39:12 $
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
 * @version $Revision: 1.55 $
 * @author  $Author: ysyam $
 * @since JDK1.3
 */
package dInternal;

import java.util.Vector;
import java.io.*;
import javax.swing.JOptionPane;
import dInterface.DApplication;
import dInternal.dData.*;
import dResources.DConst;
import dInternal.dData.LoadData;
import dInternal.dUtil.DXToolsMethods;
import dInternal.dTimeTable.TTStructure;
import dInternal.DModelEvent;
import dInternal.DModelListener;
import dInternal.dTimeTable.TTStructureListener;
import dInternal.dTimeTable.TTStructureEvent;



public class DModel implements  DModelListener, TTStructureListener {
  private Vector _dmListeners = new Vector();
  private int _type;
  private boolean _modified = false;
  private boolean _isTimeTable=true;
  private int _constructionState=0;// tell where the time construction is
  private String _version;
  private String _error;
  private SetOfStates _setOfStates;
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
    //System.out.println("type: "+type);
    _error = "";
    _setOfStates = new SetOfStates();
    _dApplic = dApplic;
    if(fileName.endsWith(".dia")){//if(fileName.endsWith(".dia")){
      _error=loadTimeTable(fileName);
      _isTimeTable=true;
    }else if(fileName.endsWith(".xml")){
      _ttStruct = new TTStructure();
      _error=_ttStruct.loadTTStructure(fileName);
      if(type==0)
        _isTimeTable=false;
      if((type==1)||(type==2))
        _isTimeTable=true;
    }else{
      _error="Wrong type of file";
    }
    _type = type;
    _modified = false;
    if (_error.length()==0){
      _ttStruct.addTTStructureListener(this);
    }
  }

  public String getError(){
    return _error;
  }
  public boolean getModified(){
    return _modified;
  }

  public boolean isTimeTable(){
    return _isTimeTable;
  }

  public SetOfStates getSetOfStates() {
    return _setOfStates;
  }

  public DApplication getDApplication(){
    return _dApplic;
  }
  public void incrementModification() {
    _setOfStates.incrementModification();
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
      if (_ttStruct.getError().length() != 0)
        return _ttStruct.getError();
      _setOfInstructors = (SetOfInstructors)project.get(2);
      resizeInstructorsAvailability();//
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
    _constructionState=1;
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
      resizeInstructorsAvailability();//
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
     _constructionState=1;
     return "";
  }

  private String rreadTT(String fileName){
    JOptionPane.showMessageDialog(_dApplic.getJFrame(),
                         "rreadTT was here",
                          "trace",
                             JOptionPane.OK_OPTION);
    return "";
  }
/*  public String loadTTStruct(String str) {
    LoadData loadData = new LoadData(str);
    // import set of instructors
      _ttStruct = loadData.extractTTStruct();
     if( _ttStruct.getError().length()!=0){
       return _ttStruct.getError();
     }
     return "";
  }*/

  public SetOfInstructors getSetOfInstructors(){
    return _setOfInstructors;
  }

  public SetOfActivities getSetOfActivities(){
    return _setOfActivities;
  }

  public SetOfStudents getSetOfStudents(){
    return _setOfStudents;
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


  public String saveTimeTable(String filename) {
    SaveData saveD= new SaveData("1.5");
    //resizeInstructorsAvailability();//
    String error = "";
    if(_isTimeTable){
      error = saveD.saveTimeTable(_ttStruct,_setOfInstructors,_setOfRooms,_setOfActivities,_setOfStudents,filename);
      if (error.length() != 0)
        return error;
    }else{
      saveD.saveTTStructure(_ttStruct,filename);
    }
    _modified = false;
    return error;
  }

  public void sendEvent() {
    _modified = true;
    DModelEvent event = new DModelEvent(this);
    for (int i=0; i< _dmListeners.size(); i++) {
      DModelListener dml = (DModelListener) _dmListeners.elementAt(i);
      dml.changeInDModel(event);
      System.out.println("Dmodel listener started: "+i);//debug
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

  /**
   *
   */
  public void setStateBarComponent(){
    if (_constructionState>0){
      ((State)_setOfStates.getResource(DConst.SB_T_ACT).getAttach()).setValue(_setOfActivities.size());
      ((State)_setOfStates.getResource(DConst.SB_T_INST).getAttach()).setValue(_setOfInstructors.size());
      ((State)_setOfStates.getResource(DConst.SB_T_ROOM).getAttach()).setValue(_setOfRooms.size());
      ((State)_setOfStates.getResource(DConst.SB_T_STUD).getAttach()).setValue(_setOfStudents.size());

      ((State)_setOfStates.getResource(DConst.SB_CONF).getAttach()).setValue(10);

      ((State)_setOfStates.getResource(DConst.SB_C_INST).getAttach()).setValue(1);
      ((State)_setOfStates.getResource(DConst.SB_C_ROOM).getAttach()).setValue(7);
      ((State)_setOfStates.getResource(DConst.SB_C_STUD).getAttach()).setValue(2);
    }
  }


 private void resizeInstructorsAvailability(){
   int [][] matrix;
   InstructorAttach attach;
   for (int i=0; i< _setOfInstructors.size(); i++){
     attach = (InstructorAttach)_setOfInstructors.getResourceAt(i).getAttach();
     matrix=attach.getMatrixAvailability();
     matrix = DXToolsMethods.resizeAvailability(matrix,_ttStruct);
     attach.setAvailability(matrix);
   }
  }

  public void changeInDModel(DModelEvent  e) {

  }// end actionPerformed

   public void changeInTTStructure(TTStructureEvent  e) {

   }// end actionPerformed*/

} /* end class DModel */
