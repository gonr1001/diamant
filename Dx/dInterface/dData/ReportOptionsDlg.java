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

import dInternal.dUtil.DXValue;

import dResources.DConst;

public class ReportOptionsDlg extends JDialog implements ActionListener {

  private DApplication _dApplic;
  private ReportDlg _parentDlg;
  private JList _rightList, _leftList;
  private JPanel _centerPanel, _arrowsPanel, _buttonsPanel;
  private SetOfResources _resources, _selectedResources, _externalResources;
  private String [] _buttonsNames = {DConst.BUT_OK, DConst.BUT_CANCEL};
  private String [] _arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT, DConst.TO_UP, DConst.TO_DOWN};
  private Vector _rightVec, _leftVec;

  /**
   * Constructor
   * @param dApplic
   * @param parentDlg
   * @param res The setOfResources containing the fields report already selected
   * @param reportType The index with report type
   */
  public ReportOptionsDlg(DApplication dApplic, JDialog parentDlg, SetOfResources res, int reportType){
    super(dApplic.getJFrame(), DConst.REPORT_OPTIONS_DLG_TITLE, true);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    _externalResources = res;
    _parentDlg = (ReportDlg)parentDlg;
    setSetOfResources(reportType);
    _leftVec = getChoicedFields(_resources, false);
    _rightVec = getChoicedFields(_externalResources, true);
    jbInit();
    setLocationRelativeTo(dApplic.getJFrame());
    setResizable(false);
    setVisible(true);
  }//end constructor



  /**
   * Initialize the dialog
   */
  private void jbInit(){
    Dimension dlgDim = new Dimension(400, 400);
    Dimension centerPanelDim = new Dimension((int)dlgDim.getWidth()-20, (int)dlgDim.getHeight()-75);
    Dimension listPanelDim = new Dimension((int)centerPanelDim.getWidth()/2-30, (int)centerPanelDim.getHeight());
    _leftList = new JList();
    String[] leftLabelsInfo = {DConst.REPORT_OP_FIELDS_NOT_CHOICED};
    _rightList = new JList();
    String[] rightLabelsInfo = {DConst.REPORT_OP_FIELDS__CHOICED};
    //the centerPanel
    JPanel centerPanel = new JPanel();
    centerPanel.setPreferredSize(centerPanelDim);
    centerPanel.add(DXTools.setListPanel(listPanelDim, _leftList, _leftVec, leftLabelsInfo, null));
    centerPanel.add(DXTools.arrowsPanel(this, _arrowsNames,true));
    centerPanel.add(DXTools.setListPanel(listPanelDim, _rightList, _rightVec, rightLabelsInfo, null));
    //buttonsPanel
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    //placing the elements into the JDialog
    setSize(dlgDim);
    setResizable(false);
    getContentPane().add(centerPanel, BorderLayout.CENTER);
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
  }//end method



  /**
   * Builds a setOfResources to manage the report fields
   * @param reportType
   */
  private void setSetOfResources(int reportType){
    String [][] reportElements = null;
    _resources = new SetOfResources(100);
    Resource res;
    DXValue dxv;
    switch(reportType){
      case 0://_activitiesReport
        String [][] activitiesElements =
        {
          {DConst.R_ACTIVITY_NAME, DConst.R_ACTIVITY_NAME_L},
          {DConst.R_TYPE_NAME, DConst.R_TYPE_NAME_L},
          {DConst.R_SECTION_NAME, DConst.R_SECTION_NAME_L},
          {DConst.R_UNITY_NAME, DConst.R_UNITY_NAME_L},
          {DConst.R_DURATION, DConst.R_DURATION_L},
          {DConst.R_DAY_NUMBER, DConst.R_DAY_NUMBER_L},
          {DConst.R_DAY_NAME, DConst.R_DAY_NAME_L},
          {DConst.R_ACTIVITY_BEGIN_HOUR, DConst.R_ACTIVITY_BEGIN_HOUR_L},
          {DConst.R_ACTIVITY_END_HOUR, DConst.R_ACTIVITY_END_HOUR_L},
          {DConst.R_INSTRUCTOR_NAME, DConst.R_INSTRUCTOR_NAME_L},
          {DConst.R_ROOM_NAME, DConst.R_ROOM_NAME_L}
        };
        reportElements = activitiesElements;
        break;
      case 1://_conflictsReport
        String [][] conflictsElements =
        {
          {DConst.R_DAY_NUMBER, DConst.R_DAY_NUMBER_L},
          {DConst.R_DAY_NAME, DConst.R_DAY_NAME_L},
          {DConst.R_SEQUENCE_ID, DConst.R_SEQUENCE_ID_L},
          {DConst.R_UNITY_NAME, DConst.R_UNITY_NAME_L},
          {DConst.R_EVENT1_ID, DConst.R_EVENT1_ID_L},
          {DConst.R_EVENT2_ID, DConst.R_EVENT2_ID_L},
          {DConst.R_CONFLICT_INT, DConst.R_CONFLICT_INT_L},
          {DConst.R_CONFLICT_STRING, DConst.R_CONFLICT_STRING_L},
        };
        reportElements = conflictsElements;
        break;
    }//end switch
    for(int i = 0; i < reportElements.length; i++){
      dxv = new DXValue();
      //the field name
      dxv.setStringValue(reportElements[i][1]);
      //the field report index
      dxv.setIntValue(i);
      //if field is selected
      dxv.setBooleanValue(false);
      res = new Resource(reportElements[i][0], dxv);
      _resources.addResource(res, 1);
    }//end for(int i = 0; i < reportElements.length; i++)
    Resource externalR, internalR;
    if (_externalResources == null)
      return;
    for(int j = 0; j < _externalResources.size(); j++){
      externalR = _externalResources.getResourceAt(j);
      internalR = _resources.getResource(externalR.getID());
      ((DXValue)internalR.getAttach()).setBooleanValue(true);
    }//end for(int j = 0; j < _externalResources.size(); j++)
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
      _parentDlg.setReport(buildSelectedResources(_resources, _rightVec));
      dispose();
    }
    if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1])){
      //toLeft button
      if (command.equals(_arrowsNames[1]))
        DXTools.listTransfers(_rightList, _leftList, _rightVec, _leftVec, 1);
      else
        //toRight button
        DXTools.listTransfers(_leftList, _rightList, _leftVec, _rightVec, 0);
      //((JPanel)((JPanel)getContentPane().getComponent(0)).getComponent(0)).getComponent(0);
    }//end if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1]))
    if (command.equals(_arrowsNames[2]) || command.equals(_arrowsNames[3])){
      int index = -1;
      Object selectedValue = _rightList.getSelectedValue();
      SetOfResources s = buildSelectedResources(_resources, _rightVec);
      Vector v;
      //toUp button
      if (command.equals(_arrowsNames[2]))
        v = transposeInSet(true, s, selectedValue).getNamesVector(0);
      //toDown button
      else
        v = transposeInSet(false, s, selectedValue).getNamesVector(0);
      _rightVec = new Vector(v);
      _rightList.setListData(_rightVec);
      _rightList.setSelectedValue(selectedValue, true);
      }//end if (command.equals(_arrowsNames[2]) || command.equals(_arrowsNames[3]))
  }//end method


  /**
   * Change the order of two adjacent elements belonging a vector
   * @param toUp true if the element "item" is raised
   * @param vec, the vector containing the elements
   * @param item the element of the vector to be changed
   * @return The index of the adjacent element
   */

  /**
   * Changes the position of the two adjacent resources in a set
   * @param toUp true if the place of the selected resource will be changed
   * with its upper resource
   * @param res the setOfResources containing the resources to be changed
   * @param item the selected resource ID
   * @return
   */
  private SetOfResources transposeInSet(boolean toUp, SetOfResources res, Object item){
    if (item == null)
      return res;
    Resource aux;
    DXValue dxv;
    int resourceIndex;
    int adjacentIndex;
    aux = new Resource(res.getResource((String)item).getID(), res.getResource((String)item).getAttach());
    resourceIndex = (int) res.getResource((String)item).getKey();
    if (resourceIndex == -1)
      return res;
    if(toUp){
      if (resourceIndex <= 1)
        return res;
      adjacentIndex = resourceIndex - 1;
    }else{//else if(toUp)
      if(resourceIndex >= res.size())
        return res;
      adjacentIndex = resourceIndex + 1;
    }//end else...if(toUp)
    //setting the actual resource
    Resource adj = res.getResource(adjacentIndex);
    dxv = (DXValue)res.getResource(adjacentIndex).getAttach();
    res.getResource((String)item).setAttach(dxv);
    res.getResource((String)item).setID(adj.getID());
    //setting the adjacent ressource
    dxv = (DXValue)aux.getAttach();
    res.getResource(adjacentIndex).setAttach(dxv);
    res.getResource(adjacentIndex).setID(aux.getID());
    return res;
  }//end method

  /**
   * Build a setOfResources from a bigger setOfResources and a vector
   * @param vec the vector containing the Resources ID to place in the new
   * setOfResources
   * @return the setOfResources built
   */
  private SetOfResources buildSelectedResources(SetOfResources fullResources, Vector vec){
    Resource r = null;
    fullResources.sortSetOfResourcesByID();
    SetOfResources selectedResources = new SetOfResources(100);
    for (int i = 0; i < vec.size(); i++){
      r = fullResources.getResource((String)vec.elementAt(i));
      selectedResources.addResource(r, 0);
    }//end for
    return selectedResources;
  }//end method

  /**
   *
   * @param res
   * @param isSelected
   * @return
   */

  /**
   * Builds a vector with the resource fields IDs weather selected or not, depending
   * on the parameter isChoiced
   * @param res
   * @param isSelected
   * @return
   */
  private Vector getChoicedFields(SetOfResources res, boolean isChoiced){
    if (res == null)
      return new Vector();
    Vector vec = new Vector();
    DXValue dxv;
    for (int i = 0; i < res.size(); i++){
      dxv = (DXValue)res.getResourceAt(i).getAttach();
      if (dxv.getBooleanValue() == isChoiced)
        vec.add(res.getResourceAt(i).getID());
    }//end for
    if (vec == null)
      return new Vector();
    return vec;
  }//end method


}//end class