/**
 *
 * Title: DModel $Revision: 1.115 $  $Date: 2005-01-21 21:56:53 $
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
 * @version $Revision: 1.115 $
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

import dInternal.dData.DLoadData;
import dInternal.dData.DSaveData;
import dInternal.dData.StandardCollection;

import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dActivities.Type;
import dInternal.dData.dActivities.Unity;
import dInternal.dData.dActivities.Section;
import dInternal.dData.dActivities.SetOfActivities;


import dInternal.dData.dRooms.RoomAttach;
import dInternal.dData.dRooms.SetOfCategories;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dRooms.SetOfSites;


import dInternal.dData.dInstructors.SetOfInstructors;
import dInternal.dData.dInstructors.InstructorAttach;


import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.SetOfStudents;

import dInternal.dDataTxt.ExportData;


import dInternal.dOptimization.SetOfEvents;
import dInternal.dOptimization.TestConditions;
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.TTStructureEvent;
import dInternal.dTimeTable.TTStructureListener;
import dInternal.dUtil.DXValue;

/**
 * Description: DDocument is a class used to  
 *
 *              <p>
 *              
 *
 */

public class DModel extends DModelProcess implements DModelListener, TTStructureListener {
	private Vector _dmListeners = new Vector();
	private int _type;
	private boolean _importDone; 
	private boolean _mergeDone; 
	private boolean _modified = false;
	private boolean _isATimeTable = true;
	protected int _constructionState=0;// tell where the time construction is
	private String _version;
	private String _error;
	protected static DSetOfStates _setOfStates;
	protected static SetOfInstructors _setOfInstructors=null;
	protected static SetOfSites _setOfSites = null;
	protected static SetOfCategories _setOfCategories=null;
	protected static SetOfRooms _setOfRooms = null;	
	protected static SetOfStuSites _setOfStuSites = null;
	protected static SetOfStudents _setOfStudents = null;
	private static SetOfActivitiesSites _setOfActivitiesSite = null;
	protected static SetOfActivities _setOfActivities = null;
	private DDocument _dDocument = null;
	private TTStructure _ttStruct;
	protected SetOfEvents _setOfEvents;
	private int _currentCycle = 1;
	protected TestConditions _conditionTest;
	
	/**
	 * _setOfImportErrors contains a DXValue object where
	 * the intvalue is the type of resource 0= activities, 1= students,
	 * 2= instructors, 3 = rooms, 4= other
	 * and string value is the error message
	 */
	private DSetOfResources _setOfImportErrors=null; //
	private DSetOfResources _setOfImportSelErrors=null; //
	
	/**
	 * intvalue is between 0-1000 and give the state of the progress bar
	 */
	private DXValue _progressBarState;
	
	/**
	 * for new and open Timetable
	 * for new TTStructure and open a TTStructure from a file
	 * @param dApplic
	 * @param fileName
	 * @param type
	 */
	
