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
  private Dimension size;
  private int _numberOfSections;
  private JComboBox _actCombo, _typeCombo;
  private JList _notAssignedList;
  private JPanel _arrowsPanel, _assignedPanel, _centerPanel, _notAssignedPanel, _panel, _topPanel, _bottomPanel, _test;
  private JScrollPane _scrPane;
  private Section _section;
  private SetOfActivities _activities;
  private SetOfStudents _students;
  private String _actID, _typeID;
  private Type _type;
  private Vector _actVector, _notAssignedVector, _typeVector;
  private JButton _button = new JButton("Hi");
  private JButton _button2 = new JButton("Adios");

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
      jbInitC();
      pack();
      setLocationRelativeTo(dApplic.getJFrame());
      setVisible(true);
    }
}

public void jbInit(){
  getContentPane().setLayout(new BorderLayout());
  setSize(600,600);
  setResizable(true);
  setTopPanel();
  /*setNotAssignedPanel();
  setArrowsPanel();
  setAssignedPanel();*/
  setCenterPanel();
}

public void jbInitB(){
  getContentPane().setLayout(new BorderLayout());
  setSize(600,600);
  setResizable(true);
  setTopPanel();
  _arrowsPanel = new JPanel();
  _arrowsPanel.setPreferredSize(new Dimension(100,400));
  _assignedPanel = new JPanel();
  _assignedPanel.setPreferredSize(new Dimension(200,400));
  _notAssignedPanel = new JPanel();
  _notAssignedPanel.setPreferredSize(new Dimension(200,400));

  _arrowsPanel.add(new JButton("arrow"));
  _assignedPanel.add(new JButton("assign"));
  _notAssignedPanel.add(new JButton("notAssign"));

  getContentPane().add(_topPanel, BorderLayout.NORTH);
  getContentPane().add(_notAssignedPanel, BorderLayout.WEST);
  getContentPane().add(_assignedPanel, BorderLayout.EAST);
  getContentPane().add(_arrowsPanel, BorderLayout.CENTER);


  /*
  getContentPane().setLayout( new BorderLayout() );
  setSize(new Dimension(600, 470));
  setResizable(true);
  setTopPanel();
  _assignedPanel = new JPanel( new BorderLayout() );
  _assignedPanel.setBorder(new TitledBorder(new EtchedBorder(), "Assigned"));
  _assignedPanel.setSize( new Dimension(0, 0) );
  _bottomPanel = new JPanel();
  JPanel jPanel4 = new JPanel( new GridLayout(0, 1 , 0, 0 ) );
  //_topPanel = new JPanel();
  _centerPanel = new JPanel();
  _notAssignedPanel = new JPanel(new BorderLayout());
  _notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(), "noAssig"));
  //nbFreePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
  //actPanel = new JPanel();
  //actPanel.setBorder(new TitledBorder(new EtchedBorder(), MES01));
  //typePanel = new JPanel();
  //typePanel.setBorder(new TitledBorder(new EtchedBorder(), MES03));

  JScrollPane scrollPane = new JScrollPane();
  scrollPane.setBorder(null);
  scrollPane.setPreferredSize(new Dimension(300, 330));
  JScrollPane scrollTable1 = new JScrollPane();
  scrollTable1.setPreferredSize(new Dimension(246, 300));


  _assignedPanel.add(scrollPane, BorderLayout.CENTER);
  */
  /*
  actPanel.add(jComboBoxActivity);
  topPanel.add(actPanel);
  typePanel.add(jComboBoxType);
  topPanel.add(typePanel);
  */
  //this.getContentPane().add(topPanel, BorderLayout.NORTH);
  //nbFreePanel.add(jLabel7);
  //nbFreePanel.add(jTextField1);
  //freePanel.add(nbFreePanel, BorderLayout.NORTH);
  //freePanel.add(scrollTable1, BorderLayout.CENTER);
  /*
  _centerPanel.add(_notAssignedPanel);
  _centerPanel.add(_assignedPanel);
  this.getContentPane().add(_centerPanel, BorderLayout.CENTER);
  //bottomPanel.add(jButtonOk, null);
  //bottomPanel.add(jButtonCancel, null);
  //bottomPanel.add(jButtonApply, null);
  this.getContentPane().add(_bottomPanel, BorderLayout.SOUTH);
  scrollPane.setViewportView(jPanel4);
*/

}

public void jbInitC(){
  getContentPane().setLayout(new BorderLayout());
  size = new Dimension(400,600);
  _test= new JPanel();
  //getContentPane().setSize(size);
  setSize(size);
  setResizable(true);
  setTopPanel();
  _scrPane= new JScrollPane();
  _scrPane.setBorder(null);
    _scrPane.setPreferredSize(new Dimension(200, 230));
    _scrPane.setViewportView(_button);
  _panel = new JPanel();
  //_panel.setPreferredSize(new Dimension(20,200));
  //_test.add(_button);
  //_test.add(_button2,1);
  //_panel.add(_test);
  _panel.add(_scrPane);
  getContentPane().add(_topPanel, BorderLayout.NORTH);
  getContentPane().add(_panel, BorderLayout.CENTER);
  /*_arrowsPanel = new JPanel();
  _arrowsPanel.setPreferredSize(new Dimension(100,400));
  _assignedPanel = new JPanel();
  _assignedPanel.setPreferredSize(new Dimension(200,400));
  _notAssignedPanel = new JPanel();
  _notAssignedPanel.setPreferredSize(new Dimension(200,400));

  _arrowsPanel.add(new JButton("arrow"));
  _assignedPanel.add(new JButton("assign"));
  _notAssignedPanel.add(new JButton("notAssign"));

  getContentPane().add(_topPanel, BorderLayout.NORTH);
  getContentPane().add(_notAssignedPanel, BorderLayout.WEST);
  getContentPane().add(_assignedPanel, BorderLayout.EAST);
  getContentPane().add(_arrowsPanel, BorderLayout.CENTER);
  */
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
  //actPanel.setPreferredSize(new Dimension(100,50));
  _actID = (String)_actVector.get(0);
  //panel of types
  _typeVector = ((Activity)(_activities.getResource(_actID).getAttach())).getSetOfTypes().getNamesVector();
  _typeCombo = new JComboBox(_typeVector);
  typePanel = new JPanel();
  typePanel.setBorder(new TitledBorder(new EtchedBorder(), ACTIVITY));
  typePanel.add(_typeCombo);
  //typePanel.setPreferredSize(new Dimension(100,50));
  _typeID = (String)_typeVector.get(0);
  setCurrents();
  //adding the panels to topPanel
  _topPanel = new JPanel();
  //_topPanel.setPreferredSize(new Dimension(350,100));
  _topPanel.add(actPanel);
  _topPanel.add(typePanel);
  //getContentPane().add(_topPanel, BorderLayout.NORTH);
  //adding the actionListeners
  _actCombo.addActionListener(this);
  _typeCombo.addActionListener(this);
}

