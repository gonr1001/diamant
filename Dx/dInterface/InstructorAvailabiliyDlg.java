/**
 *
 * Title: InstructorAvailabiliyDlg $Revision: 1.4 $  $Date: 2003-03-14 15:50:22 $
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
 * @author  $Author: rgr $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */
package dInterface;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dInternal.Instructor;
import dInternal.DModel;

/*import dInternal.DModelListener;
import dInternal.DModelEvent;*/


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
  private final int nbPer = 14;
  private final int nbDay = 5;

  final static String DAY[] = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi"};

  public String TIME[] = {"8:30 ", "9:30 ", "10:30 ", "11:30 ", "12:30 ",
                                "13:30 ", "14:30 ", "15:30 ", "16:30 ", "17:30 ",
                                "18:30 ", "19:00 ", "20:00 ", "21:00 "};

  final static String TITLE = "Enseignants";
  final static String MES00 = "Disponibilités";
  final static String BUT00 = "Appliquer";
  final static String BUT01 = "OK";
  final static String BUT02 = "Annuler";
  final static String TTT00 = "Enregistre les modifications";
  final static String TTT01 = "Quitte en enregistrant les modifications";
  final static String TTT02 = "Quitte sans enregistrer les modifications";

  JPanel butPanel = new JPanel();
  JPanel chooserPanel = new JPanel();
  JPanel centerPanel;
  JButton butCancel;
  JButton butOk;
  JButton butApply;
  JComboBox chooser;
  Vector _posVect;
  int nbPerParJour;
  private boolean modified = false;
  private DModel _dm;
  //private InstructorsList _insList; // clone of the dictionnary
  private Instructor  _currentInstr;
  private int [][] _currentAvailbility;
  //private String _sel;


  /**
   * Default constructor.
   *
   * @param owner The component on which the dialog will be displayed.
   * @param doc The active document.  Used to access the dictionnaries.
   */
  public InstructorAvailabiliyDlg(JFrame jFrame, String str, DModel dm) {
    super(jFrame, str, true);
    try {
      _dm = dm;
      //nbPer = _ddv._timeTable.nbPeriodPerDay+2;
      //nbDay = _ddv._timeTable.nbDays;
      //setTIME();
      jbInit();
      pack();
      setLocationRelativeTo(jFrame);
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
    chooser = new JComboBox(_dm.getInstructorsList().getNamesVector());
    chooser.addItemListener( this );
    chooserPanel.add(chooser, null);
    this.getContentPane().add(chooserPanel, BorderLayout.NORTH);

    //gridPanel
    String sel = (String)chooser.getSelectedItem();
    _currentInstr = (Instructor)_dm.getInstructorsList().getResource(sel).getObject();
    centerPanel = makeGridPanel(_currentInstr);
    getContentPane().add(centerPanel, BorderLayout.CENTER );

    //button Panel
    butOk = new JButton( BUT01 );
    butOk.setToolTipText(TTT01);
    butOk.addActionListener( this );
    butApply = new JButton(BUT00);
    butApply.setToolTipText(TTT00);
    butApply.addActionListener( this );
    butApply.setEnabled(false);
    butCancel = new JButton( BUT02 );
    butCancel.setToolTipText(TTT02);
    butCancel.addActionListener( this );
    butPanel.add(butOk, null);
    butPanel.add(butApply, null);
    butPanel.add(butCancel, null);
    getContentPane().add(butPanel, BorderLayout.SOUTH);
  } // end  jbInit()

  public void actionPerformed( ActionEvent event) {
    String command = event.getActionCommand();
    if (command.equals( BUT02 )) {  // cancel
      //"Enseignants --> Bouton Annuler pressé\n"
      dispose();
    } else if (command.equals( BUT01 )) {  // OK
   /*   _ddv._jFrame._log.append("Enseignants --> Bouton OK pressé\n"); */
       _currentInstr.setInstAvailability(_currentAvailbility);
        _dm.incrementModification();
      modified = false;
      butApply.setEnabled(false);
    /*  _ddv.actualTT(_ddv._dicInstr.getInstr(_sel), true);
      _ddv._dicInstr = (DDicInstructors)dicIns.clone();
      _ddv.actualTT(_inst, false);//
      _ddv._mHasChanged = modified;
      _ddv.newReport = true;
      //_ddv.actualTT();*/
      dispose();
    } else if (command.equals( BUT00 )) {  // apply
    /*  "Enseignants --> Bouton Appliquer pressé\n");*/
      _currentInstr.setInstAvailability(_currentAvailbility);
      _dm.incrementModification();
//      _dm.changeDModel();
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
        _currentInstr = (Instructor)_dm.getInstructorsList().getResource(sel).getObject();
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
  private JPanel makeGridPanel(Instructor instr) {
    JPanel gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(nbPer + 1, nbDay + 1));
    gridPanel.setBorder(BorderFactory.createTitledBorder(MES00));
    _posVect = new Vector();
    _posVect.setSize((nbPer +1 )*(nbDay+1));
    gridPanel.add(new JLabel("")); // top left corner
    for (int i = 0; i < DAY.length; i++)
      //first line :  name of days
      gridPanel.add(new JLabel(DAY[i], JLabel.CENTER));

    _currentAvailbility = _currentInstr.getInstAvailability();

    for (int j = 0; j < nbPer; j++) {
      // first column : the time of the period
      //if ((_ddv._timeTable.isCycle) && (_ddv._timeTable.nbPeriodPerDay==12))
      //TIME = _ddv._timeTable._hBegin;

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

  /** Set TIME
   * */
  private void setTIME(){
    TIME = new String [nbPer];
    int _itr =0;
    int _h = 8, _mn = 30;
    String _heure = _h+"h"+_mn;
  /*  DPeriod _per;
    Vector _creuse = creusePer(_ddv);
    for (int i=0; i<_ddv._timeTable.nbPeriodPerDay; i++){
      if (_ddv._timeTable.get(i)!=null){
        _per = (DPeriod)_ddv._timeTable.get(i);
        _h = new Integer (_per.timeBegin.get(Calendar.HOUR_OF_DAY)).intValue();
        _mn = new Integer (_per.timeBegin.get(Calendar.MINUTE)).intValue();
        _heure = _h+"h"+(_mn < 10 ? "0"+ Integer.toString(_mn):Integer.toString(_mn));
        TIME[_itr] = _heure;
        if(isCreuse(_per.timeEnd.get(Calendar.HOUR_OF_DAY),_creuse)){
          _itr++;
          _mn = _per.timeBegin.get(Calendar.MINUTE);
          _heure = _per.timeEnd.get(Calendar.HOUR_OF_DAY) +"h"+( _mn< 10 ? "0"+ Integer.toString(_mn):Integer.toString(_mn));
          System.out.println("Heure: "+_heure);//debug
          TIME[_itr] = _heure;
      }
      } else{
        _h = _h+ _ddv._timeTable.durPeriod;
        _heure = _h+"h"+_mn;
        TIME[_itr] = _heure;
      }
      _itr++;

    }
*/
  }
  // end SetTIME



} /* end InstructorAvailabilityDlg */