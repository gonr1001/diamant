package dInterface.dData;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;

import java.util.Vector;

import javax.swing.BorderFactory;
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

public class ReportOptionsDlg extends JDialog implements ActionListener {

  private DApplication _dApplic;
  private JButton _toRight, _toLeft;
  private JDialog _jd;
  /**
   * the lists containing the activities ID
   */
  private JList _rightList, _leftList;
  private JPanel _centerPanel, _arrowsPanel, _buttonsPanel;
  private Object [] _currentActivities = new Object[0];
  private SetOfActivities _activities;
  private String [] _buttonsNames = {DConst.BUT_OK, DConst.BUT_APPLY, DConst.BUT_CANCEL};
  private String [] _arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT, "+", "-"};
  /**
   * the vectors containing the activities ID
   */
  private Vector _rightVec, _leftVec;

  public ReportOptionsDlg(DApplication dApplic, JDialog parentDlg, int reportType){
    super(dApplic.getJFrame(), "OPTIONS DLG", true);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    _leftVec = new Vector();
    switch(reportType){
      case 0://_activitiesReport
         _leftVec.add("One");
         _leftVec.add("Two");
         _leftVec.add("Three");
         _leftVec.add("Four");
         _leftVec.add("Five");
        break;
      case 1://_studentsReport
        _leftVec.add("Un");
         _leftVec.add("Deux");
         _leftVec.add("Trois");
         _leftVec.add("Quatre");
         _leftVec.add("Cinq");
        break;
      case 2://_conflictsReport
        _leftVec.add("Uno");
         _leftVec.add("Dos");
         _leftVec.add("Tres");
         _leftVec.add("Cuatro");
         _leftVec.add("Cinco");
        break;
   }
    jbInit();
    setLocationRelativeTo(dApplic.getJFrame());
    this.setResizable(true);
    setVisible(true);

  }




  /**
   * Initialize the dialog
   */
  private void jbInit(){
    Dimension dlgDim = new Dimension(400, 400);
    Dimension centerPanelDim = new Dimension((int)dlgDim.getWidth()-20, (int)dlgDim.getHeight()-75);
    Dimension listPanelDim = new Dimension((int)centerPanelDim.getWidth()/2-30, (int)centerPanelDim.getHeight());

    //_leftVec = new Vector();
    _leftList = new JList();
    String[] leftLabelsInfo = {"Title 1", String.valueOf(_leftVec.size())};
    _rightVec = new Vector();
    _rightList = new JList();
    String[] rightLabelsInfo = {"Title 2 ", String.valueOf(_rightVec.size())};
    //the centerPanel
    JPanel centerPanel = new JPanel();
    centerPanel.setPreferredSize(centerPanelDim);
    centerPanel.add(DXTools.setListPanel(listPanelDim, _leftList, _leftVec, leftLabelsInfo, mouseListenerLists));
    centerPanel.add(DXTools.arrowsPanel(this, _arrowsNames));
    centerPanel.add(DXTools.setListPanel(listPanelDim, _rightList, _rightVec, rightLabelsInfo, mouseListenerLists));
    //centerPanel.setBorder(BorderFactory.createLineBorder(DConst.COLOR_QUANTITY_DLGS));
    //buttonsPanel
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    //Setting the button APPLY disable
    _buttonsPanel.getComponent(1).setEnabled(false);

    //placing the elements into the JDialog
    setSize(dlgDim);
    setResizable(false);
    getContentPane().add(centerPanel, BorderLayout.CENTER);
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
        //new EditActivityDlg(_jd,_dApplic, (String)_currentActivities[0]);
      }//end if
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){


  /**
   *
   * @param e an event
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //If buttons CANCEL
    if (command.equals(_buttonsNames[2]))
        dispose();
    //If button APPLY
    if (command.equals(_buttonsNames[1])){
      _dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      _buttonsPanel.getComponent(1).setEnabled(false);
    }
    //if button OK
    if (command.equals(_buttonsNames[0])){
      _dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      dispose();
    }
    if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1])){
      if (command.equals(_arrowsNames[1]))
        DXTools.listTransfers(_rightList, _leftList, _rightVec, _leftVec);
      else
        DXTools.listTransfers(_leftList, _rightList, _leftVec, _rightVec);
      //_lNoVisible.setText(_rightVec.size() + " " + NOT_INCLUDED);
      //_lVisible.setText(_leftVec.size() + " " + INCLUDED);
      _buttonsPanel.getComponent(1).setEnabled(true);
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);
    }//end if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1]))
  }//end method


}//end class