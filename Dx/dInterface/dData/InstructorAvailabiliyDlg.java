/**
 *
 * Title: InstructorAvailabiliyDlg $Revision: 1.13 $  $Date: 2004-05-18 17:28:14 $
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
 * @version $Revision: 1.13 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */
package dInterface.dData;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import dInterface.DApplication;
import dInterface.dUtil.TwoButtonsPanel;
import dInterface.dUtil.ButtonsPanel;
//import dInterface.dUtil.DXTools;
import dInternal.dData.InstructorAttach;


import dResources.DConst;



/**
 * Dialog used to set disponibilities for an instructor.  The user must select
 * the corresponding teacher in the combo list and click on a period to
 * select/deselect it.  A selected period (pressed) means that the teacher
 * is available at that period.
 *
 * User must be warned that changes made in this dialog box is only used by
 * DIAMANT and so does not represent what data is actually in the central
 * database.  In fact, as long as a difference exists between both sets of data,
 * user must be warned of this difference in the import/export report...
 *
 * The grid for each instructor is constructed to follow the standard model
 * proposed by the STI
 *
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
public class InstructorAvailabiliyDlg  extends JDialog
                                implements ActionListener,
                                           ItemListener {
  private DApplication _dApplic;
  private int nbPer;
  private int nbDay;
  private String[] day;
  public String[] time;

  private ButtonsPanel _applyPanel;// = new JPanel();
  private JPanel chooserPanel = new JPanel();
  private JPanel centerPanel;

  private JComboBox chooser;
  private Vector _posVect;
  private int nbPerParJour;
  private boolean modified = false;

  private InstructorAttach  _currentInstr;
  private int [][] _currentAvailbility;


  /**
   * Default constructor.
   *
   * @param owner The component on which the dialog will be displayed.
   * @param doc The active document.  Used to access the dictionnaries.
   */
  //public InstructorAvailabiliyDlg(JFrame jFrame, String str, DModel dm) {
    public InstructorAvailabiliyDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), DConst.INST_ASSIGN_TD +"rgr", false);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    //_dm = _dApplic.getDMediator().getCurrentDoc().getDM();
    time = _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getCurrentCycle().getHourOfPeriodsADay();
    nbDay= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getNumberOfActiveDays();
    day = new String[nbDay];
    //MES00 = DConst.AVAILABILITIES;
    for(int i=0; i< nbDay; i++)
      day[i]= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure()._weekTable[i];
    nbPer= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getCurrentCycle().getMaxNumberOfPeriodsADay();
    try {
      initialize();
      pack();
      setLocationRelativeTo(_dApplic.getJFrame());
      setVisible(true);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  } // end InstructorAvailabiliyDlg

  /**
   * Component's initialisation and placement.
   */
  private void initialize() throws Exception {
    //chooser Panel
    //creates the JComboBox with the list of all instructors
    chooser = new JComboBox(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfInstructors().getNamesVector(1));
    chooser.addItemListener( this );
    chooserPanel.add(chooser, null);
    this.getContentPane().add(chooserPanel, BorderLayout.NORTH);

    //gridPanel
    String sel = (String)chooser.getSelectedItem();
    _currentInstr = (InstructorAttach)_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfInstructors().getResource(sel).getAttach();
    centerPanel = makeGridPanel(_currentInstr);
    this.getContentPane().add(centerPanel, BorderLayout.CENTER );

    //_applyPanel
    String [] a ={DConst.BUT_APPLY, DConst.BUT_CLOSE};
    _applyPanel = new TwoButtonsPanel(this, a);
    //Setting the button APPLY disable
    _applyPanel.setFirstDisable();
    this.getContentPane().add(_applyPanel, BorderLayout.SOUTH);
  } // end  jbInit()

  public void actionPerformed( ActionEvent event) {
    String command = event.getActionCommand();
    if (command.equals(DConst.BUT_CLOSE)) {  // close
      dispose();
    } else if (command.equals(DConst.BUT_APPLY)) {  // apply
      _currentInstr.setAvailability(_currentAvailbility);
      modified = false;
      _applyPanel.setFirstDisable();
      _dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
    // if a button of the grid has been pressed
    } else if ( _posVect.indexOf(event.getSource() ) > -1 ) {
      int index = _posVect.indexOf(event.getSource());
      int day = index / nbPer;
      int per = index % nbPer;
      if ( ((JToggleButton)_posVect.get(index)).isSelected() ){
        _currentAvailbility [day][per] = 1;
      }
      else{
        _currentAvailbility [day][per] = 5;
      }
      modified = true;
      _applyPanel.setFirstEnable();
    }
  }

  /**
   * combobox item selected
   */
  public void itemStateChanged( ItemEvent event ) {
	_applyPanel.setFirstDisable();
    if ( event.getStateChange() == ItemEvent.SELECTED ) {
      Object source = event.getSource();
      if (source.equals( chooser ) ) {
        getContentPane().remove(centerPanel);
        String sel = (String)chooser.getSelectedItem();
        _currentInstr = (InstructorAttach)_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfInstructors().getResource(sel).getAttach();
        centerPanel = makeGridPanel(_currentInstr);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        pack();
      }
    }
  }// end itemStateChangeed


  /**
   * Creates the grid of button.  The button is pressed if the instructor
   * is free at that period, depressed if not.
   *
   * @param instr the instructor for which the grid is constructed.
   */
  private JPanel makeGridPanel(InstructorAttach instr) {
    JPanel gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(nbPer + 1, nbDay + 1));
    gridPanel.setBorder(BorderFactory.createTitledBorder(DConst.AVAILABILITIES));
    _posVect = new Vector();
    _posVect.setSize((nbPer +1 )*(nbDay+1));
    gridPanel.add(new JLabel("")); // top left corner
    for (int i = 0; i < day.length; i++)
      //first line :  name of days
      gridPanel.add(new JLabel(day[i], JLabel.CENTER));

    _currentAvailbility = _currentInstr.getMatrixAvailability();

    for (int j = 0; j < nbPer; j++) {
      // first column : the time of the period

      gridPanel.add(new JLabel(time[j], JLabel.RIGHT));
      // create a button for each day for the period
      //System.out.println(" DAInstructorDialog NbDays: "+nbDay+"   NbPerDays: "+nbPer); //DEBUG
      for (int i = 0; i < nbDay; i++) {
        JToggleButton tBut = new JToggleButton();
        tBut.setSelected(_currentAvailbility[i][j] == 1 );
        tBut.addActionListener( this );
        tBut.setPreferredSize(new Dimension(50,12));
        gridPanel.add(tBut);//, null);
        _posVect.setElementAt(tBut, (i * nbPer) + j);
      }
    }
    return gridPanel;
  }

} /* end InstructorAvailabilityDlg */