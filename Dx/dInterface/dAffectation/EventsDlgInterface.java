/**
 *
 * Title: EventsDlgInterface $Revision: 1.12 $  $Date: 2004-06-09 19:29:17 $
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
 * @version $Revision: 1.12 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: EventsDlgInterface is a class used to
 *
 */
package dInterface.dAffectation;



import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.StringTokenizer;
import dInterface.dUtil.ButtonsPanel;

import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import dInternal.dConditionsTest.EventAttach;
import dInternal.dConditionsTest.SetOfEvents;
import dInternal.dData.SetOfActivities;
import dInternal.dData.Unity;
import dInternal.dUtil.DXToolsMethods;

import dInterface.DApplication;
import dInterface.dUtil.DXTools;

import dConstants.DConst;

public abstract class EventsDlgInterface extends JDialog implements ActionListener{

  protected DApplication _dApplic;
  protected Dimension _dialogDim = new Dimension(600, 400);
  protected EventAttach _currEvent;
  protected int buttonsPanelHeight = 80;
  protected JLabel _leftLabel, _centerLabel, _rightLabel;
  protected JList _leftList, _centerList, _rightList;
  protected JPanel _leftPanel, _centerPanel, _rightPanel, _rightArrowsPanel, _leftArrowsPanel;
  protected ButtonsPanel _buttonsPanel;
  protected Object[] selectedItems;
  protected SetOfActivities _activities;
  protected SetOfEvents _events;
  protected String _eventFullKey;
  protected Unity _currUnity;
  protected Vector _leftVector, _centerVector, _rightVector;
  protected JDialog _jDialog;




  /**
   * Constructor
   * @param dApplic The application
   * @param title the title of the dialog
   */
  public EventsDlgInterface(DApplication dApplic, String title) {
    super(dApplic.getJFrame(), title, true);
    _dApplic = dApplic;
    _jDialog= this;
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    _events = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents();
  }//end method

  public abstract void actionPerformed(ActionEvent e);
  public abstract void buildArrowButtons(boolean enableArrows);
  public abstract ButtonsPanel setButtons();

  /**
   * Initialise the dialog
   */
  protected void initialize(){
    getContentPane().setLayout(new BorderLayout());
    setSize(_dialogDim);
    setResizable(false);
    buildVectors();
    setLeftPanel();
    setCenterPanel();
    setRightPanel();
    _buttonsPanel = setButtons();
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
    setLocationRelativeTo(_dApplic.getJFrame());
    setVisible(true);
  }

  /**
   * initialize label in each panel
   */
  public void initializePanel(){
    buildVectors();
    _leftLabel.setText(String.valueOf(_leftVector.size()));
    _leftList.setListData(_leftVector);
    _centerLabel.setText(String.valueOf(_centerVector.size()));
    _centerList.setListData(_centerVector);
    _rightLabel.setText(String.valueOf(_rightVector.size()));
    _rightList.setListData(_rightVector);
  }
  /**
   * build buttom to use in the dialog
   */


