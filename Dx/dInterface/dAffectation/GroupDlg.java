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
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

import dResources.DConst;

public class GroupDlg extends JDialog implements ActionListener{

  private Activity _act;
  private DApplication _dApplic = null;
  private Dimension dialogSize = new Dimension(500,500);
  private int _numberOfSections, _currentAssignedGroup = -1;
  private JButton _apply, _cancel, _ok;
  private JComboBox _actCombo, _typeCombo;
  private JList _notAssignedList, _assignedLists[];
  private JPanel _arrowsPanel, _assignedPanel, _buttonsPanel, _insidePanel, _centerPanel, _notAssignedPanel, _topPanel;
  private JScrollPane _scrollPane;
  private JTextField _numberbOfElements;
  private Object [] _currentStudents = new Object[0];
  private Section _section;
  private SetOfActivities _activities;
  private SetOfStudents _students;
  private String _actID, _typeID;
  private Type _type;
  private Vector _actVector, _notAssignedVector, _typeVector, _assignedVectors[];

  private static String ACT_STUD_NOT_ASSIGNED = DConst.ACT_STUD_NOT_ASSIGNED;
  private static String ACT_STUD_ASSIGNED = DConst.ACT_STUD_ASSIGNED;
  private static String ACTIVITY = DConst.ACTIVITY;
  private static String GROUP_DLG_TITLE = DConst.GROUP_DLG_TITLE;
  private static String APPLY = DConst.BUT_APPLY;
  private static String CANCEL = DConst.BUT_CANCEL;
  private static String GROUP = DConst.GROUP;
  private static String NUMBER_OF_ELEMENTS = DConst.NUMBER_OF_ELEMENTS;
  private static String OK = DConst.BUT_OK;
  private static String TO_LEFT = DConst.TO_LEFT;
  private static String TO_RIGHT = DConst.TO_RIGHT;
  private static String TYPE = DConst.TYPE;

