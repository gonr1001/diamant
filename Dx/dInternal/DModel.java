/**
 *
 * Title: DModel $Revision: 1.101 $  $Date: 2004-06-09 19:29:18 $
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
 * @version $Revision: 1.101 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInternal;

import java.util.Vector;

import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import dInterface.DDocument;
import dInternal.dData.*;
import dInternal.dXMLData.*;
import dInternal.dXMLData.rooms.SetOfCategories;
import dInternal.dConditionsTest.*;

import dInternal.dUtil.DXValue;

import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.TTStructureListener;
import dInternal.dTimeTable.TTStructureEvent;
import dInternal.dConditionsTest.SetOfEvents;


import dConstants.DConst;

public class DModel extends DModelProcess implements DModelListener, TTStructureListener {
  private Vector _dmListeners = new Vector();
  private int _type;
  private boolean _importDone = false;
  private boolean _modified = false;
  protected boolean _isTimeTable=true;
  protected int _constructionState=0;// tell where the time construction is
  private String _version;
  private String _error;
  protected static SetOfStates _setOfStates;
  protected static SetOfInstructors _setOfInstructors=null;
  protected static SetOfRooms _setOfRooms=null;
  protected static SetOfCategories _setOfCategories=null;
  protected static SetOfStudents _setOfStudents=null;
  protected static SetOfActivities _setOfActivities=null;
 // private DApplication _dApplic;
  private DDocument _dDocument=null;
  protected TTStructure _ttStruct;
  protected SetOfEvents _setOfEvents;
  private int _currentCycle = 1;
  protected TestConditions _conditionTest;
  /**
   * _setOfImportErrors contains a DXValue object where
   * the intvalue is the type of resource 0= activities, 1= students,
   * 2= instructors, 3 = rooms, 4= other
   * and string value is the error message
   */
  private SetOfResources _setOfImportErrors=null;//

  /**
   * intvalue is between 0-1000 and give the state of the progress bar
   */
  private DXValue _progressBarState;
  //private DModelProcess _dmProcess;


  /**
   * for new and open Timetable
   * for new TTStructure and open a TTStructure from a file
   * @param dApplic
   * @param fileName
   * @param type
   */
  public DModel(DDocument dDocument, String fileName, int type) {
    //System.out.println("type: "+type);
    setModel(this);
    _error = "";
    _setOfStates = new SetOfStates();
    _setOfEvents = new SetOfEvents(this);
    _setOfImportErrors= new SetOfResources(99);
    _progressBarState= new DXValue();
    _progressBarState.setIntValue(0);
    _dDocument = dDocument;
    if(fileName.endsWith(".dia")){//if(fileName.endsWith(".dia")){
      _error=loadTimeTable(fileName);
      _isTimeTable=true;
    }else if(fileName.endsWith(DConst.DOT_XML)){
      _ttStruct = new TTStructure();
      _error=_ttStruct.loadTTStructure(fileName);
      if(type==0)
        _isTimeTable=false;
      if((type==1)||(type==2))
        _isTimeTable=true;
    }else{
      _error="Wrong type of file";
    }
    _conditionTest = new TestConditions(this);
    _type = type;
    _modified = false;
    //System.out.println("Max number of periods in a sequence: "+ _ttStruct.getCurrentCycle().getMaxNumberOfPeriodsInASequence());//debug
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
  public DXValue getProgressBarState(){

    return _progressBarState;
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
  public boolean getImportDone(){
    return _importDone;
  }
  /**
   *
   * @return
   */
  public void setImportDone(boolean v){
    _importDone = v;
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
  public DDocument getDDocument(){
    return _dDocument;
  }

  /**
   *
   */
  public void incrementModification() {
    //_setOfStates.incrementModification();
    //sendEvent();
  }

  /**
   *
   * @param fileName
   * @return
   */
  public String loadTimeTable(String fileName){
	//	debug for xml file to be remove
	 // ysyam
	 if(DConst.DEVELOPMENT){
		 String filename= "XMLData"+ File.separator+"ImportFiles.xml";
		 XMLLoadData xmlloadData = new XMLLoadData(filename, this);
		 _setOfCategories= xmlloadData.extractRooms(null, true);
	 }
	 //	end debug

    LoadData loadD = new LoadData(this);
    Vector project = loadD.loadProject(fileName);

    if (project.size()!=0) {
      setVersion((String)project.get(0));
      _ttStruct= (TTStructure)project.get(1);
      // _dApplic.getDMediator().getCurrentDoc().addTTListener(_ttStruct);
      if (_ttStruct.getError().length() != 0)
        return _ttStruct.getError();
      _setOfInstructors = (SetOfInstructors)project.get(2);
      resizeResourceAvailability(_setOfInstructors);//
      _setOfRooms= (SetOfRooms)project.get(3);
      resizeResourceAvailability(_setOfRooms);//
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
      buildSetOfEvents();
      addAllListeners();
    }
    _constructionState=1;
    //_setOfStates.sendEvent();
    setImportDone(false);
    return"";
  }

  public void prepareExamsData(){
    // supprime natures2
    // supprime groups
    // supprime events
    for(int i =0; i < _setOfActivities.size(); i++) {
      Activity activity = (Activity)_setOfActivities.getResourceAt(i).getAttach();
      activity.getSetOfTypes().getSetOfResources().removeAllElements();
      activity.addType("1");
      Type type = (Type) activity.getSetOfTypes().getResource("1").getAttach();
      type.addSection("01");
      Section section = (Section) type.getSetOfSections().getResource("01").getAttach();
      section.addUnity("1",1,true);
      ((Unity) section.getSetOfUnities().getResource("1").getAttach()).setDuration(180);
      //unity.addAssignment("1");
    }
    // set new instructor availability
    for(int i = 0; i < _setOfInstructors.size(); i++) {
      ((InstructorAttach) _setOfInstructors.getResourceAt(i).getAttach()).setFullAvailability();
    }

    for(int i = 0; i < _setOfRooms.size(); i++) {
      ((RoomAttach) _setOfRooms.getResourceAt(i).getAttach()).setFullAvailability();
    }

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

  public void addAllListeners(){
    if (_setOfActivities!=null)
      _setOfActivities.addSetOfActivitiesListener(_dDocument);
    if(_setOfStudents!=null)
      _setOfStudents.addSetOfStudentsListener(_dDocument);
    if(_setOfInstructors!=null)
      _setOfInstructors.addSetOfInstructorsListener(_dDocument);
    if(_setOfRooms!=null)
      _setOfRooms.addSetOfRoomsListener(_dDocument);
    if(_setOfStates!=null)
      _setOfStates.addSetOfStatesListener(_dDocument);
    if(_setOfEvents!=null)
      _setOfEvents.addSetOfEventsListener(_dDocument);

  }

  /**
   *
   * @param str
   * @return
   */
  public String importData(String str) {
    // debug for xml file to be remove
	// ysyam
	if(DConst.DEVELOPMENT){
		String filename= "XMLData"+ File.separator+"ImportFiles.xml";
		XMLLoadData xmlloadData = new XMLLoadData(filename, this);
		_setOfCategories= xmlloadData.extractRooms(null, true);
	}
	//	end debug

    LoadData loadData = new LoadData(str, this);
    _dDocument.setCursor(Cursor.WAIT_CURSOR);
    // import set of instructors
    _setOfInstructors = loadData.extractInstructors(null, false);
    resizeResourceAvailability(_setOfInstructors);//
    if( _setOfInstructors.getError().length()!=0){
      return _setOfInstructors.getError();
    }

    // import set of rooms
    _setOfRooms = loadData.extractRooms(null, false);
    resizeResourceAvailability(_setOfRooms);//
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
    buildSetOfEvents();
    _setOfStates.sendEvent();
    addAllListeners();
    _dDocument.setCursor(Cursor.DEFAULT_CURSOR);
   setImportDone(true);
    return "";
  }

  //debug merge instructor file: S801.DISPROF
      //instructorsList= (SetOfInstructors) selectiveImport(instructorsList,"S801.DISPROF",true);
      // end debug

      /**
      *
      * @param str
      * @return
      */
  public String SelectiveImportData(String fileName, String selectionName) {
    String error="";
    LoadData loadData = new LoadData();
    if(selectionName.equalsIgnoreCase(DConst.IMP_SELECT_INST)){//Importation selective -- Enseignants
      _setOfInstructors= (SetOfInstructors) loadData.selectiveImport(_setOfInstructors,fileName,true);
      _setOfInstructors.sendEvent(_dDocument.getJIF());
    }else if(selectionName.equalsIgnoreCase(DConst.IMP_SELECT_ROOM)){//Importation selective -- Locaux
      _setOfRooms= (SetOfRooms) loadData.selectiveImport(_setOfRooms,fileName,true);
      _setOfRooms.sendEvent(_dDocument.getJIF());
    }else if(selectionName.equalsIgnoreCase(DConst.IMP_SELECT_ACT)){//Importation selective -- Activit�
      _setOfActivities= (SetOfActivities) loadData.selectiveImport(_setOfActivities,fileName,true);
      _conditionTest.setMatrixBuilded(false,true);
      _setOfActivities.sendEvent(_dDocument.getJIF());
    }else if(selectionName.equalsIgnoreCase(DConst.IMP_SELECT_STUD)){//Importation selective -- �tudiants
      _setOfStudents= (SetOfStudents) loadData.selectiveImport(_setOfStudents,fileName,true);
      _conditionTest.setMatrixBuilded(false,true);
      _setOfStudents.sendEvent(_dDocument.getJIF());
    }

    return error;
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
	public SetOfCategories getSetOfCategories(){
	  return _setOfCategories;
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
  public TestConditions getConditionsTest(){
    return _conditionTest;
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
  public SetOfResources getSetOfImportErrors(){
    return   _setOfImportErrors;
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
  public int getTypeOfSchedule(){
    return _type;
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
  public void sendEvent(Component component) {
    _modified = true;
    DModelEvent event = new DModelEvent(this);
    for (int i=0; i< _dmListeners.size(); i++) {
      DModelListener dml = (DModelListener) _dmListeners.elementAt(i);
      dml.changeInDModel(event, component);
      //System.out.println("Dmodel listener started: "+i);//debug
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
    //System.out.println("addDModelListener Listener ...");//debug
  }

  /**
   *
   * @param dml
   */
  public synchronized void removeTTParametersListener(DModelListener dml) {
    _dmListeners.removeElement(dml);
  }







  public void changeInDModel(DModelEvent  e, Component c) {

  }// end actionPerformed

  public void changeInTTStructure(TTStructureEvent  e) {

  }// end actionPerformed*/

  /**
   * Export data from soft to SIG
   */
  public void exportData(String dir){
    _dDocument.setCursor(Cursor.WAIT_CURSOR);
    ExportData dataExp = new ExportData(this);
    dataExp.saveExportReport(dir);
    _dDocument.setCursor(Cursor.DEFAULT_CURSOR);
  }



} /* end class DModel */