  /**
   * Sets the _centerPanel, the panel containing the _centerList and the
   * arrows panels
   */
  private void setCenterPanel(){
    Dimension panelDim = new Dimension((int)(_dialogDim.getWidth()*0.5), (int)_dialogDim.getHeight()-buttonsPanelHeight);
      _centerList = new JList(_centerVector);
      _centerList.addMouseListener(mouseListenerLists);
      JLabel titleLabel = new JLabel(DConst.EVENTS_PLACED + " ");
      _centerLabel = new JLabel(String.valueOf(_centerVector.size()));
      _centerLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);
      //The listContainerPanel
      JPanel listPanel = DXTools.listPanel(_centerList, (int)panelDim.getWidth()-140, (int)panelDim.getHeight()-25);
      JPanel listContainerPanel = new JPanel();
      listContainerPanel.setPreferredSize(new Dimension((int)panelDim.getWidth()-140, (int)panelDim.getHeight()));
      listContainerPanel.add(titleLabel);
      listContainerPanel.add(_centerLabel);
      listContainerPanel.add(listPanel);
      //the arrows panels
      /*String [] arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};
      _leftArrowsPanel = DXTools.arrowsPanel(this, arrowsNames);
      _rightArrowsPanel = DXTools.arrowsPanel(this, arrowsNames);*/
      //the _centerPanel
      _centerPanel = new JPanel();
      _centerPanel.setPreferredSize(panelDim);
      _centerPanel.add(_leftArrowsPanel);
      _centerPanel.add(listContainerPanel);
      _centerPanel.add(_rightArrowsPanel);
    getContentPane().add(_centerPanel, BorderLayout.CENTER);
  }//end method

  /**
   * Sets the _leftPanel
   */
  private void setLeftPanel(){
    Dimension panelDim = new Dimension((int)(_dialogDim.getWidth()*0.24), (int)_dialogDim.getHeight()-buttonsPanelHeight);
    _leftList = new JList(_leftVector);
    _leftList.addMouseListener(mouseListenerLists);
    JLabel titleLabel = new JLabel(DConst.EVENTS_FIXED + " ");
    _leftLabel = new JLabel(String.valueOf(_leftVector.size()));
    _leftLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);
    JPanel listPanel = DXTools.listPanel(_leftList, (int)panelDim.getWidth(), (int)panelDim.getHeight()-25);
    //the _leftPanel
    _leftPanel = new JPanel();
    _leftPanel.setPreferredSize(panelDim);
    _leftPanel.add(titleLabel);
    _leftPanel.add(_leftLabel);
    _leftPanel.add(listPanel);
    //this panel is just for harmonise the size of all panels in the dialog
    JPanel panelContainer = new JPanel();
    panelContainer.add(_leftPanel);
    getContentPane().add(panelContainer, BorderLayout.WEST);
  }//end method


  /**
   * Sets the _rightPanel
   */
  private void setRightPanel(){
    Dimension panelDim = new Dimension((int)(_dialogDim.getWidth()*0.24), (int)_dialogDim.getHeight()-buttonsPanelHeight);
    _rightList = new JList(_rightVector);
    _rightList.addMouseListener(mouseListenerLists);
    JLabel titleLabel = new JLabel(DConst.EVENTS_NOT_PLACED + " ");
    _rightLabel = new JLabel(String.valueOf(_rightVector.size()));
    _rightLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);
    JPanel listPanel = DXTools.listPanel(_rightList, (int)panelDim.getWidth(), (int)panelDim.getHeight()-25);
    //the _rightPanel
    _rightPanel = new JPanel();
    _rightPanel.setPreferredSize(panelDim);
    //_rightPanel.setPreferredSize(new Dimension((int)panelDim.getWidth(), (int)panelDim.getHeight()-10));
    _rightPanel.add(titleLabel);
    _rightPanel.add(_rightLabel);
    _rightPanel.add(listPanel);
    //this panel is just for harmonise the size of all panels in the dialog
    JPanel panelContainer = new JPanel();
    panelContainer.add(_rightPanel);
    getContentPane().add(panelContainer, BorderLayout.EAST);
  }//end method




  /**
   * Builds the vectors _rightVector, _centerVector, _leftVector for their
   * first display
   */

  private void buildVectors(){
    _leftVector = new Vector();
    _centerVector = new Vector();
    _rightVector = new Vector();
    String _eventFullID;
    StringTokenizer stk;
    for(int i = 0; i < _events.size(); i++){
      _eventFullKey = ((EventAttach)_events.getResourceAt(i).getAttach()).
                getPrincipalRescKey();
      stk = new StringTokenizer(_eventFullKey, ".");
      _currUnity = _activities.getUnity(Long.parseLong(stk.nextToken()),
                                        Long.parseLong(stk.nextToken()),
                                        Long.parseLong(stk.nextToken()),
                                        Long.parseLong(stk.nextToken()));
      stk = new StringTokenizer(_eventFullKey, ".");
      _eventFullID = _activities.getUnityCompleteName(Long.parseLong(stk.nextToken()),
          Long.parseLong(stk.nextToken()),
          Long.parseLong(stk.nextToken()),
          Long.parseLong(stk.nextToken()));
      if (_currUnity.compareByField(2, "false")){
        _rightVector.add(_eventFullID);
      }else{
        if (_currUnity.compareByField(3, "true")){
          _leftVector.add(_eventFullID);
        }else{
          _centerVector.add(_eventFullID);
        }
      }//end else if (_currUnity.compareByField(2, "false"))
    }//end for
  }//end method

  /**
   * The MouseListener for the JLists
   */
  private MouseListener mouseListenerLists = new MouseAdapter(){
    public void mouseClicked(MouseEvent e) {
      if (((JList)e.getSource()).getModel().getSize() == 0)
        return;
      if (e.getSource().equals(_leftList)){
          _centerList.clearSelection();
          _rightList.clearSelection();
          selectedItems = _leftList.getSelectedValues();
      }//end if (e.getSource().equals(_leftList))
      if (e.getSource().equals(_centerList)){
          _leftList.clearSelection();
          _rightList.clearSelection();
          selectedItems = _centerList.getSelectedValues();
      }//end if (e.getSource().equals(_centerList))
      if (e.getSource().equals(_rightList)){
          _centerList.clearSelection();
          _leftList.clearSelection();
          selectedItems = _rightList.getSelectedValues();
      }//end if (e.getSource().equals(_rightList))
      if (e.getClickCount() == 2) {
        doubleClicMouseProcess();
      }//end if
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){

  /**
  *
  */
  protected void doubleClicMouseProcess(){
  }



  /**
  * Set the unities with the values in each JList
  */
  protected void setUnities(){
    String str = null;
      for(int i = 0; i < _leftVector.size(); i++){
        str = (String)_leftVector.elementAt(i);
        _activities.setUnityField(DXToolsMethods.getToken(str,".",0),
                                  DXToolsMethods.getToken(str,".",1),
                                  DXToolsMethods.getToken(str,".",2),
                                  DXToolsMethods.getToken(str,".",3), 3, "true");
        _activities.setUnityField(DXToolsMethods.getToken(str,".",0),
                                  DXToolsMethods.getToken(str,".",1),
                                  DXToolsMethods.getToken(str,".",2),
                                  DXToolsMethods.getToken(str,".",3), 2, "true");
      }//end for
      for(int i = 0; i < _centerVector.size(); i++){
        str = (String)_centerVector.elementAt(i);
        _activities.setUnityField(DXToolsMethods.getToken(str,".",0),
                                  DXToolsMethods.getToken(str,".",1),
                                  DXToolsMethods.getToken(str,".",2),
                                  DXToolsMethods.getToken(str,".",3), 3, "false");
        _activities.setUnityField(DXToolsMethods.getToken(str,".",0),
                                  DXToolsMethods.getToken(str,".",1),
                                  DXToolsMethods.getToken(str,".",2),
                                  DXToolsMethods.getToken(str,".",3), 2, "true");
      }//end for
      for(int i = 0; i < _rightVector.size(); i++){

        str = (String)_rightVector.elementAt(i);
        _activities.setUnityField(DXToolsMethods.getToken(str,".",0),
                                  DXToolsMethods.getToken(str,".",1),
                                  DXToolsMethods.getToken(str,".",2),
                                  DXToolsMethods.getToken(str,".",3), 3, "false");
        _activities.setUnityField(DXToolsMethods.getToken(str,".",0),
                                  DXToolsMethods.getToken(str,".",1),
                                  DXToolsMethods.getToken(str,".",2),
                                  DXToolsMethods.getToken(str,".",3), 2, "false");
      }//end for
  }

}//end class