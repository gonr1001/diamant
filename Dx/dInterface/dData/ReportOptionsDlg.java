package dInterface.dData;
/**
 *
 * Title: ReportOptionsDlg $Revision: 1.22 $  $Date: 2003-11-25 15:29:34 $
 * Description: ReportOptionsDlg is a class used to display
 *              a dialog to chose the fields to include in a report
 *              also the order of fields can be defined by the dialog
 *
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
 * @version $Revision: 1.22 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Vector;


import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dInternal.Preferences;


import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;

import dInternal.dUtil.DXValue;

import dResources.DConst;



public class ReportOptionsDlg  extends JDialog implements ActionListener {
  private JDialog _parentDlg;
  private JList _rightList, _leftList;
  private JPanel _centerPanel, _arrowsPanel, _buttonsPanel;
  private String [] _buttonsNames = {DConst.BUT_OK,
    DConst.BUT_CANCEL};
  private String [] _arrowsNames = {DConst.TO_RIGHT,
    DConst.TO_LEFT,
    DConst.TO_UP,
    DConst.TO_DOWN};
  private Vector _rightVec, _leftVec;
  /**
   * Constructor
   * @param dApplic to link with the Parent JFrame
   * @param parentDlg changes in the dialog are sent to the parentDialog
   * @param options containing all the report fields, the first half is showed
   *                in the left pane the second halh in the right pane
   * @param elements is the number of elements in the first half
   */
  public ReportOptionsDlg(DApplication dApplic,
                          JDialog parentDlg,
                          Vector options,
                          int elements){
    super(dApplic.getJFrame(), DConst.REPORT_OPTIONS_DLG_TITLE, true);
    _parentDlg = parentDlg;
    _leftVec = left(options, elements);
    _rightVec=  rigth(options, elements);
    _leftList = new JList(_leftVec);
    _rightList = new JList(_rightVec);

    reportOptionsDlgInit();
    setLocationRelativeTo(dApplic.getJFrame());
    setResizable(false);
    setVisible(true);
  }//end constructor

  /**
   * Initialize the dialog
   */
  private void reportOptionsDlgInit(){
    Dimension dlgDim = new Dimension(DConst.DIALOG_DIM, DConst.DIALOG_DIM);
    Dimension centerPanelDim = new Dimension((int)dlgDim.getWidth()-DConst.CENTER_WIDTH, (int)dlgDim.getHeight()-DConst.CENTER_HEIGHT);
    Dimension listPanelDim = new Dimension((int)centerPanelDim.getWidth()/2-DConst.LIST_LENGHT, (int)centerPanelDim.getHeight());

    String[] leftLabelsInfo = {DConst.REPORT_OP_FIELDS_NOT_CHOICED};
    ;
    String[] rightLabelsInfo = {DConst.REPORT_OP_FIELDS__CHOICED};
    //the centerPanel
    JPanel centerPanel = new JPanel();
    centerPanel.setPreferredSize(centerPanelDim);
    centerPanel.add(setListPanel(listPanelDim, _leftList,  DConst.REPORT_OP_FIELDS_NOT_CHOICED));

    centerPanel.add(DXTools.arrowsPanel(this, _arrowsNames,true));
    centerPanel.add(setListPanel(listPanelDim, _rightList,  DConst.REPORT_OP_FIELDS__CHOICED));

    //buttonsPanel
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    //placing the elements into the JDialog
    setSize(dlgDim);
    setResizable(false);
    getContentPane().add(centerPanel, BorderLayout.CENTER);
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
  }//end method

  private Vector rigth(Vector v , int e) {
    Vector res = new Vector();
    for(int i = e; i < v.size(); i++) {
      res.add(v.get(i));
    }
    return res;
  }

  private Vector left(Vector v , int e) {
    Vector res = new Vector();
    for(int i = 0; i < e; i++) {
      res.add(v.get(i));
    }
    return res;
  }

  private static JPanel setListPanel(Dimension panelDim, JList theList, String  labelsInfo ){
    Dimension infoPanelDim = new Dimension((int)panelDim.getWidth(), 20);
    Dimension listPanelDim = new Dimension((int)panelDim.getWidth(), (int)(panelDim.getHeight() - infoPanelDim.getHeight()));
    //list.setListData(vec);
    //list.addMouseListener(ml);
    JPanel panelList = new JPanel(new BorderLayout());
    panelList.setPreferredSize(listPanelDim);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setPreferredSize(listPanelDim);
    scrollPane.getViewport().add(theList);
    panelList.add(scrollPane);


    //JPanel listPanel = listPanel(list, (int)listPanelDim.getWidth(), (int)listPanelDim.getHeight());
    //the panel
    JPanel panel = new JPanel();
    panel.setPreferredSize(panelDim);
    panel.add(new JLabel(labelsInfo));
    panel.add(panelList);
    //panel.setBorder(BorderFactory.createLineBorder(Color.black));
    return panel;
  }

  /**
   * Search the indices to be showed as selected in a JList. The search is made in the vector that
   * contains the list items
   * @param Vector (itemsList) the items list where we are searching indices
   * @param Object [] (selectedItemsList) the selected items array to be found in the itemsList
   * @return An array containing the indices of the items to be showed as selected
   * */