public void setNotAssignedPanel(){
  _notAssignedVector = _students.getStudentsByGroup(_actID, "1", -1);
  _notAssignedList = new JList(_notAssignedVector);
  JScrollPane scrollPane = new JScrollPane();
  //scrollPane.setPreferredSize(new Dimension(100,200));
  scrollPane.getViewport().add(_notAssignedList);
  //adding the JScrollPane to panel
  _notAssignedPanel = new JPanel();
  _notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(), ACT_STUD_NOT_ASSIGNED));
  //_notAssignedPanel.setPreferredSize(new Dimension(150,200));
  _notAssignedPanel.add(scrollPane);
  //getContentPane().add(_notAssignedPanel, BorderLayout.WEST);
}

public void setArrowsPanel(){
  _arrowsPanel = new JPanel();
  //_arrowsPanel.setPreferredSize(new Dimension(50,200));
  _arrowsPanel.add(new JButton("w"));
  //getContentPane().add(_arrowsPanel, BorderLayout.CENTER);
}

public void setAssignedPanel(){
  _assignedPanel = new JPanel(new BorderLayout());
  _assignedPanel.setBorder(new TitledBorder(new EtchedBorder(), "Assigned"));
  //_assignedPanel.setSize( new Dimension(0, 0) );
  _assignedPanel.add(new JButton("Hi"));
  //getContentPane().add(_assignedPanel, BorderLayout.EAST);
}

private void setCenterPanel(){
  setNotAssignedPanel();
  setArrowsPanel();
  setAssignedPanel();
  _centerPanel = new JPanel();
  //_centralPanel.setPreferredSize(new Dimension(500,400));
  _centerPanel.add(_notAssignedPanel);
  _centerPanel.add(_arrowsPanel);
  _centerPanel.add(_assignedPanel);
  getContentPane().add(_centerPanel, BorderLayout.CENTER);
}

public void setCurrents(){
  _act = (Activity)(_activities.getResource(_actID).getAttach());
  _type = (Type)_act.getSetOfTypes().getResource(_typeID).getAttach();
  _numberOfSections = _type.getSetOfSections().size();
}

public void actionPerformed(ActionEvent e){
  String command = e.getActionCommand();
    if (e.getSource().equals(_actCombo)){
      /*_actID = (String)_actCombo.getSelectedItem();
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
      */
      //_assignedPanel.removeAll();
      //_assignedPanel.add(new JButton("Hi2"));
      //_assignedPanel.repaint();
      //repaint();
      //remove(_assignedPanel);
      //System.out.println("getComponentCount() before" +this.getContentPane().getComponentCount());
      //this.getContentPane().remove(_panel);
      //System.out.println("_panel.getComponentCount() after " +this.getContentPane().getComponentCount());
      //this.getContentPane().repaint();
      //repaint();
      //JPanel panel2 = new JPanel(new GridLayout(1,1));
      //panel2.add(new JButton("adios"));
      //panel2.setPreferredSize(new Dimension(200,400));
      //this.getContentPane().add(panel2, BorderLayout.CENTER);
      //System.out.println("_panel.getComponentCount() after2 " +this.getContentPane().getComponentCount());
      //this.getContentPane().repaint();
      //setSize(600,601);
/*
      if (this.getSize().equals(size))
        this.setSize(new Dimension(600,601));
      else
        this.setSize(size);
*/
      //repaint();
      //_test.removeAll();
      _scrPane.setViewportView(new JButton("adios"));
      //_test.add(new JButton("adios"));
      //_panel.remove(0);
      //repaint();
      //_button.setText("Adios");





      //_panel.add(new JButton("Bye"), BorderLayout.CENTER);
      //System.out.println("_panel.getComponentCount() " +_panel.getComponentCount());
      //_panel.repaint();
      //this.getContentPane().repaint();
      //this.repaint();


    }//end if (e.getSource().equals(_actCombo))
    else if (e.getSource().equals(_typeCombo)){
      //_notAssignedVector.removeAllElements();
      //_notAssignedList.setListData(_notAssignedVector);
      //_assignedPanel.removeAll();
      /*_assignedPanel.removeAll();
      _assignedPanel.add(new JButton("Bye"));
      _assignedPanel.repaint();
      repaint();*/
      this.remove(_assignedPanel);
      _assignedPanel = null;
      _assignedPanel = new JPanel();
      _assignedPanel.add(new JButton("Type"));
      this.getContentPane().add(_assignedPanel, BorderLayout.EAST);
      //_assignedPanel.repaint();
      repaint();
    }
}//end method

}//end class