package dInterface;
/**
 *
 * Title: DefFileToImportDlg $Revision: 1.1 $  $Date: 2003-05-12 15:33:28 $
 * Description: DefFileToImportDlg is created by DefFileToImportCmd
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
 * @version $Revision: 1.1 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import dInterface.DApplication;

import dResources.DConst;
import dResources.DFileFilter;


/**
 *
 * DefFileToImportDlg is a class used to show a dialog
 *
 */

public class DefFilesToImportDlg extends JDialog
                                 implements ActionListener{

  final static int CANCEL = 0;
  final static int OK = 1;



  private JTextField _tfActivities;
  private JButton _butActivities;
  private JTextField _tfStudents;
  private JButton _butStudents;
  private JTextField _tfInstructors;
  private JButton _butInstructors;
  private JTextField _tfRooms;
  private JButton _butRooms;

  private String path;
  private int state;

  /**
    * the constructor will displays the dialog
    *
    * @param DApplication dApplic gives the parent of the dialog
    * @param str       the title of the window dialog
    * @since           JDK1.3
    */
   DApplication _dApplic;
   public DefFilesToImportDlg(DApplication dApplic, String str) {
     super(dApplic.getJFrame(),str);
     _dApplic = dApplic;
     initDlg();
   } // end constructor


   public String showDlg(){
     pack();
     setLocationRelativeTo(_dApplic.getJFrame());
     setVisible(true);
     return new String();
  }

  private void initDlg() {

    // center Panel
    JPanel centerPanel = new JPanel(new GridLayout(4, 0));

    // panel Activities
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), DConst.DEF_F_D1) );
    _tfActivities = new JTextField();
    _tfActivities.setPreferredSize(new Dimension(330,22));
    JButton butAct = new JButton(DConst.BUT_BROWSE);
    butAct.addActionListener(this);
    panel.add(_tfActivities);
    panel.add(butAct);    centerPanel.add(panel, null);

    // panel Students
    panel = new JPanel();
    panel.setBorder( BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "MES01" ) );
    _tfStudents = new JTextField();
    _tfStudents.setPreferredSize(new Dimension(330,22));
    JButton butStud = new JButton(DConst.BUT_BROWSE);
    butStud.addActionListener( this );
    panel.add(_tfStudents);
    panel.add(butStud);
    centerPanel.add(panel, null);

    // panel Teachers
    panel = new JPanel();
    panel.setBorder( BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "MES02" ) );
    _tfInstructors = new JTextField();
    _tfInstructors.setPreferredSize(new Dimension(330,22));
    JButton butTea = new JButton(DConst.BUT_BROWSE);
    butTea.addActionListener( this );
    panel.add(_tfInstructors);
    panel.add(butTea);
    centerPanel.add(panel, null);

    // panel Rooms
    panel = new JPanel();
    panel.setBorder( BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "MES03" ) );
    _tfRooms = new JTextField();
    _tfRooms.setPreferredSize(new Dimension(330,22));
    JButton butRoom = new JButton(DConst.BUT_BROWSE);
    butRoom.addActionListener( this );
    panel.add(_tfRooms);
    panel.add(butRoom);
    centerPanel.add(panel, null);

    this.getContentPane().add(centerPanel, BorderLayout.CENTER);

    //bootom Panel
    JPanel butPanel = new JPanel();
    JButton butOk = new JButton(DConst.BUT_OK );
    butOk.addActionListener( this );
    butPanel.add(butOk, null);
    JButton butCancel = new JButton(DConst.BUT_CANCEL );
    butCancel.addActionListener( this );
    butPanel.add(butCancel, null);
    getContentPane().add(butPanel, BorderLayout.SOUTH);
  } // end initDlg


  /**
   * Responds to action events
   */
  public void actionPerformed(ActionEvent event) {
    String command = event.getActionCommand();
    if (command.equals(DConst.BUT_OK )) {
      if (_tfActivities.getText().length() == 0 ||
         _tfStudents.getText().length() == 0 ||
         _tfInstructors.getText().length() == 0 ||
         _tfRooms.getText().length() == 0)
        JOptionPane.showMessageDialog(this, "MES04", null,
              JOptionPane.WARNING_MESSAGE);
      else {
        state = OK;
        dispose();
      } //end (_tfActivities.getText().length() == 0 || ...

    } else if (command.equals(DConst.BUT_CANCEL )) {
      state = CANCEL;
      dispose();
    } else if (command.equals(DConst.BUT_BROWSE )) { // Browse

    JFileChooser fc = new JFileChooser(path);

    fc.setMultiSelectionEnabled( false );
    fc.setFileFilter( new DFileFilter( new String[] {"sig", "txt"},
                    "Data Files (*.sig)" ) );
    // Display the file chooser in a dialog

    String message;
    if (event.getSource() == _butActivities )
      message = "MES00";
    else if (event.getSource() == _butStudents )
      message = "MES01";
    else if (event.getSource() == _butInstructors )
      message = "MES02";
    else
      message = "MES03";

    int returnVal = fc.showDialog(this, message);

    // If the file chooser exited sucessfully,
    // and a file was selected, continue
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      if ( event.getSource() == _butActivities )
        _tfActivities.setText(fc.getSelectedFile().getAbsolutePath());
      else if (event.getSource() == _butStudents )
        _tfStudents.setText(fc.getSelectedFile().getAbsolutePath());
      else if (event.getSource() == _butInstructors )
        _tfInstructors.setText(fc.getSelectedFile().getAbsolutePath());
      else if (event.getSource() == _butRooms )
        _tfRooms.setText(fc.getSelectedFile().getAbsolutePath());

      path = fc.getSelectedFile().getAbsolutePath();
    }
  }
  }
} /* end class DefFileToImportDlg */







