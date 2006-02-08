/**
 *
 * Title: SelectInstructors $Revision: 1.10 $  $Date: 2006-02-08 22:11:34 $
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
 * @version $Revision: 1.10 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: SelectInstructors is a class used to
 *
 */package dInterface.dAffectation;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

//import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DXTools;
import dInterface.dUtil.TwoButtonsPanel;

public class SelectInstructors
		extends JDialog
		implements ActionListener {

  /**
   * the lists containing the instructors ID
   */
  /**
	* the vectors containing the instructors ID
	*/

  //private JButton _toRight, _toLeft;
  private JLabel _lVisible, _lNoVisible;
  private JList _rightList, _leftList;
  private JPanel _centerPanel, _arrowsPanel;
  private ButtonsPanel _validatePanel;
  //private DApplication _dApplic;
  private EditActivityDlg _ead;
  private Vector _rightVec, _leftVec;
  //private JList _leftVec;

  /**
   * Dafault constructor
   * @param dApplic The application object (for extracting the JFrame)
   */

  public SelectInstructors(DApplication dApplic,
  							EditActivityDlg ead,
  							Vector leftVec,
  							Vector rightVec) {
    super(dApplic.getJFrame(), DConst.LISTS_INSTRUCTOR_TD, true); //true gives a modal Dlg
    //_dApplic = dApplic;
    _ead = ead;
    _leftVec = leftVec;
    //_leftVec.
    _rightVec = rightVec;
    for (int i=0; i< _leftVec.size(); i++)
      _rightVec.remove(_leftVec.get(i).toString());
    initialize();
    setLocationRelativeTo(dApplic.getJFrame());
    setVisible(true);
  }


  /**
   * Initialize the dialog
   */
  protected void initialize(){

    _rightList = new JList(_rightVec);
    _rightList.addMouseListener(mouseListenerLists);
    JPanel listPanel = DXTools.listPanel(_rightList, 150, 300);
    _lNoVisible = new JLabel(_rightVec.size() + " " + DConst.NOT_INCLUDED);
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.add(_lNoVisible, BorderLayout.NORTH);
    rightPanel.add(listPanel, BorderLayout.SOUTH);
    //left panel
    //_leftVec = new Vector(1);//_activities.getIDsByField(3, "true");
    _leftList = new JList(_leftVec);
    _leftList.addMouseListener(mouseListenerLists);
    _lVisible = new JLabel(_leftVec.size() + " " + DConst.INCLUDED);
    listPanel = DXTools.listPanel(_leftList, 150, 300);
    JPanel leftPanel = new JPanel();
    leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(_lVisible, BorderLayout.NORTH);
    leftPanel.add(listPanel, BorderLayout.SOUTH);
    //arrows panel  private
    String [] _arrows = {DConst.TO_RIGHT, DConst.TO_LEFT};
    _arrowsPanel = DXTools.arrowsPanel(this, _arrows, true); 
    //placing the panels and buttons into the _listsPanel
    _centerPanel = new JPanel();
    _centerPanel.add(leftPanel, BorderLayout.EAST);
    _centerPanel.add(_arrowsPanel, BorderLayout.CENTER);
    _centerPanel.add(rightPanel, BorderLayout.WEST);
    //_applyPanel
    String [] a ={DConst.BUT_VALIDATE, DConst.BUT_CLOSE};
	_validatePanel = new TwoButtonsPanel(this, a);
    //Setting the button VALIDATE disable
	_validatePanel.setFirstDisable();
    //placing the elements into the JDialog
    setSize(400, 390);
    setResizable(false);
    getContentPane().add(_centerPanel, BorderLayout.CENTER);
    getContentPane().add(_validatePanel, BorderLayout.SOUTH);
  }//end method

  /**
   * Define the mouse adapter and actions for the JLists
   * the actions are select and deselect items in the JLists
   */
  private MouseListener mouseListenerLists = new MouseAdapter(){
    public void mouseClicked(MouseEvent e) {
      if (((JList)e.getSource()).getModel().getSize() == 0){
		return;
      }
      if (e.getSource().equals(_leftList)) {
		_rightList.clearSelection();
      } else {
		_leftList.clearSelection();
      }
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){


  /**
   *
   * @param e an event
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //If buttons CANCEL
    if (command.equals(DConst.BUT_CLOSE))
        dispose();
    //If button VALIDATE
    if (command.equals(DConst.BUT_VALIDATE)){
      _ead.updateInstructorList(_leftVec);
	  _validatePanel.setFirstDisable();
	  dispose();

    }
    if (command.equals(DConst.TO_RIGHT) || command.equals(DConst.TO_LEFT)){
      if (command.equals(DConst.TO_LEFT)){
        DXTools.listTransfers(_rightList, _leftList, _rightVec, _leftVec, 1);
      } else {
		DXTools.listTransfers(_leftList, _rightList, _leftVec, _rightVec, 1);
      }
      _lNoVisible.setText(_rightVec.size() + " " + DConst.NOT_INCLUDED);
      _lVisible.setText(_leftVec.size() + " " + DConst.INCLUDED);
	  _validatePanel.setFirstEnable();
    }//end if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1]))
  }//end method

  /**
   * Sets the field "Visible" of the activities, according with their position
   * in the JLists. If an activity is in the _rightList, Visible = false.
   */
/*  private void setActivitesVisibility(){

  }*/

}// end class
