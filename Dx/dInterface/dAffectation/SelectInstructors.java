package dInterface.dAffectation;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author YS
 * @version 1.0
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;

import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dInternal.dData.SetOfActivities;


import dResources.DConst;

public class SelectInstructors extends JDialog implements ActionListener {

  //private DApplication _dApplic;
  private JButton _toRight, _toLeft;
  /**
   * the lists containing the instructors ID
   */
  private JLabel _lVisible, _lNoVisible;
  private JList _rightList, _leftList;
  private JPanel _centerPanel, _arrowsPanel;
  private ButtonsPanel _buttonsPanel;
  private DApplication _dApplic;
  private EditActivityDlg _ead;
  private String [] _arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};
  /**
   * the vectors containing the instructors ID
   */
  private Vector _rightVec, _leftVec;

  private static String NOT_INCLUDED = DConst.NOT_INCLUDED;
  private static String INCLUDED = DConst.INCLUDED;



  /**
   * Dafault constructor
   * @param dApplic The application object (for extracting the JFrame)
   */

  public SelectInstructors(DApplication dApplic, EditActivityDlg ead, Vector[] twoColumnList) {
    super(dApplic.getJFrame(), DConst.INST_ASSIGN_M, true);
    _dApplic = dApplic;
    _ead = ead;
    _leftVec= twoColumnList[0];
    _rightVec= twoColumnList[1];
    for (int i=0; i< _leftVec.size(); i++)
      _rightVec.remove(_leftVec.get(i).toString());
    jbInit();
    setLocationRelativeTo(dApplic.getJFrame());
    setVisible(true);
  }


  /**
   * Initialize the dialog
   */
  protected void jbInit(){
    //right panel
    //_rightVec = new Vector(1);//_activities.getIDsByField(3, "false");
    _rightList = new JList(_rightVec);
    _rightList.addMouseListener(mouseListenerLists);
    //_visibleList = new JList(_visibleVec);
    JPanel listPanel = DXTools.listPanel(_rightList, 150, 300);
    _lNoVisible = new JLabel(_rightVec.size() + " " + NOT_INCLUDED);
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.add(_lNoVisible, BorderLayout.NORTH);
    rightPanel.add(listPanel, BorderLayout.SOUTH);
    //left panel
    //_leftVec = new Vector(1);//_activities.getIDsByField(3, "true");
    _leftList = new JList(_leftVec);
    _leftList.addMouseListener(mouseListenerLists);
    _lVisible = new JLabel(_leftVec.size() + " " + INCLUDED);
    listPanel = DXTools.listPanel(_leftList, 150, 300);
    JPanel leftPanel = new JPanel();
    leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(_lVisible, BorderLayout.NORTH);
    leftPanel.add(listPanel, BorderLayout.CENTER);
    //arrows panel
    _arrowsPanel = DXTools.arrowsPanel(this, _arrowsNames,true);
    //placing the panels and buttons into the _listsPanel
    _centerPanel = new JPanel();
    _centerPanel.add(leftPanel, BorderLayout.EAST);
    _centerPanel.add(_arrowsPanel, BorderLayout.CENTER);
    _centerPanel.add(rightPanel, BorderLayout.WEST);
    //_applyPanel
    String [] a ={DConst.BUT_APPLY, DConst.BUT_CLOSE};
    _buttonsPanel = new TwoButtonsPanel(this, a);
    //Setting the button APPLY disable
    _buttonsPanel.setFirstDisable();
    //placing the elements into the JDialog
    setSize(400, 390);
    setResizable(false);
    getContentPane().add(_centerPanel, BorderLayout.CENTER);
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
  }//end method

  /**
   * Defins the mouse adapter and actions for the JListis
   */
  private MouseListener mouseListenerLists = new MouseAdapter(){
    public void mouseClicked(MouseEvent e) {
      if (((JList)e.getSource()).getModel().getSize() == 0)
        return;
      if (e.getSource().equals(_leftList))
        _rightList.clearSelection();
      else
        _leftList.clearSelection();
      //_currentActivities = ((JList)e.getSource()).getSelectedValues();
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){


  /**tictactictic
   *
   * @param e an event
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //If buttons CANCEL
    if (command.equals(DConst.BUT_CLOSE))
        dispose();
    //If button APPLY
    if (command.equals(DConst.BUT_APPLY)){
      _ead.setInstructorList(_leftVec);
      //setActivitesVisibility();
      //_dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      //_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);

      //_dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().sendEvent();
      _buttonsPanel.setFirstDisable();

    }
    //if button OK
   /* if (command.equals(_buttonsNames[0])){
      //setActivitesVisibility();
      //_dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      //_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);
      dispose();
    } */
    if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1])){
      if (command.equals(_arrowsNames[1]))
        DXTools.listTransfers(_rightList, _leftList, _rightVec, _leftVec, 1);
      else
        DXTools.listTransfers(_leftList, _rightList, _leftVec, _rightVec, 1);
      _lNoVisible.setText(_rightVec.size() + " " + NOT_INCLUDED);
      _lVisible.setText(_leftVec.size() + " " + INCLUDED);
      _buttonsPanel.setFirstEnable();
      //_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);
    }//end if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1]))
  }//end method

  /**
   * Sets the field "Visible" of the activities, according with their position
   * in the JLists. If an activity is in the _rightList, Visible = false.
   */
  private void setActivitesVisibility(){
      //_activities.setByField(_leftVec, 3, "true");
      //_activities.setByField(_rightVec, 3, "false");
  }

}// end class
