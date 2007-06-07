/**
*
* Title: ConflictsOfAnEventJDlg $Revision: 1.3 $  $Date: 2007-06-07 18:00:53 $
* Description: ConflictsOfAnEventJDlg is a class used to
*              display the so called Conflicts Of An Event which
*              gives the conflicts between an event and the others events
*              that are in the TTStructure
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
* @version $Revision: 1.3 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package dInterface.dTimeTable;



import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import dInterface.DToolBar;

import dInternal.DModel;
import dInternal.DResource;
import dInternal.dTimeTable.TTStructure;






public class ConflictsOfAnEventJDlg extends JDialog {
  /* ADJUST_HEIGHT is needed to ajdust the screenSize
  * minus the barSize (the value is a guess) at the bottom */
  private final static int ADJUST_HEIGHT = 88;
  /* ADJUST_WIDTH is needed to ajdust the screenSize
  * minus border pixels (the value is a guess) at each side of the screen */
  private final static int ADJUST_WIDTH = 6;
  private TTPane _ttPane;
  private TTStructure _partialTTStruct;
  //private TTStructure _totalTTStruct;
  private DToolBar _toolBar;

  /**
   * constructor
   */
  public ConflictsOfAnEventJDlg(JDialog jDialog, 
  									DToolBar toolbar,
  									DResource event,
									DModel dm) {
    super(jDialog, "COAEJDLG : "+ event.getID(), true);
    _toolBar = toolbar;   
    initDlg(event, dm);
  }

  public void initDlg(DResource event, DModel dm){
  	TTStructure totalTTStruct = dm.getTTStructure();
  	_partialTTStruct = totalTTStruct.cloneCurrentTTS();
  	dm.getConditionsToTest().buildAllConditions(_partialTTStruct);
  	_partialTTStruct.getCurrentCycle().resetAllNumberOfConflicts();
  	
  	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(new Dimension(screenSize.width - ADJUST_WIDTH,
                                screenSize.height - ADJUST_HEIGHT));
    //DResource event = dm.getSetOfEvents().getResource(eventName);
    _ttPane = new ConflictsOfAnEventTTPane(totalTTStruct,_partialTTStruct, _toolBar, true, event);
    dm.getConditionsToTest().addEventInAllPeriods(_partialTTStruct, event);
    
    _ttPane.updateTTPane(_partialTTStruct);
    //setColorOfPanel(dayIndex,seqIndex,perIndex,duration,((EventAttach)event.getAttach()).isPlaceInAPeriod());
    //end set colors;
     
    
    this.getContentPane().add(_ttPane.getPane());

    this.setVisible(true);
  }

   
  	
}// end ManualImprovementDetailed
