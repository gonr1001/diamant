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
import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;

//import dInternal.dData.SetOfActivities;
//import dInternal.dData.Activity;

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
  private ReportDlg _rdlg;
  private SetOfResources _resources;
  private String [] _buttonsNames = {DConst.BUT_OK, DConst.BUT_CANCEL};
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
    _rdlg = (ReportDlg)parentDlg;
    setListVectors(reportType);
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
    _leftList = new JList();
    String[] leftLabelsInfo = {"Title 1", String.valueOf(_leftVec.size())};
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
    //_buttonsPanel.getComponent(1).setEnabled(false);

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

/*
  0= activity name, 1= type name, 2= section name, 3= unity name, 4= duration of the activity
  * 5= day number where activity is assign, 6= day name where activity is assign
  * 7= begin hour of the activity, 8= end hour of the activity, 9= instructor name
  * 10= room name
  */

  private void setListVectors(int reportType){
    _leftVec = new Vector();
    _rightVec = new Vector();
    String [] reportElements = null;
    _resources = new SetOfResources(0);
    Resource res;
    switch(reportType){
      case 0://_activitiesReport
        String [] elements =
        {
          DConst.R_ACTIVITY_NAME,
          DConst.R_ACTIVITY_NAME,
          DConst.R_TYPE_NAME,
          DConst.R_SECTION_NAME,
          DConst.R_UNITY_NAME,
          DConst.R_DURATION,
          DConst.R_DAY_NUMBER,
          DConst.R_DAY_NAME,
          DConst.R_ACTIVITY_BEGIN_HOUR,
          DConst.R_ACTI_END_HOUR,
          DConst.R_INSTRUCTOR_NAME,
          DConst.R_ROOM_NAME,
        };
        reportElements = elements;
        /*
        _leftVec.add(DConst.R_ACTIVITY_NAME + " " + 0);
        _leftVec.add(DConst.R_TYPE_NAME + " " + 0);
        _leftVec.add(DConst.R_SECTION_NAME + " " + 0);
        _leftVec.add(DConst.R_UNITY_NAME + " " + 0);
        _leftVec.add(DConst.R_DURATION + " " + 0);
        _leftVec.add(DConst.R_DAY_NUMBER + " " + 0);
        _leftVec.add(DConst.R_DAY_NAME + " " + 0);
        _leftVec.add(DConst.R_ACTIVITY_BEGIN_HOUR + " " + 0);
        _leftVec.add(DConst.R_ACTI_END_HOUR + " " + 0);
        _leftVec.add(DConst.R_INSTRUCTOR_NAME + " " + 0);
        _leftVec.add(DConst.R_ROOM_NAME + " " + 0);
        */
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
    }//end switch
    for(int i = 0; i < reportElements.length; i++){
      res = new Resource(reportElements[i], null);
      _resources.addResource(res, 1);
    }
    _leftVec = _resources.getNamesVector(1);
  }//end method


  /**
   *
   * @param e an event
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //If buttons CANCEL
    if (command.equals(_buttonsNames[1]))
        dispose();
    //if button OK
    if (command.equals(_buttonsNames[0])){
      int[] keys = new int[_rightVec.size()];
      for (int i = 0; i < _rightVec.size(); i++)
        keys[i] = (int) _resources.getResource((String)_rightVec.elementAt(i)).getKey();
      _rdlg.setFieldReportList(keys);
      _dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      dispose();
    }
    if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1])){
      //toLeft button
      if (command.equals(_arrowsNames[1]))
        DXTools.listTransfers(_rightList, _leftList, _rightVec, _leftVec, 0);
      else
        //toRight button
        DXTools.listTransfers(_leftList, _rightList, _leftVec, _rightVec, 0);
      //_buttonsPanel.getComponent(1).setEnabled(true);
      //_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);
    }//end if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1]))

    if (command.equals(_arrowsNames[2]) || command.equals(_arrowsNames[3])){
      int index = -1;
      //toUp button
      if (command.equals(_arrowsNames[2]))
        index = transposeInVector(true, _rightVec, _rightList.getSelectedValue());
      //toDown button
      else
        index = transposeInVector(false, _rightVec, _rightList.getSelectedValue());
      _rightList.setListData(_rightVec);
      _rightList.setSelectedIndex(index);
      }
  }//end method

  /**
   * Change the order of two adjacent elements belonging a vector
   * @param toUp true if the element "item" is raised
   * @param vec, the vector containing the elements
   * @param item the element of the vector to be changed
   */
  private int transposeInVector(boolean toUp, Vector vec, Object item){
    if (item == null)
      return -1;
    Object aux;
    int thisIndex, sideIndex;
    thisIndex = vec.indexOf(item);
    if (thisIndex == -1)
      return thisIndex;
    if(toUp){
      if (thisIndex <=  0)
        return thisIndex;
      sideIndex = thisIndex - 1;
    }else{
      if (thisIndex >= vec.size()-1)
        return thisIndex;
      sideIndex = thisIndex + 1;
    }
    aux = vec.elementAt(sideIndex);
    vec.setElementAt(item, sideIndex);
    vec.setElementAt(aux, thisIndex);
    return sideIndex;
  }


}//end class