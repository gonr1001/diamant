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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dInternal.dData.SetOfActivities;
import dInternal.dData.Activity;

import dResources.DConst;

public class ActivityDlg extends JDialog implements ActionListener {

  private DApplication _dApplic;
  private SetOfActivities _activities;
  /**
   * The JList items choice by the user
   */
  private Object [] _currentActivities = new Object[0];
  private String [] _buttonsNames = {DConst.BUT_OK, DConst.BUT_CANCEL};
  private String [] _arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};
  private static String ACTLIST = DConst.ACT_LIST;
  private static String NOT_INCLUDED = DConst.NOT_INCLUDED;
  private static String INCLUDED = DConst.INCLUDED;
  /**
   * the vectors containing the activities ID
   */
  private Vector _noVisibleVec, _visibleVec;
  private JButton _toRight, _toLeft;
  private JDialog _jd;
  /**
   * the lists containing the activities ID
   */
  private JLabel _lVisible, _lNoVisible;
  private JList _noVisibleList, _visibleList;
  private JPanel _listsPanel, _arrowsPanel;


  /**
   * Dafault constructor
   * @param dApplic The application object (for extracting the JFrame)
   */
  public ActivityDlg(DApplication dApplic) {
     super(dApplic.getJFrame(), ACTLIST, true);
     _dApplic = dApplic;
     _jd = this;  //to pass this dialog to the EditActivityDlg
     if (_dApplic.getDMediator().getCurrentDoc() != null){
       _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
       jbInit();
       setLocationRelativeTo(dApplic.getJFrame());
       setVisible(true);
       triggerListeners();
     }
  }


  /**
   * Initialize the dialog
   */
  private void jbInit(){
    /*_show = new JButton(SHOW);
    _show.setPreferredSize(new Dimension(80,22));
    _cancel = new JButton(CLOSE);
    _cancel.setPreferredSize(new Dimension(80,22));
    */
    _listsPanel = new JPanel();
    //left panel
    JPanel rightPanel = new JPanel();
    JScrollPane rightSPane = new JScrollPane();
    //set the vectors
    _noVisibleVec = _activities.getIDsByField(3, "false");
    _visibleVec = _activities.getIDsByField(3, "true");
    //set the JLists
    _noVisibleList = new JList(_noVisibleVec);
    _visibleList = new JList(_visibleVec);
    rightSPane.setPreferredSize(new Dimension(150,300));
    rightSPane.getViewport().add(_noVisibleList);
    rightPanel = new JPanel(new BorderLayout());
    _lNoVisible = new JLabel(_noVisibleVec.size() + " " + NOT_INCLUDED);
    rightPanel.add(_lNoVisible , BorderLayout.NORTH);
    rightPanel.add(rightSPane, BorderLayout.CENTER);
    //right panel
    JPanel leftPanel = new JPanel();
    JScrollPane leftSPane = new JScrollPane();
    _visibleList = new JList(_visibleVec);
    leftSPane = new JScrollPane();
    leftSPane.setPreferredSize(new Dimension(150,300));
    leftSPane.getViewport().add(_visibleList);
    leftPanel = new JPanel(new BorderLayout());
    _lVisible = new JLabel(_visibleVec.size() + " " + INCLUDED);
    leftPanel.add(_lVisible, BorderLayout.NORTH);
    leftPanel.add(leftSPane, BorderLayout.CENTER);
    //arrows panel
    _arrowsPanel = DXTools.arrowsPanel(this, _arrowsNames, 50, 70);
    //placing the panels and buttons into the _listsPanel
    _listsPanel.add(leftPanel, BorderLayout.EAST);
    _listsPanel.add(_arrowsPanel, BorderLayout.CENTER);
    _listsPanel.add(rightPanel, BorderLayout.WEST);
    //buttonsPanel
    JPanel buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    //placing the elements into the JDialog
    setSize(380, 390);
    setResizable(false);
    triggerListeners();
    getContentPane().add(_listsPanel, BorderLayout.CENTER);
    getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
  }//end method


  /**
   * Launch the listeners
   */
  private void triggerListeners(){
    //_cancel.addActionListener(this);
    //_show.addActionListener(this);
    //_toLeft.addActionListener(this);
    //_toRight.addActionListener(this);
    _noVisibleList.addMouseListener(mouseListenerLists);
    _visibleList.addMouseListener(mouseListenerLists);
  }//end triggerListeners()


  /**
   * Defins the mouse adapter and actions for the JListis
   */
  private MouseListener mouseListenerLists = new MouseAdapter(){
    public void mouseClicked(MouseEvent e) {
      if (((JList)e.getSource()).getModel().getSize() == 0)
        return;
      if (e.getSource().equals(_visibleList))
        _noVisibleList.clearSelection();
      else
        _visibleList.clearSelection();
      _currentActivities = ((JList)e.getSource()).getSelectedValues();
      if (e.getClickCount() == 2) {
        new EditActivityDlg(_jd,_dApplic, (String)_currentActivities[0]);
      }//end if
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){




  /**
   *
   * @param e an event
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    if (command.equals(_buttonsNames[1]))
        dispose();
    if (command.equals(_buttonsNames[0])){
      if (_currentActivities.length != 0)
      new EditActivityDlg(this, _dApplic, (String)_currentActivities[0]);
    }// end if (command.equals(SHOW))
    if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1])){
      System.out.println("((JButton)_arrowsPanel.getComponent(0)).getText() "+((JButton)_arrowsPanel.getComponent(0)).getText());

      if (command.equals(_arrowsNames[1]))
        DXTools.actionButton(_activities, 3, "false", "true", _noVisibleList, _visibleList);
      else
        DXTools.actionButton(_activities, 3, "true", "false", _visibleList, _noVisibleList);
      _noVisibleVec = _activities.getIDsByField(3, "false");
      _visibleVec = _activities.getIDsByField(3, "true");
      _lNoVisible.setText(_noVisibleVec.size() + " " + NOT_INCLUDED);
      _lVisible.setText(_visibleVec.size() + " " + INCLUDED);
      _activities.setByField(_visibleVec, 3, "true");
      _activities.setByField(_noVisibleVec, 3, "false");
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStates().sendEvent();
    }

  }//end method

}// end class