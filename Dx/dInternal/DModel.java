/**
 *
 * Title: DModel $Revision: 1.111 $  $Date: 2004-12-01 17:16:43 $
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
 * @version $Revision: 1.111 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInternal;

import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import java.util.Vector;

import dConstants.DConst;
import dInterface.DDocument;
import dInternal.dDataTxt.Activity;
import dInternal.dDataTxt.ExportData;
import dInternal.dDataTxt.InstructorAttach;
import dInternal.dDataTxt.LoadData;
import dInternal.dDataTxt.RoomAttach;
import dInternal.dDataTxt.SaveData;
import dInternal.dDataTxt.Section;
import dInternal.dDataTxt.SetOfActivities;
import dInternal.dDataTxt.SetOfInstructors;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dDataTxt.SetOfRooms;
import dInternal.dDataTxt.SetOfStates;
import dInternal.dDataTxt.SetOfStudents;
import dInternal.dDataTxt.Type;
import dInternal.dDataTxt.Unity;
//import dInternal.dDataTxt.*;
//import dInternal.dData.*;
import dInternal.dDataXML.XMLLoadData;
import dInternal.dDataXML.rooms.SetOfCategories;
import dInternal.dOptimization.SetOfEvents;
import dInternal.dOptimization.TestConditions;
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.TTStructureEvent;
import dInternal.dTimeTable.TTStructureListener;
import dInternal.dUtil.DXValue;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.tictac.mouseTrap.dModel.Trace;

public class DModel extends DModelProcess implements DModelListener, TTStructureListener {
  private Vector _dmListeners = new Vector();
  private int _type;
  private boolean _importDone; 
  private boolean _mergeDone; 
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
  private SetOfResources _setOfImportErrors=null; //
  private SetOfResources _setOfImportSelErrors=null; //

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
//+++++++++++++++++++++++++++++
  Logger logger = Logger.getLogger(this.getClass().getName());
  Trace trace=new Trace();
  public DModel() {
    PropertyConfigurator.configureAndWatch("trace"+File.separator+"log4j.conf");
  }
  
  public DModel(boolean flag) {
  	flag=flag && true;
    PropertyConfigurator.configureAndWatch("trace"+File.separator+"log4jreex.conf");
  }
  //-----------------------------
  public DModel(DDocument dDocument, String fileName, int type) {
	//+++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(dDocument);
  	traceParams.add(fileName);
  	traceParams.add(new Integer(type));
  	logger.info(trace.write(this,traceParams));	
    //-----------------------------
	
    setModel(this);
    _error = "";
    _importDone = false;
    _mergeDone = false;
    _setOfStates = new SetOfStates();
    _setOfEvents = new SetOfEvents(this);
    _setOfImportErrors= new SetOfResources(99);
    _setOfImportSelErrors= new SetOfResources(99);
    _progressBarState= new DXValue();
    _progressBarState.setIntValue(0);
    _dDocument = dDocument;
    if(fileName.endsWith(DConst.DOT_DIA)){ 	
      _error = loadTimeTable(fileName, getCurrentDir(fileName));
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
    if (_error.length()==0)
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
   	//+++++++++++++++++++++++++++++
   	logger.info(trace.write(this));	
     //-----------------------------

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
 public boolean getMergeDone(){
   return _mergeDone;
 }
  /**
   *
   * @return
   */
  public void setImportDone(boolean v){
    //+++++++++++++++++++++++++++++
   	logger.info(trace.write(this, v));	
    //-----------------------------
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



  public void prepareExamsData(){
  	 //+++++++++++++++++++++++++++++
   	logger.info(trace.write(this));	
    //-----------------------------
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
  	//  +++++++++++++++++++++++++++++
   	logger.info(trace.write(this, version));	
    //----------------------------- 
    _version=version;
  }

  public void addAllListeners(){
  	//  +++++++++++++++++++++++++++++
   	logger.info(trace.write(this));	
    //----------------------------- 

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
  * @param fileName
  * @return
  */
 public String loadTimeTable(String fileName, String currentDir){
 	
   //+++++++++++++++++++++++++++++
 	Vector traceParams=new Vector();
 	traceParams.add(fileName);
 	traceParams.add(currentDir);
  	logger.info(trace.write(this, traceParams));
   //-----------------------------

	//	debug for xml file to be remove
	 // ysyam
	 if(DConst.DEVELOPMENT){
		 String filename= "XMLData"+ File.separator+"ImportFiles.xml";
		 XMLLoadData xmlloadData = new XMLLoadData(filename, this);
		 _setOfCategories= xmlloadData.extractRooms(null, true);
	 }
	 //	end debug

   LoadData loadD = new LoadData(this);
   Vector theTT = loadD.loadTheTT(fileName, currentDir);

   if (theTT.size()!=0) {
     setVersion((String)theTT.get(0));
     _ttStruct= (TTStructure)theTT.get(1);
     // _dApplic.getDMediator().getCurrentDoc().addTTListener(_ttStruct);
     if (_ttStruct.getError().length() != 0)
       return _ttStruct.getError();
     _setOfInstructors = (SetOfInstructors)theTT.get(2);
     resizeResourceAvailability(_setOfInstructors);//
     _setOfRooms= (SetOfRooms)theTT.get(3);
     resizeResourceAvailability(_setOfRooms);//
     _setOfActivities=(SetOfActivities)theTT.get(4);
     _setOfStudents = (SetOfStudents)theTT.get(5);
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
   return "";
 }
  /**
   *
   * @param str
   * @return
   */
  public String importData(String str) {
  	//  +++++++++++++++++++++++++++++
   	logger.info(trace.write(this, str));	
    //----------------------------- 

    // debug for xml file to be remove
	// ysyam
	if(DConst.DEVELOPMENT){
		String filename= "XMLData"+ File.separator+"ImportFiles.xml";
		XMLLoadData xmlloadData = new XMLLoadData(filename, this);
		_setOfCategories= xmlloadData.extractRooms(null, true);
	}
	//	end debug

    LoadData loadData = new LoadData( this, str);
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


      /**
      *
      * @param str
      * @return
      */
  public String mergeData(String fileName, String selectionName) {
 	//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(fileName);
  	traceParams.add(selectionName);
   	logger.info(trace.write(this, traceParams));	
    //----------------------------- 
   	
   	_setOfImportSelErrors= new SetOfResources(99);
    String error="";
    LoadData loadData = new LoadData(this);
    if(selectionName.equalsIgnoreCase(DConst.IMP_SELECT_INST)){//Importation selective -- Enseignants
      _setOfInstructors= (SetOfInstructors) loadData.selectiveImport(_setOfInstructors,fileName);
      resizeResourceAvailability(_setOfInstructors);
      error = _setOfInstructors.getError();
      _setOfInstructors.sendEvent(_dDocument.getJIF());
    }else if(selectionName.equalsIgnoreCase(DConst.IMP_SELECT_ROOM)){//Importation selective -- Locaux
      _setOfRooms= (SetOfRooms) loadData.selectiveImport(_setOfRooms,fileName);
      resizeResourceAvailability(_setOfRooms);
      error = _setOfRooms.getError();
      _setOfRooms.sendEvent(_dDocument.getJIF());
    }else if(selectionName.equalsIgnoreCase(DConst.IMP_SELECT_ACT)){//Importation selective -- Activit�
      _setOfActivities= (SetOfActivities) loadData.selectiveImport(_setOfActivities,fileName);
      error = _setOfActivities.getError();
      
      _conditionTest.setMatrixBuilded(false,true);
      _setOfActivities.sendEvent(_dDocument.getJIF());
    }else if(selectionName.equalsIgnoreCase(DConst.IMP_SELECT_STUD)){//Importation selective -- �tudiants
      _setOfStudents= (SetOfStudents) loadData.selectiveImport(_setOfStudents,fileName);
      error = _setOfStudents.getError();
      _conditionTest.setMatrixBuilded(false,true);
      _setOfStudents.sendEvent(_dDocument.getJIF());
    }
    _mergeDone= true;
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
 public SetOfResources getSetOfImportSelErrors(){
   return   _setOfImportSelErrors;
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
  	//  +++++++++++++++++++++++++++++
   	logger.info(trace.write(this, filename));	
    //-----------------------------
    SaveData saveD= new SaveData("1.5");
    String error = "";
    if(_isTimeTable){
      error = saveD.saveTimeTable(_ttStruct,_setOfInstructors,_setOfRooms,_setOfActivities,_setOfStudents,filename);
      if (error.length() != 0)
        return error;
    }else{
      saveD.saveTTStructure(_ttStruct,filename);
    }
    _mergeDone= false;
    
    _modified = false;
    return error;
  }

  /**
   *
   */
  public void sendEvent(Component component) {
  	//  +++++++++++++++++++++++++++++
   	logger.info(trace.write(this, component));	
    //-----------------------------
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
  	//  +++++++++++++++++++++++++++++
   	logger.info(trace.write(this, dml));	
    //-----------------------------
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
  	//  +++++++++++++++++++++++++++++
   	logger.info(trace.write(this, dml));	
    //-----------------------------
    _dmListeners.removeElement(dml);
  }







 public void changeInDModel(DModelEvent  e, Component c) {
 	
  }// end actionPerformed

  public void changeInTTStructure(TTStructureEvent  e) {
  	
  }// end actionPerformed

  /**
   * Export data from soft to SIG
   */
  public void exportData(String dir){
    _dDocument.setCursor(Cursor.WAIT_CURSOR);
    ExportData dataExp = new ExportData(this);
    dataExp.saveExportReport(dir);
    _dDocument.setCursor(Cursor.DEFAULT_CURSOR);
  }

  private String getCurrentDir(String fileName){
  	if(fileName.lastIndexOf(File.separator)>0) {
  		return fileName.substring(0,fileName.lastIndexOf(File.separator));
  	}
	return "";
  }

} /* end class DModel */