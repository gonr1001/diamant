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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.Font;
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

import dResources.DConst;

public class GroupDlg extends JDialog implements ActionListener{

  private Activity _act;
  private DApplication _dApplic = null;
  private Dimension dialogSize = new Dimension(500,500);
  private int _numberOfSections;
  private JButton _ok, _close;
  private JComboBox _actCombo, _typeCombo;
  private JList _notAssignedList;
  private JPanel _arrowsPanel, _assignedPanel, _buttonsPanel, _insidePanel, _centerPanel, _notAssignedPanel, _topPanel;
  private JScrollPane _scrollPane;
  private JTextField _numberbOfElements;
  private Section _section;
  private SetOfActivities _activities;
  private SetOfStudents _students;
  private String _actID, _typeID;
  private Type _type;
  private Vector _actVector, _notAssignedVector, _typeVector;


  private static String ACT_STUD_NOT_ASSIGNED = "�tudiants non assign�s";
  private static String ACT_STUD_ASSIGNED = "�tudiants assign�s";
  private static String ACTIVITY = "Activit�";
  private static String GROUP_DLG_TITLE = "Group Dlg";
  private static String OK = "OK";
  private static String CLOSE = DConst.CLOSE;
  private static String GROUP = "Group ";
  private static String NUMBER_OF_ELEMENTS = "Nombre d'�l�ments";
  private static String TO_LEFT = DConst.TO_LEFT;
  private static String TO_RIGHT = DConst.TO_RIGHT;
  private static String TYPE = "Type";

  public GroupDlg(DApplication dApplic){
    super(dApplic.getJFrame(), GROUP_DLG_TITLE, true);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() != null)
      _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    _students = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents();
    if (_activities != null && _students != null){
      jbInit();
      //pack();
      setLocationRelativeTo(dApplic.getJFrame());
      setVisible(true);
    }
  }

  private void jbInit(){
    getContentPane().setLayout(new BorderLayout());
    setSize(dialogSize);
    setResizable(true);
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

  private void setNotAssignedPanel(){
    Dimension panelDim = new Dimension((int)((dialogSize.getWidth()-50)*0.35), (int)dialogSize.getHeight()-130);
    Dimension scrollDim = new Dimension((int)panelDim.getWidth()-10, (int)panelDim.getHeight()-33);
    Dimension listDim = new Dimension((int)scrollDim.getWidth()-10,(int)scrollDim.getHeight()-20);
    _notAssignedPanel = new JPanel();
    _notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_STUD_NOT_ASSIGNED));
    _notAssignedPanel.setPreferredSize(panelDim);
    //System.out.println("_notAssignedPanel.getPreferredSize() "+_notAssignedPanel.getPreferredSize());
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setPreferredSize(scrollDim);
    _notAssignedVector = _students.getStudentsByGroup(_actID, "1", -1);
    _notAssignedList = new JList(_notAssignedVector);
    _notAssignedList.setPreferredSize(listDim);
    _notAssignedList.addMouseListener(mouseListenerLists);
    //System.out.println("_notAssignedList.getPreferredSize() "+_notAssignedList.getPreferredSize());
    scrollPane.getViewport().add(_notAssignedList);
    _notAssignedPanel.add(scrollPane);
  }


  private void setArrowsPanel(){
    //the buttons _toLeft and _toRight
    JButton _toRight = new JButton(TO_RIGHT);
    _toRight.setSize(new Dimension(30,35));
    JButton _toLeft = new JButton(TO_LEFT);
    _toLeft.setSize(new Dimension(30,35));
    _arrowsPanel = new JPanel(new BorderLayout());
    _arrowsPanel.setSize(new Dimension(50,200));
    _arrowsPanel.add(_toRight, BorderLayout.NORTH);
    _arrowsPanel.add(_toLeft, BorderLayout.SOUTH);
    //getContentPane().add(_arrowsPanel, BorderLayout.CENTER);
  }

  /**
   * Set _assignedPanel
   */
  private void setAssignedPanel(){
    Dimension panelDim = new Dimension((int)((dialogSize.getWidth()-50)*0.6), (int)dialogSize.getHeight()-130);
    Dimension scrollDim = new Dimension((int)panelDim.getWidth()-10,(int)panelDim.getHeight()-5);
    _assignedPanel = new JPanel(new BorderLayout());
    _assignedPanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_STUD_ASSIGNED));
    _assignedPanel.setPreferredSize(panelDim);
    _scrollPane = new JScrollPane();  //It is contained into _assignedPanel
    _scrollPane.setPreferredSize(scrollDim);
    //System.out.println("_scrollPane.getPreferredSize() "+_scrollPane.getPreferredSize());
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
    //Dimension scrollDim = new Dimension((int)insideDim.getWidth()-10, scrollHeight);
    //Dimension listDim = new Dimension((int)scrollDim.getWidth()-20, (int)scrollDim.getHeight()-10);
    if (_insidePanel == null){
      _insidePanel = new JPanel();
    }
    _insidePanel.setPreferredSize(insideDim);
    //System.out.println("_insidePanel.getPreferredSize() "+_insidePanel.getPreferredSize());
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
    Dimension scrollContDim = new Dimension((int)insideWidth, GroupPanelHeight-infoPanelHeight);
    Dimension listDim = new Dimension((int)scrollContDim.getWidth(), (int)scrollContDim.getHeight());

    JPanel groupPanel = new JPanel();
    groupPanel.setPreferredSize(groupPanelDim);
    System.out.println("groupPanel.getPreferredSize() "+groupPanel.getPreferredSize());
    JPanel infoPanel = new JPanel();
    //The infoPanel
    infoPanel.setPreferredSize(new Dimension(insideWidth-10, infoPanelHeight));
    System.out.println("infoPanel.getPreferredSize() "+ infoPanel.getPreferredSize());
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
    scroll.setPreferredSize(scrollContDim);
    JList assignedList;
    Vector asignedVector;
    asignedVector = _students.getStudentsByGroup(_actID, "1", groupNumber);
    assignedList = new JList(asignedVector);
    assignedList.addMouseListener(mouseListenerLists);
    scroll.getViewport().add(assignedList);
    System.out.println("scrollIntoGroup.getPreferredSize() "+scroll.getPreferredSize());
    //adding the subpanel into panel
    scrollContainer.add(scroll);
    groupPanel.add(infoPanel);
    groupPanel.add(scrollContainer);
  return groupPanel;
}


  /**
   *
   */
  private void setButtonsPanel(){
    _ok = new JButton(OK);
    _close = new JButton(CLOSE);
    _buttonsPanel = new JPanel();
    _buttonsPanel.add(_ok);
    _buttonsPanel.add(_close);
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);

  }


  /**
   * Set _centerPanel
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
    }//end if (e.getSource().equals(_actCombo))
    else if (e.getSource().equals(_typeCombo)){
      _typeID = (String)_typeCombo.getSelectedItem();
      setCurrents();
      setInsidePanel();
    }
  }//end method

  private MouseListener mouseListenerLists = new MouseAdapter(){
    public void mouseClicked(MouseEvent e) {
      if (((JList)e.getSource()).getModel().getSize() == 0)
        return;
      if (e.getSource().equals(_notAssignedList)){
        for(int i = 0; i < _numberOfSections; i++){
          getGroupList(i).clearSelection();
        }
        //_noVisibleList.clearSelection();
      }


      else
        //_visibleList.clearSelection();
      //_currentActivities = ((JList)e.getSource()).getSelectedValues();
      if (e.getClickCount() == 2) {
        //new EditActivityDlg(_jd,_dApplic, (String)_currentActivities[0]);
      }//end if
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){

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