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
import java.awt.GridLayout;
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
import dInternal.dData.Section;
import dInternal.dData.Type;

import dInterface.dUtil.DXTools;

public class GroupDlg extends JDialog implements ActionListener{

  private DApplication _dApplic;
  private SetOfActivities _activities;
  private SetOfStudents _students;
  private Activity _currentActivity;
  private Type _currentType;
  private Section _currentSection;

  private String _currActivityID, _currTypeID, _currGroupID;
  private int _currNumberOfSections;
  private Vector _activitiesVector, _typesVector, _notAssignedVector;

  private JButton _toLeft, _toRight;
  private JComboBox _activitiesCombo, _typesCombo;
//  private JLabel _lActivityID, _lActivityType, _lNotAssigned, _lAssigned, _lnumberOfElements, _lGroup;
  private JList _notAssignedList, _assignedList;
  private JPanel _titlePanel, _notAssignedPanel, _assignedPanel, _arrowsPanel, _buttonsPanel;
  private JPanel [] _groupPanels;
  private JScrollPane _scrollPane;
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
      _students = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents();
    if (_activities != null && _students != null){
      jbInit();
      setLocationRelativeTo(dApplic.getJFrame());
      setVisible(true);
    }
  }

  private void jbInit(){
    setSize(400,400);
    setResizable(false);
    setTitlePanel();
    setNotAssignedPanel();
    setAssignedPanel();
    //setGroupPanels();
    getContentPane().add(_titlePanel, BorderLayout.NORTH);
    getContentPane().add(_notAssignedPanel, BorderLayout.WEST);
    getContentPane().add(_assignedPanel, BorderLayout.EAST);
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
    _currActivityID = (String)_activitiesCombo.getSelectedItem();
    JPanel actIDPanel = new JPanel();
    actIDPanel.setBorder(new TitledBorder(new EtchedBorder(), ACTIVITY));
    actIDPanel.add(_activitiesCombo);
    actIDPanel.setPreferredSize(new Dimension(100,50));
    //The panel for the types ComboBox
    _typesVector = ((Activity)(_activities.getResource(_currActivityID).getAttach())).getSetOfTypes().getNamesVector();
    _typesCombo = new JComboBox(_typesVector);
    _typesCombo.setSelectedIndex(0);
    //_currTypeID = (String)_typesVector.elementAt(0);
    JPanel actTypePanel = new JPanel();
    actTypePanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_TYPE));
    actTypePanel.add(_typesCombo);
    actTypePanel.setPreferredSize(new Dimension(100,50));
    _titlePanel = new JPanel();
    _titlePanel.setPreferredSize(new Dimension(350,100));
    _titlePanel.add(actIDPanel);
    _titlePanel.add(actTypePanel);
    setCurrents();
  }

  /*
  private void setGroupPanels(){
    _currentActivity = (Activity)(_activities.getResource(_currActivityID).getAttach());
    System.out.println("_currActivityID "+ _currActivityID);
    _currentType = (Type)_currentActivity.getSetOfTypes().getResource((String)_typesCombo.getSelectedItem()).getAttach();
    System.out.println("_currTypeID "+ _currTypeID);
    int numberOfSections =  _currentType.getSetOfSections().size();
    _groupPanels = new JPanel[numberOfSections];
    for (int i = 0; i < numberOfSections; i++){
      _currGroupID = _currentType.getSetOfSections().getResourceAt(i).getID();
      System.out.println("_currGroupID "+ _currGroupID);
      System.out.println("Students in the group " + i + ": " +_students.getStudentsByGroup(_currActivityID, _currTypeID, DXTools.STIConvertGroup(_currGroupID)));
      //System.out.println("Students in the group " + i + ": " +_students.getStudentsByGroup(_currActivityID, _currTypeID, (String.valueOf(i))));
    }

  }
  */

  private void setNotAssignedPanel(){
    _notAssignedPanel = new JPanel();
    JScrollPane scrollaPane = new JScrollPane();
    _notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_STUD_NOT_ASSIGNED));
    _notAssignedPanel.setPreferredSize(new Dimension(200,300));
    _notAssignedVector = _students.getStudentsByGroup((String)_activitiesCombo.getSelectedItem(), "1", -1);
    System.out.println("(String)_activitiesCombo.getSelectedItem() "+(String)_activitiesCombo.getSelectedItem());
    System.out.println("_notAssignedVector" +_notAssignedVector);
    _notAssignedList = new JList(_notAssignedVector);
    scrollaPane.setPreferredSize(new Dimension(100,200));
    scrollaPane.getViewport().add(_notAssignedList);
    System.out.println("_notAssignedVector" + _notAssignedVector);
    _notAssignedPanel.add(scrollaPane);

  }

  private void setAssignedPanel(){
    _assignedPanel = new JPanel(new GridLayout(_currNumberOfSections, 1));
    JScrollPane scrollPane;
    Vector assignedVector;
    JList assignedList;
    System.out.println("_currNumberOfSections "+_currNumberOfSections);
    for (int i = 0; i < _currNumberOfSections; i++){
      assignedVector = _students.getStudentsByGroup(this._currActivityID, "1", i);
      assignedList = new JList(assignedVector);
      _assignedPanel.add(new JScrollPane());
      System.out.println("_assignedPanel.add(new JScrollPane())");
      scrollPane = (JScrollPane)_assignedPanel.getComponent(i);
      System.out.println("scrollPane " + scrollPane);
      scrollPane.setPreferredSize(new Dimension(100,100));
      scrollPane.getViewport().add(assignedList);
    }
  }


  private void triggerListeners(){
    _activitiesCombo.addActionListener(this);
    _typesCombo.addActionListener(this);

  }//end method

  /*
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
  */

  /**
   *
   * @param e an event
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    if (e.getSource().equals(_activitiesCombo)){
      _currActivityID = (String)(_activitiesCombo.getSelectedItem());
      _typesVector = ((Activity)(_activities.getResource(_currActivityID).getAttach())).getSetOfTypes().getNamesVector();
      _typesCombo.removeAllItems();
      for(int i = 0; i < _typesVector.size(); i++){
        _typesCombo.addItem(_typesVector.elementAt(i));
      }
      _typesCombo.setSelectedIndex(0);
      //_notAssignedVector = getStudents(_currActivityID, _typesVector);
      _notAssignedVector = _students.getStudentsByGroup((String)_activitiesCombo.getSelectedItem(), "1", -1);
      _notAssignedList.setListData(_notAssignedVector);
      setCurrents();
      _assignedPanel.removeAll();
      setAssignedPanel();

      _assignedPanel.repaint();
      //setGroupPanels();
    }//end if (e.getSource().equals(_activitiesCombo))
    if (e.getSource().equals(_typesCombo)){
      _currTypeID = (String)_typesCombo.getSelectedItem();
      setCurrents();
      _assignedPanel.removeAll();
      setAssignedPanel();
      _assignedPanel.repaint();
      //setGroupPanels();
    }
  }//end method

  public void setCurrents(){
    _currActivityID = (String)_activitiesCombo.getSelectedItem();
    _currTypeID = (String)_typesCombo.getSelectedItem();
    _currentActivity = (Activity)(_activities.getResource(_currActivityID).getAttach());
    _currentType = (Type)_currentActivity.getSetOfTypes().getResource(_currTypeID).getAttach();
    _currNumberOfSections = _currentType.getSetOfSections().size();
  }

}//end class