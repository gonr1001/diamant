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

  private Activity _act;
  private DApplication _dApplic = null;
  private int _numberOfSections;
  private JComboBox _actCombo, _typeCombo;
  private JList _notAssignedList;
  private JPanel _assignedPanel, _notAssignedPanel, _topPanel;
  private Section _section;
  private SetOfActivities _activities;
  private SetOfStudents _students;
  private String _actID, _typeID;
  private Type _type;
  private Vector _actVector, _notAssignedVector, _typeVector;

  private static String ACT_STUD_NOT_ASSIGNED = "Étudiants non assignés";
  private static String ACTIVITY = "Activité";
  private static String GROUP_DLG_TITLE = "Group Dlg";

public GroupDlg(DApplication dApplic){
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

public void jbInit(){
  getContentPane().setLayout(new BorderLayout());
  setSize(400,400);
  setResizable(true);
  setTopPanel();
  setNotAssignedPanel();
  setAssignedPanel();
}

/**
 * Set the panel containing the activity end type JComboBoxes
 */
public void setTopPanel(){
  //Set the panel containing the activities ComboBox
  JPanel actPanel, typePanel;
  _actVector = _activities.getNamesVector();
  //panel of activities
  _actCombo = new JComboBox(_actVector);
  actPanel = new JPanel();
  actPanel.setBorder(new TitledBorder(new EtchedBorder(), ACTIVITY));
  actPanel.add(_actCombo);
  actPanel.setPreferredSize(new Dimension(100,50));
  _actID = (String)_actVector.get(0);
  //panel of types
  _typeVector = ((Activity)(_activities.getResource(_actID).getAttach())).getSetOfTypes().getNamesVector();
  _typeCombo = new JComboBox(_typeVector);
  typePanel = new JPanel();
  typePanel.setBorder(new TitledBorder(new EtchedBorder(), ACTIVITY));
  typePanel.add(_typeCombo);
  typePanel.setPreferredSize(new Dimension(100,50));
  _typeID = (String)_typeVector.get(0);
  setCurrents();
  //adding the panels to topPanel
  _topPanel = new JPanel();
  _topPanel.setPreferredSize(new Dimension(350,100));
  _topPanel.add(actPanel);
  _topPanel.add(typePanel);
  getContentPane().add(_topPanel, BorderLayout.NORTH);
  //adding the actionListeners
  _actCombo.addActionListener(this);
  _typeCombo.addActionListener(this);
}

public void setNotAssignedPanel(){
  _notAssignedVector = _students.getStudentsByGroup(_actID, "1", -1);
  _notAssignedList = new JList(_notAssignedVector);
  JScrollPane scrollPane = new JScrollPane();
  scrollPane.setPreferredSize(new Dimension(100,200));
  scrollPane.getViewport().add(_notAssignedList);
  //adding the JScrollPane to panel
  _notAssignedPanel = new JPanel();
  _notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_STUD_NOT_ASSIGNED));
  _notAssignedPanel.setPreferredSize(new Dimension(100,200));
  _notAssignedPanel.add(scrollPane);
  getContentPane().add(_notAssignedPanel);//, BorderLayout.WEST);
}

public void setAssignedPanel(){
  _assignedPanel = new JPanel();
  _assignedPanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_STUD_NOT_ASSIGNED));
  _assignedPanel.setPreferredSize(new Dimension(100,200));
  _assignedPanel.add(new JButton("Hi"));
  getContentPane().add(_assignedPanel);//, BorderLayout.EAST);
}

public void setCurrents(){
  _act = (Activity)(_activities.getResource(_actID).getAttach());
  _type = (Type)_act.getSetOfTypes().getResource(_typeID).getAttach();
  _numberOfSections = _type.getSetOfSections().size();
}

public void actionPerformed(ActionEvent e){
  String command = e.getActionCommand();
    if (e.getSource().equals(_actCombo)){
      _actID = (String)_actCombo.getSelectedItem();
      _typeVector = ((Activity)(_activities.getResource(_actID).getAttach())).getSetOfTypes().getNamesVector();
      _typeCombo.removeAllItems();
      for(int i = 0; i < _typeVector.size(); i++){
        _typeCombo.addItem(_typeVector.elementAt(i));
      }//end for
      _typeCombo.setSelectedIndex(0);
      _typeID = (String)_typeCombo.getSelectedItem();
      setCurrents();
      _notAssignedVector = _students.getStudentsByGroup(_actID, "1", -1);
      _notAssignedList.setListData(_notAssignedVector);
      _assignedPanel.add(new JButton("Hi2"));
    }//end if (e.getSource().equals(_actCombo))
    else if (e.getSource().equals(_typeCombo)){
      _notAssignedVector.removeAllElements();
      _notAssignedList.setListData(_notAssignedVector);
      _assignedPanel.removeAll();
      _assignedPanel.repaint();
    }
}//end method

}//end class