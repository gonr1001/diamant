/**
 *
 * Title: InstructorAvailabiliyDlg $Revision: 1.4 $  $Date: 2003-09-05 17:09:41 $
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
 * @version $Revision: 1.4 $
 * @author  $Author: ysyam $
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
import dInterface.dUtil.DXTools;
import dInternal.dData.InstructorAttach;
import dInternal.DModel;
import dInternal.dTimeTable.TTStructure;
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
 * @author  David Vallee
 * @version     %I%, %G%
 * @since 1.3
 */
public class InstructorAvailabiliyDlg  extends JDialog
                                implements ActionListener,
                                           ItemListener {
  private DApplication _dApplic;
  private int nbPer;
  private int nbDay;
  private String[] DAY;
  public String[] TIME;
  public String[] buttonsNames = {DConst.BUT_OK, DConst.BUT_APPLY, DConst.BUT_CANCEL};
  private String MES00 ;
  JPanel butPanel = new JPanel();
  JPanel chooserPanel = new JPanel();
  JPanel centerPanel;
  JButton butApply;
  JComboBox chooser;
  Vector _posVect;
  int nbPerParJour;
  private boolean modified = false;
  private DModel _dm;
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
    super(dApplic.getJFrame(), DConst.INST_ASSIGN_TD, false);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    _dm = _dApplic.getDMediator().getCurrentDoc().getDM();
    TIME= _dm.getTTStructure().getCurrentCycle().getHourOfPeriodsADay();
    nbDay= _dm.getTTStructure().getNumberOfActiveDays();
    DAY = new String[nbDay];
    MES00 = DConst.AVAILABILITIES;
    for(int i=0; i< nbDay; i++)
      DAY[i]= _dm.getTTStructure()._weekTable[i];
    nbPer= _dm.getTTStructure().getCurrentCycle().getMaxNumberOfPeriodsADay();
    try {
      jbInit();
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
  private void jbInit() throws Exception {
    //chooser Panel
    //creates the JComboBox with the list of all instructors
    chooser = new JComboBox(_dm.getSetOfInstructors().getNamesVector());
    chooser.addItemListener( this );
    chooserPanel.add(chooser, null);
    this.getContentPane().add(chooserPanel, BorderLayout.NORTH);

    //gridPanel
    String sel = (String)chooser.getSelectedItem();
    _currentInstr = (InstructorAttach)_dm.getSetOfInstructors().getResource(sel).getAttach();
    centerPanel = makeGridPanel(_currentInstr);
    this.getContentPane().add(centerPanel, BorderLayout.CENTER );

    //button Panel
    butPanel = DXTools.buttonsPanel(this, buttonsNames);
    butApply = (JButton)butPanel.getComponent(1);
    this.getContentPane().add(butPanel, BorderLayout.SOUTH);
  } // end  jbInit()

  public void actionPerformed( ActionEvent event) {
    String command = event.getActionCommand();
    if (command.equals( buttonsNames[2])) {  // cancel
      //"Enseignants --> Bouton Annuler pressé\n"
      dispose();
    } else if (command.equals(buttonsNames[0])) {  // OK
   /*   _ddv._jFrame._log.append("Enseignants --> Bouton OK pressé\n"); */
       _currentInstr.setAvailability(_currentAvailbility);
        _dm.incrementModification();
      modified = false;
      butApply.setEnabled(false);
      dispose();
    } else if (command.equals(buttonsNames[1])) {  // apply
    /*  "Enseignants --> Bouton Appliquer pressé\n");*/
      _currentInstr.setAvailability(_currentAvailbility);
      _dm.incrementModification();
      modified = false;
      butApply.setEnabled( false );
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
      butApply.setEnabled( true );
    }
  }

  /**
   * combobox item selected
   */
  public void itemStateChanged( ItemEvent event ) {
    if ( event.getStateChange() == event.SELECTED ) {
      Object source = event.getSource();
      if (source.equals( chooser ) ) {
        getContentPane().remove(centerPanel);
        String sel = (String)chooser.getSelectedItem();
        _currentInstr = (InstructorAttach)_dm.getSetOfInstructors().getResource(sel).getAttach();
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
    gridPanel.setBorder(BorderFactory.createTitledBorder(MES00));
    _posVect = new Vector();
    _posVect.setSize((nbPer +1 )*(nbDay+1));
    gridPanel.add(new JLabel("")); // top left corner
    for (int i = 0; i < DAY.length; i++)
      //first line :  name of days
      gridPanel.add(new JLabel(DAY[i], JLabel.CENTER));

    _currentAvailbility = _currentInstr.getMatrixAvailability();

    for (int j = 0; j < nbPer; j++) {
      // first column : the time of the period

      gridPanel.add(new JLabel(TIME[j], JLabel.RIGHT));
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