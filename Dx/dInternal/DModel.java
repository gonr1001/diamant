/**
 *
 * Title: DModel $Revision: 1.64 $  $Date: 2003-09-08 15:23:19 $
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
 * @version $Revision: 1.64 $
 * @author  $Author: rgr $
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
import dInternal.dConditionsTest.SetOfEvents;



public class DModel extends DModelProcess implements  DModelListener, TTStructureListener {
  private Vector _dmListeners = new Vector();
  private int _type;
  private boolean _modified = false;
  protected boolean _isTimeTable=true;
  protected int _constructionState=0;// tell where the time construction is
  private String _version;
  private String _error;
  protected SetOfStates _setOfStates;
  protected SetOfInstructors _setOfInstructors=null;
  protected SetOfRooms _setOfRooms=null;
  protected SetOfStudents _setOfStudents=null;
  protected SetOfActivities _setOfActivities=null;
  private DApplication _dApplic;
  protected TTStructure _ttStruct;
  protected SetOfEvents _setOfEvents;
  private int _currentCycle = 1;
  //private DModelProcess _dmProcess;


  /**
   * for new and open Timetable
   * for new TTStructure and open a TTStructure from a file
   * @param dApplic
   * @param fileName
   * @param type
   */
  public DModel(DApplication dApplic, String fileName, int type) {
    //System.out.println("type: "+type);
    setModel(this);
    _error = "";
    _setOfStates = new SetOfStates();
    _setOfEvents = new SetOfEvents();
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
    //_dmProcess = new DModelProcess(this);
    _modified = false;
  }

  /**
   *
   * @return
   */
  public String getError(){
    return _error;
  }

  /**
   *
   * @return
   */
  public boolean getModified(){
    return _modified;
  }
  /**
   *
   * @return
   */
  public void setModified(){
     _modified = true;
  }
  /**
   *
   * @return
   */
  public boolean isTimeTable(){
    return _isTimeTable;
  }

  /**
   *
   * @return
   */
  public SetOfStates getSetOfStates() {
    return _setOfStates;
  }

  /**
   *
   * @return
   */
  public DApplication getDApplication(){
    return _dApplic;
  }

  /**
   *
   */
  public void incrementModification() {
    //_setOfStates.incrementModification();
    sendEvent();
  }

  /**
   *
   * @param fileName
   * @return
   */
  public String loadTimeTable(String fileName){
    LoadData loadD = new LoadData();
    Vector project = loadD.loadProject(fileName);

    if (project.size()!=0) {
      setVersion((String)project.get(0));
      _ttStruct= (TTStructure)project.get(1);
      // _dApplic.getDMediator().getCurrentDoc().addTTListener(_ttStruct);
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
      this.buildSetOfEvents();// yannick
      if((_setOfActivities!=null) && (_setOfStudents!=null))
        _setOfActivities.buildStudentRegisteredList(_setOfStudents);
    }
    _constructionState=1;
    //_setOfStates.sendEvent();
    return"";
  }


  /**
   *
   * @return
   */
  public String getVersion(){
    return _version;
  }

  /**
   *
   * @param version
   */
  public void setVersion(String version){
    _version=version;
  }

  /**
   *
   * @param str
   * @return
   */
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
    this.buildSetOfEvents();// yannick
    if((_setOfActivities!=null) && (_setOfStudents!=null))
      _setOfActivities.buildStudentRegisteredList(_setOfStudents);
    //_setOfStates.sendEvent();
    return "";
  }

  /**
   *
   * @return
   */
  public SetOfInstructors getSetOfInstructors(){
    return _setOfInstructors;
  }

  /**
   *
   * @return
   */
  public SetOfActivities getSetOfActivities(){
    return _setOfActivities;
  }

  /**
   *
   * @return
   */
  public SetOfRooms getSetOfRooms(){
    return _setOfRooms;
  }

  /**
   *
   * @return
   */
  public SetOfEvents getSetOfEvents(){
    return _setOfEvents;
  }

  /**
   *
   * @return
   */
  public SetOfStudents getSetOfStudents(){
    return _setOfStudents;
  }

  /**
   *
   * @return
   */
  public TTStructure getTTStructure() {
    return _ttStruct;
  }

  /**
   *
   * @return
   */
  public int getCurrentCycle() {
    return _currentCycle;
  }

  /**
   *
   * @param filename
   * @return
   */
  public String saveTimeTable(String filename) {
    SaveData saveD= new SaveData("1.5");
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

  /**
   *
   */
  public void sendEvent() {
    _modified = true;
    DModelEvent event = new DModelEvent(this);
    for (int i=0; i< _dmListeners.size(); i++) {
      DModelListener dml = (DModelListener) _dmListeners.elementAt(i);
      dml.changeInDModel(event);
      System.out.println("Dmodel listener started: "+i);//debug
    }
  }

  /**
   *
   * @param dml
   */
  public synchronized void addDModelListener(DModelListener dml) {
    if (_dmListeners.contains(dml)){
      return;
    }
    _dmListeners.addElement(dml);
  }

  /**
   *
   * @param dml
   */
  public synchronized void removeTTParametersListener(DModelListener dml) {
    _dmListeners.removeElement(dml);
  }







  public void changeInDModel(DModelEvent  e) {

  }// end actionPerformed

  public void changeInTTStructure(TTStructureEvent  e) {

  }// end actionPerformed*/



} /* end class DModel */