	public DModel() {
	}// end DModel
	
	
	/**
	 * 
	 * @param dDocument 
	 * @param fileName is the full path file name containing the TTStructure
	 * @param type is the type of timetable to be constructed see DConst.
	 * 		 possible types NO_TYPE = 0; CYCLE = 1; EXAM = 2; CYCLEANDEXAM = 3;
	 * 
	 */
	public DModel(DDocument dDocument, String fileName, int type) {	
		setModel(this);
		_error = "";
		_importDone = false;
		_mergeDone = false;
		_setOfStates = new DSetOfStates();
		_setOfEvents = new SetOfEvents(this);
		_setOfImportErrors = new StandardCollection();
		_setOfImportSelErrors = new StandardCollection();
		_progressBarState= new DXValue();
		_progressBarState.setIntValue(0);
		_dDocument = dDocument;
		if(fileName.endsWith(DConst.DOT_DIA)){ 	
			_error = loadTimeTable(fileName, getCurrentDir(fileName));
			_isATimeTable=true;
		} else if(fileName.endsWith(DConst.DOT_XML)){
			_ttStruct = new TTStructure();
			_error=_ttStruct.loadTTStructure(fileName);
			//if(type==DConst.NO_TYPE)
			//	_isATimeTable=false;
			//if((type==DConst.CYCLE)||(type==DConst.EXAM))
				_isATimeTable=false;
		}else{
			_error= "Wrong type of file" ;
		}
		if (_error.length()==0 && _isATimeTable)
			_conditionTest = new TestConditions(this);
		_type = type;
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
	public boolean getMergeDone(){
		return _mergeDone;
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
	public boolean isATimeTable(){
		return _isATimeTable;
	}
	
	/**
	 *
	 * @return
	 */
	public DSetOfStates getSetOfStates() {
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
	 * @param fileName
	 * @return
	 */
	public String loadTimeTable(String fileName, String currentDir){				
		//	debug for xml file to be remove
		// ysyam
		/*if(DConst.DEVELOPMENT){
		 String filename= "XMLData"+ File.separator+"ImportFiles.xml";
		 XMLLoadData xmlloadData = new XMLLoadData(filename, _dm);
		 _setOfCategories= xmlloadData.extractRooms(null, true);
		 }*/
		
		DLoadData loadD = new DLoadData(this);
		Vector theTT = loadD.loadTheTT(fileName, currentDir);
		
		if (theTT.size()!=0) {
			setVersion((String)theTT.get(0));
			_ttStruct= (TTStructure)theTT.get(1);
			if (_ttStruct.getError().length() != 0)
				return _ttStruct.getError();
			_setOfInstructors = (SetOfInstructors)theTT.get(2);
			resizeResourceAvailability(_setOfInstructors);
			_setOfSites= (SetOfSites)theTT.get(3);
			resizeSiteAvailability(_setOfSites);
			_setOfActivitiesSite=(SetOfActivitiesSites)theTT.get(4);
			_setOfStuSites = (SetOfStuSites)theTT.get(5);
			if( _setOfSites.getError().length()!=0){
				return _setOfSites.getError();
			}
			if( _setOfInstructors.getError().length()!=0){
				return _setOfInstructors.getError();
			}
			if( _setOfActivitiesSite.getError().length()!=0){
				return _setOfActivities.getError();
			}
			if( _setOfStuSites.getError().length()!=0){
				return _setOfStuSites.getError();
			}
			buildSetOfEvents();
			addAllListeners();
		}
		_constructionState=1;
		setImportDone(false);
		return "";
	}
	
	
	/**
	 *
	 * @param str
	 * @return
	 */
	public String importData(String str) {		
		// debug for xml file to be remove
		// ysyam
		/*		if(DConst.DEVELOPMENT){
		 String filename= "XMLData"+ File.separator+"ImportFiles.xml";
		 XMLLoadData xmlloadData = new XMLLoadData(filename, _dm);
		 _setOfCategories= xmlloadData.extractRooms(null, true);
		 }
		 //	end debug
		  */
		DLoadData loadData = new DLoadData(this, str);
		_dDocument.setCursor(Cursor.WAIT_CURSOR);
		// import set of instructors
		_setOfInstructors = loadData.extractInstructors(null, false);
		resizeResourceAvailability(_setOfInstructors);
		if( _setOfInstructors.getError().length()!=0){
			return _setOfInstructors.getError();
		}
		
		// import set of sites
		_setOfSites = loadData.extractRooms(null, false);
		resizeResourceAvailability(_setOfSites);//
		if( _setOfSites.getError().length()!=0){
			return _setOfSites.getError();
		}
		
		// import set of activities
		_setOfActivitiesSite = loadData.extractActivities(null, false);
		if( _setOfActivitiesSite.getError().length()!=0){
			return _setOfActivitiesSite.getError();
		}
		
		// import set of students
		_setOfStuSites = loadData.extractStudents(null, false);
		if(_setOfStuSites.getError().length()!=0){
			return _setOfStuSites.getError();
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
		
		
		_setOfImportSelErrors= new StandardCollection();
		String error="";
		DLoadData loadData = new DLoadData(this);
		if(selectionName.equalsIgnoreCase(DConst.IMP_SELECT_INST)){//Importation selective -- Enseignants
			_setOfInstructors= (SetOfInstructors) loadData.selectiveImport(_setOfInstructors,fileName);
			resizeResourceAvailability(_setOfInstructors);
			error = _setOfInstructors.getError();
			_setOfInstructors.sendEvent(_dDocument.getJIF());
		}else if(selectionName.equalsIgnoreCase(DConst.IMP_SELECT_ROOM)){//Importation selective -- Locaux
			_setOfSites= (SetOfSites) loadData.selectiveImport(_setOfRooms,fileName);
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
		return (SetOfActivities)_setOfActivitiesSite.getResource(DConst.ACTIVITY_STANDARD_SITE).getAttach();
	}
	
	/**
	 *
	 * @return
	 */
	public SetOfRooms getSetOfRooms(String site){
		return (SetOfRooms) _setOfSites.getResource(site).getAttach();
	}
	
	/**
	 *
	 * @return
	 */
	public SetOfRooms getSetOfRooms(){
		SetOfCategories soc = (SetOfCategories)_setOfSites.getResource(DConst.ROOM_STANDARD_SITE).getAttach();
		return (SetOfRooms) soc.getResource(DConst.ROOM_STANDARD_CAT).getAttach();
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
		
		
		return (SetOfStudents) _setOfStuSites.getResource(DConst.STUDENT_STANDARD_SITE).getAttach();
		
	}
	
	/**
	 *
	 * @return
	 */
	public DSetOfResources getSetOfImportErrors(){
		return   _setOfImportErrors;
	}
	
	/**
	 *
	 * @return
	 */
	public DSetOfResources getSetOfImportSelErrors(){
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
		
		DSaveData saveD= new DSaveData("1.5");
		String error = "";
		if(_isATimeTable){
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
		e.toString();
		c.toString();
	}// end changeInDModel
	
	public void changeInTTStructure(TTStructureEvent  e) {
		e.toString();
	}// end changeInTTStructure
	
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