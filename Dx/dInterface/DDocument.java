/**
 *
 * Title: DDocument $Revision: 1.130 $  $Date: 2005-02-01 21:27:15 $
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
 * @version $Revision: 1.130 $
 * @author  $Author: syay1801 $
 * @since JDK1.3
 */
package dInterface;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import dConstants.DConst;
import dInterface.dTimeTable.CloseCmd;
import dInterface.dTimeTable.DetailedTTPane;
import dInterface.dTimeTable.SimpleTTPane;
import dInterface.dTimeTable.TTPane;
import dInternal.DModel;


import dInternal.dData.dInstructors.SetOfInstructorsEvent;


import dInternal.dData.dRooms.SetOfRoomsEvent;




import dInternal.dTimeTable.TTStructure;


import eLib.exit.dialog.FatalProblemDlg;

/**
 * Description: DDocument is a class used to  
 *
 *              <p>
 *              
 *
 */

public class DDocument  extends InternalFrameAdapter implements Observer
/*ActionListener, DModelListener, TTStructureListener, DSetOfStatesListener,*/
/*SetOfActivitiesListener, SetOfStudentsListener  /*,SetOfInstructorsListener,
SetOfRoomsListener, SetOfEventsListener*/{
	
	private DMediator _dMediator;
	private JInternalFrame _jif;
	private String _documentName;
	private TTPane _ttPane;
	private DModel _dm;
	private DStateBar _stateBar;
	private String _version;
	
	public DDocument() {
	} //end DDocument
	//-----------------------------
	
	
	//for a new timetable and a open timetable
	//for new timetable Structure and open timetable Structure from a file
	
	/**
	 * 
	 * @param dMediator (pattern Mediator)
	 * @param ttname This string will be displayed as the title of the JIF
	 * @param fileName is the full path file name containing the TTStructure
	 * @param type is the type of timetable to be constructed see DConst.
	 * 		 possible types NO_TYPE = 0; CYCLE = 1; EXAM = 2; CYCLEANDEXAM = 3;
	 * 
	 */
	public DDocument(DMediator dMediator, String ttName, String fileName, int type) {
		_dMediator = dMediator;
		_dMediator.getDApplication().getJFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		_dm = new DModel(this, fileName, type);
		if(_dm.getError().length()==0){
			//_dm.getTTStructure().addTTStructureListener(this);
			_documentName = modifiyDocumentName(ttName);
			buidDocument(true, true);
			_ttPane.updateTTPane(_dm.getTTStructure());
			_jif.addInternalFrameListener(this);
		}
		_dMediator.getDApplication().getJFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	} // end constructor DDocument()
	
	
	public void internalFrameActivated(InternalFrameEvent e) {
		e.toString();
		_dMediator.getDApplication().getToolBar().setToolBars(getTTStructure());
	} // end internalFrameActivated 
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
		_documentName = name;
		_jif.setTitle(name);
	} // end setDocumentName
	//-------------------------------------------
	public void setCursor(int cursorValue, Component component){
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
		_version=version;
	}
	/*
	 * a revoir
	 */
	public void close(){
		_jif.dispose();
		_jif = null;
		_ttPane = null;
		_stateBar = null;
	}
	//-------------------------------------------
	private String modifiyDocumentName(String str) {
		if (str.endsWith("pref"+File.separator+"StandardTTC.xml") ||
				str.endsWith("pref"+File.separator+"StandardTTE.xml") ){
			str = str.substring(0,str.lastIndexOf("pref"));
			str += DConst.NO_NAME;
		}
		return str;
	}
	//-------------------------------------------
	/*	public void actionPerformed(ActionEvent  e) {
	 if (e.getSource() instanceof CommandHolder) {
	 ((CommandHolder) e.getSource()).getCommand().execute(_dMediator.getDApplication());
	 }
	 else {
	 System.out.println("DDocument:I do not know what to do, please help me (Action Performed)");
	 }// end if ... else
	 }// end actionPerformed*/
	//-------------------------------------------
	
	public void update(Observable dm, Object component) {
		setCursor(Cursor.WAIT_CURSOR, (Component)component);
		_ttPane.updateTTPane(((DModel)dm).getTTStructure());
		_stateBar.upDateDStateBar(((DModel)dm).getSetOfStates());
		
		setCursor(Cursor.DEFAULT_CURSOR, (Component)component);
	}// end actionPerformed
	//-------------------------------------------
	


	/*public void changeInStateBar (DSetOfStatesEvent e){
		e.toString();
		_dm.setStateBarComponent();
		_stateBar.upDateDStateBar(_dm.getSetOfStates());
	}*/
	
	//-------------------------------------------
	/**
	 *
	 * @param e
	 */
/*	public void changeInTTStructure(TTStructureEvent  e) {
		//e.toString();
		//System.out.println("I was in changeInTTStructure");
		setCursor(Cursor.WAIT_CURSOR);
		_dm.setModified();
		_ttPane.updateTTPane(_dm.getTTStructure());
		_dm.setStateBarComponent();
		_stateBar.upDateDStateBar(_dm.getSetOfStates());
		setCursor(Cursor.DEFAULT_CURSOR);
	} // end changeInTTStructure
	//-------------------------------------------
*/
	
	/**
	 *
	 * @param e
	 * @param component
	 */

	

	
	/**
	 *
	 * @param e
	 * @param component
	 */
	public void changeInSetOfInstructors(SetOfInstructorsEvent  e, Component component) {
		e.toString();
		System.out.println("I was in changeInSetOfInstructors");
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
		e.toString();
		System.out.println("I was in changeInSetOfRooms");
		component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		_dm.setModified();
		_dm.getSetOfStates().sendEvent();
		component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}// end changeInSetOfRooms
	//-------------------------------------------
	public void displaySimple(){
		close();
		buidDocument(true, true);
		_ttPane.updateTTPane(_dm.getTTStructure());
	}
	//-------------------------------------------
	public void displayHorizontalSplit(){
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
		
		_jif = new JInternalFrame(_documentName, true, true, true, true);
		_jif.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		_jif.addInternalFrameListener( new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				e.toString();
				new CloseCmd().execute(_dMediator.getDApplication());
			}
		} );
		
		_dm.addObserver(this);
		_stateBar = new DStateBar(_dm.getSetOfStates());
		_dm.setStateBarComponent();
		_stateBar.upDateDStateBar(_dm.getSetOfStates());
		
		_jif.getContentPane().add(_stateBar, BorderLayout.SOUTH);
		
		_jif.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		_jif.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
				
		if (simple) {
			_ttPane = new SimpleTTPane(_dm.getTTStructure(),
					_dMediator.getDApplication().getToolBar());
		} else {
			_ttPane = new DetailedTTPane(_dm.getTTStructure(),
					_dMediator.getDApplication().getToolBar(),
					vertical); 
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