package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import dInternal.dData.Activity;
import dInterface.DApplication;
import dInternal.dData.SetOfActivities;
import dInternal.dData.SetOfStudents;
import dInternal.dData.SetOfResources;
import dInternal.dData.StudentAttach;

public class GroupDlg extends JDialog implements ActionListener{

  private DApplication _dApplic;
  private SetOfActivities _activities;
  private SetOfStudents _students;
  private Vector _activitiesVector, _typesVector, _notAssignedVector;

  private JButton _toLeft, _toRight;
  private JComboBox _activitiesCombo, _typesCombo;
  private JLabel _lActivityID, _lActivityType, _lNotAssigned, _lAssigned, _lnumberOfElements, _lGroup;
  private JList _notAssignedList, _assignedList;
  private JPanel _titlePanel, _notAssignedPanel, _assignedPanel, _arrowsPanel, _buttonsPanel;
  private JScrollPane _scrollPane;
  private JTabbedPane _tabbedPane;
  private JTextField _tfNumberOfNotAssigned, _tfNumberOfAssigned;

  private static String GROUP_DLG_TITLE = "Group Dlg";
  private static String ACTIVITY = "Activité";
  private static String ACT_TYPE = "Type d'activité";
  private static String ACT_STUD_NOT_ASSIGNED = "Étudiants non assignés";
  private static String ACT_STUD_ASSIGNED = "Étudiants assignés";
  private static String ACT_GROUP = "Groupe";
  private static String ACT_NUMBER_ELEMENTS = "Nombre d'éléments";

  public GroupDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), GROUP_DLG_TITLE, true);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() != null)
      _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    if (_activities != null){
      jbInit();
      setLocationRelativeTo(dApplic.getJFrame());
      setVisible(true);
    }
  }

  private void jbInit(){
    setSize(400,400);
    setResizable(false);
    setTitlePanel();
    getContentPane().add(_titlePanel, BorderLayout.NORTH);
    setnotAssignedPanel();
    getContentPane().add(_notAssignedPanel, BorderLayout.WEST);
    triggerListeners();
  }

  /**
   * Set the panel containing the activity end type JComboBoxes
   */
  private void setTitlePanel(){
    //The panel for the avtivities ComboBox
    _activitiesVector = _activities.getNamesVector();
    _activitiesCombo = new JComboBox(_activitiesVector);
    _activitiesCombo.setSelectedIndex(0);
    JPanel actIDPanel = new JPanel();
    actIDPanel.setBorder(new TitledBorder(new EtchedBorder(), ACTIVITY));
    actIDPanel.add(_activitiesCombo);
    actIDPanel.setPreferredSize(new Dimension(100,50));
    //The panel for the types ComboBox
    _typesVector = ((Activity)(_activities.getResource((String)_activitiesCombo.getSelectedItem()).getAttach())).getSetOfTypes().getNamesVector();
    _typesCombo = new JComboBox(_typesVector);
    JPanel actTypePanel = new JPanel();
    actTypePanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_TYPE));
    actTypePanel.add(_typesCombo);
    actTypePanel.setPreferredSize(new Dimension(100,50));
    _titlePanel = new JPanel();
    _titlePanel.setPreferredSize(new Dimension(350,100));
    _titlePanel.add(actIDPanel);
    _titlePanel.add(actTypePanel);
  }

  private void setnotAssignedPanel(){
    _notAssignedPanel = new JPanel();
    _notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_STUD_NOT_ASSIGNED));
    _notAssignedPanel.setPreferredSize(new Dimension(200,300));
    //_notAssignedVector = getStudents((String)_activitiesCombo.getSelectedItem(), _typesVector);
    //_notAssignedVector = _students.getStudentsByGroup((String)_activitiesCombo.getSelectedItem(), )
    System.out.println("(String)_activitiesCombo.getSelectedItem() "+(String)_activitiesCombo.getSelectedItem());
    System.out.println("_notAssignedVector" +_notAssignedVector);
    _notAssignedList = new JList(_notAssignedVector);
    //_notAssignedList = new JList();
    System.out.println("_notAssignedVector" + _notAssignedVector);
    _notAssignedPanel.add(_notAssignedList);

  }

  private JPanel setAssignedPanel(){
    JPanel assignedPanel = null;
    return assignedPanel;
  }

  private JTabbedPane setTabbedPane(){
    JTabbedPane tabbedPane = null;
    return tabbedPane;
  }

  private void triggerListeners(){
    _activitiesCombo.addActionListener(this);

  }//end method

  private Vector getStudents(String activityID, Vector activityTypes){
    SetOfStudents setOfStudents = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents();
    System.out.println("setOfStudents.getNamesVector()" + setOfStudents.getNamesVector());
    SetOfResources studentActivities;
    Vector activitiesID;
    Vector students = new Vector();
    for (int i = 0; i< setOfStudents.size(); i++){
      studentActivities = ((StudentAttach)(setOfStudents.getResourceAt(i).getAttach())).getCoursesList();
      System.out.println("studentActivities "+studentActivities.getNamesVector());
      for (int j = 0; j < activityTypes.size(); j++){
        studentActivities.getResource(activityID+activityTypes.elementAt(j));
        if (studentActivities.getResource(activityID+activityTypes.elementAt(j)) != null)
          students.add(setOfStudents.getResourceAt(i).getID());
      }
    }
    return students;
  }

  /**
   *
   * @param e an event
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    String actID;
    if (e.getSource().equals(_activitiesCombo)){
      actID = (String)(_activitiesCombo.getSelectedItem());
      //System.out.println("actID " + actID);
      _typesVector = ((Activity)(_activities.getResource(actID).getAttach())).getSetOfTypes().getNamesVector();
      //System.out.println("_typesVector "+_typesVector);
      _typesCombo.removeAllItems();
      for(int i = 0; i < _typesVector.size(); i++){
        _typesCombo.addItem(_typesVector.elementAt(i));
      }
      _notAssignedVector = getStudents(actID, _typesVector);
      System.out.println("_notAssignedVector "+ _notAssignedVector);
      this._notAssignedList.setListData(_notAssignedVector);
    }//end if (e.getSource().equals(_activitiesCombo))
  }//end method

}//end class