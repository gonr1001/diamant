/**
 *
 * Title: DDocument $Revision: 1.120 $  $Date: 2004-11-05 13:53:48 $
 * Description: DDocument is a class used to
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
 * @version $Revision: 1.120 $
 * @author  $Author: syay1801 $
 * @since JDK1.3
 */
package dInterface;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import dConstants.DConst;
import dInterface.dTimeTable.CloseCmd;
import dInterface.dTimeTable.DetailedTTPane;
import dInterface.dTimeTable.SimpleTTPane;
import dInterface.dTimeTable.TTPane;
import dInternal.DModel;
import dInternal.DModelEvent;
import dInternal.DModelListener;
import dInternal.dDataTxt.SetOfActivitiesEvent;
import dInternal.dDataTxt.SetOfActivitiesListener;
import dInternal.dDataTxt.SetOfInstructorsEvent;
import dInternal.dDataTxt.SetOfInstructorsListener;
import dInternal.dDataTxt.SetOfRoomsEvent;
import dInternal.dDataTxt.SetOfRoomsListener;
import dInternal.dDataTxt.SetOfStatesEvent;
import dInternal.dDataTxt.SetOfStatesListener;
import dInternal.dDataTxt.SetOfStudentsEvent;
import dInternal.dDataTxt.SetOfStudentsListener;
import dInternal.dOptimization.SetOfEventsEvent;
import dInternal.dOptimization.SetOfEventsListener;
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.TTStructureEvent;
import dInternal.dTimeTable.TTStructureListener;
import eLib.exit.dialog.FatalProblemDlg;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.tictac.mouseTrap.dModel.Trace;

//import dInterface.dUtil.DXTools;
//debug

