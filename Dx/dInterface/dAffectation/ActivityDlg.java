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


import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;


import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dInternal.dData.SetOfActivities;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;


import dResources.DConst;

public class ActivityDlg extends JDialog implements ActionListener {

  private DApplication _dApplic;
  private JButton _toRight, _toLeft;
  private JDialog _jd;
  /**
   * the lists containing the activities ID
   */
  private JLabel _lVisible, _lNoVisible;
  private JList _rightList, _leftList;
  private JPanel _centerPanel, _arrowsPanel;
  private ButtonsPanel _buttonsPanel;
  private Object [] _currentActivities = new Object[0];
  private SetOfActivities _activities;
  private String [] _arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};
  /**
   * the vectors containing the activities ID
   */
  private Vector _rightVec, _leftVec;

  /**
   * Dafault constructor
   * @param dApplic The application object (for extracting the JFrame)
   */

  public ActivityDlg() {
  }

  public ActivityDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), DConst.ACT_LIST, true);
    _dApplic = dApplic;
    _jd = this;  //to pass this dialog to the EditActivityDlg
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    jbInit();
    setLocationRelativeTo(dApplic.getJFrame());
    setVisible(true);
  }


  /**
   * Initialize the dialog
   */
  protected void jbInit(){
    //right panel
    _rightVec = _activities.getIDsByField(3, "false");
    _rightList = new JList(_rightVec);
    _rightList.addMouseListener(mouseListenerLists);
    //_visibleList = new JList(_visibleVec);
    JPanel listPanel = DXTools.listPanel(_rightList, 150, 300);
    _lNoVisible = new JLabel(_rightVec.size() + " " + DConst.NOT_INCLUDED);
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.add(_lNoVisible, BorderLayout.NORTH);
    rightPanel.add(listPanel, BorderLayout.SOUTH);
    //left panel
    _leftVec = _activities.getIDsByField(3, "true");
    _leftList = new JList(_leftVec);
    _leftList.addMouseListener(mouseListenerLists);
    _lVisible = new JLabel(_leftVec.size() + " " + DConst.INCLUDED);
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
    setSize(400, 400);
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
      _currentActivities = ((JList)e.getSource()).getSelectedValues();
      if (e.getClickCount() == 2) {
        new EditActivityDlg(_jd,_dApplic, (String)_currentActivities[0], false);
      }//end if
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){


  /**
   *
   * @param e an event
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //If buttons CLOSE
    if (command.equals(DConst.BUT_CLOSE))
        dispose();
    //If button APPLY
    if (command.equals(DConst.BUT_APPLY)){
      setActivitesVisibility();
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);
      _buttonsPanel.setFirstDisable();
    }
    //no  button OK

    if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1])){
      if (command.equals(_arrowsNames[1])) {
        DXTools.listTransfers(_rightList, _leftList, _rightVec, _leftVec, 1);
      } else {
        DXTools.listTransfers(_leftList, _rightList, _leftVec, _rightVec, 1);
      }
      _lNoVisible.setText(_rightVec.size() + " " + DConst.NOT_INCLUDED);
      _lVisible.setText(_leftVec.size() + " " + DConst.INCLUDED);
      _buttonsPanel.setFirstEnable();
    }//end if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1]))
  }//end method

  /**
   * Sets the field "Visible" of the activities, according with their position
   * in the JLists. If an activity is in the _rightList, Visible = false.
   */
  private void setActivitesVisibility(){
      _activities.setByField(_leftVec, 3, "true");
      _activities.setByField(_rightVec, 3, "false");
  }

}// end class