/* private static int[] getIndicesToSelect(Vector itemsList, Object[] selectedItemsList){
 int [] indices = new int[selectedItemsList.length];//the place for keeping the indices to set selected
 for (int i = 0; i < selectedItemsList.length; i++){
   indices[i] = itemsList.indexOf(selectedItemsList[i]);
 }
  return indices;
  }*/

  private void listTransfers(Object [] elementsToTransfer,
                             Vector source,
                             JList s,
                             Vector destination,
                             JList d,
                             boolean flag){
    if (elementsToTransfer.length != 0){
      String currentElement;
      for (int i = 0; i < elementsToTransfer.length; i++){
        source.remove(elementsToTransfer[i]);
        destination.add(elementsToTransfer[i]);
      }

      if(flag)
        destination= DXTools.sortVector(destination);
      else
        source=DXTools.sortVector(source);
      int[] indices = DXTools.getIndicesToSelect(destination, elementsToTransfer);
      d.setListData(destination);
      d.setSelectedIndices(indices);
      s.setListData(source);
      s.clearSelection();
    }//end for
  }

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //If buttons CANCEL
    if (command.equals(_buttonsNames[1]))
      dispose();
    //if button OK
    if (command.equals(_buttonsNames[0])){
          /*_parentDlg.setReport(buildChoicedResources(_resources, _rightVec));

          if ( _parentDlg.getTabbedPane().getSelectedIndex() == 0)
            _dApplic.getPreferences().setSelectedOptionsInFullReport(_rightVec);
          else
            _dApplic.getPreferences().setSelectedOptionsInConflictReport(_rightVec);
          _dApplic.getPreferences().save();*/
      dispose();
    }
    if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1])){
      //toLeft button
      if (command.equals(_arrowsNames[1]))
        listTransfers(_rightList.getSelectedValues(),_rightVec,_rightList,_leftVec, _leftList,true);

      else
        //toRight button
        listTransfers(_leftList.getSelectedValues(),_leftVec, _leftList,_rightVec,_rightList,false);

    }//end if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1]))
    if (command.equals(_arrowsNames[2]) || command.equals(_arrowsNames[3])){
      int i = -1;
      if (_rightList.getSelectedIndices().length == 1) {
        String selectedValue = (String)_rightList.getSelectedValue();
        i = _rightList.getSelectedIndex();
        //SetOfResources s = buildChoicedResources(_resources, _rightVec);
        //Vector v;*/
        //toUp button
        if (command.equals(_arrowsNames[2]))
          swap(_rightVec,i,i-1);//v = transposeInSet(true, s, selectedValue).getNamesVector(0);
        //toDown button
        else
          swap(_rightVec,i,i+1);//v = transposeInSet(false, s, selectedValue).getNamesVector(0);
        //_rightVec = new Vector(v);
        //_rightList.setListData(_rightVec);
        //rgr _dApplic.getPreferences().setSelectedOptions(_rightVec);
        //rgr _dApplic.getPreferences().save();
        _rightList.setListData(_rightVec);
        _rightList.setSelectedValue(selectedValue, true);
      }
    }//end if (command.equals(_arrowsNames[2]) || command.equals(_arrowsNames[3]))


  }
  private void swap(Vector v, int s, int d) {
    Object aux = v.get(s);
    if (d >=0 && d < v.size()) {
      v.setElementAt(v.get(d),s);
      v.setElementAt(aux,d);
    }
  }
}