  /**
   * Constructor
   * @param dApplic
   */
  public GroupDlg(DApplication dApplic){
    super(dApplic.getJFrame(), GROUP_DLG_TITLE, true);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() != null)
      _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    _students = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents();
    if (_activities != null && _students != null){
      jbInit();
      setLocationRelativeTo(dApplic.getJFrame());
      setVisible(false);
    }//end if (_activities != null && _students != null)
  }//end method

  /**
   * Initilize the Dialog
   */
  private void jbInit(){
    getContentPane().setLayout(new BorderLayout());
    setSize(dialogSize);
    //setResizable(true);
    setTopPanel();
    setButtonsPanel();
    setCenterPanel();
  }


  /**
   * Set the panel containing the activity end type JComboBoxes
   */
  private void setTopPanel(){
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
    typePanel.setBorder(new TitledBorder(new EtchedBorder(), TYPE));
    typePanel.add(_typeCombo);
    typePanel.setPreferredSize(new Dimension(100,50));
    _typeID = (String)_typeVector.get(0);
    setCurrents();
    //adding the panels to topPanel
    _topPanel = new JPanel();
    _topPanel.setPreferredSize(new Dimension(300,60));
    _topPanel.add(actPanel);
    _topPanel.add(typePanel);
    getContentPane().add(_topPanel, BorderLayout.NORTH);
    //adding the actionListeners
    _actCombo.addActionListener(this);
    _typeCombo.addActionListener(this);
  }

  /**
   * Set the left panel who shows the list of the not assigned students
   */
  private void setNotAssignedPanel(){
    Dimension panelDim = new Dimension((int)((dialogSize.getWidth()-50)*0.35), (int)dialogSize.getHeight()-130);
    Dimension scrollDim = new Dimension((int)panelDim.getWidth()-10, (int)panelDim.getHeight()-33);
    Dimension listDim = new Dimension((int)scrollDim.getWidth()-10,(int)scrollDim.getHeight()-20);
    _notAssignedPanel = new JPanel();
    _notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_STUD_NOT_ASSIGNED));
    _notAssignedPanel.setPreferredSize(panelDim);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setPreferredSize(scrollDim);
    _notAssignedVector = _students.getStudentsByGroup(_actID, "1", -1);
    _notAssignedList = new JList(_notAssignedVector);
    _notAssignedList.setPreferredSize(listDim);
    _notAssignedList.addMouseListener(mouseListenerLists);
    scrollPane.getViewport().add(_notAssignedList);
    _notAssignedPanel.add(scrollPane);
  }


  /**
   * Set the panel conatining the arrows "toLeft" and "toRight"
   */
  private void setArrowsPanel(){
    //the buttons _toLeft and _toRight
    JButton _toRight = new JButton(TO_RIGHT);
    _toRight.setSize(new Dimension(30,35));
    _toRight.addActionListener(this);
    JButton _toLeft = new JButton(TO_LEFT);
    _toLeft.setSize(new Dimension(30,35));
    _toLeft.addActionListener(this);
    _arrowsPanel = new JPanel(new BorderLayout());
    _arrowsPanel.setSize(new Dimension(50,200));
    _arrowsPanel.add(_toRight, BorderLayout.NORTH);
    _arrowsPanel.add(_toLeft, BorderLayout.SOUTH);
  }

  /**
   * Set _assignedPanel, the panel containing the lists of assigned students
   */
  private void setAssignedPanel(){
    _assignedVectors = new Vector[_numberOfSections];
    _assignedLists = new JList[_numberOfSections];
    Dimension panelDim = new Dimension((int)((dialogSize.getWidth()-50)*0.6), (int)dialogSize.getHeight()-130);
    Dimension scrollDim = new Dimension((int)panelDim.getWidth()-10,(int)panelDim.getHeight()-5);
    _assignedPanel = new JPanel(new BorderLayout());
    _assignedPanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_STUD_ASSIGNED));
    _assignedPanel.setPreferredSize(panelDim);
    _scrollPane = new JScrollPane();  //It is contained into _assignedPanel
    _scrollPane.setPreferredSize(scrollDim);
    _insidePanel = new JPanel(new GridLayout(_numberOfSections, 1)); //It is contained into _scrollPane
    setInsidePanel();
    _assignedPanel.add(_scrollPane);
  }

  /**
   * Set the panel contained in _scrollPane. This panel contains the sub-JScrollPanes
   * corresponding to the groups in a type of activity
   */
  private void setInsidePanel(){
    int insideWidth = (int)_scrollPane.getPreferredSize().getWidth()-20;
    int scrollHeight = (int)((_scrollPane.getPreferredSize().getHeight()-10)/2);
    Dimension insideDim = new Dimension(insideWidth, (int)scrollHeight*_numberOfSections+10);
    if (_insidePanel == null)
      _insidePanel = new JPanel();
    _insidePanel.setPreferredSize(insideDim);
    _insidePanel.removeAll();
    _insidePanel.setLayout(new GridLayout(_numberOfSections, 1));
    for (int i = 0; i < _numberOfSections; i++){
      _insidePanel.add(setGroupPanel(i));
    }
    _scrollPane.setViewportView(_insidePanel);
  }//end method

  /**
   * Sets the panel containing the list of students belonging a section (a group)
   * @param groupNumber The SectionID
   * @return a panel to be inserted into _insidePanel
   */
  private JPanel setGroupPanel(int groupNumber){
    int insideWidth = (int)_insidePanel.getPreferredSize().getWidth();
    int GroupPanelHeight = (int)((_scrollPane.getPreferredSize().getHeight())/2);
    int infoPanelHeight = 25;
    Dimension groupPanelDim = new Dimension(insideWidth, GroupPanelHeight);
    Dimension scrollContDim = new Dimension((int)insideWidth-5, GroupPanelHeight-infoPanelHeight-20);
    Dimension scrollDim = new Dimension((int)scrollContDim.getWidth(), (int)scrollContDim.getHeight()-3);
    JPanel groupPanel = new JPanel();
    groupPanel.setPreferredSize(groupPanelDim);
    groupPanel.addMouseListener(mouseListenerGroupPanel);
    JPanel infoPanel = new JPanel();
    //The infoPanel
    infoPanel.setPreferredSize(new Dimension(insideWidth-10, infoPanelHeight));
    JLabel lGroup = new JLabel(GROUP);
    JLabel lGroupNumber = new JLabel(String.valueOf(groupNumber));
    JLabel lNumberOfElements = new JLabel(NUMBER_OF_ELEMENTS);
    _numberbOfElements = new JTextField("num");
    infoPanel.add(lGroup);
    infoPanel.add(lGroupNumber);
    infoPanel.add(new JLabel(" - "));
    infoPanel.add(lNumberOfElements);
    infoPanel.add(_numberbOfElements);
    //The scrollPane
    JPanel scrollContainer = new JPanel();
    scrollContainer.setPreferredSize(scrollContDim);
    JScrollPane scroll = new JScrollPane();
    scroll.setPreferredSize(scrollDim);
    _assignedVectors[groupNumber] = _students.getStudentsByGroup(_actID, "1", groupNumber);
    _assignedLists[groupNumber] = new JList(_assignedVectors[groupNumber]);
    _assignedLists[groupNumber].addMouseListener(mouseListenerLists);
    scroll.getViewport().add(_assignedLists[groupNumber]);
    //adding the subpanel into panel
    scrollContainer.add(scroll);
    groupPanel.add(infoPanel);
    groupPanel.add(scrollContainer);
    return groupPanel;
  }


  /**
   * Set the panel containing the buttons
   */
  private void setButtonsPanel(){
    _apply = new JButton(APPLY);
    _apply.addActionListener(this);
    _cancel = new JButton(CANCEL);
    _cancel.addActionListener(this);
    _ok = new JButton(OK);
    _ok.addActionListener(this);
    _buttonsPanel = new JPanel();
    _buttonsPanel.add(_ok);
    _buttonsPanel.add(_apply);
    _buttonsPanel.add(_cancel);
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
  }


  /**
   * Set _centerPanel, the panel containing _assignedPanel, _arrowsPanel and
   * _notAssignedPanel
   */
  private void setCenterPanel(){
    setNotAssignedPanel();
    setArrowsPanel();
    setAssignedPanel();
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
      setInsidePanel();
    }//end if (e.getSource().equals(_actCombo))
    if (e.getSource().equals(_typeCombo)){
      _typeID = (String)_typeCombo.getSelectedItem();
      setCurrents();
      _assignedLists = new JList[_numberOfSections];
      _assignedVectors = new Vector[_numberOfSections];
      setInsidePanel();
    }//end if (e.getSource().equals(_typeCombo))
    if (command.equals(CANCEL))
      dispose();
    if (command.equals(OK)){
      //if (_currentActivities.length != 0)
      //new EditActivityDlg(this, _dApplic, (String)_currentActivities[0]);
    }// end if (command.equals(SHOW))
    if (command.equals(TO_LEFT) || command.equals(TO_RIGHT)){
      if (command.equals(TO_LEFT)){
        if (_currentAssignedGroup > -1)
          DXTools.listTransfers(_assignedLists[_currentAssignedGroup], _notAssignedList, _assignedVectors[_currentAssignedGroup], _notAssignedVector);
      }//end if (command.equals(TO_LEFT))
      else{
        if (_currentAssignedGroup > -1)
          DXTools.listTransfers(_notAssignedList, _assignedLists[_currentAssignedGroup], _notAssignedVector, _assignedVectors[_currentAssignedGroup]);
      }//end else
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
        System.out.println("_assignedLists "+_assignedLists);
        System.out.println("_numberOfSections "+_numberOfSections);
        for(int i = 0; i < _numberOfSections; i++){
          System.out.println("_assignedLists["+i+"]= "+_assignedLists[i]);
          _assignedLists[i].clearSelection();
        }
      }//end if (e.getSource().equals(_notAssignedList))
      //else{
        for(int i = 0; i<_numberOfSections; i++){
          if (e.getSource().equals(_assignedLists[i])){
            _currentAssignedGroup = i;
            setGroupBorders(_currentAssignedGroup, Color.blue);
            //System.out.println("_currentAssignedGroup " + _currentAssignedGroup);
            _notAssignedList.clearSelection();
            for (int j = 0; j<_numberOfSections; j++)
              if (j != i)
                _assignedLists[j].clearSelection();
          }//if (e.getSource().equals(_assignedLists[i]))
        }//end for(int i = 0; i<_numberOfSections; i++)
      _currentStudents = ((JList)e.getSource()).getSelectedValues();
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){

  private MouseListener mouseListenerGroupPanel = new MouseAdapter(){
    public void mouseClicked(MouseEvent e) {
      for(int i = 0; i < _numberOfSections; i++){
        if (e.getSource().equals( (JPanel)_insidePanel.getComponent(i))){
          _currentAssignedGroup = i;

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
        panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
      else
        panel.setBorder(null);
    }
  }


  /**
  * Returns a List containing the elements of a group
  * @param groupNumber the number of the group
  * @return a List containing the elements of a group
  */
  private JList getGroupList(int groupNumber){
    JScrollPane jcp;
    JPanel groupPanel, scrollContainer;
    JList list;
    groupPanel = (JPanel)_insidePanel.getComponent(groupNumber);
    scrollContainer = (JPanel)groupPanel.getComponent(1);
    jcp = (JScrollPane)scrollContainer.getComponent(0);
    list = (JList)jcp.getViewport().getComponent(0);
    return list;
  }

}//end class