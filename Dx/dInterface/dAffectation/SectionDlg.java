/**
 *
 * Title: SectionDlg $Revision: 1.29 $  $Date: 2004-06-10 15:11:31 $
 * Description: SectionDlg is class used
 *           to display a dialog to modifiy students in groupes
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
 * @version $Revision: 1.29 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3

 */
package dInterface.dAffectation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dInterface.DApplication;
import dInterface.dUtil.TwoButtonsPanel;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DXTools;

import dInternal.dDataTxt.Activity;
import dInternal.dDataTxt.Resource;
import dInternal.dDataTxt.SetOfActivities;
import dInternal.dDataTxt.SetOfStudents;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dDataTxt.StudentAttach;
import dInternal.dDataTxt.Section;
import dInternal.dDataTxt.Type;

import dConstants.DConst;

import dInterface.dUtil.DXJComboBox;
import com.iLib.gDialog.InformationDlg;

public class SectionDlg extends JDialog implements ActionListener{
  private DApplication _dApplic;
  private int _numberOfTypes, _numberOfSections, _currentAssignedGroup, _sortIndex;
  private JButton _sortButton;
  private DXJComboBox _actCombo, _typeCombo, _sortCombo;
  private JList _notAssignedList, _assignedLists[];
  private JPanel _arrowsPanel, _assignedPanel, _insidePanel, _centerPanel, _notAssignedPanel;
  private ButtonsPanel _applyPanel;
  private JScrollPane _scrollPane;
  private Section _section;
  private SetOfActivities _activities;
  private SetOfStudents _students;
  private String _actID, _typeID, _sortID;
  private String[] _arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};

  private Type _type;
  private Vector _actVector, _notAssignedVector, _typeVector, _assignedVectors[], _sortVector;

  /**
   * Constructor
   * @param dApplic
   */
  public SectionDlg(DApplication dApplic){
    super(dApplic.getJFrame(), DConst.SECTION_DLG_TITLE, true);
    _dApplic = dApplic;
    _sortIndex = 0;
    _currentAssignedGroup = -1;
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    _students = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents();
    _students.sortSetOfResourcesByID();
    if (_activities != null && _students != null){
      initialize();
      setLocationRelativeTo(dApplic.getJFrame());
      setVisible(true);
    }
  }//end method

  /**
   * Initilize the Dialog
   */
  private void initialize(){
    Dimension dialogDim = new Dimension(550,550);
    getContentPane().setLayout(new BorderLayout());
    setSize(dialogDim);
    setResizable(false);
    JPanel top = initTopPanel();
    getContentPane().add(top, BorderLayout.NORTH);


    //_applyPanel
    String [] a ={DConst.BUT_APPLY, DConst.BUT_CLOSE};
    _applyPanel = new TwoButtonsPanel(this, a);
    //Setting the button APPLY disable
    _applyPanel.setFirstDisable();
    getContentPane().add(_applyPanel, BorderLayout.SOUTH);
    setListsLoad(_sortIndex, false);  //setLists
    setCenterPanel(dialogDim);

  }


  /**
   * Set the panel containing the activity end type JComboBoxes
   */
  private JPanel initTopPanel(){
    JPanel actPanel, typePanel, sortPanel;
    //This vector contains the activities whose their champ visibility = true
    _actVector = new Vector();
    _actVector = _activities.getIDsByField(3, "true");
    //panel of activities
    _actCombo = new DXJComboBox(_actVector);
    //_actCombo.setSelectedIndex(0);
    actPanel = new JPanel();
    actPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.ACTIVITY));
    actPanel.add(_actCombo);
    actPanel.setPreferredSize(new Dimension(100,52));
    _actID = (String)_actVector.elementAt(0);
    //panel of types
    _typeVector = ((Activity)(_activities.getResource(_actID).getAttach())).getSetOfTypes().getNamesVector(1);
    _typeCombo = new DXJComboBox(_typeVector);
    //_typeCombo.setSelectedIndex(0);
    typePanel = new JPanel();
    typePanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.TYPE));
    typePanel.add(_typeCombo);
    typePanel.setPreferredSize(new Dimension(100,52));
    _typeID = (String)_typeVector.elementAt(0);
    setCurrents();
    //panel of sort
    _sortVector = buildSortVector();
    _sortCombo = new DXJComboBox(_sortVector);
    /*_sortButton = new JButton(DConst.SORT_BY_MATRICUL);
    _sortButton.setPreferredSize(new Dimension(140, 25));
    _sortButton.addActionListener(this);*/
    sortPanel = new JPanel();
    sortPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.SORT_TITLE));
    sortPanel.add(_sortCombo);
    sortPanel.setPreferredSize(new Dimension(150,52));
    //_sortID = (String)_sortVector.elementAt(0);
    _sortCombo.setSelectedIndex(_sortIndex);
    //adding the panels to topPanel
    JPanel topPanel = new JPanel();
    topPanel.setPreferredSize(new Dimension(400,70));
    topPanel.add(actPanel);
    topPanel.add(typePanel);
    topPanel.add(sortPanel);

    //adding the actionListeners
    _actCombo.addActionListener(this);
    _typeCombo.addActionListener(this);
    _sortCombo.addActionListener(this);
    return topPanel;

  }

  /**
   * Set the left panel who shows the list of the not assigned students
   */
  private void setNotAssignedPanel(Dimension dialogDim){
    Dimension panelDim = new Dimension((int)((dialogDim.getWidth()-50)*0.40), (int)dialogDim.getHeight()-150);
    _notAssignedPanel = DXTools.listPanel(_notAssignedList, (int)(panelDim.getWidth()-112), (int)panelDim.getHeight()-35);
    _notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.ACT_STUD_NOT_ASSIGNED));
    _notAssignedPanel.setPreferredSize(panelDim);
  }

  /**
   * Set _assignedPanel, the panel containing the lists of assigned students
   */
  private void setAssignedPanel(Dimension dialogDim){
    Dimension panelDim = new Dimension((int)((dialogDim.getWidth()-50)*0.55), (int)dialogDim.getHeight()-150);
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
    Dimension scrollContDim = new Dimension((int)insideWidth-5, GroupPanelHeight-infoPanelHeight-10);
    JPanel groupPanel = new JPanel();
    groupPanel.setPreferredSize(groupPanelDim);
    groupPanel.addMouseListener(mouseListenerGroupPanel);
    JPanel infoPanel = new JPanel();
    //The scrollPane
    JPanel scrollContainer = new JPanel();
    scrollContainer = DXTools.listPanel(_assignedLists[groupNumber], (int)(insideWidth-20), GroupPanelHeight-infoPanelHeight-20);
    infoPanel.setPreferredSize(new Dimension(insideWidth-10, infoPanelHeight));
    numberOfElements = _assignedVectors[groupNumber].size();
    JLabel lGroup = new JLabel(DConst.SECTION);
    //The infoPanel
    JLabel lGroupID = new JLabel(_type.getSetOfSections().getResourceAt(groupNumber).getID());
    lGroupID.setForeground(DConst.COLOR_QUANTITY_DLGS);
    JLabel lNumber = new JLabel(DConst.NUMBER_OF_ELEMENTS + " ");
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

    // combo
    //if activity combo box
    if (e.getSource().equals(_actCombo)){
      if(!_applyPanel.isFirstEnable()){
        _actID = (String)_actCombo.getSelectedItem();
        _typeVector = ((Activity)(_activities.getResource(_actID).getAttach())).getSetOfTypes().getNamesVector(1);
        _typeCombo.disableActionListeners();
        _typeCombo.removeAllItems();
        _typeCombo.enableActionListeners();
        for(int i = 0; i < _typeVector.size(); i++){
          _typeCombo.addItem(_typeVector.elementAt(i));
        }//end for
        _typeID = (String)_typeVector.get(0);
        setCurrents();
        setListsLoad(_sortIndex, false);
        setScrollPane(_scrollPane.getPreferredSize());
        //_applyPanel.setFirstEnable();
      } else {
        new InformationDlg(this, "Appliquer ou fermer pour continuer", "Operation interdite");
        _actCombo.setSelectedItem((String)_actID);
      }
    }//end if (e.getSource().equals(_actCombo))
    //if type combo box
    if (e.getSource().equals(_typeCombo)){
      if(!_applyPanel.isFirstEnable()){
        _typeID = (String)_typeCombo.getSelectedItem();
        setCurrents();
        setListsLoad(_sortIndex, false);
        setScrollPane(_scrollPane.getPreferredSize());
      } else {
        new InformationDlg(this, "Appliquer ou fermer pour continuer", "Operation interdite");
        _typeCombo.setSelectedItem((String)_typeID);
      }
      //_applyPanel.setFirstEnable();
    }//end if (e.getSource().equals(_typeCombo))
    //if sort button
    if (e.getSource().equals(_sortCombo)){
      //int oldIndex = _sortIndex;
      setCurrents();
      //setLists(_sortIndex, false);
      setScrollPane(_scrollPane.getPreferredSize());
      //_applyPanel.setFirstEnable();
      setLists(_sortCombo.getSelectedIndex(), true);
      _sortIndex = _sortCombo.getSelectedIndex();
    }//end if (e.getSource().equals(_typeCombo))
    //if sort button

    //buttons
    //if Button CLOSE is pressed
    if (command.equals(DConst.BUT_CLOSE))
      dispose();
    //if Button APPLY is pressed
    if (command.equals(DConst.BUT_APPLY)){
      setStudentsInGroups();
      //_buttonsPanel.getComponent(1).setEnabled(false);
      //_sortButton.setEnabled(true);
      _sortCombo.setEnabled(true);
      _applyPanel.setFirstDisable();
      //_dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      //_conditionTest.setMatrixBuilded(false,true);
      _dApplic.getDMediator().getCurrentDoc().getDM().getConditionsTest().setMatrixBuilded(false,false);
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents().sendEvent(this);
      //_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStates().sendEvent();
    }

    // Arrows
    if ((command.equals(_arrowsNames[1]) || command.equals(_arrowsNames[0])) && _currentAssignedGroup > -1){
      //if "toLeft" button is pressed
      if (command.equals(_arrowsNames[1])){
        if (_currentAssignedGroup > -1){
          listTransfers(_assignedLists[_currentAssignedGroup], _notAssignedList, _assignedVectors[_currentAssignedGroup], _notAssignedVector, DConst.CHAR_FIXED_IN_GROUP, true, _sortIndex);
          //_buttonsPanel.getComponent(1).setEnabled(true);
          //_sortCombo.setEnabled(false);
          _applyPanel.setFirstEnable();
          _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStates().sendEvent();
        }
      }
      else{
        if (_currentAssignedGroup > -1){
          listTransfers(_notAssignedList, _assignedLists[_currentAssignedGroup], _notAssignedVector, _assignedVectors[_currentAssignedGroup], DConst.CHAR_FIXED_IN_GROUP, false, _sortIndex);
          //_buttonsPanel.getComponent(1).setEnabled(true);
          //_sortButton.setEnabled(false);
          //_sortCombo.setEnabled(false);
          _applyPanel.setFirstEnable();
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
      if (e.getSource().equals(_notAssignedList)){
        if(_notAssignedVector.size() != 0){
          for(int i = 0; i < _numberOfSections; i++)
            _assignedLists[i].clearSelection();
        }//end if(_notAssignedVector.size() != 0)
      }//end if (e.getSource().equals(_notAssignedList))
      for(int i = 0; i<_numberOfSections; i++){
        if (e.getSource().equals(_assignedLists[i])){
          _currentAssignedGroup = i;
          setGroupBorders(_currentAssignedGroup, Color.blue);
          if ((_assignedVectors[i]).size() != 0){
            _notAssignedList.clearSelection();
          }
          for (int j = 0; j<_numberOfSections; j++){
            if (j != i){
              _assignedLists[j].clearSelection();
            }
          }
          if(e.getClickCount() == 2){
            changeFixedInGroup(((JList)e.getSource()).getSelectedValues(), _currentAssignedGroup);
            _applyPanel.setFirstEnable();
          }//end if(e.getClickCount() == 2)
        }//if (e.getSource().equals(_assignedLists[i]))
      }//end for(int i = 0; i<_numberOfSections; i++)

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

  private  SetOfStudents getSortStudents(JList list, int newIndex, int group) {
    //sortIndex= 0; // to comment
    SetOfStudents students = new SetOfStudents();
    if (list != null) {
      for (int i= 0; i < list.getModel().getSize(); i++ ) {
        String str = (String) list.getModel().getElementAt(i);
        Resource resource = getStudent(str, _sortIndex);
        StudentAttach studAtt= new StudentAttach();
        studAtt.addCourse(_actID+_typeID);
        if (str.endsWith(DConst.CHAR_FIXED_IN_GROUP))
          studAtt.setInGroup(_actID+_typeID, group, true);
        else
          studAtt.setInGroup(_actID+_typeID, group, false);
        //s.setInGroup(_actID+_typeID, -1, false);
        students.addStudent(resource.getKey(),resource.getID(),((StudentAttach)resource.getAttach()).getAuxField(), studAtt);
      }
    }
    return students;
  }


  private void setListsLoad(int sortIndex, boolean forUpdate){
    _notAssignedVector = _students.getStudentsByGroup(_actID, (String)_typeVector.elementAt(_typeCombo.getSelectedIndex()), -1, _sortIndex);
    if (_notAssignedList == null){
      _notAssignedList = new JList(_notAssignedVector);
      _notAssignedList.setFont(DConst.JLISTS_FONT);
      _notAssignedList.addMouseListener(mouseListenerLists);
    }
    else
      _notAssignedList.setListData(_notAssignedVector);
    if (!forUpdate){
      _assignedVectors = new Vector[_numberOfSections];
      _assignedLists = new JList[_numberOfSections];
    }
    Type type=_activities.getType(_actID,_typeID);
    for(int i=0; i< type.getSetOfSections().size(); i++){
      int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(i).getID());
      //for(int i = 0; i < _numberOfSections; i++){
      _assignedVectors[i] = _students.getStudentsByGroup(_actID, _typeID, group, _sortIndex);
      //System.out.println("_assignedVectors[i] "+_assignedVectors[i]);
      if (!forUpdate){
        _assignedLists[i] = new JList(_assignedVectors[i]);
        _assignedLists[i].setFont(DConst.JLISTS_FONT);
        _assignedLists[i].addMouseListener(mouseListenerLists);
      }
      else
        _assignedLists[i].setListData(_assignedVectors[i]);
    }
  }//end method
  private void setLists(int newIndex, boolean forUpdate){
    _notAssignedVector = getSortStudents(_notAssignedList, newIndex, -1).getStudentsByGroup(_actID, _typeID, -1, newIndex);
    if (_notAssignedList == null){
      _notAssignedList = new JList(_notAssignedVector);
      _notAssignedList.setFont(DConst.JLISTS_FONT);
      _notAssignedList.addMouseListener(mouseListenerLists);
    }
    else
      _notAssignedList.setListData(_notAssignedVector);
    if (!forUpdate){
      _assignedVectors = new Vector[_numberOfSections];
      _assignedLists = new JList[_numberOfSections];
    }
    Type type=_activities.getType(_actID,_typeID);
    for(int i=0; i< type.getSetOfSections().size(); i++){
      int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(i).getID());
      //for(int i = 0; i < _numberOfSections; i++){
      _assignedVectors[i] = getSortStudents(_assignedLists[i], newIndex, group).getStudentsByGroup(_actID, _typeID, group, newIndex);
      //System.out.println("_assignedVectors[i] "+_assignedVectors[i]);
      if (!forUpdate){
        _assignedLists[i] = new JList(_assignedVectors[i]);
        _assignedLists[i].setFont(DConst.JLISTS_FONT);
        _assignedLists[i].addMouseListener(mouseListenerLists);
      }
      else
        _assignedLists[i].setListData(_assignedVectors[i]);
    }
  }//end method


  /**
   * Sets the students in the groups indicated by the JLists
   */
  private void setStudentsInGroups(){
    StudentAttach s;
    String studentData;
    for(int i = 0; i < _notAssignedVector.size(); i++){
      studentData = (String)_notAssignedVector.elementAt(i);
      s = getStudentAttach(studentData);
      s.setInGroup(_actID+_typeID, -1, false);
      //System.out.println("Student: "+studentData+" -Activity: "+String.valueOf(_actID+_typeID)+
      //" -StudentAttach Group: "+ s.getGroup(_actID+_typeID));
    }//end for(int i = 0; i < _notAssignedVector.size(); i++)
    Type type=_activities.getType(_actID,_typeID);
    for(int j = 0; j < _assignedVectors.length; j++){
      int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(j).getID());
      for(int k = 0; k < _assignedVectors[j].size(); k++){
        studentData = (String)_assignedVectors[j].elementAt(k);
        s = getStudentAttach(studentData);
        //System.out.println("Student: "+studentData+" -Activity: "+String.valueOf(_actID+_typeID)+
        // " -StudentAttach: "+ s);
        if (studentData.endsWith(DConst.CHAR_FIXED_IN_GROUP))
          s.setInGroup(_actID+_typeID, group, true);
        else
          s.setInGroup(_actID+_typeID, group, false);
      }//end for(int k = 0; k < _assignedVectors[j].size(); k++)
    }//end for(int j = 0; j < _assignedVectors.length; j++)
  }//end method

  private void changeFixedInGroup(Object [] obj, int group){
    boolean fixedInGroup;
    int index = 0;
    String studentData = "";
    for(int i = 0; i < obj.length; i++){
      studentData = (String)obj[i];
      index = _assignedVectors[group].indexOf(studentData);
      if(studentData.endsWith(DConst.CHAR_FIXED_IN_GROUP)) {
        studentData = studentData.substring(0, studentData.length()-DConst.CHAR_FIXED_IN_GROUP.length());
      }else{
        studentData = studentData + DConst.CHAR_FIXED_IN_GROUP;
      }

      _assignedVectors[group].setElementAt(studentData, index);
    }//end for
    _notAssignedList.setListData(_notAssignedVector);
    //setLists(_sortIndex, true);
  }


  private void listTransfers(JList sourceList, JList destinationList, Vector sourceVector, Vector destinationVector, String chain, boolean toLeft, int sortIndex){
    if (sourceList == null || destinationList == null || sourceVector == null || destinationVector == null )
      return;
    SetOfResources destinationRes = new SetOfResources(0);
    Resource res;
    Object [] elementsToTransfer = sourceList.getSelectedValues();
    String strElement, ID, key;
    if (elementsToTransfer.length != 0){
      for (int i = 0; i < elementsToTransfer.length; i++){
        sourceVector.remove(elementsToTransfer[i]);
        strElement = (String)elementsToTransfer[i];
        if (toLeft){
          if(strElement.endsWith(chain))
            elementsToTransfer[i] = strElement.substring(0, strElement.length()-chain.length());
        }else{
          elementsToTransfer[i] = strElement + chain;
        }
        destinationVector.add(elementsToTransfer[i]);
      }
      for(int j = 0; j < destinationVector.size(); j++){
        res = new Resource((String)destinationVector.elementAt(j),null);
        destinationRes.addResource(res, 1);
      }
      if (sortIndex == 0)
        destinationRes.sortSetOfResourcesByID();
      if (sortIndex == 1)
        destinationRes.sortSetOfResourcesByKey();
      destinationVector.removeAllElements();
      destinationRes.getNamesVector(destinationVector);
      sourceList.setListData(sourceVector);
      destinationList.setListData(destinationVector);
      int[] indices = DXTools.getIndicesOfIntersection(destinationVector, elementsToTransfer);
      destinationList.setSelectedIndices(indices);
      sourceList.clearSelection();
    }//end for
  }//end method


  /**
   * Build StudentAttach of a String containing some information whom the student ID
   * @param studentData the information containig the studentID and other informations
   * @param sortIndex An index indicating the place of the StudentId
   * @return the StudentAttach according ti the ID found
   */
  private StudentAttach getStudentAttach(String studentData){
    StudentAttach s;
    String studentID = null;
    long studentKey;
    if (_sortIndex == 0)
      studentID = studentData.substring(DConst.STUDENT_ID_LENGTH+1, DConst.STUDENT_ID_LENGTH+1+DConst.STUDENT_KEY_LENGTH).trim();
    if (_sortIndex == 1)
      studentID = studentData.substring(0, DConst.STUDENT_KEY_LENGTH).trim();
    if (_sortIndex == 2)
      studentID = studentData.substring(DConst.STUDENT_PROGRAM_LENGTH+DConst.STUDENT_ID_LENGTH+1, DConst.STUDENT_PROGRAM_LENGTH+DConst.STUDENT_ID_LENGTH+1+DConst.STUDENT_KEY_LENGTH+1).trim();
    //System.out.println("studentkey " + studentID);
    studentKey = Long.parseLong(studentID);
    s = (StudentAttach)_students.getResource(studentKey).getAttach();
    return s;
  }//end method


  private Resource getStudent(String studentData, int sortIndex){
    Resource s;
    String studentID = null;
    long studentKey;
    if (_sortIndex == 0)
      studentID = studentData.substring(DConst.STUDENT_ID_LENGTH+1, DConst.STUDENT_ID_LENGTH+1+DConst.STUDENT_KEY_LENGTH).trim();
    if (_sortIndex == 1)
      studentID = studentData.substring(0, DConst.STUDENT_KEY_LENGTH).trim();
    if (_sortIndex == 2)
      studentID = studentData.substring(DConst.STUDENT_PROGRAM_LENGTH+DConst.STUDENT_ID_LENGTH+1, DConst.STUDENT_PROGRAM_LENGTH+DConst.STUDENT_ID_LENGTH+1+DConst.STUDENT_KEY_LENGTH+1).trim();
    //System.out.println("studentkey " + studentID);
    studentKey = Long.parseLong(studentID);
    s = _students.getResource(studentKey);
    return s;
  }//end method
  private Vector buildSortVector() {
    Vector v = new Vector();
    v.add(DConst.SORT_BY_NAME);
    v.add(DConst.SORT_BY_MATRICUL);
    v.add(DConst.SORT_BY_PROGRAM);
    return v;
  }
}//end class