public class DDocument  extends InternalFrameAdapter implements
    ActionListener, DModelListener, TTStructureListener, SetOfStatesListener,
    SetOfActivitiesListener, SetOfStudentsListener, SetOfInstructorsListener,
    SetOfRoomsListener, SetOfEventsListener{

	private DMediator _dMediator;
	private JInternalFrame _jif;
	private String _documentName;
	private TTPane _ttPane;
	private DModel _dm;
	private DStateBar _stateBar;
	private String _version;

//+++++++++++++++++++++++++++++
  Logger logger = Logger.getLogger(this.getClass().getName());
  Trace trace=new Trace();
  public DDocument() {
    PropertyConfigurator.configureAndWatch("trace"+File.separator+"log4j.conf");
  }
/*  public DDocument(boolean flag) {
    PropertyConfigurator.configureAndWatch("trace"+File.separator+"log4jreex.conf");
  }*/
  //-----------------------------
  
  //for a new timetable and a open timetable
  //for new timetable Structure and open timetable Structure from a file
  public DDocument(DMediator dMediator, String ttName, String fileName, int type) {
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(dMediator);
  	traceParams.add(ttName);
  	traceParams.add(fileName);
  	logger.info(trace.write(this,traceParams));	
    //-----------------------------
    _dMediator = dMediator;
    _dMediator.getDApplication().getJFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    _dm = new DModel(this, fileName, type);
    if(_dm.getError().length()==0){
      _dm.getTTStructure().addTTStructureListener(this);
      //ttName = modifiyDocumentName(ttName); // used only in the case of New TTStructure
      _documentName = modifiyDocumentName(ttName);
      buidDocument(true, true);
      _ttPane.updateTTPane(_dm.getTTStructure());
      _jif.addInternalFrameListener(this);
    }
    _dMediator.getDApplication().getJFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  } // end constructor DDocument()
  //-------------------------------------------
  //public DDocument(){ //rgd:it was commented for the logger(trap)
  //}
  //-------------------------------------------
  public void internalFrameActivated(InternalFrameEvent e) {
//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this,e));	
    //-----------------------------
    _dMediator.getDApplication().getToolBar().setToolBars(getTTStructure());
  }
  //-------------------------------------------
  public final JInternalFrame getJIF() {
    return _jif;
  } // end getJIF
  //-------------------------------------------
  public final String getDocumentName() {
    return _documentName;
  } // end getDocumentName
  //-------------------------------------------
  public final void setDocumentName(String name) {
//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this,name));	
    //-----------------------------
    _documentName = name;
    _jif.setTitle(name);
  } // end setDocumentName
  //-------------------------------------------
  public void setCursor(int cursorValue, Component component){
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(new Integer (cursorValue));
  	traceParams.add(component);
  	logger.info(trace.write(this,traceParams));	
    //-----------------------------
    _dMediator.getCurrentFrame().setCursor(Cursor.getPredefinedCursor(cursorValue));
    _dMediator.getDApplication().getJFrame().setCursor(Cursor.getPredefinedCursor(cursorValue));
    if(component!=null)
      component.setCursor(Cursor.getPredefinedCursor(cursorValue));
  }
  /**
   *
   * @param cursorValue
   */
  public void setCursor(int cursorValue){
//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this,cursorValue));	
    //-----------------------------

    _dMediator.getCurrentFrame().setCursor(Cursor.getPredefinedCursor(cursorValue));
    _dMediator.getDApplication().getJFrame().setCursor(Cursor.getPredefinedCursor(cursorValue));
  }
  //-------------------------------------------
  public String getError(){
    return _dm.getError();
  }
  //-------------------------------------------
  public boolean isModified(){
    return _dm.getModified();
  } // end getModified
  //-------------------------------------------
  public DModel getDM(){
    return _dm;
  } //end getDModel
  //-------------------------------------------
  public DMediator getDMediator(){
    return _dMediator;
  } //end getDModel
  //-------------------------------------------
  public TTPane getTTPane(){
    return _ttPane;
  }
  //-------------------------------------------
  public TTStructure getTTStructure() {
    return _dm.getTTStructure();
  } // end getJIF
  //-------------------------------------------
  public String getVersion(){
    return this._version;
  }
  //-------------------------------------------
  /**
   * */
  public void setVersion(String version){
//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this,version));	
    //-----------------------------
    _version=version;
  }
/*
  * a revoir
*/
  public void close(){
//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this));	
    //-----------------------------

    _jif.dispose();
    _jif = null;
    _ttPane = null;
    _stateBar = null;
  }
  //-------------------------------------------
  private String modifiyDocumentName(String str) {
//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this, str));	
    //-----------------------------

    if (str.endsWith("pref"+File.separator+"StandardTTC.xml") ||
        str.endsWith("pref"+File.separator+"StandardTTE.xml") ){
      str = str.substring(0,str.lastIndexOf("pref"));
      str += DConst.NO_NAME;
    }
    return str;
  }
  //-------------------------------------------
  public void actionPerformed(ActionEvent  e) {
  	//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this, e));	
    //-----------------------------

    if (e.getSource() instanceof CommandHolder) {
      ((CommandHolder) e.getSource()).getCommand().execute(_dMediator.getDApplication());
    }
    else {
      System.out.println("I do not know what to do, please help me (Action Performed)");
    }// end if ... else
  }// end actionPerformed
  //-------------------------------------------
  public void changeInDModel(DModelEvent  e, Component component) {
  	//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(e);
  	traceParams.add(component);
  	logger.info(trace.write(this, traceParams));	
    //-----------------------------

    setCursor(Cursor.WAIT_CURSOR, component);
    _dm.setModified();

    if (_dm.getTypeOfSchedule() == 2) {
      _dm.prepareExamsData();
    }
    //System.out.println("Type of Schedule: "+_dm.getTypeOfSchedule());
    _dm.buildSetOfEvents();
    _dm.getConditionsTest().initAllConditions();
    _dm.setStateBarComponent();
    _dm.getSetOfActivities().sortSetOfResourcesByID();

    _ttPane.updateTTPane(_dm.getTTStructure());
    _stateBar.upDateDStateBar(_dm.getSetOfStates());

    setCursor(Cursor.DEFAULT_CURSOR, component);
  }// end actionPerformed
  //-------------------------------------------
    /*
    */
  public void changeInStateBar (SetOfStatesEvent e){
  	//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this,e));	
    //-----------------------------

    _dm.setStateBarComponent();
    _stateBar.upDateDStateBar(_dm.getSetOfStates());
  }
  //-------------------------------------------
  /**
   *
   * @param e
   */
  public void changeInTTStructure(TTStructureEvent  e) {
  	//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this, e));	
    //-----------------------------
    System.out.println("I was in ttstructure listener");
    setCursor(Cursor.WAIT_CURSOR);
    _dm.setModified();
    _ttPane.updateTTPane(_dm.getTTStructure());
     _dm.setStateBarComponent();
    _stateBar.upDateDStateBar(_dm.getSetOfStates());
    setCursor(Cursor.DEFAULT_CURSOR);
  }
  //-------------------------------------------
  /**
   *
   * @param e
   * @param component
   */
  public void changeInSetOfActivities(SetOfActivitiesEvent  e, Component component) {
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(e);
  	traceParams.add(component);
  	
  	logger.info(trace.write(this, traceParams));	
    //-----------------------------
    setCursor(Cursor.WAIT_CURSOR, component);

    _dm.setModified();
    _dm.getConditionsTest().setMatrixBuilded(false,true);
    _dm.buildSetOfEvents();
    _dm.getConditionsTest().initAllConditions();
    _dm.setStateBarComponent();
    _ttPane.updateTTPane(_dm.getTTStructure());
    _stateBar.upDateDStateBar(_dm.getSetOfStates());
    _dm.getSetOfActivities().sortSetOfResourcesByID();
    setCursor(Cursor.DEFAULT_CURSOR, component);
  }// end ac

  /**
   *
   * @param e
   * @param component
   */
  public void changeInSetOfStudents(SetOfStudentsEvent  e, Component component) {
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(e);
  	traceParams.add(component);
  	logger.info(trace.write(this,traceParams));	
    //-----------------------------
    component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    _dm.setModified();
    _dm.getSetOfActivities().buildStudentRegisteredList(_dm.getSetOfStudents());
    //_dm.getConditionsTest().setMatrixBuilded(false,true);
    _dm.getConditionsTest().initAllConditions();
    _dm.setStateBarComponent();
    _ttPane.updateTTPane(_dm.getTTStructure());
    _stateBar.upDateDStateBar(_dm.getSetOfStates());
    _dm.getSetOfActivities().sortSetOfResourcesByID();
    component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }// end ac

  /**
   *
   * @param e
   * @param component
   */
  public void changeInSetOfEvents(SetOfEventsEvent  e, Component component) {
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(e);
  	traceParams.add(component);
  	logger.info(trace.write(this, traceParams));	
    //-----------------------------
    setCursor(Cursor.WAIT_CURSOR, component);

    _dm.setModified();
    _dm.buildSetOfEvents();
    //_dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
    System.out.println("SetOfEvents Event started");//debug
    _dm.getConditionsTest().initAllConditions();
    _dm.setStateBarComponent();
    _ttPane.updateTTPane(_dm.getTTStructure());
    _stateBar.upDateDStateBar(_dm.getSetOfStates());

    setCursor(Cursor.DEFAULT_CURSOR, component);
  }// end ac

  /**
   *
   * @param e
   * @param component
   */
  public void changeInSetOfInstructors(SetOfInstructorsEvent  e, Component component) {
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(e);
  	traceParams.add(component);
  	logger.info(trace.write(this, traceParams));	
    //-----------------------------
    component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    _dm.setModified();
    _dm.getSetOfStates().sendEvent();

    component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }// end ac

  /**
   *
   * @param e
   * @param component
   */
  public void changeInSetOfRooms(SetOfRoomsEvent  e, Component component) {
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(e);
  	traceParams.add(component);
  	logger.info(trace.write(this, traceParams));	
    //-----------------------------
    component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    _dm.setModified();
    _dm.getSetOfStates().sendEvent();
    component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }// end changeInSetOfRooms
  //-------------------------------------------
  public void displaySimple(){
//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this));	
    //-----------------------------
    close();
    buidDocument(true, true);
    _ttPane.updateTTPane(_dm.getTTStructure());
  }
  //-------------------------------------------
  public void displayHorizontalSplit(){
  	//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this));	
    //-----------------------------

    close();
    buidDocument(false, false);
    _ttPane.updateTTPane(_dm.getTTStructure());
  }

  public void displayVericalSplit(){
    close();
    buidDocument(false, true);
    _ttPane.updateTTPane(_dm.getTTStructure());
  }
  //-------------------------------------------
  private void  buidDocument(boolean simple, boolean vertical){
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(new Boolean (simple));
  	traceParams.add(new Boolean (vertical));
  	logger.info(trace.write(this, traceParams));	
    //-----------------------------
    /* MIN_HEIGHT is needed to ajdust the minimum
    * height of the _jif */
    final int MIN_HEIGHT = 512;
    /* MIN_WIDTH is needed to ajdust the minimum
    * width of the _jif */
    final int MIN_WIDTH = 512;
    /* MIN_HEIGHT is needed to ajdust the minimum
    * height of the _jif */
    final int MAX_HEIGHT = 2048;
    /* MIN_WIDTH is needed to ajdust the minimum
    * width of the _jif */
    final int MAX_WIDTH = 2048;
    //_documentName = title;
    _jif = new JInternalFrame(_documentName, true, true, true, true);
    _jif.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
    _jif.addInternalFrameListener( new InternalFrameAdapter() {
      public void internalFrameClosing(InternalFrameEvent e) {
        new CloseCmd().execute(_dMediator.getDApplication());
      }
    } );

    _dm.addDModelListener(this);
    _stateBar = new DStateBar(_dm.getSetOfStates());
    _dm.getSetOfStates().sendEvent();
    _jif.getContentPane().add(_stateBar, BorderLayout.SOUTH);

    _jif.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    _jif.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    //System.out.println("H " +_jif.getSize().height + "  W " + _jif.getSize().width);
    //_jif.getSize();

    if (simple) {
      _ttPane = new SimpleTTPane(_dm.getTTStructure(),
                                 _dMediator.getDApplication().getToolBar());
    } else {
      _ttPane = new DetailedTTPane(_dm.getTTStructure(),
                                     _dMediator.getDApplication().getToolBar(),
                                     vertical); //,
                                    // _dMediator.getDApplication().getJFrame().getSize());
    }
    _jif.getContentPane().add(_ttPane.getPane(), BorderLayout.CENTER);
    _jif.pack();
    _dMediator.getDApplication().getDesktop().add(_jif, new Integer(1));
    _jif.setVisible(true);
    //to comment if work with jifs
    try {
      _jif.setMaximum(true);  //This line allows the scrollbars of the TTPanel
      // to be present when the _jif is resized
    }
    catch (java.beans.PropertyVetoException pve) {
      new FatalProblemDlg("I was in DDocument trying to make steMaximum!!!" );
      System.exit(52);
      pve.printStackTrace();
    }
  } // end buidDocument
  
 
} /* end DDocument class */