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
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Vector;

import javax.swing.BorderFactory;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import dInternal.dData.Activity;
import dInternal.dData.SetOfActivities;
import dInternal.dData.SetOfStudents;

import dInternal.dData.StudentAttach;
import dInternal.dData.Section;
import dInternal.dData.Type;

import dInterface.DApplication;
import dInterface.dUtil.DXTools;

import dResources.DConst;

public class GroupDlg extends JDialog implements ActionListener{

  private DApplication _dApplic = null;
  private int _numberOfTypes, _numberOfSections, _currentAssignedGroup = -1;
  private JComboBox _actCombo, _typeCombo;
  private JList _notAssignedList, _assignedLists[];
  private JPanel _arrowsPanel, _assignedPanel, _buttonsPanel, _insidePanel, _centerPanel, _notAssignedPanel;
  private JScrollPane _scrollPane;
  private Section _section;
  private SetOfActivities _activities;
  private SetOfStudents _students;
  private String _actID, _typeID;
  private String[] _arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};
  private String[] _buttonsNames = {DConst.BUT_OK, DConst.BUT_APPLY, DConst.BUT_CANCEL};
  private Type _type;
  private Vector _actVector, _notAssignedVector, _typeVector, _assignedVectors[];

  /**
   * Constructor
   * @param dApplic
   */
  public GroupDlg(DApplication dApplic){
    super(dApplic.getJFrame(), DConst.GROUP_DLG_TITLE, true);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    _students = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents();
    if (_activities != null && _students != null){
      jbInit();
      setLocationRelativeTo(dApplic.getJFrame());
      setVisible(true);
    }
  }//end method

  /**
   * Initilize the Dialog
   */
  private void jbInit(){
    Dimension dialogDim = new Dimension(550,500);
    getContentPane().setLayout(new BorderLayout());
    setSize(dialogDim);
    setResizable(false);
    setTopPanel();
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
    setCenterPanel(dialogDim);
  }


  /**
   * Set the panel containing the activity end type JComboBoxes
   */
  private void setTopPanel(){
    JPanel actPanel, typePanel;
    //This vector contains the activities whose their champ visibility = true
    _actVector = new Vector();
    _actVector = _activities.getIDsByField(3, "true");
    //panel of activities
    _actCombo = new JComboBox(_actVector);
    //_actCombo.setSelectedIndex(0);
    actPanel = new JPanel();
    actPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.ACTIVITY));
    actPanel.add(_actCombo);
    actPanel.setPreferredSize(new Dimension(100,50));
    _actID = (String)_actVector.elementAt(0);
    //panel of types
    _typeVector = ((Activity)(_activities.getResource(_actID).getAttach())).getSetOfTypes().getNamesVector(1);
    _typeCombo = new JComboBox(_typeVector);
    //_typeCombo.setSelectedIndex(0);
    typePanel = new JPanel();
    typePanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.TYPE));
    typePanel.add(_typeCombo);
    typePanel.setPreferredSize(new Dimension(100,50));
    _typeID = (String)_typeVector.elementAt(0);
    setCurrents();
    //adding the panels to topPanel
    JPanel topPanel = new JPanel();
    topPanel.setPreferredSize(new Dimension(300,60));
    topPanel.add(actPanel);
    topPanel.add(typePanel);
    getContentPane().add(topPanel, BorderLayout.NORTH);
    //adding the actionListeners
    _actCombo.addActionListener(this);
    _typeCombo.addActionListener(this);
  }

  /**
   * Set the left panel who shows the list of the not assigned students
   */
  private void setNotAssignedPanel(Dimension dialogDim){
    Dimension panelDim = new Dimension((int)((dialogDim.getWidth()-50)*0.40), (int)dialogDim.getHeight()-130);
    _notAssignedVector = _students.getStudentsByGroup(_actID, (String)_typeVector.elementAt(0), -1);
    _notAssignedVector = DXTools.sortVector(_notAssignedVector);
    _notAssignedList = new JList(_notAssignedVector);
    _notAssignedList.setFont(new java.awt.Font("Courier", Font.PLAIN, 12));
    _notAssignedList.addMouseListener(mouseListenerLists);
    _notAssignedPanel = DXTools.listPanel(_notAssignedList, (int)(panelDim.getWidth()-112), (int)panelDim.getHeight()-35);
    _notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.ACT_STUD_NOT_ASSIGNED));
    _notAssignedPanel.setPreferredSize(panelDim);
  }

  /**
   * Set _assignedPanel, the panel containing the lists of assigned students
   */
  private void setAssignedPanel(Dimension dialogDim){
    //setAssignedVectors();
    setAssignedLists();
    Dimension panelDim = new Dimension((int)((dialogDim.getWidth()-50)*0.55), (int)dialogDim.getHeight()-130);
    Dimension scrollDim = new Dimension((int)panelDim.getWidth()-10,(int)panelDim.getHeight()-5);
    _assignedPanel = new JPanel(new BorderLayout());
    _assignedPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.ACT_STUD_ASSIGNED));
    _assignedPanel.setPreferredSize(panelDim);
    _scrollPane = new JScrollPane();
    _scrollPane.setPreferredSize(scrollDim);
    _insidePanel = new JPanel(new GridLayout(_numberOfSections, 1)); //It is contained into _scrollPane
    setScrollPane(scrollDim);
    _assignedPanel.add(_scrollPane);
  }



  /**
   * Set the panel contained in _scrollPane. This panel contains the sub-JScrollPanes
   * corresponding to the groups in a type of activity
   */
  private void setScrollPane(Dimension ScrollPaneDim){
    int insideWidth = (int)ScrollPaneDim.getWidth()-20;
    int scrollHeight = (int)((ScrollPaneDim.getHeight()-10)/2);
    Dimension insideDim = new Dimension(insideWidth, (int)scrollHeight*_numberOfSections+10);
    JLabel[] lNumberOfElements = new JLabel[_numberOfSections];
    if (_insidePanel == null)
      _insidePanel = new JPanel();
    _insidePanel.setPreferredSize(insideDim);
    _insidePanel.removeAll();
    _insidePanel.setLayout(new GridLayout(_numberOfSections, 1));
    for (int i = 0; i < _numberOfSections; i++){
      _insidePanel.add(setGroupPanel(i, lNumberOfElements[i]));
    }
    _scrollPane.setViewportView(_insidePanel);
    _currentAssignedGroup = -1;
  }//end method

  /**
   * Sets the panel containing the list of students belonging a section (a group)
   * @param groupNumber The SectionID
   * @return a panel to be inserted into _insidePanel
   */
  private JPanel setGroupPanel(int groupNumber, JLabel lNumberOfElements){
    int numberOfElements = 0;
    int insideWidth = (int)_insidePanel.getPreferredSize().getWidth();
    int GroupPanelHeight = (int)((_scrollPane.getPreferredSize().getHeight())/2);
    int infoPanelHeight = 25;
    Dimension groupPanelDim = new Dimension(insideWidth, GroupPanelHeight);
    Dimension scrollContDim = new Dimension((int)insideWidth-5, GroupPanelHeight-infoPanelHeight-20);
    JPanel groupPanel = new JPanel();
    groupPanel.setPreferredSize(groupPanelDim);
    groupPanel.addMouseListener(mouseListenerGroupPanel);
    JPanel infoPanel = new JPanel();
    //The scrollPane
    JPanel scrollContainer = new JPanel();
    scrollContainer = DXTools.listPanel(_assignedLists[groupNumber], (int)(insideWidth-20), GroupPanelHeight-infoPanelHeight-20);
    //The infoPanel
    infoPanel.setPreferredSize(new Dimension(insideWidth-10, infoPanelHeight));
    numberOfElements = _assignedVectors[groupNumber].size();
    JLabel lGroup = new JLabel(DConst.GROUP);
    //JLabel lGroupID = new JLabel(String.valueOf(groupNumber));
    JLabel lGroupID = new JLabel(_type.getSetOfSections().getResourceAt(groupNumber).getID());
    lGroupID.setForeground(DConst.COLOR_QUANTITY_DLGS);
    JLabel lNumber = new JLabel(DConst.NUMBER_OF_ELEMENTS + " ");
    //_lNumberOfElements = new JLabel [numberOfElements];
    lNumberOfElements = new JLabel(String.valueOf(numberOfElements));
    lNumberOfElements.setForeground(DConst.COLOR_QUANTITY_DLGS);
    infoPanel.add(lGroup);
    infoPanel.add(lGroupID);
    infoPanel.add(new JLabel(" - "));
    infoPanel.add(lNumber);
    infoPanel.add(lNumberOfElements);
    //adding the sub-panels to the panel
    groupPanel.add(infoPanel);
    groupPanel.add(scrollContainer);
    return groupPanel;
  }


  /**
   * Set _centerPanel, the panel containing _assignedPanel, _arrowsPanel and
   * _notAssignedPanel
   */
  private void setCenterPanel(Dimension dialogDim){
    setNotAssignedPanel(dialogDim);
    _arrowsPanel = DXTools.arrowsPanel(this, _arrowsNames,true);
    setAssignedPanel(dialogDim);
    _centerPanel = new JPanel();
    _centerPanel.add(_notAssignedPanel);
    _centerPanel.add(_arrowsPanel);
    _centerPanel.add(_assignedPanel);
    getContentPane().add(_centerPanel, BorderLayout.CENTER);
  }

  /**
   * Set the current values of the resources Activity end Type and the nomber
   * of sections
   */
  private void setCurrents(){
    Activity act = (Activity)(_activities.getResource(_actID).getAttach());
    _numberOfTypes = act.getSetOfTypes().size();
    _type = (Type)act.getSetOfTypes().getResource(_typeID).getAttach();
    _numberOfSections = _type.getSetOfSections().size();
  }

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if activity combo box
    if (e.getSource().equals(_actCombo)){
      _actID = (String)_actCombo.getSelectedItem();
      _typeVector = ((Activity)(_activities.getResource(_actID).getAttach())).getSetOfTypes().getNamesVector(1);
      _typeCombo.removeAllItems();
      for(int i = 0; i < _typeVector.size(); i++){
        _typeCombo.addItem(_typeVector.elementAt(i));
      }//end for
      _typeID = (String)_typeVector.get(0);
      setCurrents();
      //setNotAssignedVectors();
      _notAssignedVector = _students.getStudentsByGroup(_actID, _typeID, -1);
      _notAssignedList.setListData(_notAssignedVector);
      setAssignedLists();
      setScrollPane(_scrollPane.getPreferredSize());
    }//end if (e.getSource().equals(_actCombo))
    //if type combo box
    if (e.getSource().equals(_typeCombo)){
      _typeID = (String)_typeCombo.getSelectedItem();
      setCurrents();
      _notAssignedVector = _students.getStudentsByGroup(_actID, _typeID, -1);
      _notAssignedList.setListData(_notAssignedVector);
      setAssignedLists();
      setScrollPane(_scrollPane.getPreferredSize());
    }//end if (e.getSource().equals(_typeCombo))
    //if Button CANCEL is pressed
    if (command.equals(_buttonsNames[2]))
      dispose();
    //if Button APPLY is pressed
    if (command.equals(_buttonsNames[1])){
      setStudentsInGroups();
      _buttonsPanel.getComponent(1).setEnabled(false);
      //_dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents().sendEvent(this);
    }
    //if Button OK is pressed
    if (command.equals(_buttonsNames[0])){
      setStudentsInGroups();
      //_dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents().sendEvent(this);
      dispose();
    }// end if (command.equals(SHOW))
    if ((command.equals(_arrowsNames[1]) || command.equals(_arrowsNames[0])) && _currentAssignedGroup > -1){
      //if "toLeft" button is pressed
      if (command.equals(_arrowsNames[1])){
        if (_currentAssignedGroup > -1){
          DXTools.listTransfers(_assignedLists[_currentAssignedGroup], _notAssignedList, _assignedVectors[_currentAssignedGroup], _notAssignedVector, 1);
          _buttonsPanel.getComponent(1).setEnabled(true);
          _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents().sendEvent(this);
        }
      }
      else{
        if (_currentAssignedGroup > -1){
          DXTools.listTransfers(_notAssignedList, _assignedLists[_currentAssignedGroup], _notAssignedVector, _assignedVectors[_currentAssignedGroup], 1);
          _buttonsPanel.getComponent(1).setEnabled(true);
          _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents().sendEvent(this);
        }
      }
      //SetText for the JLabel containing the number of elements in a group
      ((JLabel)((JPanel)((JPanel)_insidePanel.getComponent(
          _currentAssignedGroup)).getComponent(0)).getComponent(4)).setText(
          String.valueOf(_assignedVectors[_currentAssignedGroup].size()));
    }//end if (command.equals(TO_LEFT) || command.equals(TO_RIGHT))
  }//end method


  /**
   * The mouseListener for the JLists
   */
  private MouseListener mouseListenerLists = new MouseAdapter(){
    public void mouseClicked(MouseEvent e) {
      //if (((JList)e.getSource()).getModel().getSize() == 0)
        //return;
      if (e.getSource().equals(_notAssignedList)){
        if(_notAssignedVector.size() != 0){
          for(int i = 0; i < _numberOfSections; i++){
            _assignedLists[i].clearSelection();
          }
        }//end if(_notAssignedVector.size() != 0)
      }//end if (e.getSource().equals(_notAssignedList))
        for(int i = 0; i<_numberOfSections; i++){
          if (e.getSource().equals(_assignedLists[i])){
            _currentAssignedGroup = i;
            setGroupBorders(_currentAssignedGroup, Color.blue);
            if ((_assignedVectors[i]).size() != 0){
              _notAssignedList.clearSelection();
            }
            for (int j = 0; j<_numberOfSections; j++)
              if (j != i){
                _assignedLists[j].clearSelection();
              }
          }//if (e.getSource().equals(_assignedLists[i]))
        }//end for(int i = 0; i<_numberOfSections; i++)
      //_currentStudents = ((JList)e.getSource()).getSelectedValues();
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){

  private MouseListener mouseListenerGroupPanel = new MouseAdapter(){
    public void mouseClicked(MouseEvent e) {
      for(int i = 0; i < _numberOfSections; i++){
        if (e.getSource().equals( (JPanel)_insidePanel.getComponent(i))){
          _currentAssignedGroup = i;
        }else{
          _assignedLists[i].clearSelection();
        }
      }//end for(int i = 0; i<_numberOfSections; i++)
      setGroupBorders(_currentAssignedGroup, Color.blue);
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){

  /**
  * Set the clor border of the groupPanels. Clor = blue for the panel selected, null for the
  * other panels
  * @param selectedPanelID
  * @param colorBorder
  */
  private void setGroupBorders(int selectedPanelID, Color colorBorder){
    JPanel panel;
    for (int i = 0; i < _numberOfSections; i++){
      panel = (JPanel)_insidePanel.getComponent(i);
      if(i == selectedPanelID)
        panel.setBorder(BorderFactory.createLineBorder(DConst.COLOR_QUANTITY_DLGS));
      else
        panel.setBorder(null);
    }
  }//end method



  /**
   * Sets the vectors containig the students assigned to the groups and the
   * list whose DataModel are that vectors
   */
  private void setAssignedLists(){
    _assignedVectors = new Vector[_numberOfSections];
    _assignedLists = new JList[_numberOfSections];
    for(int i = 0; i < _numberOfSections; i++){
      _assignedVectors[i] = _students.getStudentsByGroup(_actID, _typeID, i+1);
      _assignedVectors[i] = DXTools.sortVector(_assignedVectors[i]);
      _assignedLists[i] = new JList(_assignedVectors[i]);
      _assignedLists[i].setFont(DConst.JLISTS_FONT);
      _assignedLists[i].addMouseListener(mouseListenerLists);
    }
  }

  /**
   * Sets the students in the groups according to the contents of the vectors
   * _notAssignedVector and _assignedVectors
   */
  private void setStudentsInGroups(){
    StudentAttach s;
    String studentData;
    String studentID;
    for(int i = 0; i < _notAssignedVector.size(); i++){
      studentData = (String)_notAssignedVector.elementAt(i);
      studentID = studentData.substring(0, DConst.STUDENT_ID_LENGTH).trim();
      s = (StudentAttach)_students.getResource(studentID).getAttach();
      s.setInGroup(_actID+_typeID, -1, false);
    }//end for(int i = 0; i < _notAssignedVector.size(); i++)
    for(int j = 0; j < _assignedVectors.length; j++){
      for(int k = 0; k < _assignedVectors[j].size(); k++){
        studentData = (String)_assignedVectors[j].elementAt(k);
        studentID = studentData.substring(0, DConst.STUDENT_ID_LENGTH).trim();
        s = (StudentAttach)_students.getResource(studentID).getAttach();
        s.setInGroup(_actID+_typeID, j+1, false);
      }//end for(int k = 0; k < _assignedVectors[j].size(); k++)
    }//end for(int j = 0; j < _assignedVectors.length; j++)
  }//end method



}//end class